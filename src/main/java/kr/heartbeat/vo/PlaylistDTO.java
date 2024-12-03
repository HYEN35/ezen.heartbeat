package kr.heartbeat.vo;

import lombok.Data;

@Data
public class PlaylistDTO {
	private int music_id;
	private Integer artist_id;
	private String music_name;
	private String track;
	private String playtime;
	private String art_name;
	private String art_img;
	private String fan;
	private int hashtag_id;
	private String hashtag_name;
}
