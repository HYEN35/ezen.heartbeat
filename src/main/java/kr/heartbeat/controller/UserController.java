package kr.heartbeat.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.heartbeat.membership.service.MembershipService;
import kr.heartbeat.notice.service.NoticeService;
import kr.heartbeat.service.UserServiceImpl;
import kr.heartbeat.vo.NoticeCommentVO;
import kr.heartbeat.vo.NoticeVO;
import kr.heartbeat.vo.PageDTO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

@Controller
@Transactional
public class UserController {

	@Inject
	private UserServiceImpl userServiceImpl;
	@Autowired
	private MembershipService membershipService;
	@Autowired
	private NoticeService noticeService;

	//이메일 중복확인
	@PostMapping("/join/emailcheck")
	@ResponseBody
	public String idCheck(HttpServletRequest request) throws Exception {
		String email = request.getParameter("email");
		UserVO userVO = userServiceImpl.idCheck(email);
		String result = null;

		if (userVO != null) result = "success";

		return result;
	}
	//전화번호 중복확인
	@PostMapping("/join/phonecheck")
	@ResponseBody
	public String phoneCheck(HttpServletRequest request) throws Exception {
		String phone = request.getParameter("phone");
		UserVO userVO = userServiceImpl.phoneCheck(phone);
		String result = null;

		if (userVO != null) result = "success";

		return result;
	}
	//닉네임 중복확인
	@PostMapping("/join/nicknamecheck")
	@ResponseBody
	public String nicknameCheck(HttpServletRequest request) throws Exception {

		String nickname = request.getParameter("nickname");
		UserVO userVO = userServiceImpl.nicknameCheck(nickname);
		String result = null;

		if (userVO != null) result = "success";

		return result;
	}

	//회원가입
	@PostMapping("/join")
	public String insertUser(UserVO userVO, RedirectAttributes rttr) {
		System.out.println("========== Presentaion member email(id) : "+userVO.getEmail());
		System.out.println("========== Presentaion member getBirth : "+userVO.getBirth());
		String email = userVO.getEmail();
		String url = null;
		int resultUser = userServiceImpl.insertUser(userVO);
		int reulstUserRole = userServiceImpl.insertUserRole(email); //회원가입 시 유저 역할 추가
		if(resultUser == 1 && reulstUserRole==1) { //회원가입 성공
			rttr.addFlashAttribute("message", "회원가입에 성공하셨습니다.");
			url ="redirect:/login";
		} else { //회원가입 실패
			rttr.addFlashAttribute("message", "회원가입에 실패하셨습니다.");
			url = "redirect:/join";
		}
		return url;
	}


	//로그인
	@PostMapping("/login")
	public String login(UserVO userVO, HttpSession session,UserroleVO userrolevo, RedirectAttributes rttr,Model model) throws Exception {
		UserVO dbuserVO = userServiceImpl.login(userVO);
		UserroleVO rolelevel = userServiceImpl.role(userrolevo);
		String email = userVO.getEmail();
		String url = null;
		Date date = new Date();
		// 원하는 형식으로 변환할 SimpleDateFormat 객체
		SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date 객체를 원하는 형식의 문자열로 포맷팅
		String formattedDate = desiredFormat.format(date);
		// 출력
		System.out.println(formattedDate);  // 예시: 2024-11-20

		if (dbuserVO != null) {
			// 패스워드가 일치하는 경우
			if (userVO.getPwd().equals(dbuserVO.getPwd())) {
				// 맴버십 종료 날짜 확인
				SubscriptionVO subscriptionVO = membershipService.checkEndDate(email);
				Date nowDate = desiredFormat.parse(formattedDate);
				if (subscriptionVO != null) {
					Date endDate = desiredFormat.parse(subscriptionVO.getEnd_date());
					long now = nowDate.getTime() - endDate.getTime();
					System.out.println(now);

					// 맴버십 종료 여부 확인
					if (now >= 0) {
						// 맴버십 기간 종료 시
						membershipService.deleteLevel(email);
						membershipService.updateLevel(email, 0, 0);
						dbuserVO = userServiceImpl.login(dbuserVO);  // 로그인 재실행
						session.setAttribute("UserVO", dbuserVO);  // session에 dbuserVO 저장
						model.addAttribute("alertMsg", "맴버십 이용 기간이 종료되었습니다.");
						return "heartbeat/membership";  // 맴버십 페이지로 이동
					} else {
						// 맴버십 기간이 유효한 경우
						session.setAttribute("UserVO", dbuserVO);  // session에 dbuserVO 저장
						rttr.addFlashAttribute("message", "로그인에 성공하셨습니다. Heartbeat에 오신걸 환영합니다.");
						url = "redirect:/chart";  // 차트 페이지로 이동
					}
				} else {
					// 구독 정보가 없는 경우
					session.setAttribute("UserVO", dbuserVO);  // session에 dbuserVO 저장
					rttr.addFlashAttribute("message", "로그인에 성공하셨습니다. Heartbeat에 오신걸 환영합니다.");
					url = "redirect:/chart";  // 차트 페이지로 이동
				}

				// 역할에 따른 페이지 분기 (관리자 및 일반 사용자)
				int roleId = rolelevel.getRole_id();
				if (roleId == 0) {
					// 관리자 페이지로 이동
					session.setAttribute("UserVO", dbuserVO);  // session에 dbuserVO 저장
					url = "redirect:/admin/summary";  // 관리자 대시보드로 이동
				} else {
					// 일반 사용자 페이지로 이동
					if (dbuserVO != null) {
						session.setAttribute("UserVO", dbuserVO);  // session에 dbuserVO 저장
						session.setAttribute("level", dbuserVO.getLevel());  // 사용자 레벨 설정
						rttr.addFlashAttribute("message", "로그인에 성공하셨습니다. Heartbeat에 오신걸 환영합니다.");
						url = "redirect:/chart";  // 일반 사용자 차트 페이지로 이동
					} else {
						url = "redirect:/login";  // 로그인 페이지로 이동
					}
				}
			} else {
				// 패스워드 불일치
				session.setAttribute("UserVO", null);
				rttr.addFlashAttribute("pwd", false);
				url = "redirect:/login";  // 로그인 페이지로 이동
			}
		} else {
			// dbuserVO가 null인 경우
			session.setAttribute("UserVO", null);
			rttr.addFlashAttribute("email", false);
			url = "redirect:/login";  // 로그인 페이지로 이동
		}


		return url;
	}

