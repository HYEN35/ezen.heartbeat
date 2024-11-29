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
	
	public int getUserCount(HashMap<String, Object> map) throws Exception;
	
	public void memberdelete(String email) throws Exception;
	
	//Staff
	public List<UserVO> getStaffList(HashMap<String, Object> map) throws Exception;
	
	public int getStaffCount(HashMap<String, Object> map) throws Exception;
	
	public void staffdelete(String email) throws Exception;
	
	//post
	public List<PostVO> getPostList(int displayPost, int postNum, String searchType, String keyword) throws Exception;
	
	public List<CommentVO> getCommentList() throws Exception;

	public UserVO getUserOne(String email) throws Exception;

	public void update(UserVO uvo) throws Exception;
	
	public void podelete(String post_id) throws Exception;

}
