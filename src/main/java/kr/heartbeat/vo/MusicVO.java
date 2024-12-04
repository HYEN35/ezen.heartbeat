package kr.heartbeat.vo;

import lombok.Data;

@Data
public class MusicVO {
	private int music_id;
	private String artist_id;
	private String music_name;
	private String track;
	private String playtime;
}
