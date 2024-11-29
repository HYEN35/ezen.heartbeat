package kr.heartbeat.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PostVO {
	private int post_id;
	private String email;
	private int artist_id;
	private String nickname;
	private String content;
	private String post_img;
	private Date post_date;
}
