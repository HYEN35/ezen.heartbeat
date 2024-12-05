package kr.heartbeat.admin.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.RoleVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;
import kr.heartbeat.vo.likeVO;

public interface AdminPersistence {
	
	//summary
	public int count_a(String reg_date) throws Exception;
	
	public int count_b() throws Exception;
	
	public Map<String, Object> count_c() throws Exception;
	//좋아요 많은 게시물 5개 가져오기
	public List<likeVO> getMostLikePost() throws Exception;
	
	//summary 그래프
	// 회원 총 인원
	public int levelTotalCnt() throws Exception;
	
	// 레벨 별 인원
	public int levelCnt(int level) throws Exception;

	//member
	public List<UserVO> getUserList(HashMap<String, Object> map) throws Exception;
	
	public int getUserCount(HashMap<String, Object> map) throws Exception;
	
	public void memberdelete(String email) throws Exception;
	
	//Staff
	public List<UserVO> getStaffList(HashMap<String, Object> map) throws Exception;
	
	public int getStaffCount(HashMap<String, Object> map) throws Exception;
	
	public void staffdelete(String email) throws Exception;
	
	//post
	public List<PostVO> getPostList(HashMap<String, Object> map) throws Exception;
	
	public int getPostCount(HashMap<String, Object> map) throws Exception;
	
	public void podelete(int post_id) throws Exception;
	
	//comment
	public List<CommentVO> getCommentList(HashMap<String, Object> map) throws Exception;
	
	public int getCommentCount(HashMap<String, Object> map) throws Exception;
	
	public void codelete(int comment_id) throws Exception;

	//edit
	public UserVO getUserOne(String email) throws Exception;

	public void update(UserVO uvo) throws Exception;
	
	//계정생성
	// 유저 기본 정보 삽입
	public int insertUser(UserVO userVO);
    // 유저 역할 정보 삽입
	public int insertUserRole(UserroleVO userroleVO);
    // 구독 정보 삽입
	public int insertSubscription(SubscriptionVO subscriptionVO);
	
	public List<RoleVO> getRole();
	
	//중복확인
	public UserVO idCheck(String email);
	public UserVO phoneCheck(String phone);
	public UserVO nicknameCheck(String nickname);
}
