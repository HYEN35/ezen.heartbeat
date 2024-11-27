package kr.heartbeat.admin.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.heartbeat.admin.persistence.AdminPersistenceImpl;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminPersistenceImpl persistence;

	@Override
	public int count_a(String reg_date) throws Exception {
	    return persistence.count_a(reg_date);
	}
	
	@Override
	public int count_b() throws Exception {
	    return persistence.count_b();
	}
	
	@Override
	public int count_c() throws Exception {
	    return persistence.count_c();
	}
	
	public Map<Integer, Integer> countByMonth() throws Exception {
        Map<Integer, Integer> monthCounts = new HashMap<>();
        int currentYear = LocalDate.now().getYear();
        System.out.println("=asdasdasd"+currentYear);
            for (int month = 1; month <= 12; month++) {
                int count = persistence.countByMonth(currentYear, month);
                monthCounts.put(month, count);
            }
        return monthCounts;
    }
	
	@Override
	public List<UserVO> getUserList() throws Exception {
		return persistence.getUserList();
	}
	
	@Override
	public List<PostVO> getPostList() throws Exception {
		return persistence.getPostList();
	}
	@Override
	public List<CommentVO> getCommentList() throws Exception {
		return persistence.getCommentList();
	}

	@Override
	public UserVO getUserOne(String email) throws Exception {
		return persistence.getUserOne(email);
	}

	@Override
	public void update(UserVO uvo) throws Exception {
		persistence.update(uvo);
	}

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
