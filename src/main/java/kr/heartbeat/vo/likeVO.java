package kr.heartbeat.vo;

import lombok.Data;

@Data
public class likeVO {
	private int like_id;
	private String email;
	private int post_id;
	
	
	private int like_count;
	private String nickname;
}
