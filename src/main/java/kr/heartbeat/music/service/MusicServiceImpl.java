package kr.heartbeat.music.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.heartbeat.music.persistence.MusicPersistence;
import kr.heartbeat.vo.PlaylistDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.heartbeat.vo.MusicVO;

@Service
public class MusicServiceImpl implements MusicService {

	@Autowired
    private SqlSession sqlSession;

    @Autowired
    private MusicPersistence musicPersistence;

    @Override
    // Persistence에서 음악 리스트 조회
    public List<MusicVO> getMusicListByHashtags(List<Integer> hashtagIds) {
        return musicPersistence.findMusicByHashtags(hashtagIds);
    }

    @Override
    public List<MusicVO> findAllMusic(){
        return musicPersistence.findAllMusic();
    }

    @Override
    public List<PlaylistDTO> findAllMusicAjax(String hashtag1, String hashtag2, String hashtag3){
        //System.out.println("************* 선택된 해시태그들의 값(MusicServiceImpl) : 해시태그1 " + hashtag1 + ", 해시태그2 " + hashtag2 + ", 해스태그3 " + hashtag3);
        return musicPersistence.findAllMusicAjax(hashtag1, hashtag2, hashtag3);
    }
}
