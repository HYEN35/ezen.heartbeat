package kr.heartbeat.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.heartbeat.notice.service.NoticeService;
import kr.heartbeat.vo.NoticeCommentVO;
import kr.heartbeat.vo.NoticeVO;
import kr.heartbeat.vo.PageDTO;

@Controller
@RequestMapping("/notice/*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	// 공지 페이지 들어가면서 게시물 가져오기
	@RequestMapping(value="/notice", method = RequestMethod.GET) 
	public String notice(int num, String searchType, String keyword, Model model) throws Exception{
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(noticeService.getPostCount(searchType,keyword)); // 뉴진스 팬 게시물 개수 
		page.setSearchType(searchType);
		page.setKeyword(keyword);

		
		List<NoticeVO> adminPost = noticeService.getAdminNotice(); 
		List<NoticeVO> userPost = noticeService.getUserNotice(page.getDisplayPost(), page.getPostNum(),searchType,keyword);
		
		
		model.addAttribute("adminPost", adminPost);		
		model.addAttribute("userPost", userPost);		
		model.addAttribute("page", page);
		model.addAttribute("select", num);	
		model.addAttribute("searchType", searchType);	
		model.addAttribute("keyword", keyword);	
		
		return "heartbeat/notice";	
		
	}
	
	
	@GetMapping("/postNotice")
	public String postNotice() {
		return "/heartbeat/noticePost";
	}
	
	@PostMapping("/noticeWrite") // 게시물 작성
	public String postNotice(NoticeVO noticeVO) throws Exception {
		noticeService.postNotice(noticeVO);

		
		return "redirect:/notice/notice?num=1";
	}
	
	@RequestMapping("/getPostOne") // 게시물 상세보기
	public String getPostOne(@RequestParam("notice_id")int notice_id,int num,String searchType, String keyword, Model model)throws Exception {
		NoticeVO noticeVO = noticeService.getPostOne(notice_id);
		List<NoticeCommentVO> commentVO = noticeService.getComment(notice_id);
		
		
		model.addAttribute("num", num);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("noticeVO", noticeVO);
		model.addAttribute("searchType", searchType);	
		model.addAttribute("keyword", keyword);	

		return "/heartbeat/noticeShow";
	}
	
	@PostMapping("/noticeModifyShow") // 게시물 수정 페이지 이동
	public String noticeModifyShow(int notice_id,int num,String searchType, String keyword, Model model) throws Exception{
		NoticeVO noticeVO = noticeService.getPostOne(notice_id);
		
		model.addAttribute("num", num);
		model.addAttribute("noticeVO", noticeVO);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		return "/heartbeat/noticeModify";
	}
	
	@PostMapping("/noticeModify") // 게시물 수정
	public String noticeModify(@RequestParam("num")int num,NoticeVO noticeVO,
								@RequestParam("searchType")String searchType, 
								@RequestParam("keyword")String keyword,Model model) throws Exception{
		
		noticeService.noticeModify(noticeVO);
		NoticeVO dbnoticeVO = noticeService.getPostOne(noticeVO.getNotice_id());
		List<NoticeCommentVO> commentVO = noticeService.getComment(dbnoticeVO.getNotice_id());
		model.addAttribute("num", num);
		model.addAttribute("noticeVO", dbnoticeVO);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		return "/heartbeat/noticeShow";
	}
	
	@RequestMapping("/noticeDelete") // 게시물 삭제
	public String noticeDelete(@RequestParam("notice_id")int notice_id) throws Exception{
		noticeService.noticeDelete(notice_id);

		return "redirect:/notice/notice?num=1";
	}
	
	@PostMapping("/commentWrite") // 댓글 작성
	public String commentWrite(@RequestParam("num")int num,NoticeCommentVO noticeCommentVO,Model model) throws Exception{
		noticeService.commentWrite(noticeCommentVO);
		NoticeVO noticeVO = noticeService.getPostOne(noticeCommentVO.getNotice_id());
		List<NoticeCommentVO> commentVO = noticeService.getComment(noticeCommentVO.getNotice_id());
		
		model.addAttribute("num", num);
		model.addAttribute("commentVO", commentVO);
		model.addAttribute("noticeVO", noticeVO);
		return "redirect:/notice/getPostOne?notice_id="+noticeCommentVO.getNotice_id()+"&num=1";
	}
	
	@PostMapping("/commentUpdate") // 댓글 수정
	@ResponseBody
	public String commentUpdate(NoticeCommentVO noticeCommentVO) throws Exception{
		noticeService.updateComment(noticeCommentVO);
		return "댓글 수정 성공";
	}
	
	@PostMapping("/commentDelete") // 댓글삭제
	@ResponseBody
	public String commentDelete(int notice_comment_id)throws Exception {
		noticeService.commentDelete(notice_comment_id);
		return "success";
	}
	
}