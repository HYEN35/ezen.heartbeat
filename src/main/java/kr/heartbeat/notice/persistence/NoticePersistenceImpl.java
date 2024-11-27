package kr.heartbeat.notice.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.heartbeat.vo.NoticeCommentVO;
import kr.heartbeat.vo.NoticeVO;

@Repository
public class NoticePersistenceImpl implements NoticePersistence {
	
	@Inject
	private SqlSession sql;
	
	private static String namespace = "kr.heartbeat.mappers.notice";
	
	
	@Override //공지사항 게시물 작성
	public void postNotice(NoticeVO noticeVO) throws Exception{
		sql.insert(namespace+".postNotice", noticeVO);
	}
	@Override // 공지 전체 게시물 가져오기
	public List<NoticeVO> getPost(int displayPost, int postNum) throws Exception {
		HashMap <String,Object> map = new HashMap<String, Object>();
		map.put("displayPost", displayPost);
		map.put("postNum", postNum);
		return sql.selectList(namespace+".getPost", map);
	}
	
	@Override // 공지 관리자 게시물 가져오기
	public List<NoticeVO> getAdminNotice() throws Exception {
		return sql.selectList(namespace+".getAdminNotice");
	}
	@Override // 공지 유저 게시물 가져오기
	public List<NoticeVO> getUserNotice(int displayPost, int postNum,String searchType, String keyword) throws Exception {
		HashMap <String,Object> map = new HashMap<String, Object>();
		map.put("displayPost", displayPost);
		map.put("postNum", postNum);
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		return sql.selectList(namespace+".getUserNotice",map);
	}
	
	@Override // 게시물 상세보기
	public NoticeVO getPostOne(int notice_id) throws Exception{
		return sql.selectOne(namespace+".getPostOne", notice_id);
	}
	
	@Override // 게시물 수정
	public void noticeModify(NoticeVO noticeVO) throws Exception {
		sql.update(namespace+".noticeModify", noticeVO);
	}
	
	@Override // 게시물 삭제
	public void noticeDelete(int notice_id) throws Exception {
		sql.delete(namespace+".noticeDelete", notice_id);
	}
	@Override // 게시물 개수
	public int getPostCount(String searchType, String keyword) throws Exception {
		HashMap <String,Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		return sql.selectOne(namespace+".getPostCount",map);
	}
	@Override // 댓글 작성
	public void commentWrite(NoticeCommentVO noticeCommentVO) throws Exception{
		sql.insert(namespace+".commentWrite", noticeCommentVO);
	}
	@Override // 댓글 목록 가져오기
	public List<NoticeCommentVO> getComment(int notice_id) throws Exception {
		return sql.selectList(namespace+".getComment", notice_id);
	}
	@Override // 댓글 수정
	public void updateComment(NoticeCommentVO noticeCommentVO) throws Exception {
		sql.update(namespace+".updateComment", noticeCommentVO);
	}
	@Override // 댓글 삭제
	public void commentDelete(int notice_comment_id) throws Exception {
		sql.delete(namespace+".commentDelete", notice_comment_id);
	}
}
