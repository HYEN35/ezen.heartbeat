package kr.heartbeat.community.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	public String community(Model model,HttpSession session) {
		UserVO userVO = (UserVO) session.getAttribute("UserVO");
		UserVO dbuserVO = userServiceImpl.login(userVO);
		int level =2;

		model.addAttribute("uvo", dbuserVO);
		model.addAttribute("level", level);
		return "/community/community";
	}

	// 뉴진스 페이지 들어가면서 게시물 가져오는거
	@RequestMapping("/artist/newjeans")
	public String newjeans(@RequestParam("num")int num, Model model,HttpSession session) throws Exception {
		UserVO uservo = (UserVO) session.getAttribute("UserVO");
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getNewjeansFanPostCount()); // 뉴진스 팬 게시물 개수 
		List<PostVO> newjinsfanPosts = communityService.getNewjeansFanPostList(page.getDisplayPost(), page.getPostNum()); // 뉴진스 팬 게시물
		List<PostVO> postList = communityService.getPostList(); // 전체 게시물 
		String artist_name = communityService.getArtistName(uservo.getArtist_id()); // 구독중인 아티스트 이름 가져오기


		//artistVO art_name = communityService.getLevel(uservo);

		String url = null;
		List<PostVO> newjinsPosts = new ArrayList<>(); // 민지 게시물
		if (uservo.getEmail().equals("admin")) {
			// 게시물 나누기
			for (PostVO post : postList) {
				if (post.getArtist_id() == 20109) {
					if ("minji".equals(post.getEmail()) || "hanni".equals(post.getEmail()) || "danielle".equals(post.getEmail()) || "haerin".equals(post.getEmail()) || "hyein".equals(post.getEmail()) ) {
						newjinsPosts.add(post);
					}
				}
			}


			model.addAttribute("newjinsPosts", newjinsPosts);
			model.addAttribute("newjinsfanPosts", newjinsfanPosts);
			model.addAttribute("page", page);
			model.addAttribute("select", num);
			return "/community/artist/newjeans";
		}


		if (uservo.getArtist_id() == 20109) {
			// 게시물 나누기
			for (PostVO post : postList) {
				if (post.getArtist_id() == 20109) {
					if ("minji".equals(post.getEmail()) || "hanni".equals(post.getEmail()) || "danielle".equals(post.getEmail()) || "haerin".equals(post.getEmail()) || "hyein".equals(post.getEmail()) ) {
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
			model.addAttribute("artist_name", artist_name);
			url = "/community/community";
		}

		return url;
	}
	
	// 뉴진스 게시물 작성
	@PostMapping("/newjeansPostWrite")
	public String newjeansPostWrite(PostVO postvo, Model model, HttpServletRequest request,@RequestParam("post_Img") MultipartFile postImg) throws Exception {
		//프로필 이미지 저장 경로 지정
				String realPath="C:\\heartbeat-upload\\";
				String file1,file2="";
				
				if(postImg !=null && !postImg.isEmpty()) {
					String fileName=UUID.randomUUID().toString() + "_"+ postImg.getOriginalFilename() ;
					file1=realPath + fileName;
					postImg.transferTo(new File(file1));
					file2 =fileName;
					postvo.setPost_img(file2);
				}
				communityService.postWrite(postvo);
				
				return "redirect:/community/artist/newjeans?email="+postvo.getEmail()+"&num=1";
	}

	// 있지 페이지 들어가면서 게시물 가져오기
	@RequestMapping("/artist/itzy")
	public String itzy(@RequestParam("num")int num,Model model,HttpSession session) throws Exception {
		UserVO uservo = (UserVO) session.getAttribute("UserVO");
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getItzyFanPostCount()); // 있지 팬 게시물 개수
		List<PostVO> itzyFanPosts = communityService.getItzyFanPostList(page.getDisplayPost(), page.getPostNum()); // 있지 팬 게시물
		List<PostVO> postList = communityService.getPostList(); // 전체 게시물
		String artist_name = communityService.getArtistName(uservo.getArtist_id()); // 구독중인 아티스트 이름 가져오기
		

		String url = null;
		List<PostVO> itzyPosts = new ArrayList<>(); // 있지 게시물

		if ("admin".equals(uservo.getEmail())) {
			// 게시물 나누기
			for (PostVO post : postList) {
				if (post.getArtist_id() == 20117) {
					if ("chaeryeong".equals(post.getEmail()) || "lia".equals(post.getEmail()) || "ryujin".equals(post.getEmail()) || "yeji".equals(post.getEmail()) || "yuna".equals(post.getEmail()) ) {
						itzyPosts.add(post);
					}
				}
			}


			model.addAttribute("itzyPosts", itzyPosts);
			model.addAttribute("itzyFanPosts", itzyFanPosts);
			model.addAttribute("page", page);
			model.addAttribute("select", num);
			return "/community/artist/itzy";
		}

		if (uservo.getArtist_id() == 20117) {
			// 게시물 나누기
			for (PostVO post : postList) {
				if (post.getArtist_id() == 20117) {
					if ("chaeryeong".equals(post.getEmail()) || "lia".equals(post.getEmail()) || "ryujin".equals(post.getEmail()) || "yeji".equals(post.getEmail()) || "yuna".equals(post.getEmail()) ) {
						itzyPosts.add(post);
					}
				}
			}


			model.addAttribute("itzyPosts", itzyPosts);
			model.addAttribute("itzyFanPosts", itzyFanPosts);
			model.addAttribute("page", page);
			model.addAttribute("select", num);
			url = "/community/artist/itzy";
		} else {
			model.addAttribute("artist_name", artist_name);
			url = "/community/community";
		}

		return url;

	}
	
	// 있지 게시물 작성
	@PostMapping("/itzyPostWrite")
	public String itzyPostWrite(PostVO postvo, Model model, HttpServletRequest request,@RequestParam("post_Img") MultipartFile postImg) throws Exception {
		//프로필 이미지 저장 경로 지정
		String realPath="C:\\heartbeat-upload\\";
		String file1,file2="";
		
		if(postImg !=null && !postImg.isEmpty()) {
			String fileName=UUID.randomUUID().toString() + "_"+ postImg.getOriginalFilename() ;
			file1=realPath + fileName;
			postImg.transferTo(new File(file1));
			file2 =fileName;
			postvo.setPost_img(file2);
		}
		communityService.postWrite(postvo);
		
		return "redirect:/community/artist/itzy?email="+postvo.getEmail()+"&num=1";
	}
	
	// 블략핑크 페이지 들어가면서 게시물 가져오기
	@RequestMapping("/artist/blackpink")
	public String blackpink(@RequestParam("num")int num,Model model,HttpSession session) throws Exception {
		UserVO uservo = (UserVO) session.getAttribute("UserVO");
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getBlackpinkFanPostCount()); // 있지 팬 게시물 개수 
		List<PostVO> blackpinkFanPosts = communityService.getBlackpinkFanPostList(page.getDisplayPost(), page.getPostNum()); // 있지 팬 게시물
		List<PostVO> postList = communityService.getPostList(); // 전체 게시물 
		String artist_name = communityService.getArtistName(uservo.getArtist_id()); // 구독중인 아티스트 이름 가져오기
		
		

		String url = null;
		List<PostVO> blackpinkPosts = new ArrayList<>(); 
		
		if ("admin".equals(uservo.getEmail())) {
			for (PostVO post : postList) {
				if (post.getArtist_id() == 20119) {
					if ("jennie".equals(post.getEmail()) || "jisoo".equals(post.getEmail()) || "lisa".equals(post.getEmail()) || "rose".equals(post.getEmail())) {
						blackpinkPosts.add(post);
					}
				}
			}
			
			
			model.addAttribute("blackpinkPosts", blackpinkPosts);
			model.addAttribute("blackpinkFanPosts",blackpinkFanPosts);
			model.addAttribute("page", page);
			model.addAttribute("select", num);
			return "/community/artist/blackpink";
		}

		if (uservo.getArtist_id() == 20119) {
			// 게시물 나누기
			for (PostVO post : postList) {
				if (post.getArtist_id() == 20119) {
					if ("jennie".equals(post.getEmail()) || "jisoo".equals(post.getEmail()) || "lisa".equals(post.getEmail()) || "rose".equals(post.getEmail())) {
						blackpinkPosts.add(post);
					}
				}
			}
			
			
			model.addAttribute("blackpinkPosts", blackpinkPosts);
			model.addAttribute("blackpinkFanPosts",blackpinkFanPosts);
			model.addAttribute("page", page);
			model.addAttribute("select", num);
			url = "/community/artist/blackpink";
		} else {
			model.addAttribute("artist_name", artist_name);
			url = "/community/community";
		}

		return url;
		
	}
	
	// 블랙핑크 게시물 작성
	@PostMapping("/blackpinkPostWrite")
	public String blackpinkPostWrite(PostVO postvo, Model model, HttpServletRequest request,@RequestParam("post_Img") MultipartFile postImg) throws Exception {
		//프로필 이미지 저장 경로 지정
		String realPath="C:\\heartbeat-upload\\";
		String file1,file2="";
		
		if(postImg !=null && !postImg.isEmpty()) {
			String fileName=UUID.randomUUID().toString() + "_"+ postImg.getOriginalFilename() ;
			file1=realPath + fileName;
			postImg.transferTo(new File(file1));
			file2 =fileName;
			postvo.setPost_img(file2);
		}
		communityService.postWrite(postvo);
		
		return "redirect:/community/artist/blackpink?email="+postvo.getEmail()+"&num=1";
	}
	

	// 뉴진스 게시물 삭제
	@PostMapping("/deletePost")
	public String deletePost(@RequestParam("post_id") int post_id, HttpServletRequest request) throws Exception {
		communityService.deletePost(post_id);
		
		// 이전 페이지 URL 얻기
	    String referer = request.getHeader("Referer");
	    
	    
		return "redirect:"+referer;
	}
	// 게시물에서 이미지만 삭제
	@PostMapping("/deletePostImage")
	@ResponseBody
	public Map<String,Object> deletePostImage(@RequestBody Map<String, Object> request) throws Exception{
 		communityService.deletePostImg(request);
 		Map<String,Object> map = new HashMap<String, Object>();
 		map.put("post_id",request.get("post_id").toString());
	    return map;
	}

	// 게시물 수정
	@PostMapping("/modifyPost")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> modifyPost(PostVO postVO, @RequestParam(value="post_img_name", required=false) MultipartFile postImgFile) throws Exception {
	    if (postImgFile != null && !postImgFile.isEmpty()) {
	        // 이미지가 있을 경우 이미지 파일 처리
	        String fileName = saveImage(postImgFile);  // 이미지 저장 메서드
	        postVO.setPost_img(fileName);
	    }
	    
	    Map<String,Object> reMap = new HashMap<String,Object>(); reMap.put("post_img",postVO.getPost_img());
	    
	    communityService.modifyPost(postVO);  // 게시물 수정 서비스 호출
	    return ResponseEntity.ok(reMap);  // 수정 성공 응답
	}

	private String saveImage(MultipartFile file) throws IOException {
	    String uploadDir = "C:\\heartbeat-upload\\";
	    String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
	    Path path = Paths.get(uploadDir + fileName);
	    Files.copy(file.getInputStream(), path);
	    return fileName;
	}
	
	
	// 유저 게시물 상세보기
	@RequestMapping("/getUserPost")
	public String getUserPost(PostVO postVO, Model model) throws Exception {
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

		// 결과를 JSON 형식으로 반환
		response.put("status", "success"); // 성공 여부
		response.put("comment_id", comment_id); // 삭제된 댓글 ID
		response.put("totalComment", totalComment);

		return ResponseEntity.ok(response); // 클라이언트에게 성공 메시지 전달
	}
	
	
	@PostMapping("/likeToggle") // 좋아요 버튼
	@ResponseBody 
	public int likeToggle(PostVO postVO,Map<String, Object> response) throws Exception {

		communityService.likeToggle(postVO);
		Integer totalLike = communityService.totalLike(postVO); 
		
		// 응답 데이터 설정
	    response.clear(); // 기존 데이터 제거 (불필요한 데이터가 남아 있을 수 있으므로)
		
		response.put("totalLike", totalLike);

		return totalLike;
	}
	
}
