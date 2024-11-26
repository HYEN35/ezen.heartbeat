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
	public void modify(String newPwd, UserVO userVO);
	//멤버쉽 수정(level)
	public void membership(UserVO userVO);
	//회원 탈퇴
	public void delete(UserVO uvo);
	
	public UserroleVO role(UserroleVO userrolevo);
}
