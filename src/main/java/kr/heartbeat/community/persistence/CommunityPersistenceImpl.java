package kr.heartbeat.community.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

@Repository
public class CommunityPersistenceImpl implements CommunityPersistence {

	@Inject
	private SqlSession sql;
	private HttpSession session;
	
	private static String namespace = "kr.heartbeat.mappers.community";
	
	@Override // �ɹ��� ���� ��������
	public UserVO checkMemberShipLevel(UserVO uservo) throws Exception {
		return sql.selectOne(namespace+".checkMemberShipLevel", uservo);
	}
	
	@Override // �Խù� �ۼ�
	public void postWrite(PostVO postvo) throws Exception {
		sql.insert(namespace+".postWrite", postvo);
	}
	
	@Override // ��ü �Խù� ��� ��������
	public List<PostVO> getPostList() throws Exception {
//		System.out.println("===========CommunityPersistenceImplGetPost : "+postvo);

		return sql.selectList(namespace+".getPostList");
	}
	
	@Override // ������ �� �Խù�  ��������
	public List<PostVO> getFanPostList(int displayPost, int postNum) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("minji", "minji");
	    map.put("haerin", "haerin");
	    map.put("displayPost", displayPost);
	    map.put("postNum", postNum);
	    return sql.selectList(namespace+".getFanPostList", map);
	}
	
	@Override //�� �Խù� ���� �������� 
	public int getFanPostCount() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("minji", "minji");
		map.put("haerin", "haerin");
		int a = sql.selectOne(namespace+".getFanPostCount",map);
		System.out.println("====================�� �Խù� ���� ��������" + a);
		return a;
	}
	
	@Override // �Խù� �ϳ� ��������
	public PostVO getPost(PostVO postVO) throws Exception {
		return sql.selectOne(namespace+".getPost", postVO);
	}
	
	@Override // �Խù� ����
	public void modifyPost(PostVO postVO) throws Exception {
		sql.update(namespace+".modifyPost", postVO);
		
	}
	
	@Override // �Խù� ����
	public void deletePost(int post_id) throws Exception {
		sql.delete(namespace+".deletePost", post_id);
	}
	
	@Override // ���� ��� ��������
	public UserVO getLevel(UserVO uservo) throws Exception {
		return sql.selectOne(namespace+".getLevel",uservo);
	}
	
	@Override // ��� �ۼ�
	public void commentWrite(CommentVO commentVO) throws Exception {
		sql.insert(namespace+".commentWrite", commentVO);
	}
	
	@Override // ��� �������� 
	public List<CommentVO> getComment(PostVO postVO) throws Exception {
		return sql.selectList(namespace+".getComment", postVO);
	}
	
	@Override // �� ��� �� ��������
	public int totalComment(int post_id) throws Exception {
		return sql.selectOne(namespace+".totalComment",post_id );
	}
	
	@Override // ��� ����
	public CommentVO modifyComment(CommentVO commentVO) throws Exception {
		// ����� ���� ���� (������Ʈ)
	    int result = sql.update(namespace + ".modifyComment", commentVO);

	    // ������Ʈ�� ���������� �Ǿ��ٸ�, �ش� ����� �ٽ� ��ȸ�Ͽ� ��ȯ
	    if (result > 0) {
	        // ������ ����� �ٽ� ��ȸ
	        return sql.selectOne(namespace + ".getmodifyComment", commentVO.getComment_id());
	    } else {
	        // ������Ʈ�� ������ ��� (0�̸� ����)
	        return null;  // �Ǵ� ���� ó��
	    }
	}
	
	@Override // ��� ����
	public void commentdelete(int comment_id) throws Exception {
		sql.delete(namespace+".commentdelete", comment_id);
	}
	
	@Override // ���� �ۼ��� ��� ��������
	public CommentVO getNewComment(int post_id) throws Exception {
		System.out.println("===========�۽ý��Ͻ� Ȯ��"+post_id);
		return sql.selectOne(namespace+".getNewComment", post_id);
	}

	@Override // ���ƿ� ��ư
	public void likeToggle(PostVO postVO) throws Exception {
		System.out.println(postVO);
		int result = sql.selectOne(namespace+".likeToggle", postVO);
		System.out.println(result);
		if (result > 0 ) {
			sql.delete(namespace+".deleteLike", postVO);
		} else {
			sql.insert(namespace+".addLike", postVO);
		}
		
	}

	@Override
	public int totalLike(PostVO postVO) throws Exception {
		return sql.selectOne(namespace+".totalLike", postVO);
	}

	@Override
	public int checkLike(PostVO postVO) throws Exception {
		System.out.println("===============checkLike:" +postVO);
		return sql.selectOne(namespace+".likeToggle", postVO);
	}


}
