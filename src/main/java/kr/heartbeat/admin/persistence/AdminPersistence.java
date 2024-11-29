package kr.heartbeat.admin.persistence;

import java.util.List;
import java.util.Map;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

public interface AdminPersistence {
	
	public int count_a(String reg_date) throws Exception;
	
	public int count_b() throws Exception;
	
	public int count_c() throws Exception;
	
	public int countByMonth(int year, int month) throws Exception;
	
	public List<UserVO> getUserList() throws Exception;
	
	public List<PostVO> getPostList() throws Exception;
	
	public List<CommentVO> getCommentList() throws Exception;

	public UserVO getUserOne(String email) throws Exception;

	public void update(UserVO uvo) throws Exception;
	
	public void podelete(String post_id) throws Exception;

}