	//아이디 찾기
	@PostMapping("/login/findId")
	@ResponseBody //@ResponseBody를 사용하면 model 객체를 쓸 수 없다.
	public HashMap<String,Object> findId(UserVO userVO) {
		// HashMap을 사용할 때 @ResponseBody로 반환되는 객체를 JSON으로 변환하려면 jackson-databind를 pom.xml에 의존성 주입을 해야 한다.
			
			UserVO uvo = userServiceImpl.findId(userVO);
			HashMap<String, Object> response = new HashMap<String, Object>();
			
			if(uvo != null) {
				response.put("result", "success");
				response.put("email", uvo.getEmail());
			} 
			response.put("name", userVO.getName());

			return response;
		}
		
		//비밀번호 찾기 - 메일 전송 버전
		@PostMapping("/login/searchPwd")
		public String searchPwd(UserVO userVO, RedirectAttributes redirectAttributes) {
			System.out.println("(============ : " +userVO.getEmail());
			
			int result = userServiceImpl.searchPwd(userVO);
			if(result == 1) {
				 redirectAttributes.addFlashAttribute("message", "새 비밀번호가 이메일로 전송되었습니다. 로그인하시고 비밀번호를 바꿔주세요.");
				 return "redirect:/login";
			}
			redirectAttributes.addFlashAttribute("message", "비밀번호 찾기 실패했습니다. 다시 시도해 주세요.");
			return "redirect:/login"; 
		}
		
		//로그아웃
		@GetMapping("/logout")
		public String logout(HttpSession session) throws Exception {
			session.invalidate();
			return "redirect:/login";
		}	
		
		// 마이페이지 
		@RequestMapping("/mypage") 
		public String mypage() throws Exception {
			
			return "heartbeat/mypage"; 
		}

		
		// 마이페이지 - 정보 변경
		@PostMapping("/mypage/modify")
		public String modify(@RequestParam("newPwd") String newPwd, UserVO userVO, HttpSession session, RedirectAttributes rttr) {
			UserVO uvo = (UserVO) session.getAttribute("UserVO");	//세션에서 user 데이터 불러와서 email 값을 전달해야 한다.
		    userVO.setEmail(uvo.getEmail());
		   
		    System.out.println("========== 정보수정 Presentaion member newPwd : "+newPwd);
		    System.out.println("========== 정보수정 Presentaion member user nickname : "+userVO.getNickname());
		    
	        userServiceImpl.modify(newPwd, userVO);
	       
	        uvo.setNickname(userVO.getNickname());  // 세션에 저장된 user 객체의 닉네임 업데이트
	        session.setAttribute("UserVO", uvo);  
			rttr.addFlashAttribute("message", "회원 정보가 변경되었습니다." );

		    return "redirect:/mypage";
		}
		
		//마이페이지 - 탈퇴
		@PostMapping("mypage/delete")
		public String delete(UserVO userVO, HttpSession session) {
			String email = userVO.getEmail(); //폼에서 입력받은 이메일
			String pwd = userVO.getPwd(); //폼에서 입력받은 비밀번호
			String url = null;
			UserVO uvo = (UserVO) session.getAttribute("UserVO");
			
			if(email.equals(uvo.getEmail()) && pwd.equals(uvo.getPwd())) {
				userServiceImpl.delete(uvo);
				url="redirect:/login";
			} else {
				url="redirect:/mypage";
			}
								
			return url;
		}
		
