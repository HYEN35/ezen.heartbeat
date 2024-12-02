package kr.heartbeat.persistence;

import java.util.List;

import kr.heartbeat.vo.NoticeVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

public interface UserPersistence {
	//중복확인
	public UserVO idCheck(String email);
	public UserVO phoneCheck(String phone);
	public UserVO nicknameCheck(String nickname);
	
	//회원가입
	public int insertUser(UserVO userVO);
	//회원가입 시 유저 역할 추가
	public int insertUserRole(String email);
	
	//로그인
	public UserVO login(UserVO userVO);
	//아이디찾기
	public UserVO findId(UserVO userVO);

	//비밀번호 찾기 - 메일 전송 버전
	public UserVO searchPwd(UserVO userVO);
	//새비밀번호
	public int updatePwd(String email, String newPassword);

	//회원수정
	public void modify(UserVO userVO);

	//회원 탈퇴
	public void delete(UserVO uvo);
	
	public UserroleVO role(UserroleVO userrolevo);
	
	//맴버십 날짜 가져오기
	public SubscriptionVO checkMyMembershipDate(String email) throws Exception;
	// 내 게시물 개수 가져오기
	public int getMyPostCount(String searchType, String keyword, String email)throws Exception;
	// 유저 개인 게시물 가져오기
	public List<PostVO> getUserPost(int displayPost, int postNum, String searchType, String keyword, String email) throws Exception;
	// 유저 개인 게시물 삭제하기
	public void deleteMyPost(int post_id) throws Exception;
	// 내 문의 개수 가져오기
	public int getMyNoticeCount(String searchType, String keyword, String email)throws Exception;
	// 내 문의 가져오기
	public List<NoticeVO> getUserNotice(int displayPost, int postNum, String searchType, String keyword, String email) throws Exception;
	// 내 문의 삭제하기
	public void deleteMyNotice(int notice_id) throws Exception;
}
