package kr.heartbeat.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.heartbeat.admin.service.AdminServiceImpl;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PageDTO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.RoleVO;
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
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
	) throws Exception {
		
	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getUserCount(searchType, keyword));
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<UserVO> urList = service.getUserList(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
	    model.addAttribute("urList", urList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);
	    
	    System.out.println(searchType);
	    System.out.println("keyword======="+keyword);
	    System.out.println(urList);
	}
	
	//member 삭제
	@GetMapping("/member/delete")
	public String memberdelete(@RequestParam("email") String email) throws Exception {
	    service.memberdelete(email);
	    return "redirect:/admin/member";
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
	
	//insert
	
	//이메일 중복확인
	@PostMapping("/adminjoin/checkEmail")
	@ResponseBody
	public String idCheck(HttpServletRequest request) throws Exception {
		String email = request.getParameter("email");
		UserVO userVO = service.idCheck(email);
		String result = null;

		if (userVO != null) result = "success"; 	
		
		return result;
	}
	//전화번호 중복확인
	@PostMapping("/adminjoin/checkPhone")
	@ResponseBody
	public String phoneCheck(HttpServletRequest request) throws Exception {
		String phone = request.getParameter("phone");
		UserVO userVO = service.phoneCheck(phone);
		String result = null;
		
		if (userVO != null) result = "success"; 	
		
		return result;
	}
	//닉네임 중복확인
	@PostMapping("/adminjoin/checkNickname")
	@ResponseBody
	public String nicknameCheck(HttpServletRequest request) throws Exception {
		
		String nickname = request.getParameter("nickname");
		UserVO userVO = service.nicknameCheck(nickname);
		String result = null;
		
		if (userVO != null) result = "success"; 
		
		return result;
	}
	
	@GetMapping("/adminjoin")
	public String adminJoinPage(Model model) {
	    List<RoleVO> role = service.getRole();
	    model.addAttribute("role", role);
	    
	    return "/admin/adminjoin";
	}
	
	//계정생성
	@PostMapping("/adminjoin")
	public String adminInsertUser(UserVO userVO) {
        System.out.println("========== Controller member(admin) email(id): " + userVO.getEmail());
        
        int result = service.insertUser(userVO); // 서비스 호출
        if (result > 0) { // 성공
            return "redirect:/admin/member"; // 회원가입 성공 시 이동
        } else { // 실패
            return "redirect:/admin/adminjoin"; // 회원가입 실패 시 이동
        }
    }

	
}
