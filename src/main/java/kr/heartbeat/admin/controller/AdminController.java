package kr.heartbeat.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.heartbeat.admin.service.AdminServiceImpl;
import kr.heartbeat.membership.service.MembershipService;
import kr.heartbeat.notice.service.NoticeService;
import kr.heartbeat.service.UserServiceImpl;
import kr.heartbeat.vo.AgeGroupDTO;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.NoticeCommentVO;
import kr.heartbeat.vo.NoticeVO;
import kr.heartbeat.vo.PageDTO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.RoleVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.likeVO;

@Controller
@Transactional
@RequestMapping("/admin/*")
public class AdminController {
	
	@Autowired
	private AdminServiceImpl service;
	
	@Autowired
	private NoticeService noticeService;
	
	@Inject
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private MembershipService membershipService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	//summary
	@GetMapping("/summary")
	public String getAdminSummary(Model model) throws Exception {
	    String today = LocalDate.now().toString();
	    List<likeVO> lvo = service.getMostLikePost();

	    // 오늘 가입자 수
	    int todayUserCount = service.count_a(today);
	    model.addAttribute("count_a", todayUserCount);
	    
	    // 오늘 탈퇴한 유저 수
 		int todayDeleteUser = service.todayDeleteUser(today);
 		model.addAttribute("todayDeleteUser", todayDeleteUser);

	    // 총 구독자 수
	    int totalSubscribers = service.count_b();
	    model.addAttribute("count_b", totalSubscribers);

	    // 가장 많은 구독자를 보유한 아티스트
	    Map<String, Object> topArtist = service.count_c();
	    model.addAttribute("count_c", topArtist);
	    
	    //summary 그래프
	    int total = service.levelTotalCnt();
	    int level0Cnt = service.levelCnt(0);
	    int level1Cnt = service.levelCnt(1);
	    int level2Cnt = service.levelCnt(2);
	    
	    int level1Price = level1Cnt * 3900;
	    int level2Price = level2Cnt * 6900;
	    int totalPrice = level1Price+ level2Price;
	    int targetAmount = 1000000;
	    
	    // 회원 연령대별 분류
 		List<AgeGroupDTO> ageGroup = service.countAgeGroup();
	    
	    	// 뷰로 데이터를 전달
	    model.addAttribute("total", total);
	    model.addAttribute("level0Cnt", level0Cnt);
	    model.addAttribute("level1Cnt", level1Cnt);
	    model.addAttribute("level2Cnt", level2Cnt);
	    
	    model.addAttribute("level1Price", level1Price);
	    model.addAttribute("level2Price", level2Price);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("targetAmount", targetAmount);
	    model.addAttribute("ageGroup", ageGroup);
	    
	    
	    model.addAttribute("lvo", lvo);
	    
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
	    model.addAttribute("searchType", searchType);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("role_id", roleId);
	    
	}
	
	//member 삭제
	@GetMapping("/member/delete")
	public String memberdelete(@RequestParam("email") String email,RedirectAttributes rttr) throws Exception {
	    service.memberdelete(email);
	    rttr.addFlashAttribute("message", "회원탈퇴가 완료되었습니다.");
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
		
	// post 리스트 구현
	@RequestMapping("/post")
	public String getPostList(
		Model model,
	    @RequestParam(value = "num") int num,
	    @RequestParam(value = "searchType", required = false, defaultValue = "name") String searchType,
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "role_id", required = false) String roleId // role_id 값 처리
	) throws Exception {
	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getPostCount(searchType, keyword, roleId)); // role_id 추가
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<PostVO> poList = service.getPostList(page.getDisplayPost(), page.getPostNum(), searchType, keyword, roleId);
	    model.addAttribute("poList", poList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);
	    model.addAttribute("searchType", searchType);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("role_id", roleId);
	    
