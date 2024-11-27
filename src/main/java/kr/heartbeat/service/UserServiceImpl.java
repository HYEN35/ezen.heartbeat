package kr.heartbeat.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.heartbeat.persistence.UserPersistenceImpl;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	private UserPersistenceImpl userPersistenceImpl;
	//중복체크
	@Override
	public UserVO idCheck(String email) {
		return userPersistenceImpl.idCheck(email);
	}
	@Override
	public UserVO phoneCheck(String phone) {
		return userPersistenceImpl.phoneCheck(phone);
	}
	@Override
	public UserVO nicknameCheck(String nickname) {
		return userPersistenceImpl.nicknameCheck(nickname);
	}
	//회원가입
	@Override
	public int insertUser(UserVO userVO) {
		System.out.println("========== Service member email(id) : "+userVO.getEmail());
		return userPersistenceImpl.insertUser(userVO);
	}
	//로그인
	@Override
	public UserVO login(UserVO userVO) {
		System.out.println("========== 로그인 Service member email(id) : "+userVO.getEmail());
		return userPersistenceImpl.login(userVO);
	}
	//아이디찾기
	@Override
	public UserVO findId(String name, String birth, String phone) {
		System.out.println("=====================Service name: "+ name);
		return userPersistenceImpl.findId(name, birth, phone);
	}
	//비밀번호 찾기
	@Override
	public UserVO findPwd(String email, String name, String birth) {
		System.out.println("========== Service email : "+email);
		return userPersistenceImpl.findPwd(email, name, birth);
	}
	//회원수정
	@Override
	public void modify(String newPwd, UserVO userVO) {
		System.out.println("========== 로그인 Service member newPwd : "+newPwd);
		System.out.println("========== 로그인 Service member getNickname : "+userVO.getNickname());
		userPersistenceImpl.modify(newPwd,userVO);
	}
	//멤버쉽 수정(level)
	@Override
	public void membership(UserVO userVO) {
		userPersistenceImpl.membership(userVO);
	}
	//회원 탈퇴
	@Override
	public void delete(UserVO uvo) {
		userPersistenceImpl.delete(uvo);
	}
	
	
	@Override
	public UserroleVO role(UserroleVO userrolevo) {
		System.out.println("=============서비스role : "+userrolevo.getRole_id());
		return userPersistenceImpl.role(userrolevo);
	}

	
	

	

}
