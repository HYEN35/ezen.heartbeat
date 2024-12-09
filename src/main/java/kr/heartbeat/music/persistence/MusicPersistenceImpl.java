package kr.heartbeat.music.persistence;

import kr.heartbeat.vo.MusicVO;
import kr.heartbeat.vo.PlaylistDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MusicPersistenceImpl implements MusicPersistence{
	@Autowired
	private SqlSession sqlSession;

	private static final String namespace = "kr.heartbeat.mappers.music";

	@Override
	public List<MusicVO> findMusicByHashtags(List<Integer> hashtagIds) {
		return sqlSession.selectList(namespace + ".findMusicByHashtags", hashtagIds);
	}

	public List<MusicVO> findAllMusic(){
		return sqlSession.selectList(namespace + ".findAllMusic");
	}

	public List<PlaylistDTO> findAllMusicAjax(String hashtag1, String hashtag2, String hashtag3){

		HashMap<String, String> map = new HashMap<>();
		map.put("hashtag1", hashtag1);
		map.put("hashtag2", hashtag2);
		map.put("hashtag3", hashtag3);

		// 쿼리 실행 후 결과 확인
		List<PlaylistDTO> resultList = sqlSession.selectList(namespace + ".findAllMusicAjax", map);

		return (resultList != null && !resultList.isEmpty()) ? resultList : new ArrayList<>();
	}
}
