package kr.heartbeat.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.heartbeat.admin.service.AdminServiceImpl;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PageDTO;
import kr.heartbeat.vo.PageDTO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.RoleVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;

@Controller
@Transactional
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

	    return "/admin/summary";
	}

	
	//member 리스트 구현
	@RequestMapping("/member")
	public void getUserList(
	    Model model,
	    @RequestParam(value = "num", required = false, defaultValue = "1") int num,
	    @RequestParam(value = "searchType", required = false, defaultValue = "name") String searchType,
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "role_id", required = false) String roleId // role_id 값 처리
	) throws Exception {

	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getUserCount(searchType, keyword, roleId)); // role_id 추가
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<UserVO> urList = service.getUserList(page.getDisplayPost(), page.getPostNum(), searchType, keyword, roleId);
	    model.addAttribute("urList", urList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);

	    System.out.println("searchType: " + searchType);
	    System.out.println("keyword: " + keyword);
	    System.out.println("roleId: " + roleId);
	}
	
	//member 삭제
	@GetMapping("/member/delete")
	public String memberdelete(@RequestParam("email") String email) throws Exception {
	    service.memberdelete(email);
	    return "redirect:/admin/member";
	}
	
	//staff 리스트 구현
	@RequestMapping("/staff")
	public void getStaffList(
	        Model model,
	        @RequestParam(value = "num", required = false, defaultValue = "1") int num,
	        @RequestParam(value = "searchType", required = false, defaultValue = "name") String searchType,
	        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
	) throws Exception {
	    
	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getStaffCount(searchType, keyword));
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<UserVO> staffList = service.getStaffList(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
	    
	    model.addAttribute("staffList", staffList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);

	}

	//staff 삭제
	@GetMapping("/staff/delete")
	public String deleteStaff(@RequestParam("email") String email) throws Exception {
	    service.staffdelete(email); // 기존 memberdelete 재활용
	    return "redirect:/admin/staff";
	}
		
	//post 리스트 구현
	@RequestMapping("/post")
	public void getPostList(
	    Model model,
	    @RequestParam(value = "num", required = false, defaultValue = "1") int num,
	    @RequestParam(value = "searchType", required = false, defaultValue = "post_id") String searchType,
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
	) throws Exception {
		
	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getPostCount(searchType, keyword));
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<PostVO> poList = service.getPostList(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
	    model.addAttribute("poList", poList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);
	    
	    System.out.println(num);
	    System.out.println(page);
	}
	
	//post 삭제 구현
	@GetMapping("/post/delete")
	public String delete(@RequestParam("post_id") int post_id) throws Exception {
		service.podelete(post_id);
		return "redirect:/admin/post";
	}
	
	//comment 리스트 구현
	@RequestMapping("/comment")
	public void getCommentList(
	    Model model,
	    @RequestParam(value = "num", required = false, defaultValue = "1") int num,
	    @RequestParam(value = "searchType", required = false, defaultValue = "comment_id") String searchType,
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
	) throws Exception {
		System.out.println(keyword);
		System.out.println(searchType);
		
	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getCommentCount(searchType, keyword));
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<CommentVO> coList = service.getCommentList(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
	    model.addAttribute("coList", coList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);
	    System.out.println(coList);
	}
	
	@GetMapping("/comment/delete")
	public String commentdelete(@RequestParam("comment_id") int comment_id) throws Exception {
		service.codelete(comment_id);
		return "redirect:/admin/comment";
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
	    System.out.println("전달된 데이터: " + uvo); // 모든 필드 출력
	    service.update(uvo);
	    return "redirect:/admin/edit?email=" + uvo.getEmail();
	}

	@GetMapping("/adminjoin")
	public String adminJoinPage(Model model) {
	    List<RoleVO> role = service.getRole();
	    model.addAttribute("role", role);
	    
	    return "/admin/adminjoin";
	}
	
	//계정생성
	@PostMapping("/adminjoin")
	public String adminInsertUser(UserVO userVO,
	                              @RequestParam("role_id") int role_id,
	                              @RequestParam(value = "start_date", required = false) String start_date,
	                              @RequestParam(value = "end_date", required = false) String end_date,
	                              RedirectAttributes redirectAttributes) {
	    System.out.println("========== Controller member(admin) email: " + userVO.getEmail());
	    
	    //비밀번호 암호화
  		String pwd =userVO.getPwd();
  		String encodePwd = bCryptPasswordEncoder.encode(pwd);
  		System.out.println("========= Presentation member pwd : "+ encodePwd);
  		userVO.setPwd(encodePwd);
	    
	    
	    // SubscriptionVO 생성 (구독 정보가 있을 경우에만)
	    SubscriptionVO subscriptionVO = null;
	    if (userVO.getLevel() > 0 && start_date != null && end_date != null) {
	        subscriptionVO = new SubscriptionVO();
	        subscriptionVO.setEmail(userVO.getEmail());
	        subscriptionVO.setLevel(userVO.getLevel());
	        subscriptionVO.setArtist_id(userVO.getArtist_id()); // 기본값 0
	        subscriptionVO.setStart_date(start_date);
	        subscriptionVO.setEnd_date(end_date);
	    }

	    // 서비스 호출
	    int result = service.insertUser(userVO, role_id, subscriptionVO);

	 // 결과에 따른 리다이렉트 처리
	    if (result > 0) {
	        return "redirect:/admin/member"; // 회원가입 성공 시 이동
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "계정 생성에 실패했습니다.");
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
