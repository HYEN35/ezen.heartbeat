package kr.heartbeat.community.service;

import java.util.List;
import java.util.Map;

import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;


public interface CommunityService {
	// 게시물 작성
	public void postWrite(PostVO postvo) throws Exception;
	// 게시물 목록
	public List<PostVO> getPostList() throws Exception;
	// 뉴진스 팬 게시물 목록
	public List<PostVO> getNewjeansFanPostList(int displayPost, int postNum) throws Exception;
	// 뉴진스 팬 게시물 개수
	public int getNewjeansFanPostCount() throws Exception;	
	// 있지 팬 게시물 목록
	public List<PostVO> getItzyFanPostList(int displayPost, int postNum) throws Exception;
	// 있지 팬 게시물 개수
	public int getItzyFanPostCount() throws Exception;	
	// 블랙핑크 팬 게시물 목록
	public List<PostVO> getBlackpinkFanPostList(int displayPost, int postNum) throws Exception;
	// 블랙핑크 팬 게시물 개수
	public int getBlackpinkFanPostCount() throws Exception;	
	// 게시물 하나 가져오기
	public PostVO getPost(PostVO postVO) throws Exception;
	// 구독중인 아티스트 이름 가져오기
	public String getArtistName(int artist_id) throws Exception;
	// 게시물 수정
	public void modifyPost(PostVO postVO) throws Exception;
	// 게시물 삭제
	public void deletePost(int post_id) throws Exception;
	//포스트 이미지 삭제
	public int deletePostImg(Map<String, Object> request) throws Exception;
	// 맴버십 레벨 확인
	public UserVO checkMemberShipLevel(UserVO uservo) throws Exception;
	// 댓글 작성
	public void commentWrite(CommentVO commentVO) throws Exception;
	// 댓글 목록
	public List<CommentVO> getComment(PostVO postVO) throws Exception;
	// 총 댓글 개수
	public int totalComment(int post_id) throws Exception;
	// 댓글 삭제
	public void commentdelete(int comment_id) throws Exception;
	// 새로 작성한 댓글 가져오기
	public CommentVO getNewComment(int post_id) throws Exception;
	// 댓글 수정
	public CommentVO modifyComment(CommentVO commentVO) throws Exception;
	// 좋아요 버튼
	public void likeToggle(PostVO postVO) throws Exception;
	// 총 좋아요 개수
	public int totalLike(PostVO postVO) throws Exception;
	// 좋아요 여부
	public int checkLike(PostVO postVO) throws Exception;

}
