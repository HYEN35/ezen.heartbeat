package kr.heartbeat.membership.persistence;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.SubscriptionVO;

@Repository
public class MembershipPersistenceImpl implements MembershipPersistence {
	
	@Inject
	private SqlSession sql;
	
	private static String namespace = "kr.heartbeat.mappers.membership";
	
	@Override
	public int checkArtistId(String custom_data) throws Exception {
		return sql.selectOne(namespace+".checkArtistId", custom_data);
	}
	@Override
	public void updateLevel(String email, int artist_id, int level) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("artist_id", artist_id);
		map.put("level", level);
		sql.update(namespace+".updateLevel", map);
	}
	
	@Override
	public void insertSubscription(String email, int artist_id, int level) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("artist_id", artist_id);
		map.put("level", level);
		System.out.println("======인서트 확인" + map);
		sql.update(namespace+".insertSubscription", map);
	}
	// 레벨 삭제 
	@Override
	public void deleteLevel(String email) throws Exception {
		sql.delete(namespace+".deleteLevel", email);
	}
	
	// 맴버십 종료 날짜 확인
	@Override
	public SubscriptionVO checkEndDate(String email) throws Exception {
		return sql.selectOne(namespace+".checkEndDate", email);
	}
}
