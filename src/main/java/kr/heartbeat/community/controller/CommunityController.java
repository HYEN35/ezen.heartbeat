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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
		System.out.println(dbuserVO);
		model.addAttribute("uvo", dbuserVO);
		return "/community/community";
	}

	// 뉴진스 페이지 들어가면서 게시물 가져오는거
	@RequestMapping("/artist/newjeans")
	public String newjeans(@RequestParam("num")int num, PostVO postvo, Model model, UserVO uservo) throws Exception {
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getFanPostCount()); // 뉴진스 팬 게시물 개수 
		List<PostVO> newjinsfanPosts = communityService.getFanPostList(page.getDisplayPost(), page.getPostNum()); // �돱吏꾩뒪 �뙩 寃뚯떆臾�
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
	public String postWrite(PostVO postvo, Model model, HttpServletRequest request,@RequestParam("postImg")MultipartFile[] postImg) throws Exception {
		System.out.println("===========CommunityController : "+postvo);
		
		String uri = "";

        // ** MultipartFile (프로필 이미지 업로드) ** 
        String realPath = "C:\\workspace\\project\\src\\main\\webapp\\resources\\upload\\"; // 파일 저장 경로
        String file1, file2 = "";

        MultipartFile[] uploadfilef = postvo.getPostImg(); // 업로드된 파일
        for(MultipartFile file : uploadfilef ) {
	        
	        if (file != null && !file.isEmpty()) {
	            // 파일이 존재하면 저장 경로 설정
	            // 파일명 중복 방지를 위한 UUID 추가
	            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	            file1 = realPath + fileName; // 물리적 경로
	            file.transferTo(new File(file1)); // 파일 저장
	
	            file2 = fileName; // DB에 저장할 파일명
	            postvo.setPost_img_name(file2);
	            communityService.postWrite(postvo);
	        }
        }
		
		
		/*
		 * for(int i = 0; i < postImg.length;i++) {
		 * postvo.setPost_img_name(UUID.randomUUID().toString() + "_"
		 * +postImg[i].getOriginalFilename()); communityService.postWrite(postvo); }
		 */
		int num = 1;
		
		PageDTO page = new PageDTO();
		page.setNum(num);
		page.setCount(communityService.getFanPostCount());
		List<PostVO> newjinsfanPosts = communityService.getFanPostList(page.getDisplayPost(), page.getPostNum()); 
		List<PostVO> postList = communityService.getPostList();

		List<PostVO> newjinsPosts = new ArrayList<>(); //  민지 게시물
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
	
	//게시물 수정 부분
	@PostMapping("/modifyPost")
	@ResponseBody
	public String modifyPost(PostVO postVO, @RequestParam("post_img_name") MultipartFile postImgFile) throws Exception {
	    if (postImgFile != null && !postImgFile.isEmpty()) {
	        // 이미지가 있을 경우 이미지 파일 처리
	        String fileName = saveImage(postImgFile);  // 이미지 저장 메서드
	        postVO.setPost_img(fileName);
	    }
	    
	    
	    
	    communityService.modifyPost(postVO);  // 게시물 수정 서비스 호출
	    return "success";  // 수정 성공 응답
	}

	private String saveImage(MultipartFile file) throws IOException {
	    String uploadDir = "C:\\heartbeat-main\\heartbeat\\src\\main\\webapp\\resources\\upload\\";
	    String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
	    Path path = Paths.get(uploadDir + fileName);
	    Files.copy(file.getInputStream(), path);
	    return fileName;
	}
	
	
	//게시물 삭제
	@PostMapping("/deletePost")
	public String deletePost(@RequestParam("post_id") int post_id, HttpServletRequest request) throws Exception {
		communityService.deletePost(post_id);
		
		// 이전 페이지 URL 얻기
		// 이전 페이지 URL 얻기
	    String referer = request.getHeader("Referer");
	    
	    
		return "redirect:"+referer;
	}

	// �쑀�� 寃뚯떆臾� �긽�꽭蹂닿린
	@RequestMapping("/getUserPost")
	public String getUserPost(PostVO postVO, Model model) throws Exception {
		PostVO dbpost = communityService.getPost(postVO); // 寃뚯떆臾� �젙蹂�
		List<CommentVO> commentList = communityService.getComment(postVO); // �뙎湲� 紐⑸줉
		Integer totlaCommnet = communityService.totalComment(postVO.getPost_id()); // 珥� �뙎湲� 媛쒖닔

		
		model.addAttribute("totalComment", totlaCommnet);
		model.addAttribute("commentList", commentList);
		model.addAttribute("PostVO", dbpost);

		return "/popup/pop-post-fan";
	}

	// �븘�떚�뒪�듃 寃뚯떆臾� �긽�꽭蹂닿린
	@PostMapping("/getArtistPost")
	public String getArtistPost(PostVO postVO, Model model) throws Exception{
		System.out.println("=============�븘�떚�뒪�듃 寃뚯떆臾� �긽�꽭蹂닿린"+postVO);
		
		PostVO dbpost = communityService.getPost(postVO); // 寃뚯떆臾� �젙蹂�
		List<CommentVO> commentList = communityService.getComment(postVO); // �뙎湲� 紐⑸줉
		Integer totalCommnet = communityService.totalComment(postVO.getPost_id()); // 珥� �뙎湲� 媛쒖닔
		Integer totalLike = communityService.totalLike(postVO); // 珥� 醫뗭븘�슂 媛쒖닔
		Integer checkLike = communityService.checkLike(postVO); // 醫뗭븘�슂 �뿬遺� �솗�씤

		model.addAttribute("totalComment", totalCommnet);
		model.addAttribute("commentList", commentList);
		model.addAttribute("PostVO", dbpost);
		model.addAttribute("totalLike", totalLike);
		model.addAttribute("checkLike", checkLike);
		System.out.println(checkLike);
		System.out.println(dbpost);


		return "/popup/pop-post-artist";
	}

	// 寃뚯떆臾� �깉濡쒓퀬移�
	@PostMapping("/resetPost")
	@ResponseBody  // �씠 �뼱�끂�뀒�씠�뀡�쓣 異붽��븯�뿬 JSON �쓳�떟�쓣 諛섑솚�븯�룄濡� �븿
	public Map<String, Object> resetPost(PostVO postVO, Model model) throws Exception {
	
		Integer checkLike = communityService.checkLike(postVO); // 좋아요 여부 확인
		Integer checkLike = communityService.checkLike(postVO); //좋아요 여부 확인
		
	    // JSON�쑝濡� �뜲�씠�꽣瑜� 諛섑솚
	    Map<String, Object> response = new HashMap<>();
	    response.put("checkLike", checkLike);
	    
	    return response;
	}


	// 댓글 작성
	// 댓글 작성
	@PostMapping("/commentWrites")
	@ResponseBody
	public String commentWrite(CommentVO commentVO, HttpServletRequest request, RedirectAttributes rttr) throws Exception{
		communityService.commentWrite(commentVO);
	    
		return "success";
	}

	// 댓글 작성
	// 댓글 작성
	@PostMapping("/commentWrite")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> commentWrite(CommentVO commentVO, Map<String, Object> response) throws Exception {
	    // 댓글 저장
	    // 댓글 저장
	    communityService.commentWrite(commentVO);

	    // 새로 작성된 댓글 정보 가져오기
	    // 새로 작성된 댓글 정보 가져오기
	    CommentVO newComment = communityService.getNewComment(commentVO.getPost_id());
	    int totalComment = communityService.totalComment(commentVO.getPost_id());
	    
	    // 응답 데이터 설정
	    response.clear(); // 기존 데이터 제거 (불필요한 데이터가 남아 있을 수 있으므로)
	    // 응답 데이터 설정
	    response.clear(); // 기존 데이터 제거 (불필요한 데이터가 남아 있을 수 있으므로)

	    // 응답 데이터 설정
	    // 응답 데이터 설정
	    response.put("data", "success");
	    //  새 댓글 정보만 넣기 (commentVO 객체를 그대로 넣지 않음)
//	    response.put("newComment", newComment.getComment()); //새 댓글 내용
//	    response.put("newnick", newComment.getNickname());   // 새 댓글 작성자
//	    response.put("newComment_date", newComment.getComment_date()); // 새 댓글 작성 시간
//	    response.put("totalComment", totalComment); // 최신 댓글 수

	    return ResponseEntity.ok(response); // �겢�씪�씠�뼵�듃�뿉寃� �꽦怨� 硫붿떆吏� �쟾�떖
	}
	
	@PostMapping("/modifyComment") // 댓글 수정
	@PostMapping("/modifyComment") // 댓글 수정
	@ResponseBody
	public String modifyComment(CommentVO commentVO) throws Exception {
		communityService.modifyComment(commentVO);
		
		return "success";
	}

	@PostMapping("/commentdelete") // �뙎湲� �궘�젣
	@ResponseBody // JSON �쓳�떟�쓣 諛섑솚�븯湲� �쐞�빐 @ResponseBody 異붽�
	public ResponseEntity<Map<String, Object>> commentdelete(int comment_id, int post_id, Map<String, Object> response) throws Exception {
		communityService.commentdelete(comment_id);
		Integer totalComment = communityService.totalComment(post_id);
		System.out.println(post_id);
		System.out.println(comment_id);
		System.out.println(post_id);
		System.out.println(comment_id);

		// 寃곌낵瑜� JSON �삎�떇�쑝濡� 諛섑솚
		response.put("status", "success"); // �꽦怨� �뿬遺�
		response.put("comment_id", comment_id); // �궘�젣�맂 �뙎湲� ID
		response.put("totalComment", totalComment);

		return ResponseEntity.ok(response); // �겢�씪�씠�뼵�듃�뿉寃� �꽦怨� 硫붿떆吏� �쟾�떖
	}
	
	
	@PostMapping("/likeToggle") // 醫뗭븘�슂 踰꾪듉
	@ResponseBody 
	public int likeToggle(PostVO postVO,Map<String, Object> response) throws Exception {
		System.out.println("寃뚯떆臾쇰쾲�샇�옉 �씠硫붿씪 �쟾�떖 �솗�씤 " + postVO);

		communityService.likeToggle(postVO);
		Integer totalLike = communityService.totalLike(postVO); 
		
		// �쓳�떟 �뜲�씠�꽣 �꽕�젙
	    response.clear(); // 湲곗〈 �뜲�씠�꽣 �젣嫄� (遺덊븘�슂�븳 �뜲�씠�꽣媛� �궓�븘 �엳�쓣 �닔 �엳�쑝誘�濡�)
		
		response.put("totalLike", totalLike);

		return totalLike;
	}
	
}
