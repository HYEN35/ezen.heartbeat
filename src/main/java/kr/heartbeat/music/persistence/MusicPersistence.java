package kr.heartbeat.music.persistence;

import kr.heartbeat.vo.MusicVO;
import kr.heartbeat.vo.PlaylistDTO;

import java.util.List;
import java.util.Map;

public interface MusicPersistence {
	// 해시태그 IDs를 기반으로 음악 리스트를 가져오는 메서드
	List<MusicVO> findMusicByHashtags(List<Integer> hashtagIds);

	List<MusicVO> findAllMusic();

	public List<PlaylistDTO> findAllMusicAjax(String hashtag1, String hashtag2, String hashtag3);
}
