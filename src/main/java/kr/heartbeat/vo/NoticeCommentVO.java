package kr.heartbeat.vo;

import java.util.Date;

import lombok.Data;

@Data
public class NoticeCommentVO {
	private int notice_comment_id;
	private int notice_id;
	private String email;
	private String nickname;
	private String comment;
	private Date comment_date;
}
