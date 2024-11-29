package kr.heartbeat.music.controller;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.heartbeat.music.config.SpotifyAPI;
import kr.heartbeat.music.persistence.MusicPersistence;
import kr.heartbeat.music.service.MusicService;
import kr.heartbeat.service.UserServiceImpl;
import kr.heartbeat.vo.PlaylistDTO;
import kr.heartbeat.vo.UserVO;

@Controller
public class MusicController {
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private SpotifyAPI spotifyAPI;

	@Autowired
	private MusicService musicService;

	@Autowired
	private MusicPersistence musicPersistence;

	@GetMapping("/music/playlist")
	@ResponseBody
	public HashMap<String, Object> allPlaylist(HttpServletRequest request) {

		// 개별 해시태그 값 받아오기
		String hashtag1 = request.getParameter("hashtag1");
		String hashtag2 = request.getParameter("hashtag2");
		String hashtag3 = request.getParameter("hashtag3");

		//System.out.println("************* 선택된 해시태그들의 값 : 해시태그1 " + hashtag1 + ", 해시태그2 " + hashtag2 + ", 해스태그3 " + hashtag3);

		List<PlaylistDTO> playlistDTO = musicPersistence.findAllMusicAjax(hashtag1, hashtag2, hashtag3);

		HashMap<String, Object> response = new HashMap<String, Object>();

		// null 체크
		/*if (playlistDTO == null) {
			System.out.println("******* 반환된 PlaylistDTO가 null입니다.");
		} else {
			System.out.println("******* 반환된 PlaylistDTO: " + playlistDTO);
		}*/
		//System.out.println("******* 해시태그(MusicController) : " + response);
		response.put("playlist", playlistDTO);

		return response;

	}

	@GetMapping("/playlist")
	public String getPlaylistTrackInfo(UserVO userVO, HttpSession session,  Model model) {
		UserVO uvo = (UserVO) session.getAttribute("UserVO");	
		int level = 1;
		model.addAttribute("uvo", uvo);
		model.addAttribute("level", level);
		return "heartbeat/playlist";
	}
	
	

	@GetMapping("/playlist/play")
	@ResponseBody // ★ @ResponseBody를 쓸 땐 Model을 사용할 수 없다.
	public String playTrack(@RequestParam("trackTitle") String trackTitle, @RequestParam("artist") String artist, Model model) {
		try {
			// YouTube URL 가져오기
			String youTubeUrl = spotifyAPI.getYouTubeUrl(trackTitle, artist);

			// YouTube URL을 embed 형식으로 변경
			if (youTubeUrl != null && youTubeUrl.contains("youtube.com/watch?v=")) {
				youTubeUrl = youTubeUrl.replace("youtube.com/watch?v=", "youtube.com/embed/");
			}

			model.addAttribute("youTubeUrl", youTubeUrl);

			// URL 반환
			return youTubeUrl != null ? youTubeUrl : "No URL found";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error occurred";
		}
	}

}
