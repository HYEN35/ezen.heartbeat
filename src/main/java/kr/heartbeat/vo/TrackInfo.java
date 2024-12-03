package kr.heartbeat.vo;

import lombok.Data;

@Data
public class TrackInfo {
	private String title;  // 노래 제목
    private String artist; // 아티스트(가수)
    private String albumImageUrl;
    private String youTubeUrl;  // YouTube URL 추가
    
    // 생성자
    public TrackInfo(String title, String artist, String albumImageUrl) {
        this.title = title;
        this.artist = artist;
        this.albumImageUrl = albumImageUrl;
    }
    
    // YouTube URL getter, setter 추가
    public String getYouTubeUrl() {
        return youTubeUrl;
    }

    public void setYouTubeUrl(String youTubeUrl) {
        this.youTubeUrl = youTubeUrl;
    }
}
