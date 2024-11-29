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
	    System.out.println("구독자 수 ========================= "+totalSubscribers);
	    
	    // 가장 많은 구독자를 보유한 아티스트 
	    int topartist = service.count_c();
	    model.addAttribute("count_c", topartist);
	    System.out.println("아티스트 아이디 수 ========================= "+topartist);
	    
	    Map<Integer, Integer> monthCounts = service.countByMonth();
        model.addAttribute("monthCounts", monthCounts);
        int marchCount = monthCounts.get(11);
        
        System.out.println("SADSADSA ========================= "+marchCount);
        System.out.println("SADSADSA ========================= "+monthCounts.get("count"));
        return "admin/summary";
	}
	
	//member 리스트 구현
	@GetMapping("/member")
	public void getUserList(Model model) throws Exception {
		List<UserVO> urList = service.getUserList();
		model.addAttribute("urList", urList);
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
	    service.memberdelete(email); // 기존 memberdelete 재활용
	    return "redirect:/admin/staff";
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

	
}
