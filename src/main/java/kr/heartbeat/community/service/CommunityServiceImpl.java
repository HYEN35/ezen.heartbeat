package kr.heartbeat.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.heartbeat.community.persistence.CommunityPersistence;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

@Service
public class CommunityServiceImpl implements CommunityService {

	@Autowired
	private CommunityPersistence communityPersistence;
	
	@Override //�ɹ��� ���� �������� 
	public UserVO checkMemberShipLevel(UserVO uservo) throws Exception {
		return communityPersistence.checkMemberShipLevel(uservo);
	}
	
	@Override //�Խù� �ۼ�
	public void postWrite(PostVO postvo) throws Exception {
		communityPersistence.postWrite(postvo);
	}
	
	@Override // �Խù� ��� ��������
	public List<PostVO> getPostList() throws Exception {
//		System.out.println("===========CommunityServiceGetPost : "+postvo);
		return communityPersistence.getPostList();
	}
	
	@Override // �� �Խù� ��� ��������
	public List<PostVO> getFanPostList(int displayPost, int postNum) throws Exception {
		return communityPersistence.getFanPostList(displayPost, postNum);
	}
	
	@Override
	public int getFanPostCount() throws Exception {
		return communityPersistence.getFanPostCount();
	}
	
	@Override // �Խù� �ϳ� ��������
	public PostVO getPost(PostVO postVO) throws Exception {
		return communityPersistence.getPost(postVO);
	}
	
	@Override // �Խù� ����
	public void modifyPost(PostVO postVO) throws Exception {
		 communityPersistence.modifyPost(postVO);
	}
	
	@Override // �Խù� ����
	public void deletePost(int post_id) throws Exception {
		communityPersistence.deletePost(post_id);
	}
	
	@Override 
	public UserVO getLevel(UserVO uservo) throws Exception {
		return communityPersistence.getLevel(uservo);
	}
	
	@Override // ��� �ۼ�
	public void commentWrite(CommentVO commentVO) throws Exception {
		communityPersistence.commentWrite(commentVO);
	}
	
	@Override // ��� ��������
	public List<CommentVO> getComment(PostVO postVO) throws Exception {
		return communityPersistence.getComment(postVO);
	}
	
	@Override // �� ��� �� ��������
	public int totalComment(int post_id) throws Exception {
		return communityPersistence.totalComment(post_id);
	}
	
	@Override // ��� ����
	public CommentVO modifyComment(CommentVO commentVO) throws Exception {
		return communityPersistence.modifyComment(commentVO);
	}
	
	@Override // ��� ���� 
	public void commentdelete(int comment_id) throws Exception {
		communityPersistence.commentdelete(comment_id);
	}
	
	@Override // ���� �ۼ��� ��� ��������
	public CommentVO getNewComment(int post_id) throws Exception {
		return communityPersistence.getNewComment(post_id);
	}

	@Override // ���ƿ� ��ư
	public void likeToggle(PostVO postVO) throws Exception {
		communityPersistence.likeToggle(postVO);
	}

	@Override // �� ���ƿ� ����
	public int totalLike(PostVO postVO) throws Exception {
		return communityPersistence.totalLike(postVO);
	}

	@Override
	public int checkLike(PostVO postVO) throws Exception {
		return communityPersistence.checkLike(postVO);
	}
	
	

}
