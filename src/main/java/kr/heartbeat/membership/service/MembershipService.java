package kr.heartbeat.membership.service;

import kr.heartbeat.vo.SubscriptionVO;

public interface MembershipService {
	// 아티스트 아이디 확인
	public int checkArtistId(String custom_data) throws Exception;
	// 회원 레벨 업데이트
	public void updateLevel(String email, int artist_id,int level) throws Exception;
	// 구독 테이블 작성
	public void insertSubscription(String email, int artist_id, int level) throws Exception;
	// 레벨 삭제 
	public void deleteLevel(String email) throws Exception;
	// 레벨 1로 변경 
	public void deleteAndUpdateLevel1(String email) throws Exception;
	// 맴버십 종료 날짜 확인
	public SubscriptionVO checkEndDate(String email) throws Exception;
	// 맴버십 레벨 확인
	public int checkLevel(String email) throws Exception;
	// 아티스트 아이디 확인
	public int checkArtistID(String email) throws Exception;
}
