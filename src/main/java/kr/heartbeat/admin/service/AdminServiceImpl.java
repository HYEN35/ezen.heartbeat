package kr.heartbeat.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int getUserCount(String searchType, String keyword, String roleId) throws Exception {
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("searchType", searchType);
	    map.put("keyword", keyword);
	    map.put("roleId", roleId); // role_id 추가

	    return persistence.getUserCount(map);
	}
	
	@Override
	public void memberdelete(String email) throws Exception {
		persistence.memberdelete(email);
	}
	
	//Staff
	@Override
	public int getStaffCount(String searchType, String keyword) throws Exception {
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("searchType", searchType);
	    map.put("keyword", keyword);
	    
	    return persistence.getStaffCount(map);
	}

	@Override
	public List<UserVO> getStaffList(int displayPost, int postNum, String searchType, String keyword) throws Exception {
	    HashMap<String, Object> map = new HashMap<>();
	    map.put("displayPost", displayPost);
	    map.put("postNum", postNum);
	    map.put("searchType", searchType);
	    map.put("keyword", keyword);

	    return persistence.getStaffList(map);
	}

	
	
	@Override
	public void staffdelete(String email) throws Exception {
		persistence.staffdelete(email);
	}
	
	//post
	@Override
	public List<PostVO> getPostList(int displayPost, int postNum, String searchType, String keyword) throws Exception {	
		return persistence.getPostList(displayPost, postNum, searchType, keyword);
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
	public void podelete(String post_id) throws Exception {
		persistence.podelete(post_id);
	}

	

}
