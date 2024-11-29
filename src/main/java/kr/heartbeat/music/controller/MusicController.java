package kr.heartbeat.music.controller;
import kr.heartbeat.music.persistence.MusicPersistence;
import kr.heartbeat.music.service.MusicService;
import kr.heartbeat.vo.PlaylistDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class MusicController {
	@Autowired
	private SqlSession sqlSession;

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

}
