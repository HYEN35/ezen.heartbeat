package kr.heartbeat.notice.service;

import java.util.List;

import kr.heartbeat.vo.NoticeCommentVO;
import kr.heartbeat.vo.NoticeVO;

public interface NoticeService {
	// 공지 게시물 작성
	public void postNotice(NoticeVO noticeVO) throws Exception;
	// 공지 전체 게시물 가져오기
	public List<NoticeVO> getPost(int displayPost, int postNum) throws Exception;
	// 공지 관리자 게시물 가져오기
	public List<NoticeVO> getAdminNotice() throws Exception;
	// 공지 유저 게시물 가져오기
	public List<NoticeVO> getUserNotice(int displayPost, int postNum, String searchType, String keyword) throws Exception;
	// 공지 게시물 상세보기
	public NoticeVO getPostOne(int notice_id) throws Exception;
	// 게시물 수정
	public void noticeModify(NoticeVO oticeVO) throws Exception;
	// 게시물 삭제
	public void noticeDelete(int notice_id) throws Exception;
	// 게시물 개수 가져오기
	public int getPostCount(String searchType, String keyword) throws Exception;
	// 댓글 작성
	public void commentWrite(NoticeCommentVO noticeCommentVO) throws Exception;
	// 댓글 목록 가져오기
	public List<NoticeCommentVO> getComment(int notice_id) throws Exception;
	// 댓글 수정
	public void updateComment(NoticeCommentVO noticeCommentVO) throws Exception;
	// 댓글 삭제
	public void commentDelete(int notice_comment_id) throws Exception;
	
}
