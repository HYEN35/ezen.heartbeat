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
	
	@Override // 유저 level 업데이트
	public void updateLevel(String email, int artist_id, int level) throws Exception {
		membershipPersistence.updateLevel(email,artist_id,level);
	}
	
	@Override // 구독정보 저장
	public void insertSubscription(String email, int artist_id, int level) throws Exception {
		membershipPersistence.insertSubscription(email,artist_id,level);
	}
	
	@Override // 레벨 삭제
	public void deleteLevel(String email) throws Exception {
		membershipPersistence.deleteLevel(email);
	}
	@Override // 레벨 1로 변경
	public void deleteAndUpdateLevel1(String email) throws Exception {
		membershipPersistence.deleteLevel(email);
	}
	// 맴버십 종료 날짜 확인
	@Override
	public SubscriptionVO checkEndDate(String email) throws Exception {
		return membershipPersistence.checkEndDate(email);
	}
	
	// 맴버십 레벨 확인
	@Override
	public int checkLevel(String email) throws Exception {
		return membershipPersistence.checkLevel(email);
	}
}
