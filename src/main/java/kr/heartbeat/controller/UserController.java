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
	  
	  // �̸��� �ߺ� Ȯ��
	    @PostMapping("/check-email")
	    @ResponseBody
	    public Map<String, Object> checkEmail(@RequestBody Map<String, String> params) {
	        String email = params.get("email");
	        boolean exists = memberService.isEmailExists(email);
	        Map<String, Object> response = new HashMap<>();
	        response.put("exists", exists);
	        return response;
	    }

	    // ��ȭ��ȣ �ߺ� Ȯ��
	    @PostMapping("/check-phone")
	    @ResponseBody
	    public Map<String, Object> checkPhone(@RequestBody Map<String, String> params) {
	        String phone = params.get("phone");
	        boolean exists = memberService.isPhoneExists(phone);
	        Map<String, Object> response = new HashMap<>();
	        response.put("exists", exists);
	        return response;
	    }

	    // �г��� �ߺ� Ȯ��
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

	        // ** MultipartFile (������ �̹��� ���ε�) **
	        String realPath = "C:\\workspace\\project\\src\\main\\webapp\\resources\\upload\\"; // ���� ���� ���
	        String file1, file2 = "";

	        MultipartFile uploadfilef = uservo.getProfileimgf(); // ���ε�� ����
	        if (uploadfilef != null && !uploadfilef.isEmpty()) {
	            // ������ �����ϸ� ���� ��� ����
	            // ���ϸ� �ߺ� ������ ���� UUID �߰�
	            String fileName = UUID.randomUUID().toString() + "_" + uploadfilef.getOriginalFilename();
	            file1 = realPath + fileName; // ������ ���
	            uploadfilef.transferTo(new File(file1)); // ���� ����

	            file2 = fileName; // DB�� ������ ���ϸ�
	        }

	        // ������ �̹��� ���� ��� ����
	        uservo.setProfileimg(file2);

	        try {
	            // ȸ������ ó��
	            memberService.insertUser(uservo);

	            // ���� �� login.jsp��
	            rttr.addFlashAttribute("message", "ȸ�������� �Ϸ�Ǿ����ϴ�.");
	            return "/heartbeat/login"; 
	        } catch (Exception e) {
	            e.printStackTrace();
	            // ���� �� join.jsp�� �����̷�Ʈ
	            rttr.addFlashAttribute("errorMessage", "ȸ������ �� ������ �߻��߽��ϴ�.");
	            return "redirect:/heartbeat/join";
	        }
	    }
	

}