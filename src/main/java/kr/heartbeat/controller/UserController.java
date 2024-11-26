package kr.heartbeat.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.heartbeat.service.MemberService;
import kr.heartbeat.vo.UserVO;

@Controller
public class UserController {

	  @Autowired
	    private MemberService memberService;
	  
	  // 이메일 중복 확인
	    @PostMapping("/check-email")
	    @ResponseBody
	    public Map<String, Object> checkEmail(@RequestBody Map<String, String> params) {
	        String email = params.get("email");
	        boolean exists = memberService.isEmailExists(email);
	        Map<String, Object> response = new HashMap<>();
	        response.put("exists", exists);
	        return response;
	    }

	    // 전화번호 중복 확인
	    @PostMapping("/check-phone")
	    @ResponseBody
	    public Map<String, Object> checkPhone(@RequestBody Map<String, String> params) {
	        String phone = params.get("phone");
	        boolean exists = memberService.isPhoneExists(phone);
	        Map<String, Object> response = new HashMap<>();
	        response.put("exists", exists);
	        return response;
	    }

	    // 닉네임 중복 확인
	    @PostMapping("/check-nickname")
	    @ResponseBody
	    public Map<String, Object> checkNickname(@RequestBody Map<String, String> params) {
	        String nickname = params.get("nickname");
	        boolean exists = memberService.isNicknameExists(nickname);
	        Map<String, Object> response = new HashMap<>();
	        response.put("exists", exists);
	        return response;
	    }
	

	    
	    
	    @RequestMapping(value="/register", method=RequestMethod.POST)
	    public String insertUser(RedirectAttributes rttr, UserVO uservo) throws IOException {
	        System.out.println("uservo+++++++++++!!!!!!!!!!!!!!!" + uservo);

	        String uri = "";

	        // ** MultipartFile (프로필 이미지 업로드) **
	        String realPath = "C:\\workspace\\project\\src\\main\\webapp\\resources\\upload\\"; // 파일 저장 경로
	        String file1, file2 = "";

	        MultipartFile uploadfilef = uservo.getProfileimgf(); // 업로드된 파일
	        if (uploadfilef != null && !uploadfilef.isEmpty()) {
	            // 파일이 존재하면 저장 경로 설정
	            // 파일명 중복 방지를 위한 UUID 추가
	            String fileName = UUID.randomUUID().toString() + "_" + uploadfilef.getOriginalFilename();
	            file1 = realPath + fileName; // 물리적 경로
	            uploadfilef.transferTo(new File(file1)); // 파일 저장

	            file2 = fileName; // DB에 저장할 파일명
	        }

	        // 프로필 이미지 파일 경로 설정
	        uservo.setProfileimg(file2);

	        try {
	            // 회원가입 처리
	            memberService.insertUser(uservo);

	            // 성공 시 login.jsp로
	            rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
	            return "/heartbeat/login"; 
	        } catch (Exception e) {
	            e.printStackTrace();
	            // 실패 시 join.jsp로 리다이렉트
	            rttr.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다.");
	            return "redirect:/heartbeat/join";
	        }
	    }
	

}