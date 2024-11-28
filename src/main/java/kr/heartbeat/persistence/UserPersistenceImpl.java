package kr.heartbeat.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.NoticeVO;
import kr.heartbeat.vo.PostVO;
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
	public UserVO findId(UserVO userVO) {
		System.out.println("=====================Persistence name: "+ userVO.getName());
		
		return sql.selectOne(namespace+".findId", userVO);
	}

	//비밀번호 찾기 - 메일 전송 버전
	@Override
	public UserVO searchPwd(UserVO userVO) {		
		return  sql.selectOne(namespace+".searchPwd", userVO);
	}
	//새 비밀번호 		
	@Override
	public int updatePwd(String email, String newPassword) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("newPassword", newPassword);
		return sql.update(namespace+".updatePwd", map);
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



	// 내 게시물 개수 가져오기
	public int getMyPostCount(String searchType, String keyword, String email)throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("email", email);
		return sql.selectOne(namespace+".getMyPostCount", map);
	}
	// 유저 개인 게시물 가져오기
	public List<PostVO> getUserPost(int displayPost, int postNum, String searchType, String keyword, String email) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("displayPost", displayPost);
		map.put("postNum", postNum);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("email", email);
		return sql.selectList(namespace+".getUserPost", map);
	}
	// 유저 개인 게시물 삭제하기
	public void deleteMyPost(int post_id) throws Exception {
		sql.delete(namespace+".deleteMyPost", post_id);
	}
	
	// 내 문의 개수 가져오기
	public int getMyNoticeCount(String searchType, String keyword, String email)throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("email", email);
		return sql.selectOne(namespace+".getMyNoticeCount", map);
	}
	// 내 문의 가져오기
	public List<NoticeVO> getUserNotice(int displayPost, int postNum, String searchType, String keyword, String email) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("displayPost", displayPost);
		map.put("postNum", postNum);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("email", email);
		return sql.selectList(namespace+".getUserNotice", map);
	}
	
	// 내 문의 삭제하기
	public void deleteMyNotice(int notice_id) throws Exception {
		sql.delete(namespace+".deleteMyNotice", notice_id);
	}





	
}