		//마이페이지 - 멤버쉽 변경(level)
		@RequestMapping("/mymembership")
		public String mymembership(UserVO userVO, HttpSession session) {
			
			return "heartbeat/mymembership";
		}
		// 마이페이지 - 내 게시물 확인
		@RequestMapping("/mypost") 
		public String mypost(int num, String searchType, String keyword,Model model,HttpServletRequest request,HttpSession session) throws Exception {
			UserVO uvo = (UserVO) session.getAttribute("UserVO");
			String email = uvo.getEmail();
			PageDTO page = new PageDTO();
			page.setNum(num);
			page.setCount(userServiceImpl.getMyPostCount(searchType,keyword,email)); // 내 게시물 개수
			page.setSearchType(searchType);
			page.setKeyword(keyword);
			
			List<PostVO> userPostList = userServiceImpl.getUserPost(page.getDisplayPost(), page.getPostNum(),searchType,keyword,email);
			if (searchType!= null) {
				model.addAttribute("addClass", "addClass");				
			}
			model.addAttribute("userPostList", userPostList);
			model.addAttribute("page", page);		
			model.addAttribute("select", num);
			
			return "heartbeat/mypost"; 
		}
		
		@PostMapping("/mypost/deletePost")
		public String deletePost(@RequestParam("post_id") String post_id) throws Exception {
		    // postIds는 콤마로 구분된 문자열이므로, 이를 분리하여 배열로 변환
		    String[] postIdArray = post_id.split(",");

		    // 각 post_id에 대해 삭제 처리
		    for (String postId : postIdArray) {
		        userServiceImpl.deleteMyPost(Integer.parseInt(postId));  // 삭제 서비스 호출
		    }

		    return "redirect:/mypost?num=1";
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
			
			return "heartbeat/mynotice";
		}
		
		// 내 문의 상세보기
		@RequestMapping("/getMyPostOne") // 게시물 상세보기
		public String getMyPostOne(@RequestParam("notice_id")int notice_id,int num, Model model)throws Exception {
			NoticeVO noticeVO = noticeService.getPostOne(notice_id);
			List<NoticeCommentVO> commentVO = noticeService.getComment(notice_id);
			
			
			model.addAttribute("num", num);
			model.addAttribute("commentVO", commentVO);
			model.addAttribute("noticeVO", noticeVO);

			return "/heartbeat/myNoticeShow";
		}
		
		@PostMapping("/myNoticeModifyShow") // 게시물 수정 페이지 이동
		public String noticeModifyShow(int notice_id,int num,Model model) throws Exception{
			NoticeVO noticeVO = noticeService.getPostOne(notice_id);
			
			model.addAttribute("num", num);
			model.addAttribute("noticeVO", noticeVO);
			return "/heartbeat/myNoticeModify";
		}
		
		@PostMapping("/myNoticeModify") // 게시물 수정
		public String noticeModify(@RequestParam("num")int num,NoticeVO noticeVO,Model model) throws Exception{
			
			noticeService.noticeModify(noticeVO);
			NoticeVO dbnoticeVO = noticeService.getPostOne(noticeVO.getNotice_id());
			List<NoticeCommentVO> commentVO = noticeService.getComment(dbnoticeVO.getNotice_id());
			model.addAttribute("num", num);
			model.addAttribute("noticeVO", dbnoticeVO);
			model.addAttribute("commentVO", commentVO);
			return "/heartbeat/myNoticeShow";
		}
		
		@RequestMapping("/myNoticeDelete") // 게시물 삭제
		public String noticeDelete(@RequestParam("notice_id")int notice_id) throws Exception{
			noticeService.noticeDelete(notice_id);

			return "redirect:/mynotice?num=1";
		}
		
		@PostMapping("/myCommentWrite") // 댓글 작성
		public String commentWrite(@RequestParam("num")int num,NoticeCommentVO noticeCommentVO,Model model) throws Exception{
			noticeService.commentWrite(noticeCommentVO);
			NoticeVO noticeVO = noticeService.getPostOne(noticeCommentVO.getNotice_id());
			List<NoticeCommentVO> commentVO = noticeService.getComment(noticeCommentVO.getNotice_id());
			
			model.addAttribute("num", num);
			model.addAttribute("commentVO", commentVO);
			model.addAttribute("noticeVO", noticeVO);
			return "redirect:/getMyPostOne?notice_id="+noticeCommentVO.getNotice_id()+"&num=1";
		}
		
		// 내 공지 삭제 
		@PostMapping("/mypage/deleteNotice")
		public String deleteNotice(@RequestParam("notice_id") String notice_id) throws Exception {
			// postIds는 콤마로 구분된 문자열이므로, 이를 분리하여 배열로 변환
			String[] noticeIdArray = notice_id.split(",");
			
			// 각 post_id에 대해 삭제 처리
			for (String noticeId : noticeIdArray) {
				userServiceImpl.deleteMyNotice(Integer.parseInt(noticeId));  // 삭제 서비스 호출
			}
			

			
			return "redirect:/mynotice?num=1";
		}


}