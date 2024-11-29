package kr.heartbeat.admin.service;

import java.util.List;
import java.util.Map;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

public interface AdminService {
	
	public int count_a(String reg_date) throws Exception;
	
	public int count_b() throws Exception;
	
	public int count_c() throws Exception;
	
	//summary 그래프
	// 회원 총 인원
	public int levelTotalCnt() throws Exception;
	
	// 레벨 별 회원수
	public int levelCnt(int level) throws Exception;
	
	//member
	public List<UserVO> getUserList(int displayPost, int postNum, String searchType, String keyword, String roleId) throws Exception;
	
	public int getUserCount(String searchType, String keyword, String roleId) throws Exception;
								
	public void memberdelete(String email) throws Exception;
	
	//Staff
	public int getStaffCount(String searchType, String keyword) throws Exception;
	
	public List<UserVO> getStaffList(int displayPost, int postNum, String searchType, String keyword) throws Exception;
								
	public void staffdelete(String email) throws Exception;
	
	//post
	public List<PostVO> getPostList(int displayPost, int postNum, String searchType, String keyword) throws Exception;
	
	public List<CommentVO> getCommentList() throws Exception;
	
	public int getCommentCount(String searchType, String keyword) throws Exception;
	
	public void codelete(int comment_id) throws Exception;
	
	//edit
	public UserVO getUserOne(String email) throws Exception;

	public void update(UserVO uvo) throws Exception;
	
	public void podelete(String post_id) throws Exception;

}
