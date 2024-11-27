package kr.heartbeat.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.heartbeat.admin.service.AdminServiceImpl;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	private AdminServiceImpl service;
	
	//summary
	@GetMapping("/summary")
	public String getAdminSummary(Model model) throws Exception {
	    String today = LocalDate.now().toString();
	    
	    // 오늘 가입자 수
	    int todayUserCount = service.count_a(today);
	    model.addAttribute("count_a", todayUserCount);
	    System.out.println("가입자 수 ========================= "+todayUserCount);
	    System.out.println(today);
	    
	    // 총 구독자 수
	    int totalSubscribers = service.count_b();
	    model.addAttribute("count_b", totalSubscribers);

	    // 가장 많은 구독자를 보유한 아티스트
	    Map<String, Object> topArtist = service.count_c();
	    model.addAttribute("count_c", topArtist);
	    System.out.println("Top Artist: " + topArtist);
	    
	    //summary 그래프
	    int total = service.levelTotalCnt();
	    int level0Cnt = service.levelCnt(0);
	    int level1Cnt = service.levelCnt(1);
	    int level2Cnt = service.levelCnt(2);
	    
	    	// 뷰로 데이터를 전달
	    model.addAttribute("total", total);
	    model.addAttribute("level0Cnt", level0Cnt);
	    model.addAttribute("level1Cnt", level1Cnt);
	    model.addAttribute("level2Cnt", level2Cnt);
	    
	    System.out.println("총 회원 수: " + total);
	    System.out.println("레벨 0 회원 수: " + level0Cnt);
	    System.out.println("레벨 1 회원 수: " + level1Cnt);
	    System.out.println("레벨 2 회원 수: " + level2Cnt);
	    //58464
	    return "/admin/summary";
	}
	
	//member 리스트 구현
	@GetMapping("/member")
	public void getUserList(Model model) throws Exception {
		List<UserVO> urList = service.getUserList();
		model.addAttribute("urList", urList);
	}
	
	//post 리스트 구현
	@RequestMapping("/post")
	public void getPostList(Model model) throws Exception {
		List<PostVO> poList = service.getPostList();
		model.addAttribute("poList", poList);
		System.out.println(poList);
	}
	
	//post 삭제 구현
	@GetMapping("/post/delete")
	public String delete(@RequestParam("post_id") String post_id) throws Exception {
		service.podelete(post_id);
		return "redirect:/admin/post";
	}
	
	//comment 리스트 구현
	@RequestMapping("/comment")
	public void getcommentList(Model model) throws Exception {
		List<CommentVO> coList = service.getCommentList();
		model.addAttribute("coList", coList);
		System.out.println(coList);
	}

	//edit
	@GetMapping("/edit")
	public void modify(@RequestParam("email") String email, Model model) throws Exception {
		UserVO uvo = service.getUserOne(email);
		model.addAttribute("email", email);
		System.out.println("view==========================="+uvo);
		model.addAttribute("modify", uvo);
	}
	
	//edit 데이터처리
	@PostMapping("/edit")
	public String update(UserVO uvo) throws Exception {
	    // 암호화 없이 바로 전달받은 비밀번호를 사용
		System.out.println("데이터처리==========================="+uvo);
	    service.update(uvo);
	    
	    return "redirect:/admin/edit?email=" + uvo.getEmail();
	}

	@GetMapping("/adminjoin")
	public String adminJoinPage(Model model) {
	    List<RoleVO> role = service.getRole();
	    model.addAttribute("role", role);
	    
	    return "/admin/adminjoin";
	}
	
	// 계정 생성
	@PostMapping("/adminjoin")
	public String adminInsertUser(@RequestParam("email") String email,
	                              @RequestParam("pwd") String pwd,
	                              @RequestParam("pwdCheck") String pwdCheck,
	                              @RequestParam("name") String name,
	                              @RequestParam("phone") String phone,
	                              @RequestParam("nickname") String nickname,
	                              @RequestParam("birth") String birth,
	                              @RequestParam("role_id") int role_id,
	                              RedirectAttributes redirectAttributes) {
	    System.out.println("========== Controller member(admin) email: " + email);

	    // 서버 측 비밀번호 확인 검증
	    if (!pwd.equals(pwdCheck)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
	        return "redirect:/admin/adminjoin";
	    }

	    // UserVO 생성 및 데이터 설정
	    UserVO userVO = new UserVO();
	    userVO.setEmail(email);
	    userVO.setPwd(pwd);
	    userVO.setName(name);
	    userVO.setPhone(phone);
	    userVO.setBirth(birth);
	    userVO.setNickname(nickname);

	    // 서버 측 유효성 검증
	    if (!isValidUser(userVO)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "유효성 검증에 실패했습니다. 입력 데이터를 확인해주세요.");
	        return "redirect:/admin/adminjoin";
	    }

	    // 서비스 호출
	    int result = service.insertUser(userVO, role_id);

	    // 결과에 따른 리다이렉트 처리
	    if (result > 0) {
	        return "redirect:/admin/member"; // 회원가입 성공 시 이동
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "계정 생성에 실패했습니다. 다시 시도해주세요.");
	        return "redirect:/admin/adminjoin"; // 회원가입 실패 시 이동
	    }
	}

	// 이메일 중복 확인
	@PostMapping("/adminjoin/checkEmail")
	@ResponseBody
	public String idCheck(@RequestParam("email") String email) throws Exception {
	    // 이메일 중복 여부 확인
	    boolean isAvailable = service.idCheck(email) == null; // null이면 사용 가능
	    return isAvailable ? "success" : "fail";
	}

	// 전화번호 중복 확인
	@PostMapping("/adminjoin/checkPhone")
	@ResponseBody
	public String phoneCheck(@RequestParam("phone") String phone) throws Exception {
	    boolean isAvailable = service.phoneCheck(phone) == null; // 중복 여부 확인
	    return isAvailable ? "success" : "fail";
	}

	// 닉네임 중복 확인
	@PostMapping("/adminjoin/checkNickname")
	@ResponseBody
	public String nicknameCheck(@RequestParam("nickname") String nickname) throws Exception {
	    boolean isAvailable = service.nicknameCheck(nickname) == null; // 중복 여부 확인
	    return isAvailable ? "success" : "fail";
	}

    private boolean isValidUser(UserVO userVO) {
        if (userVO.getEmail() == null || userVO.getEmail().isEmpty()) {
            return false;
        }
        if (userVO.getPwd() == null || userVO.getPwd().isEmpty()) {
            return false;
        }
        if (userVO.getName() == null || userVO.getName().isEmpty()) {
            return false;
        }
        if (userVO.getPhone() == null || userVO.getPhone().isEmpty()) {
            return false;
        }
        if (userVO.getNickname() == null || userVO.getNickname().isEmpty()) {
            return false;
        }

        // 중복 확인 로직 추가
        if (service.idCheck(userVO.getEmail()) != null) {
            return false; // 이메일 중복 시 false 반환
        }
        if (service.phoneCheck(userVO.getPhone()) != null) {
            return false; // 전화번호 중복 시 false 반환
        }
        if (service.nicknameCheck(userVO.getNickname()) != null) {
            return false; // 닉네임 중복 시 false 반환
        }

        return true; // 모든 조건을 통과하면 true 반환
    }
}
