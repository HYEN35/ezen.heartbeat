package kr.heartbeat.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.heartbeat.membership.service.MembershipService;
import kr.heartbeat.service.UserServiceImpl;
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
	
		//�씠硫붿씪 以묐났�솗�씤
		@PostMapping("/join/emailcheck")
		@ResponseBody
		public String idCheck(HttpServletRequest request) throws Exception {
			String email = request.getParameter("email");
			UserVO userVO = userServiceImpl.idCheck(email);
			String result = null;
	
			if (userVO != null) result = "success"; 	
			
			return result;
		}
		//�쟾�솕踰덊샇 以묐났�솗�씤
		@PostMapping("/join/phonecheck")
		@ResponseBody
		public String phoneCheck(HttpServletRequest request) throws Exception {
			String phone = request.getParameter("phone");
			UserVO userVO = userServiceImpl.phoneCheck(phone);
			String result = null;
			
			if (userVO != null) result = "success"; 	
			
			return result;
		}
		//�땳�꽕�엫 以묐났�솗�씤
		@PostMapping("/join/nicknamecheck")
		@ResponseBody
		public String nicknameCheck(HttpServletRequest request) throws Exception {
			
			String nickname = request.getParameter("nickname");
			UserVO userVO = userServiceImpl.nicknameCheck(nickname);
			String result = null;
			
			if (userVO != null) result = "success"; 
			
			return result;
		}
		
		//�쉶�썝媛��엯
				@PostMapping("/join")
				public String insertUser(UserVO userVO)throws IOException {
					System.out.println("========== Presentaion member email(id) : "+userVO.getEmail());
					System.out.println("========== Presentaion member getBirth : "+userVO.getBirth());
					String email = userVO.getEmail();
					String url = null;
					//프로필 사진 업로드 부분
					String realPath = "C:\\heartbeat-main\\heartbeat\\src\\main\\webapp\\resources\\upload\\"; 
			        String file1, file2 = "";
			        
			        MultipartFile uploadfilef = userVO.getProfileimgf(); 
			        System.out.println("================"+uploadfilef);
			        if (uploadfilef != null && !uploadfilef.isEmpty()) {
			            String fileName = UUID.randomUUID().toString() + "_" + uploadfilef.getOriginalFilename();
			            file1 = realPath + fileName; 
			            uploadfilef.transferTo(new File(file1)); 
			            file2 = fileName;
			            userVO.setProfileimg(file2);
			        }


					int resultUser = userServiceImpl.insertUser(userVO);
					int reulstUserRole = userServiceImpl.insertUserRole(email); 
					if(resultUser == 1 && reulstUserRole==1) { 
						url ="/heartbeat/login";
					} else { 
						url = "/heartbeat/join";
					}
					return url;
				}
		

		//濡쒓렇�씤 
		@PostMapping("/login")
		public String login(UserVO userVO, HttpSession session,UserroleVO userrolevo, RedirectAttributes rttr,Model model) throws Exception {
			UserVO dbuserVO = userServiceImpl.login(userVO);
			UserroleVO rolelevel = userServiceImpl.role(userrolevo);
			String email = userVO.getEmail();
			String url = null;
			Date date = new Date();
	        // �썝�븯�뒗 �삎�떇�쑝濡� 蹂��솚�븷 SimpleDateFormat 媛앹껜
	        SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd");	        
	        // Date 媛앹껜瑜� �썝�븯�뒗 �삎�떇�쓽 臾몄옄�뿴濡� �룷留룻똿
	        String formattedDate = desiredFormat.format(date);	        
	        // 異쒕젰
	        System.out.println(formattedDate);  // �삁�떆: 2024-11-20
			
	        if (dbuserVO != null) {
	            // �뙣�뒪�썙�뱶媛� �씪移섑븯�뒗 寃쎌슦
	            if (userVO.getPwd().equals(dbuserVO.getPwd())) {
	                // 留대쾭�떗 醫낅즺 �궇吏� �솗�씤
	                SubscriptionVO subscriptionVO = membershipService.checkEndDate(email);
	                Date nowDate = desiredFormat.parse(formattedDate);
	                if (subscriptionVO != null) {
	                    Date endDate = desiredFormat.parse(subscriptionVO.getEnd_date());
	                    long now = nowDate.getTime() - endDate.getTime();
	                    System.out.println(now);

	                    // 留대쾭�떗 醫낅즺 �뿬遺� �솗�씤
	                    if (now >= 0) {
	                        // 留대쾭�떗 湲곌컙 醫낅즺 �떆
	                        membershipService.deleteLevel(email);
	                        membershipService.updateLevel(email, 0, 0);
	                        dbuserVO = userServiceImpl.login(dbuserVO);  // 濡쒓렇�씤 �옱�떎�뻾
	                        session.setAttribute("UserVO", dbuserVO);  // session�뿉 dbuserVO ���옣
	                        model.addAttribute("alertMsg", "留대쾭�떗 �씠�슜 湲곌컙�씠 醫낅즺�릺�뿀�뒿�땲�떎.");
	                        return "heartbeat/membership";  // 留대쾭�떗 �럹�씠吏�濡� �씠�룞
	                    } else {
	                        // 留대쾭�떗 湲곌컙�씠 �쑀�슚�븳 寃쎌슦
	                        session.setAttribute("UserVO", dbuserVO);  // session�뿉 dbuserVO ���옣
	                        url = "redirect:/chart";  // 李⑦듃 �럹�씠吏�濡� �씠�룞
	                    }
	                } else {
	                    // 援щ룆 �젙蹂닿� �뾾�뒗 寃쎌슦
	                    session.setAttribute("UserVO", dbuserVO);  // session�뿉 dbuserVO ���옣
	                    url = "redirect:/chart";  // 李⑦듃 �럹�씠吏�濡� �씠�룞
	                }

	                // �뿭�븷�뿉 �뵲瑜� �럹�씠吏� 遺꾧린 (愿�由ъ옄 諛� �씪諛� �궗�슜�옄)
	                int roleId = rolelevel.getRole_id();
	                if (roleId == 0) {
	                    // 愿�由ъ옄 �럹�씠吏�濡� �씠�룞
	                    session.setAttribute("UserVO", dbuserVO);  // session�뿉 dbuserVO ���옣
	                    url = "/admin/summary";  // 愿�由ъ옄 ���떆蹂대뱶濡� �씠�룞
	                } else {
	                    // �씪諛� �궗�슜�옄 �럹�씠吏�濡� �씠�룞
	                    if (dbuserVO != null) {
	                        session.setAttribute("UserVO", dbuserVO);  // session�뿉 dbuserVO ���옣
	                        session.setAttribute("level", dbuserVO.getLevel());  // �궗�슜�옄 �젅踰� �꽕�젙
	                        url = "/heartbeat/chart";  // �씪諛� �궗�슜�옄 李⑦듃 �럹�씠吏�濡� �씠�룞
	                    } else {
	                        url = "/heartbeat/login";  // 濡쒓렇�씤 �럹�씠吏�濡� �씠�룞
	                    }
	                }
	            } else {
	                // �뙣�뒪�썙�뱶 遺덉씪移�
	                session.setAttribute("UserVO", null);
	                rttr.addFlashAttribute("pwd", false);
	                url = "redirect:/login";  // 濡쒓렇�씤 �럹�씠吏�濡� �씠�룞
	            }
	        } else {
	            // dbuserVO媛� null�씤 寃쎌슦
	            session.setAttribute("UserVO", null);
	            rttr.addFlashAttribute("email", false);
	            url = "redirect:/login";  // 濡쒓렇�씤 �럹�씠吏�濡� �씠�룞
	        }

			
			return url;
		}
		
		//�븘�씠�뵒 李얘린
		@PostMapping("/login/findId")
		@ResponseBody //@ResponseBody瑜� �궗�슜�븯硫� model 媛앹껜瑜� �벝 �닔 �뾾�떎.
		public HashMap<String,Object> findId(HttpServletRequest request) {
		// HashMap�쓣 �궗�슜�븷 �븣 @ResponseBody濡� 諛섑솚�릺�뒗 媛앹껜瑜� JSON�쑝濡� 蹂��솚�븯�젮硫� jackson-databind瑜� pom.xml�뿉 �쓽議댁꽦 二쇱엯�쓣 �빐�빞 �븳�떎.
			String name = request.getParameter("name");
			String birth = request.getParameter("birth");
			String phone = request.getParameter("phone");
			
			UserVO userVO = userServiceImpl.findId(name, birth, phone);
			HashMap<String, Object> response = new HashMap<String, Object>();
			
			if(userVO != null) {
				response.put("result", "success");
				response.put("email", userVO.getEmail());
			}

			return response;
		}
		
		//鍮꾨�踰덊샇 李얘린
		@PostMapping("/login/findPwd")
		@ResponseBody
		public HashMap<String, Object> findPwd(HttpServletRequest request) {
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String birth = request.getParameter("birth");
			
			UserVO userVO = userServiceImpl.findPwd(email, name, birth);
			HashMap<String, Object> response = new HashMap<String, Object>();
			
			System.out.println("========== Presentaion member getPwd : "+userVO.getPwd());
			
			if(userVO != null) {
				response.put("result", "successpwd");
				response.put("pwd", userVO.getPwd());
			}
			return response;
		}
		
		//濡쒓렇�븘�썐
		@GetMapping("/logout")
		public String logout(HttpSession session) throws Exception {
			session.invalidate();
			return "redirect:/login";
		}		
		
		// 마이페이지 회원 정보 수정
		@PostMapping("/mypage/modify")
		public String modify(@RequestParam(value = "newPwd", required = false) String newPwd,
		                     @RequestParam(value = "nickname", required = false) String nickname,
		                     @RequestParam(value = "profileimgf", required = false) MultipartFile profileImage,
		                     HttpSession session) throws IOException {

			UserVO userVO = (UserVO) session.getAttribute("UserVO");
		    
		    // 비밀번호 수정 처리
		    if (newPwd != null && !newPwd.isEmpty()) {
		    	userVO.setPwd(newPwd); 
		    }

		    // 닉네임 수정 처리
		    if (nickname != null && !nickname.isEmpty()) {
		    	userVO.setNickname(nickname); 
		    }

		    // 프로필 사진 수정 처리
		    if (profileImage != null && !profileImage.isEmpty()) {
		        String fileName = UUID.randomUUID().toString() + "_" + profileImage.getOriginalFilename();
		        String filePath = "C:\\heartbeat-main\\heartbeat\\src\\main\\webapp\\resources\\upload\\" + fileName;

		        // 기존 프로필 사진 삭제
		        if (userVO.getProfileimg() != null && !userVO.getProfileimg().isEmpty()) {
		            String oldFilePath = "C:\\heartbeat-main\\heartbeat\\src\\main\\webapp\\resources\\upload\\" +userVO.getProfileimg();
		            File oldFile = new File(oldFilePath);
		            if (oldFile.exists()) {
		                oldFile.delete();
		            }
		        }

		        // 새로운 프로필 사진 저장
		        profileImage.transferTo(new File(filePath));
		        userVO.setProfileimg(fileName); // 새로운 파일명을 UserVO에 설정
		    }

		    // 수정된 사용자 정보 DB에 저장
		    userServiceImpl.modify(userVO); // userServiceImpl 수정 메소드 호출

		    // 세션 갱신
		    session.setAttribute("UserVO", userVO); // 세션에 수정된 사용자 정보 업데이트

		    return "redirect:/mypage"; // 마이페이지로 리다이렉트
		}
		
		//留덉씠�럹�씠吏� - �깉�눜
		@PostMapping("mypage/delete")
		public String delete(UserVO userVO, HttpSession session) {
			String email = userVO.getEmail(); //�뤌�뿉�꽌 �엯�젰諛쏆� �씠硫붿씪
			String pwd = userVO.getPwd(); //�뤌�뿉�꽌 �엯�젰諛쏆� 鍮꾨�踰덊샇
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
		
		//留덉씠�럹�씠吏� - 硫ㅻ쾭�돺 蹂�寃�(level)
		@PostMapping("mypage/membership")
		public String membership(UserVO userVO, HttpSession session) {
			UserVO uvo = (UserVO) session.getAttribute("UserVO");	
		    userVO.setEmail(uvo.getEmail());
			System.out.println("========== 硫ㅻ쾭�돺 Presentaion member level : "+userVO.getLevel());
			
			userServiceImpl.membership(userVO);
			
			uvo.setLevel(userVO.getLevel());
			session.setAttribute("UserVO", uvo);  
			
			return "redirect:/mypage";
		}

		
}
