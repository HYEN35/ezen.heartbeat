package kr.heartbeat.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {
	private int comment_id;
	private String email;
	private int post_id;
	private String nickname;
	private String comment;
	private Date comment_date;
}
