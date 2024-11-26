package kr.heartbeat.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.heartbeat.notice.persistence.NoticePersistence;
import kr.heartbeat.vo.NoticeCommentVO;
import kr.heartbeat.vo.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticePersistence noticePersistence;
	
	@Override
	public void postNotice(NoticeVO noticeVO) throws Exception {
		noticePersistence.postNotice(noticeVO);
	}
	@Override // 공지 전체 게시물 가져오기
	public List<NoticeVO> getPost(int displayPost, int postNum) throws Exception {
		return noticePersistence.getPost(displayPost, postNum);
	}
	
	@Override // 관리자 게시물 가져오기
	public List<NoticeVO> getAdminNotice() throws Exception {
		return noticePersistence.getAdminNotice();
	}
	
	@Override // 유저 게시물 가져오기
	public List<NoticeVO> getUserNotice(int displayPost, int postNum) throws Exception {
		return noticePersistence.getUserNotice(displayPost,postNum);
	}
	@Override // 게시물 상세보기
	public NoticeVO getPostOne(int notice_id) throws Exception {
		return noticePersistence.getPostOne(notice_id);
	}
	@Override // 게시물 수정
	public void noticeModify(NoticeVO noticeVO) throws Exception{
		noticePersistence.noticeModify(noticeVO);
	}
	@Override // 게시물 삭제
	public void noticeDelete(int notice_id) throws Exception {
		noticePersistence.noticeDelete(notice_id);
	}
	@Override // 게시물 개수 가져오기
	public int getPostCount() throws Exception {
		return noticePersistence.getPostCount();
	}
	@Override // 댓글 작성
	public void commentWrite(NoticeCommentVO noticeCommentVO) throws Exception {
		noticePersistence.commentWrite(noticeCommentVO);
	}
	@Override // 댓글 목록 가져오기
	public List<NoticeCommentVO> getComment(int notice_id) throws Exception {
		return noticePersistence.getComment(notice_id);
	}
	@Override //댓글 수정
	public void updateComment(NoticeCommentVO noticeCommentVO) throws Exception {
		noticePersistence.updateComment(noticeCommentVO);
	}
	@Override // 댓글 삭제
	public void commentDelete(int notice_comment_id) throws Exception {
		noticePersistence.commentDelete(notice_comment_id);
	}
	

}
