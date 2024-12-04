package kr.heartbeat.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.heartbeat.membership.persistence.MembershipPersistence;
import kr.heartbeat.vo.SubscriptionVO;

@Service
public class MembershipServiceImpl implements MembershipService {
	
	@Autowired
	private MembershipPersistence membershipPersistence;
	
	@Override // 아티스트 아이디 확인 
	public int checkArtistId(String custom_data) throws Exception {
		return membershipPersistence.checkArtistId(custom_data);
	}
	
	@Override
	public void updateLevel(String email, int artist_id, int level) throws Exception {
		membershipPersistence.updateLevel(email,artist_id,level);
	}
	
	@Override
	public void insertSubscription(String email, int artist_id, int level) throws Exception {
		System.out.println("인서트 서비스 부분 확인 : " +email+""+artist_id);
		membershipPersistence.insertSubscription(email,artist_id,level);
	}
	
	@Override
	public void deleteLevel(String email) throws Exception {
		membershipPersistence.deleteLevel(email);
	}
	// 맴버십 종료 날짜 확인
	@Override
	public SubscriptionVO checkEndDate(String email) throws Exception {
		return membershipPersistence.checkEndDate(email);
	}
}
