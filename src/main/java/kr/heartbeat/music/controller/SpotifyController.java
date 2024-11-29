package kr.heartbeat.music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.heartbeat.music.config.SpotifyAPI;
import kr.heartbeat.vo.TrackInfo;

@Controller
public class SpotifyController {

    @Autowired
    private SpotifyAPI spotifyAPI;

    @GetMapping("/chart")
    public String getPlaylistTrackInfo(Model model) {
    	// 일일 차트 -  https://open.spotify.com/playlist/37i9dQZEVXbNxXF4SkHj9F    	
    	// 주간 차트 - https://open.spotify.com/playlist/37i9dQZEVXbJZGli0rRP3r
    	// 바이럴 차트 - https://open.spotify.com/playlist/37i9dQZEVXbM1H8L6Tttw9
    	//https://open.spotify.com/playlist/37i9dQZEVXbJZGli0rRP3r
        String playlistIdDay = "4cRo44TavIHN54w46OqRVc"; 
        String playlistIdWeek = "6wM4OcNf0K38Aq53knIYaS"; 
        String playlistIdViral = "1CW5PH1aYDo3tiNbwPesNN"; 
        List<TrackInfo> trackInfoList = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdDay);
        List<TrackInfo> trackInfoListWeek = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdWeek);
        List<TrackInfo> trackInfoListViral = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdViral);
        
        // 드라이브 - https://open.spotify.com/playlist/37i9dQZF1DX3sCx6B9EAOr
        // 운동 - https://open.spotify.com/playlist/37i9dQZF1DX3ZeFHRhhi7Y
        // 출퇴근 - https://open.spotify.com/playlist/3LyYTIOLOvwEHgmzbq18dq
        // 공부 - https://open.spotify.com/playlist/7FWBpBXucyI6TsQNa4cbkS
        // 작업 - https://open.spotify.com/playlist/37i9dQZF1DX5eq3AONkaho
        // 자기 전 - https://open.spotify.com/playlist/3JnX5h0ZKQVcpidH25IUQs
        String playlistIdDrive = "1HlaVWWtBFkOpBSn28AB83"; 
        String playlistIdHealth = "2m3FYPEseaDvt32A1hUc1b"; 
        String playlistIdGoto = "3LyYTIOLOvwEHgmzbq18dq"; 
        String playlistIdStudy = "7FWBpBXucyI6TsQNa4cbkS"; 
        String playlistIdWork = "2k2rYd6OgH77Boz57Vt3hh"; 
        String playlistIdSleep = "3JnX5h0ZKQVcpidH25IUQs"; 
        List<TrackInfo> trackInfoListDrive = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdDrive);
        List<TrackInfo> trackInfoListHealth = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdHealth);
        List<TrackInfo> trackInfoListGoto = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdGoto);
        List<TrackInfo> trackInfoListStudy = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdStudy);
        List<TrackInfo> trackInfoListWork = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdWork);
        List<TrackInfo> trackInfoListSleep = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdSleep);
        
        // Model에 리스트를 추가하여 JSP로 전달
        model.addAttribute("trackInfoList", trackInfoList);    
        model.addAttribute("trackInfoListWeek", trackInfoListWeek);   
        model.addAttribute("trackInfoListViral", trackInfoListViral);   
        
        model.addAttribute("trackInfoListDrive", trackInfoListDrive);  
        model.addAttribute("trackInfoListHealth", trackInfoListHealth);  
        model.addAttribute("trackInfoListGoto", trackInfoListGoto);  
        model.addAttribute("trackInfoListStudy", trackInfoListStudy); 
        model.addAttribute("trackInfoListWork", trackInfoListWork);  
        model.addAttribute("trackInfoListSleep", trackInfoListSleep);  
        
        return "heartbeat/chart"; 
    }
    
    @GetMapping("/playTrack")
    @ResponseBody // ★ @ResponseBody를 쓸 땐 Model을 사용할 수 없다.
    public String playTrack(@RequestParam("trackTitle") String trackTitle, @RequestParam("artist") String artist, Model model) {
    	
    	// 괄호와 괄호 안의 내용 제거
    	trackTitle = trackTitle.replaceAll("\\(.*?\\)", "").trim();
        try {
            // YouTube URL 가져오기
            String youTubeUrl = spotifyAPI.getYouTubeUrl(trackTitle, artist);
            
            System.out.println("=========youTubeUrl : "+ youTubeUrl);
           
            // URL 반환
            return youTubeUrl != null ? youTubeUrl : "No URL found";
 			
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
    
}
