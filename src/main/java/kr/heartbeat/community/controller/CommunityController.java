package kr.heartbeat.community.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.heartbeat.community.service.CommunityService;
import kr.heartbeat.service.UserServiceImpl;
import kr.heartbeat.vo.CommentVO;
import kr.heartbeat.vo.PageDTO;
import kr.heartbeat.vo.PostVO;
import kr.heartbeat.vo.UserVO;

@Controller
@RequestMapping("/community/*")
public class CommunityController {

	@Autowired
	private CommunityService communityService;
	@Inject
	private UserServiceImpl userServiceImpl;

	@GetMapping("/community")
	public String community(UserVO userVO,Model model) {
		UserVO dbuserVO = userServiceImpl.login(userVO);
		int level =2; 
		System.out.println(dbuserVO);
		
		model.addAttribute("uvo", dbuserVO);
		model.addAttribute("level", level);
		return "/community/community";
	}

	// 뉴진스 페이지 들어가면서 게시물 가져오는거
	@RequestMapping("/artist/newjeans")
	public String newjeans(@RequestParam("num")int num, PostVO postvo, Model model, UserVO uservo) throws Exception {
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getFanPostCount()); // 뉴진스 팬 게시물 개수 
		List<PostVO> newjinsfanPosts = communityService.getFanPostList(page.getDisplayPost(), page.getPostNum()); // 뉴진스 팬 게시물
		List<PostVO> postList = communityService.getPostList(); // 전체 게시물 
		UserVO artist_id = communityService.getLevel(uservo); 
		
		
		//artistVO art_name = communityService.getLevel(uservo);

		String url = null;
		List<PostVO> newjinsPosts = new ArrayList<>(); // 민지 게시물

		if (artist_id.getArtist_id() == 2) {
			// 게시물 나누기
			for (PostVO post : postList) {
				if (post.getArtist_id() == 2) {
					if ("minji".equals(post.getEmail()) || "haerin".equals(post.getEmail())) {
						newjinsPosts.add(post);
					} 
				}
			}
			
			
			model.addAttribute("newjinsPosts", newjinsPosts);
			model.addAttribute("newjinsfanPosts", newjinsfanPosts);
			model.addAttribute("page", page);
			model.addAttribute("select", num);
			url = "/community/artist/newjeans";
		} else {

			url = "/community/community";
		}

