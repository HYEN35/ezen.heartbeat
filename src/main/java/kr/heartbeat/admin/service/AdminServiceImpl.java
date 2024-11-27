package kr.heartbeat.admin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.heartbeat.admin.persistence.AdminPersistenceImpl;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.RoleVO;
import kr.heartbeat.vo.SubscriptionVO;
import kr.heartbeat.vo.UserVO;
import kr.heartbeat.vo.UserroleVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminPersistenceImpl persistence;

	//summary
	@Override
	public int count_a(String reg_date) throws Exception {
	    return persistence.count_a(reg_date);
	}
	
	@Override
	public int count_b() throws Exception {
	    return persistence.count_b();
	}
	
	@Override
	public Map<String, Object> count_c() throws Exception {
	    return persistence.count_c();
	}
	
	//summary 그래프
	// 회원 총 인원
	@Override
	public int levelTotalCnt() throws Exception {
	    return persistence.levelTotalCnt();
	}
	
	//레벨 별 회원수
	@Override
	public int levelCnt(int level) throws Exception {
	    return persistence.levelCnt(level);
	}
	
	//member
	@Override
	public List<UserVO> getUserList(int displayPost, int postNum, String searchType, String keyword) throws Exception {	
		return persistence.getUserList(displayPost, postNum, searchType, keyword);
	}
	
	@Override
	public int getUserCount(String searchType, String keyword) throws Exception {
		return persistence.getUserCount(searchType, keyword);
	}
	
	@Override
	public void memberdelete(String email) throws Exception {
		persistence.memberdelete(email);
	}
	
	//post
	@Override
	public List<PostVO> getPostList(int displayPost, int postNum, String searchType, String keyword) throws Exception {	
		return persistence.getPostList(displayPost, postNum, searchType, keyword);
	}
	
	@Override
	public int getPostCount(String searchType, String keyword) throws Exception {
		return persistence.getPostCount(searchType, keyword);
	}

	
	@Override
	public void podelete(int post_id) throws Exception {
		persistence.podelete(post_id);
	}
	
	//comment
	@Override
	public List<CommentVO> getCommentList(int displayPost, int postNum, String searchType, String keyword) throws Exception {	
		return persistence.getCommentList(displayPost, postNum, searchType, keyword);
	}
	
	@Override
	public int getCommentCount(String searchType, String keyword) throws Exception {
		return persistence.getCommentCount(searchType, keyword);
	}
	
	@Override
	public void codelete(int comment_id) throws Exception {
		persistence.codelete(comment_id);
	}
	
	//edit
	@Override
	public UserVO getUserOne(String email) throws Exception {
		return persistence.getUserOne(email);
	}

	@Override
	public void update(UserVO uvo) throws Exception {
		persistence.update(uvo);
	}
	
	//계정생성
	@Override
    @Transactional // 트랜잭션 처리
    public int insertUser(UserVO userVO, int role_id) {
        // 1. user_tbl에 데이터 삽입
        int result = persistence.insertUser(userVO);
        if (result > 0) {
            // 2. user_role_tbl에 데이터 삽입
            UserroleVO userRole = new UserroleVO();
            userRole.setEmail(userVO.getEmail());
            userRole.setRole_id(role_id);
            persistence.insertUserRole(userRole);
        }
        return result;
    }
    @Override
    public int insertUserRole(UserroleVO userroleVO) {
        return persistence.insertUserRole(userroleVO);
    }
    @Override
    public int insertSubscription(SubscriptionVO subscriptionVO) {
        return persistence.insertSubscription(subscriptionVO);
    }
	
	@Override
    public List<RoleVO> getRole() {
        return persistence.getRole();
    }
	
	//중복체크
	@Override
	public UserVO idCheck(String email) {
		return persistence.idCheck(email);
	}
	@Override
	public UserVO phoneCheck(String phone) {
		return persistence.phoneCheck(phone);
	}
	@Override
	public UserVO nicknameCheck(String nickname) {
		return persistence.nicknameCheck(nickname);
	}


}
