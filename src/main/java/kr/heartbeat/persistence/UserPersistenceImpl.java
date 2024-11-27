package kr.heartbeat.persistence;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

@Repository
public class UserPersistenceImpl implements UserPersistence {
	
	@Inject
	private SqlSession sql;
	
	private static String namespace="kr.heartbeat.mappers.user";
	
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
	//회원가입
	@Override
	public int insertUser(UserVO userVO) {
		return sql.insert(namespace+".join", userVO);
	}
	//회원가입 시 유저 역할 추가
	@Override
	public int insertUserRole(String email) {
		return sql.insert(namespace+".joinUserRole", email);
	}
	//로그인
	@Override
	public UserVO login(UserVO userVO) {
		return sql.selectOne(namespace+".login", userVO);
	}
	//아이디 찾기
	@Override
	public UserVO findId(String name, String birth, String phone) {
		System.out.println("=====================Persistence name: "+ name);
		
		HashMap<String, String> map = new HashMap();
	
		map.put("name", name);
		map.put("birth", birth);
		map.put("phone", phone);
		
		return sql.selectOne(namespace+".findId", map);
	}
	//비밀번호 찾기
		@Override
		public UserVO findPwd(String email, String name, String birth) {
			System.out.println("=====================Persistence email : "+email);
			HashMap<String, String> map = new HashMap();
			
			map.put("email", email);
			map.put("name", name);
			map.put("birth", birth);
			
			return sql.selectOne(namespace+".findPwd", map);
		}	
	//회원정보수정
	@Override
	public void modify(String newPwd, UserVO userVO) {
	    HashMap<String, Object> map = new HashMap<String, Object>();

	    map.put("userVO", userVO);
	    map.put("newPwd", newPwd);
	    System.out.println("==================Map contents: " + map); 

	    sql.update(namespace + ".modify", map); 
    }
	//멤버쉽 수정(level)
	@Override
	public void membership(UserVO userVO) {
		System.out.println("===================Persistence getEmail"+ userVO.getEmail());
		System.out.println("===================Persistence level"+ userVO.getLevel());
		sql.update(namespace+".membership", userVO);
	}
	//회원 탈퇴
	@Override
	public void delete( UserVO uvo) {
		System.out.println("===================Persistence getEmail"+ uvo.getEmail());
		System.out.println("===================Persistence level"+ uvo.getPwd());
			
		//1. 사용자의 이메일, 비밀번호로 사용자 찾기 (사용자 조회)
		int searchResult = sql.selectOne(namespace+".userSearch", uvo);
		//2. 사용자의 정보를 탈퇴유저테이블로 옮기기
		if(searchResult == 1) {
			 int insertResult = sql.insert(namespace+".delUserInsert", uvo);
			//3. 사용자의 정보를 유저테이블에서 삭제
				if(insertResult ==1 ) {
					sql.delete(namespace+".delete", uvo);
				} 
		}
		
	}
	
	@Override
	public UserroleVO role(UserroleVO userrolevo) {
		System.out.println("===========다오role : " +userrolevo.getEmail());
		return sql.selectOne(namespace+".role", userrolevo);
	}
	

	





	
}
