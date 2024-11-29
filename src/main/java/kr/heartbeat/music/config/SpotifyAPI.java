package kr.heartbeat.music.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.wrapper.spotify.SpotifyApi;
// Client ID, Client Secret, Access Token 등 인증 정보를 설정하고, 이를 통해 API 요청을 처리
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
// 클라이언트 자격 증명(Client ID와 Client Secret)을 통해 인증하고, 이를 통해 Access Token을 획득
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;

import kr.heartbeat.vo.TrackInfo;


@Component
public class SpotifyAPI {
	
	private static final String CLIENT_ID = "3c4ae2cae71d42cd8550f1f2a7d8cd02";
    private static final String CLIENT_SECRET = "e4843b34b2724c5ca273c0206350ccdb";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    
    private SpotifyApi spotifyApi;
        
    private static String accessToken = null;
    
    public SpotifyAPI() {
    	
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build();
    }
    
    public void connect() {
        System.out.println("Spotify API connected");
    }
    
    // ClientCredentials 흐름을 사용하여 액세스 토큰을 얻는 메소드
    public static String getAccessToken() {
        if (accessToken == null) {
            try {
                // ClientCredentials 방식으로 인증
                SpotifyApi spotifyApi = new SpotifyApi.Builder()
                        .setClientId(CLIENT_ID)
                        .setClientSecret(CLIENT_SECRET)
                        .build();
                
                // 클라이언트 자격 증명을 사용하여 액세스 토큰 얻기
                ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
                accessToken = credentials.getAccessToken();
                System.out.println("Access Token: " + accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return accessToken;
    }
    
    // 플레이리스트 불러오기
    // 플레이리스트 정보는 플레이리스트에 대한 전체 정보를 담고 있으며, 트랙 목록도 포함되지만, 그 자체로는 트랙 객체를 포함하지 않습니다.
    public Playlist getPlaylistFromSpotify(String playlistId) {
        try {
            // 인증된 액세스 토큰을 설정하여 요청
            spotifyApi.setAccessToken(getAccessToken());

            // 플레이리스트의 정보를 가져옴
            Playlist playlist = spotifyApi.getPlaylist("", playlistId).build().execute();
            // getPlaylist() 메서드는 두 개의 파라미터 필요
            //  user_id :  플레이리스트의 소유자의 사용자 이름
            // playlistId: 플레이리스트의 ID (예: "37i9dQZEVXbNxXF4SkHj9F")
            
            if (playlist != null) {
                System.out.println("Playlist Name: " + playlist.getName());
                return playlist;
            } else {
                System.out.println("No playlist found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 
    
    // 예시: 플레이리스트에서 트랙 목록을 가져오는 메서드
    // 트랙 목록은 Playlist 객체에서 트랙만 추출한 리스트입니다. 여기에는 트랙의 이름, 아티스트 정보 등 실제 음악에 대한 정보만 포함됩니다.
    public List<TrackInfo> getTracksFromPlaylist(String playlistId) {
        try {
            // 플레이리스트 정보를 가져오기
            Playlist playlist = getPlaylistFromSpotify(playlistId);
            
            // playlistId를 제대로 전달했는지 확인
            if (playlist != null) {
                // PlaylistTrack에서 Track을 추출하여 List<TrackInfo>를 생성
                List<TrackInfo> trackInfoList = new ArrayList<>();
                for (PlaylistTrack playlistTrack : playlist.getTracks().getItems()) {
                    Track track = playlistTrack.getTrack(); // PlaylistTrack에서 Track을 추출
                    String title = track.getName();  // 트랙 제목
                    String artist = track.getArtists()[0].getName(); // 첫 번째 아티스트의 이름
                    String albumImageUrl = track.getAlbum().getImages()[0].getUrl(); // 첫 번째 앨범 이미지 URL

                    // TrackInfo 객체 생성 후 리스트에 추가
                    trackInfoList.add(new TrackInfo(title, artist, albumImageUrl));
                }
                return trackInfoList;  // TrackInfo 리스트 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // 예외 발생 시 null 반환
    }
    
    // getTracksFromPlaylist 메서드를 사용하여 트랙 목록에서 제목과 가수를 가져오는 코드   
    public List<TrackInfo> getTrackTitlesAndArtistsFromPlaylist(String playlistId) {
        List<TrackInfo> trackInfoList = new ArrayList<>();
        try {
            // 플레이리스트에서 트랙 목록을 가져옵니다.
            Playlist playlist = getPlaylistFromSpotify(playlistId);

            if (playlist != null) {
                // 트랙 목록을 추출합니다.
                List<PlaylistTrack> playlistTracks = Arrays.asList(playlist.getTracks().getItems());
                for (PlaylistTrack playlistTrack : playlistTracks) {
                    Track track = playlistTrack.getTrack(); // 실제 트랙 객체를 가져옵니다.
                    
                    // 트랙 제목, 아티스트, 앨범 이미지 URL을 TrackInfo 객체에 담습니다.
                    String title = track.getName(); // 트랙 제목
                    String artist = track.getArtists()[0].getName(); // 첫 번째 아티스트 이름
                    String albumImageUrl = track.getAlbum().getImages()[0].getUrl(); // 앨범 이미지 URL

                    // TrackInfo 객체 생성 후 리스트에 추가
                    trackInfoList.add(new TrackInfo(title, artist, albumImageUrl));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackInfoList; // TrackInfo 객체 리스트 반환
    }


    //유튜브 url      
      public String getYouTubeUrl(String trackTitle, String artist) {
    	  String query =  artist  + " "+  trackTitle;

    	    try {
    	        String apiUrl = "https://www.googleapis.com/youtube/v3/search";
    	        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
    	                .queryParam("part", "snippet")
    	                .queryParam("q", query)
    	                .queryParam("key", "AIzaSyAZ6iXLzKrXSwPo5EK_Ucmkiq9v078wY8g")
    	                .queryParam("maxResults", 1)
    	                .queryParam("videoCategoryId", "10")  // 음악 카테고리만 필터링
    	                .queryParam("type", "video");

    	        // YouTube API 호출
    	        RestTemplate restTemplate = new RestTemplate();
    	        ResponseEntity<Map> response = restTemplate.getForEntity(builder.toUriString(), Map.class);
    	        
    	        // 응답 로그 출력 (디버깅)
    	        System.out.println("API Response: " + response.getBody());
    	        
    	        List<Map> items = (List<Map>) response.getBody().get("items");
    	        if (items != null && !items.isEmpty()) {
    	            Map<String, Object> videoId = (Map<String, Object>) items.get(0).get("id");
    	            if (videoId != null && videoId.get("videoId") != null) {
    	                String youtubeUrl = "https://www.youtube.com/watch?v=" + videoId.get("videoId");
    	                System.out.println("YouTube URL: " + youtubeUrl);
    	                return youtubeUrl;
    	            } else {
    	                System.out.println("Video ID not found in response.");
    	            }
    	        } else {
    	            System.out.println("No items found in response.");
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        System.out.println("Error occurred while fetching YouTube URL.");
    	    }
    	    return null;
    	}


   
    
    
    
    /*
    // 앨범 검색
    public Paging<AlbumSimplified> getAlbumsFromSpotify(String query) {
        try {
            // Spotify API에 인증된 토큰을 사용하여 요청
            spotifyApi.setAccessToken(getAccessToken());

            Paging<AlbumSimplified> albums = spotifyApi.searchAlbums(query).build().execute();
            if (albums != null) {
                return albums;
            } else {
                System.out.println("No albums found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    */
}