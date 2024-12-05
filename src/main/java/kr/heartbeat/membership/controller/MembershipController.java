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
@RequestMapping("/purchase/*")
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
        
        int level = (int)paymentData.get("level");
        int artist_id = membershipService.checkArtistId(custom_data); // 아티스트 아이디 확인
        UserVO uvo = new UserVO();
        uvo.setEmail(email);
        UserVO dbuserVO = userServiceImpl.login(uvo);
        if (dbuserVO.getLevel() == 1) {
        	membershipService.deleteLevel(email);
        }
        
        membershipService.updateLevel(email, artist_id,level); //맴버십 레벨 업데이트
		membershipService.insertSubscription(email,artist_id,level); // subscription에 insert

        UserVO newdbuserVO = userServiceImpl.login(uvo);

        session.setAttribute("UserVO", newdbuserVO);
        return "redirect/purchase";
    	}
	
		@PostMapping("/streamingPay")
		public String streamingPay(@RequestBody Map<String, Object> paymentData,HttpSession session, Model model )throws Exception {

			
	        String email = (String)paymentData.get("buyer_email");
	        int level = (int)paymentData.get("level");
	        int artist_id = (int)paymentData.get("custom_data");
	        UserVO uvo = new UserVO();
	        uvo.setEmail(email);
	        membershipService.updateLevel(email, artist_id,level); //맴버십 레벨 업데이트
			membershipService.insertSubscription(email,artist_id,level); // subscription에 insert
			UserVO dbuserVO = userServiceImpl.login(uvo);
			
			session.setAttribute("UserVO", dbuserVO);
			return "redirect:/purchase";
		}
	}

	
