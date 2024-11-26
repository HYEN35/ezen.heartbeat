package kr.heartbeat.community.service;

import java.util.List;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;


public interface CommunityService {
	// �Խù� �ۼ�
		public void postWrite(PostVO postvo) throws Exception;
		// �Խù� ���
		public List<PostVO> getPostList() throws Exception;
		// ������ �� �Խù� ���
		public List<PostVO> getFanPostList(int displayPost, int postNum) throws Exception;
		// �� �Խù� ����
		public int getFanPostCount() throws Exception;	
		// �Խù� �ϳ� ��������
		public PostVO getPost(PostVO postVO) throws Exception;
		// ��Ƽ��Ʈ ���� ��������
		public UserVO getLevel(UserVO uservo) throws Exception;
		// �Խù� ����
		public void modifyPost(PostVO postVO) throws Exception;
		// �Խù� ����
		public void deletePost(int post_id) throws Exception;
		// �ɹ��� ���� Ȯ��
		public UserVO checkMemberShipLevel(UserVO uservo) throws Exception;
		// ��� �ۼ�
		public void commentWrite(CommentVO commentVO) throws Exception;
		// ��� ���
		public List<CommentVO> getComment(PostVO postVO) throws Exception;
		// �� ��� ����
		public int totalComment(int post_id) throws Exception;
		// ��� ����
		public void commentdelete(int comment_id) throws Exception;
		// ���� �ۼ��� ��� ��������
		public CommentVO getNewComment(int post_id) throws Exception;
		// ��� ����
		public CommentVO modifyComment(CommentVO commentVO) throws Exception;
		// ���ƿ� ��ư
		public void likeToggle(PostVO postVO) throws Exception;
		// �� ���ƿ� ����
		public int totalLike(PostVO postVO) throws Exception;
		// ���ƿ� ����
		public int checkLike(PostVO postVO) throws Exception;
}
