package kr.heartbeat.persistence;

import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

public interface UserPersistence {
	//중복확인
	public UserVO idCheck(String email);
	public UserVO phoneCheck(String phone);
	public UserVO nicknameCheck(String nickname);
	
	//회원가입
	public int insertUser(UserVO userVO);
	
	//로그인
	public UserVO login(UserVO userVO);
	//아이디찾기
	public UserVO findId(String name, String birth, String phone);
	//비밀번호 찾기
	public UserVO findPwd(String email, String name, String birth);
	
	//회원수정
	public void modify(String newPwd, UserVO userVO);
	//멤버쉽 수정(level)
	public void membership(UserVO userVO);
	//회원 탈퇴
	public void delete(UserVO uvo);
	
	public UserroleVO role(UserroleVO userrolevo);
	// 내 게시물 개수 가져오기
	public int getMyPostCount(String searchType, String keyword, String email)throws Exception;
	// 유저 개인 게시물 가져오기
	public List<PostVO> getUserPost(int displayPost, int postNum, String searchType, String keyword, String email) throws Exception;
	// 유저 개인 게시물 삭제하기
	public void deleteMyPost(int post_id) throws Exception;
}
