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
        String playlistIdDay = "37i9dQZEVXbNxXF4SkHj9F";
        List<TrackInfo> trackInfoList = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdDay);
        String playlistIdWeek = "37i9dQZEVXbJZGli0rRP3r";
        List<TrackInfo> trackInfoListWeek = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdWeek);
        String playlistIdViral = "37i9dQZEVXbM1H8L6Tttw9";
        List<TrackInfo> trackInfoListViral = spotifyAPI.getTrackTitlesAndArtistsFromPlaylist(playlistIdViral);

        // Model에 리스트를 추가하여 JSP로 전달
        model.addAttribute("trackInfoList", trackInfoList);
        model.addAttribute("trackInfoListWeek", trackInfoListWeek);
        model.addAttribute("trackInfoListViral", trackInfoListViral);

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

            //System.out.println("=========youTubeUrl : "+ youTubeUrl);

            // URL 반환
            return youTubeUrl != null ? youTubeUrl : "No URL found";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

}