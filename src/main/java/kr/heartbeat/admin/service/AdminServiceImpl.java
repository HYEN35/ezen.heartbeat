package kr.heartbeat.admin.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void podelete(String post_id) throws Exception {
		persistence.podelete(post_id);
	}

	

}
