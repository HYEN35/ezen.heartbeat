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
    	 //https://open.spotify.com/playlist/37i9dQZEVXbNxXF4SkHj9F
        String playlistId = "37i9dQZEVXbNxXF4SkHj9F"; 
        List<TrackInfo> trackInfoList = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistId);

        // Model에 리스트를 추가하여 JSP로 전달
        model.addAttribute("trackInfoList", trackInfoList);    
        
        return "heartbeat/chart"; 
    }
    
    @GetMapping("/playTrack")
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