	    return "/admin/post";
	}
	
	//post 삭제 구현
	@GetMapping("/post/delete")
	public String delete(@RequestParam("post_id") int post_id,int num, String searchType,String keyword,String role_id) throws Exception {
		service.podelete(post_id);
		return "redirect:/admin/post?num="+num+"&searchType="+searchType+"&keyword="+keyword+"&role_id="+role_id;
		
	}
	
	//comment 리스트 구현
	@RequestMapping("/comment")
	public String getCommentList(
		Model model,
	    @RequestParam(value = "num", required = false, defaultValue = "1") int num,
	    @RequestParam(value = "searchType", required = false, defaultValue = "name") String searchType,
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "role_id", required = false) String roleId // role_id 값 처리
	) throws Exception {

	    // 검색 및 페이징 처리 로직
	    PageDTO page = new PageDTO();
	    page.setNum(num);
	    page.setCount(service.getCommentCount(searchType, keyword, roleId)); // role_id 추가
	    page.setSearchType(searchType);
	    page.setKeyword(keyword);

	    List<CommentVO> coList = service.getCommentList(page.getDisplayPost(), page.getPostNum(), searchType, keyword, roleId);
	    model.addAttribute("coList", coList);
	    model.addAttribute("page", page);
	    model.addAttribute("select", num);
	    model.addAttribute("searchType", searchType);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("role_id", roleId);
	    
	    return "/admin/comment";
	}
	
	@GetMapping("/comment/delete")
	public String commentdelete(@RequestParam("comment_id") int comment_id,int num, String searchType,String keyword,String role_id) throws Exception {
		service.codelete(comment_id);

		return "redirect:/admin/comment?num="+num+"&searchType="+searchType+"&keyword="+keyword+"&role_id="+role_id;
	}

	//edit
	@GetMapping("/edit")
	public void modify(@RequestParam("email") String email,String num,String searchType,String keyword,String role_id,Model model) throws Exception {
		UserVO uvo = service.getUserOne(email);
		model.addAttribute("email", email);
		model.addAttribute("modify", uvo);
		model.addAttribute("num", num);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		model.addAttribute("role_id", role_id);
	}
	
	//edit 데이터처리
	@PostMapping("/edit")
	public String update(UserVO uvo,String num,String searchType,String keyword,String role_id, RedirectAttributes redirectAttributes) throws Exception {
		System.out.println(uvo);
		if(uvo.getLevel() == 0) {
			membershipService.deleteLevel(uvo.getEmail());
			membershipService.updateLevel(uvo.getEmail(), 0, 0);
		} else if(uvo.getLevel() == 1){
			int level = membershipService.checkLevel(uvo.getEmail());
			System.out.println(level);
			if (level == 0) {
				System.out.println("레벨 0일때");
				membershipService.insertSubscription(uvo.getEmail(), 0, 1);
				membershipService.updateLevel(uvo.getEmail(), 0, 1);			

			} else {
				System.out.println("레벨 1이나 2일떄");
				membershipService.updateLevel(uvo.getEmail(), 0, 1);			
				membershipService.deleteAndUpdateLevel1(uvo.getEmail());
			}
		} else if (uvo.getLevel() == 2) { 
			int checkArtistID = membershipService.checkArtistID(uvo.getEmail());
	//		if (checkArtistID  0) {
				membershipService.deleteLevel(uvo.getEmail());
//			} 				
			membershipService.updateLevel(uvo.getEmail(), uvo.getArtist_id(),uvo.getLevel()); //맴버십 레벨 업데이트
			membershipService.insertSubscription(uvo.getEmail(), uvo.getArtist_id(),uvo.getLevel()); // subscription에 insert
		}
	    service.update(uvo);
		redirectAttributes.addFlashAttribute("success", "success");
		redirectAttributes.addFlashAttribute("num", num);
		redirectAttributes.addFlashAttribute("searchType", searchType);
		redirectAttributes.addFlashAttribute("keyword", keyword);
		redirectAttributes.addFlashAttribute("role_id", role_id);
		return "redirect:/admin/member?num="+num+"&searchType="+searchType+"&keyword="+keyword+"&role_id="+role_id;
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

    @SuppressWarnings("unused")
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
    
    
    //==================== notice ====================
    
    // 공지 페이지 들어가면서 게시물 가져오기
 	@RequestMapping(value="/notice", method = RequestMethod.GET) 
 	public String notice(int num, String searchType, String keyword, Model model) throws Exception{		
 		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(noticeService.getPostCount(searchType,keyword)); // 뉴진스 팬 게시물 개수 
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		
		List<NoticeVO> adminPost = noticeService.getAdminNotice(); 
		List<NoticeVO> userPost = noticeService.getUserNotice(page.getDisplayPost(), page.getPostNum(),searchType,keyword);
 		
 		
 		model.addAttribute("adminPost", adminPost);		
 		model.addAttribute("userPost", userPost);		
 		model.addAttribute("page", page);		
 		model.addAttribute("select", num);	
 		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);	
 		
 		return "/admin/notice";
 		
 	}
 	
 	
 	@GetMapping("/postNotice")
 	public String postNotice() {
 		return "/admin/noticePost";
 	}
 	
 	@PostMapping("/noticeWrite") // 게시물 작성
 	public String postNotice(NoticeVO noticeVO) throws Exception {
 		noticeService.postNotice(noticeVO);

 		
 		return "redirect:/admin/notice?num=1";
 	}
 	
 	@RequestMapping("/getPostOne") // 게시물 상세보기
 	public String getPostOne(@RequestParam("notice_id")int notice_id,int num,String searchType, String keyword, Model model)throws Exception {
 		NoticeVO noticeVO = noticeService.getPostOne(notice_id);
 		List<NoticeCommentVO> commentVO = noticeService.getComment(notice_id);
 		
 		
 		model.addAttribute("num", num);
 		model.addAttribute("commentVO", commentVO);
 		model.addAttribute("noticeVO", noticeVO);
 		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);

 		return "/admin/noticeShow";
 	}
 	
 	@PostMapping("/noticeModifyShow") // 게시물 수정 페이지 이동
 	public String noticeModifyShow(int notice_id,int num,String searchType, String keyword,Model model) throws Exception{
 		NoticeVO noticeVO = noticeService.getPostOne(notice_id);
 		
 		model.addAttribute("num", num);
 		model.addAttribute("noticeVO", noticeVO);
 		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);
 		return "/admin/noticeModify";
 	}
 	
 	@PostMapping("/noticeModify") // 게시물 수정
 	public String noticeModify(@RequestParam("num")int num,NoticeVO noticeVO,String searchType, String keyword,Model model) throws Exception{
 		
 		noticeService.noticeModify(noticeVO);
 		NoticeVO dbnoticeVO = noticeService.getPostOne(noticeVO.getNotice_id());
 		List<NoticeCommentVO> commentVO = noticeService.getComment(dbnoticeVO.getNotice_id());
 		model.addAttribute("num", num);
 		model.addAttribute("noticeVO", dbnoticeVO);
 		model.addAttribute("commentVO", commentVO);
 		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);
 		return "/admin/noticeShow";
 	}
 	
 	@RequestMapping("/noticeDelete") // 게시물 삭제
 	public String noticeDelete(@RequestParam("notice_id")int notice_id) throws Exception{
 		noticeService.noticeDelete(notice_id);

 		return "redirect:/admin/notice?num=1";
 	}
 	
 	@PostMapping("/notice/commentWrite") // 댓글 작성
 	public String commentWrite(@RequestParam("num")int num,NoticeCommentVO noticeCommentVO,Model model) throws Exception{
 		noticeService.commentWrite(noticeCommentVO);
 		NoticeVO noticeVO = noticeService.getPostOne(noticeCommentVO.getNotice_id());
 		List<NoticeCommentVO> commentVO = noticeService.getComment(noticeCommentVO.getNotice_id());
 		
 		model.addAttribute("num", num);
 		model.addAttribute("commentVO", commentVO);
 		model.addAttribute("noticeVO", noticeVO);
 		return "redirect:/admin/getPostOne?notice_id="+noticeCommentVO.getNotice_id()+"&num=1";
 	}
 	
 	@PostMapping("/commentUpdate") // 댓글 수정
 	@ResponseBody
 	public String commentUpdate(NoticeCommentVO noticeCommentVO) throws Exception{
 		noticeService.updateComment(noticeCommentVO);
 		return "댓글 수정 성공";
 	}
 	
 	@PostMapping("/commentDelete") // 댓글삭제
 	@ResponseBody
 	public String commentDelete(int notice_comment_id)throws Exception {
 		noticeService.commentDelete(notice_comment_id);
 		return "댓글 삭제 성공";
 	}
 	
 	// 마이페이지 - 내 문의 확인
	@RequestMapping("/mynotice") 
	public String mynotice(int num, String searchType, String keyword,Model model,HttpServletRequest request,HttpSession session) throws Exception {
		UserVO uvo = (UserVO) session.getAttribute("UserVO");
		String email = uvo.getEmail();
		
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(userServiceImpl.getMyNoticeCount(searchType,keyword,email)); // 내 게시물 개수
		page.setSearchType(searchType);
		page.setKeyword(keyword);
		
		List<NoticeVO> userNoticeList = userServiceImpl.getUserNotice(page.getDisplayPost(), page.getPostNum(),searchType,keyword,email);

		model.addAttribute("userNoticeList", userNoticeList);
		model.addAttribute("page", page);		
		model.addAttribute("select", num);
		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);
		
		return "/admin/mynotice";
	}
	
	// 내 문의 상세보기
	@RequestMapping("/getMyPostOne") // 게시물 상세보기
	public String getMyPostOne(@RequestParam("notice_id")int notice_id,int num,String searchType, String keyword, Model model)throws Exception {
		NoticeVO noticeVO = noticeService.getPostOne(notice_id);
		List<NoticeCommentVO> commentVO = noticeService.getComment(notice_id);
		
		
		model.addAttribute("num", num);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("noticeVO", noticeVO);
		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);

		return "/admin/myNoticeShow";
	}
	
	@PostMapping("/myNoticeModifyShow") // 게시물 수정 페이지 이동
	public String adminnoticeModifyShow(int notice_id,int num,String searchType, String keyword,Model model) throws Exception{
		NoticeVO noticeVO = noticeService.getPostOne(notice_id);
		
		model.addAttribute("num", num);
		model.addAttribute("noticeVO", noticeVO);
		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);
		return "/admin/myNoticeModify";
	}
	
	@PostMapping("/myNoticeModify") // 게시물 수정
	public String adminnoticeModify(@RequestParam("num")int num,NoticeVO noticeVO,String searchType, String keyword,Model model) throws Exception{
		
		noticeService.noticeModify(noticeVO);
		NoticeVO dbnoticeVO = noticeService.getPostOne(noticeVO.getNotice_id());
		List<NoticeCommentVO> commentVO = noticeService.getComment(dbnoticeVO.getNotice_id());
		model.addAttribute("num", num);
		model.addAttribute("noticeVO", dbnoticeVO);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("searchType", searchType);	
 		model.addAttribute("keyword", keyword);
		return "/admin/myNoticeShow";
	}
	
	@RequestMapping("/myNoticeDelete") // 게시물 삭제
	public String adminnoticeDelete(@RequestParam("notice_id")int notice_id) throws Exception{
		noticeService.noticeDelete(notice_id);

		return "redirect:/admin/mynotice?num=1";
	}
	
	@PostMapping("/myCommentWrite") // 댓글 작성
	public String admincommentWrite(@RequestParam("num")int num,NoticeCommentVO noticeCommentVO,Model model) throws Exception{
		noticeService.commentWrite(noticeCommentVO);
		NoticeVO noticeVO = noticeService.getPostOne(noticeCommentVO.getNotice_id());
		List<NoticeCommentVO> commentVO = noticeService.getComment(noticeCommentVO.getNotice_id());
		
		model.addAttribute("num", num);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("noticeVO", noticeVO);
		return "redirect:/admin/getMyPostOne?notice_id="+noticeCommentVO.getNotice_id()+"&num=1";
	}
	
	
	// 내 공지 삭제 
	@PostMapping("/deleteNotice")
	public String deleteNotice(@RequestParam("notice_id") String notice_id) throws Exception {
		// postIds는 콤마로 구분된 문자열이므로, 이를 분리하여 배열로 변환
		String[] noticeIdArray = notice_id.split(",");
		
		// 각 post_id에 대해 삭제 처리
		for (String noticeId : noticeIdArray) {
			userServiceImpl.deleteMyNotice(Integer.parseInt(noticeId));  // 삭제 서비스 호출
		}
		
		return "redirect:/admin/mynotice?num=1";
	}
 	
}