		return url;
	}

	// 게시물 작성
	@PostMapping("/postWrite")
	public String postWrite(PostVO postvo, Model model, HttpServletRequest request) throws Exception {
		System.out.println("===========CommunityController : "+postvo);
		communityService.postWrite(postvo);
		int num = 1;
		
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getFanPostCount());
		List<PostVO> newjinsfanPosts = communityService.getFanPostList(page.getDisplayPost(), page.getPostNum()); // 뉴진스 팬 게시물
		List<PostVO> postList = communityService.getPostList();

		List<PostVO> newjinsPosts = new ArrayList<>(); // 민지 게시물

		// 게시물 나누기
		for (PostVO post : postList) {
			if (post.getArtist_id() == 2) {
				if ("minji".equals(post.getEmail()) || "haerin".equals(post.getEmail())) {
					newjinsPosts.add(post);
				} 
			}
		}
		
		page.setNum(num);
		page.setCount(newjinsfanPosts.size());
		

		model.addAttribute("newjinsPosts", newjinsPosts);
		model.addAttribute("newjinsfanPosts", newjinsfanPosts);
		model.addAttribute("page", page);
		model.addAttribute("select", num);

		String referer = request.getHeader("Referer");
		return "redirect:"+referer;
	}
	
	// 게시물 수정
	@PostMapping("/modifyPost")
	@ResponseBody
	public String modifyPost(PostVO postVO) throws Exception {
		communityService.modifyPost(postVO);
		return "success";
			
	}

	// 게시물 삭제
	@PostMapping("/deletePost")
	public String deletePost(@RequestParam("post_id") int post_id, HttpServletRequest request) throws Exception {
		communityService.deletePost(post_id);
		
		// 이전 페이지 URL 얻기
	    String referer = request.getHeader("Referer");
	    
	    
		return "redirect:"+referer;
	}

	// 유저 게시물 상세보기
	@RequestMapping("/getUserPost")
	public String getUserPost(PostVO postVO, Model model) throws Exception {
		System.out.println("=============유저 게시물 상세보기"+postVO);

		PostVO dbpost = communityService.getPost(postVO); // 게시물 정보
		List<CommentVO> commentList = communityService.getComment(postVO); // 댓글 목록
		Integer totlaCommnet = communityService.totalComment(postVO.getPost_id()); // 총 댓글 개수

		model.addAttribute("totalComment", totlaCommnet);
		model.addAttribute("commentList", commentList);
		model.addAttribute("PostVO", dbpost);

		return "/popup/pop-post-fan";
	}

	// 아티스트 게시물 상세보기
	@PostMapping("/getArtistPost")
	public String getArtistPost(PostVO postVO, Model model) throws Exception{
		System.out.println("=============아티스트 게시물 상세보기"+postVO);
		
		PostVO dbpost = communityService.getPost(postVO); // 게시물 정보
		List<CommentVO> commentList = communityService.getComment(postVO); // 댓글 목록
		Integer totalCommnet = communityService.totalComment(postVO.getPost_id()); // 총 댓글 개수
		Integer totalLike = communityService.totalLike(postVO); // 총 좋아요 개수
		Integer checkLike = communityService.checkLike(postVO); // 좋아요 여부 확인

		model.addAttribute("totalComment", totalCommnet);
		model.addAttribute("commentList", commentList);
		model.addAttribute("PostVO", dbpost);
		model.addAttribute("totalLike", totalLike);
		model.addAttribute("checkLike", checkLike);
		System.out.println(checkLike);
		System.out.println(dbpost);


		return "/popup/pop-post-artist";
	}

	// 게시물 새로고침
	@PostMapping("/resetPost")
	@ResponseBody  // 이 어노테이션을 추가하여 JSON 응답을 반환하도록 함
	public Map<String, Object> resetPost(PostVO postVO, Model model) throws Exception {
	
		Integer checkLike = communityService.checkLike(postVO); // 좋아요 여부 확인
		
	    // JSON으로 데이터를 반환
	    Map<String, Object> response = new HashMap<>();
	    response.put("checkLike", checkLike);
	    
	    return response;
	}


	// 댓글 작성
	@PostMapping("/commentWrites")
	@ResponseBody
	public String commentWrite(CommentVO commentVO, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		communityService.commentWrite(commentVO);
	    
		return "success";
	}

	// 댓글 작성
	@PostMapping("/commentWrite")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> commentWrite(CommentVO commentVO, Map<String, Object> response) throws Exception {
	    // 댓글 저장
	    communityService.commentWrite(commentVO);

	    // 새로 작성된 댓글 정보 가져오기
	    CommentVO newComment = communityService.getNewComment(commentVO.getPost_id());
	    int totalComment = communityService.totalComment(commentVO.getPost_id());
	    
	    // 응답 데이터 설정
	    response.clear(); // 기존 데이터 제거 (불필요한 데이터가 남아 있을 수 있으므로)

	    // 응답 데이터 설정
	    response.put("data", "success");
	    // 새 댓글 정보만 넣기 (commentVO 객체를 그대로 넣지 않음)
//	    response.put("newComment", newComment.getComment()); // 새 댓글 내용
//	    response.put("newnick", newComment.getNickname());   // 새 댓글 작성자
//	    response.put("newComment_date", newComment.getComment_date()); // 새 댓글 작성 시간
//	    response.put("totalComment", totalComment); // 최신 댓글 수
	    System.out.println("========댓글 작성 response값 확인?? :"+ response);

	    return ResponseEntity.ok(response); // 클라이언트에게 성공 메시지 전달
	}
	
	@PostMapping("/modifyComment") // 댓글 수정
	@ResponseBody
	public String modifyComment(CommentVO commentVO) throws Exception {
		communityService.modifyComment(commentVO);
		
		return "success";
	}

	@PostMapping("/commentdelete") // 댓글 삭제
	@ResponseBody // JSON 응답을 반환하기 위해 @ResponseBody 추가
	public ResponseEntity<Map<String, Object>> commentdelete(int comment_id, int post_id, Map<String, Object> response) throws Exception {
		communityService.commentdelete(comment_id);
		Integer totalComment = communityService.totalComment(post_id);
		System.out.println(post_id);
		System.out.println(comment_id);

		// 결과를 JSON 형식으로 반환
		response.put("status", "success"); // 성공 여부
		response.put("comment_id", comment_id); // 삭제된 댓글 ID
		response.put("totalComment", totalComment);

		return ResponseEntity.ok(response); // 클라이언트에게 성공 메시지 전달
	}
	
	
	@PostMapping("/likeToggle") // 좋아요 버튼
	@ResponseBody 
	public int likeToggle(PostVO postVO,Map<String, Object> response) throws Exception {
		System.out.println("게시물번호랑 이메일 전달 확인 " + postVO);

		communityService.likeToggle(postVO);
		Integer totalLike = communityService.totalLike(postVO); 
		
		// 응답 데이터 설정
	    response.clear(); // 기존 데이터 제거 (불필요한 데이터가 남아 있을 수 있으므로)
		
		response.put("totalLike", totalLike);

		return totalLike;
	}
	
}
