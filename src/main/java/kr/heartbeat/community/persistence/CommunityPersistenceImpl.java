package kr.heartbeat.community.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override // 맴버십 레벨 가져오기
	public UserVO checkMemberShipLevel(UserVO uservo) throws Exception {
		return sql.selectOne(namespace+".checkMemberShipLevel", uservo);
	}
	
	@Override // 게시물 작성
	public void postWrite(PostVO postvo) throws Exception {
		sql.insert(namespace+".postWrite", postvo);
	}
	
	@Override // 전체 게시물 목록 가져오기
	public List<PostVO> getPostList() throws Exception {
		return sql.selectList(namespace+".getPostList");
	}
	
	@Override // 뉴진스 팬 게시물  가져오기
	public List<PostVO> getNewjeansFanPostList(int displayPost, int postNum) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("minji", "minji");
	    map.put("haerin", "haerin");
	    map.put("hanni", "hanni");
	    map.put("danielle", "danielle");
	    map.put("hyein", "hyein");
	    map.put("hanni", "hanni");
	    map.put("danielle", "danielle");
	    map.put("hyein", "hyein");
	    map.put("displayPost", displayPost);
	    map.put("postNum", postNum);
	    return sql.selectList(namespace+".getNewjeansFanPostList", map);
	}
	
	@Override //뉴진스 팬 게시물 개수 가져오기 
	public int getNewjeansFanPostCount() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("minji", "minji");
		map.put("haerin", "haerin");
		map.put("hanni", "hanni");
	    map.put("danielle", "danielle");
	    map.put("hyein", "hyein");
		int count = sql.selectOne(namespace+".getNewjeansFanPostCount",map);
		return count;
	}
	
	@Override // 있지 팬 게시물  가져오기
	public List<PostVO> getItzyFanPostList(int displayPost, int postNum) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("chaeryeong", "chaeryeong");
	    map.put("lia", "lia");
	    map.put("ryujin", "ryujin");
	    map.put("yeji", "yeji");
	    map.put("yuna", "yuna");
	    map.put("displayPost", displayPost);
	    map.put("postNum", postNum);
	    return sql.selectList(namespace+".getItzyFanPostList", map);
	}
	
	@Override //있지 팬 게시물 개수 가져오기 
	public int getItzyFanPostCount() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chaeryeong", "chaeryeong");
	    map.put("lia", "lia");
	    map.put("ryujin", "ryujin");
	    map.put("yeji", "yeji");
	    map.put("yuna", "yuna");
		int count = sql.selectOne(namespace+".getItzyFanPostCount",map);
		return count;
	}
	
	@Override // 블랙핑크 팬 게시물  가져오기
	public List<PostVO> getBlackpinkFanPostList(int displayPost, int postNum) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
	    map.put("jennie", "jennie");
	    map.put("jisoo", "jisoo");
	    map.put("lisa", "lisa");
	    map.put("rose", "rose");
	    map.put("displayPost", displayPost);
	    map.put("postNum", postNum);
	    return sql.selectList(namespace+".getBlackpinkFanPostList", map);
	}
	
	@Override //블랙핑크 팬 게시물 개수 가져오기 
	public int getBlackpinkFanPostCount() throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("jennie", "jennie");
	    map.put("jisoo", "jisoo");
	    map.put("lisa", "lisa");
	    map.put("rose", "rose");
		int count = sql.selectOne(namespace+".getBlackpinkFanPostCount",map);
		return count;
	}
	
	
	@Override // 게시물 하나 가져오기
	public PostVO getPost(PostVO postVO) throws Exception {
		return sql.selectOne(namespace+".getPost", postVO);
	}
	
	@Override // 게시물 수정
	public void modifyPost(PostVO postVO) throws Exception {
		sql.update(namespace+".modifyPost", postVO);
		
	}
	
	@Override // 게시물 삭제
	public void deletePost(int post_id) throws Exception {
		sql.delete(namespace+".deletePost", post_id);
	}
	
	@Override // 게시물에서 이미지만 삭제
	public int deletePostImg(Map<String, Object> request) throws Exception{
		return sql.delete(namespace+".deletePostImg",request);
	}
	
	@Override // 구독 중인 아티스트 이름 가져오기
	public String getArtistName(int artist_id) throws Exception {
		return sql.selectOne(namespace+".getArtistName",artist_id);
	}
	
	@Override // 댓글 작성
	public void commentWrite(CommentVO commentVO) throws Exception {
		sql.insert(namespace+".commentWrite", commentVO);
	}
	
	@Override // 댓글 가져오기 
	public List<CommentVO> getComment(PostVO postVO) throws Exception {
		return sql.selectList(namespace+".getComment", postVO);
	}
	
	@Override // 총 댓글 수 가져오기
	public int totalComment(int post_id) throws Exception {
		return sql.selectOne(namespace+".totalComment",post_id );
	}
	
	@Override // 댓글 수정
	public CommentVO modifyComment(CommentVO commentVO) throws Exception {
		// 댓글을 먼저 수정 (업데이트)
	    int result = sql.update(namespace + ".modifyComment", commentVO);

	    // 업데이트가 성공적으로 되었다면, 해당 댓글을 다시 조회하여 반환
	    if (result > 0) {
	        // 수정된 댓글을 다시 조회
	        return sql.selectOne(namespace + ".getmodifyComment", commentVO.getComment_id());
	    } else {
	        // 업데이트가 실패한 경우 (0이면 실패)
	        return null;  // 또는 예외 처리
	    }
	}
	
	@Override // 댓글 삭제
	public void commentdelete(int comment_id) throws Exception {
		sql.delete(namespace+".commentdelete", comment_id);
	}
	
	@Override // 새로 작성한 댓글 가져오기
	public CommentVO getNewComment(int post_id) throws Exception {
		return sql.selectOne(namespace+".getNewComment", post_id);
	}

	@Override // 좋아요 버튼
	public void likeToggle(PostVO postVO) throws Exception {
		int result = sql.selectOne(namespace+".likeToggle", postVO);
		
		if (result > 0 ) {
			sql.delete(namespace+".deleteLike", postVO);
		} else {
			sql.insert(namespace+".addLike", postVO);
		}
		
	}

	@Override // 좋아요 개수
	public int totalLike(PostVO postVO) throws Exception {
		return sql.selectOne(namespace+".totalLike", postVO);
	}

	@Override // 좋아요 여부
	public int checkLike(PostVO postVO) throws Exception {
		return sql.selectOne(namespace+".likeToggle", postVO);
	}
	

}
