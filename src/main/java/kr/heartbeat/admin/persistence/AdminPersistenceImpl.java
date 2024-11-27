package kr.heartbeat.admin.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.RoleVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

@Repository
public class AdminPersistenceImpl implements AdminPersistence {

	@Inject
	private SqlSession sql;
	
	private static String namespace = "kr.heartbeat.mappers.admin";
	
	//summary 상단 데이터(왼쪽부터 a~c)
	@Override
	public int count_a(String reg_date) throws Exception {
	    return sql.selectOne(namespace + ".count_a", reg_date);
	}
	
	@Override
	public int count_b() throws Exception {
	    return sql.selectOne(namespace + ".count_b");
	}
	
	@Override
	public Map<String, Object> count_c() throws Exception {
	    return sql.selectOne(namespace + ".count_c");
	}
	
	//summary 그래프
	// 회원 총 인원
	public int levelTotalCnt() throws Exception {
	    return sql.selectOne(namespace + ".levelTotalCnt");
	}

	public int levelCnt(int level) throws Exception {
	    System.out.println("레벨 0 회원 수: " + level);
	    int result = sql.selectOne(namespace + ".levelCnt", level);
	    System.out.println(result);
		
	    return sql.selectOne(namespace + ".levelCnt", level);
	}

	//member 리스트
	@Override
	public List<UserVO> getUserList(HashMap<String, Object> map) throws Exception {
	    return sql.selectList(namespace + ".user_list", map);
	}
	
	@Override
	public int getUserCount(HashMap<String, Object> map) throws Exception {
	    return sql.selectOne(namespace + ".getUserCount", map);
	}

	@Override
	public void memberdelete(String email) throws Exception {
		sql.delete(namespace + ".memberdelete", email);
	}
	
	//post 리스트
	@Override
	public List<PostVO> getPostList(int displayPost, int postNum, String searchType, String keyword) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("displayPost", displayPost);
		map.put("postNum", postNum);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sql.selectList(namespace+ ".post_list", map);
	}
	
	@Override
	public int getPostCount(String searchType, String keyword) throws Exception {
		HashMap map = new HashMap();
		
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sql.selectOne(namespace + ".getPostCount", map);
	}
	
	@Override
	public void podelete(int post_id) throws Exception {
		sql.delete(namespace + ".podelete", post_id);
	}
	
	//comment 리스트
	@Override
	public List<CommentVO> getCommentList(int displayPost, int postNum, String searchType, String keyword) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("displayPost", displayPost);
		map.put("postNum", postNum);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sql.selectList(namespace+ ".comment_list", map);
	}
	
	@Override
	public int getCommentCount(String searchType, String keyword) throws Exception {
		HashMap map = new HashMap();
		
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		
		return sql.selectOne(namespace + ".getCommentCount", map);
	}
	
	@Override
	public void codelete(int comment_id) throws Exception {
	    System.out.println("Deleting comment with ID: " + comment_id);
	    sql.delete(namespace + ".codelete", comment_id);
	}
	
	//edit
	@Override
	public UserVO getUserOne(String email) throws Exception {
		return sql.selectOne(namespace + ".edit", email);
	}
	
	@Override
	public void update(UserVO uvo) throws Exception {
	    System.out.println("update called with: " + uvo); // 로그 추가
	    sql.update(namespace + ".update", uvo);
	}
	
	//계정생성
	// 유저 기본 정보 삽입
    @Override
    public int insertUser(UserVO userVO) {
        return sql.insert(namespace + ".insertUser", userVO);
    }
    // 유저 역할 정보 삽입
    @Override
    public int insertUserRole(UserroleVO userroleVO) {
        return sql.insert(namespace + ".insertUserRole", userroleVO);
    }
    // 구독 정보 삽입
    @Override
    public int insertSubscription(SubscriptionVO subscriptionVO) {
        return sql.insert(namespace + ".insertSubscription", subscriptionVO);
    }

    @Override
    public List<RoleVO> getRole() {
        return sql.selectList(namespace + ".getRole");
    }
    
	//중복 확인
	@Override
	public UserVO idCheck(String email) {
		return sql.selectOne(namespace + ".emailcheck", email);
	}
	@Override
	public UserVO phoneCheck(String phone) {
		return sql.selectOne(namespace + ".phonecheck", phone);
	}
	@Override
	public UserVO nicknameCheck(String nickname) {
		return sql.selectOne(namespace + ".nicknamecheck", nickname);
	}

}