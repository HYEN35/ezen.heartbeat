package kr.heartbeat.membership.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.heartbeat.membership.service.MembershipService;
import kr.heartbeat.service.UserServiceImpl;
import kr.heartbeat.vo.UserVO;

@RestController
@RequestMapping("/membership/*")
public class MembershipController {

	@Autowired
	private MembershipService membershipService;
	@Autowired
	private UserServiceImpl userServiceImpl;

	private static final String IMP_KEY = "8222383208550316"; // 본인의 API Key
	private static final String IMP_SECRET = "Ef2oRQvlageeXJiHoZ7IgmMWhJQZ9NaVazj9VBxBoPWpkspP7YCKhNy87dena5UzEOuVM5mmlxPwouxl"; // 본인의
																																	// API
																																	// Secret

	@PostMapping("/artistPay")
    public String artistPay(@RequestBody Map<String, Object> paymentData,HttpSession session,Model model) throws Exception {
        String pgToken = (String) paymentData.get("pg_token");
        String custom_data = (String)paymentData.get("custom_data");
        String email = (String)paymentData.get("buyer_email");
        System.out.println(paymentData);
//        int amount = (int)paymentData.get("amount");
//        if (amount != 6900) {
//        	model.addAttribute("msg", "금액이 잘못됐습니다");
//		   	return "redirect:/membership";
//        }
        
        int level = (int)paymentData.get("level");
        int artist_id = membershipService.checkArtistId(custom_data);
        UserVO uvo = new UserVO();
        uvo.setEmail(email);
        UserVO dbuserVO = userServiceImpl.login(uvo);
        if (dbuserVO.getLevel() == 1) {
        	membershipService.deleteLevel(email);
        }
        
        membershipService.updateLevel(email, artist_id,level);
		membershipService.insertSubscription(email,artist_id,level);

        UserVO newdbuserVO = userServiceImpl.login(uvo);
        // 아이엠포트 결제 확인 API 호출
       // String url = "https://api.iamport.kr/paid/verify";
       // HttpHeaders headers = new HttpHeaders();
       // headers.set("Content-Type", "application/json");

        // 요청 파라미터 설정
       // Map<String, String> requestPayload = new HashMap<>();
       // requestPayload.put("imp_key", IMP_KEY);
      //  requestPayload.put("imp_secret", IMP_SECRET);
      //  requestPayload.put("pg_token", pgToken);

      //  RestTemplate restTemplate = new RestTemplate();
      //  HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestPayload, headers);

            // 외부 API 호출
        //ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        //String responseBody = responseEntity.getBody();
        session.setAttribute("UserVO", newdbuserVO);
        return "redirect/membership";
    	}
	
		@PostMapping("/streamingPay")
		public String streamingPay(@RequestBody Map<String, Object> paymentData,HttpSession session, Model model )throws Exception {
			System.out.println("Payment Data: " + paymentData);  // 서버 로그로 확인
//	        int amount = (int)paymentData.get("amount");
//			if (amount != 3900) {
//	        	model.addAttribute("msg", "금액이 잘못됐습니다");
//	        	return "redirect:/membership";
//	        }
			
	        String email = (String)paymentData.get("buyer_email");
	        int level = (int)paymentData.get("level");
	        int artist_id = (int)paymentData.get("custom_data");
	        System.out.println("아티스트 아이디 확인"+artist_id);
	        UserVO uvo = new UserVO();
	        uvo.setEmail(email);
	        membershipService.updateLevel(email, artist_id,level);
			membershipService.insertSubscription(email,artist_id,level);
			UserVO dbuserVO = userServiceImpl.login(uvo);
			
			session.setAttribute("UserVO", dbuserVO);
			return "redirect:/membership";
		}
	}

	
