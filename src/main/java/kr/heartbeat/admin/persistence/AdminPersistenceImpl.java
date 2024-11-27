package kr.heartbeat.admin.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

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
	
	public int count_c() throws Exception {
	    return sql.selectOne(namespace + ".count_c");
	}
	
	public int countByMonth(int year, int month) throws Exception {
		HashMap<String , Object> map = new HashMap<String, Object>();
		System.out.println(year+""+month);
		map.put("year", year);
		map.put("month", month);
       return sql.selectOne(namespace + ".countByMonth", map);
    }
	
	//member 리스트
	@Override
	public List<UserVO> getUserList() throws Exception {
		return sql.selectList(namespace+ ".list");
	}
	
	//post 리스트
	@Override
	public List<PostVO> getPostList() throws Exception {
		return sql.selectList(namespace+ ".post_list");
	}
	
	//comment 리스트
	@Override
	public List<CommentVO> getCommentList() throws Exception {
		return sql.selectList(namespace+ ".comment_list");
	}

	//edit CRUD
	@Override
	public UserVO getUserOne(String email) throws Exception {
		return sql.selectOne(namespace + ".edit", email);
	}
	
	@Override
	public void update(UserVO uvo) throws Exception {
	    System.out.println("update called with: " + uvo); // 로그 추가
	    sql.update(namespace + ".update", uvo);
	}
	
	@Override
	public void podelete(String post_id) throws Exception {
		sql.delete(namespace + ".podelete", post_id);
	}
	
}
