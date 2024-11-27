package kr.heartbeat.vo;

import java.util.Date;

import lombok.Data;

@Data
public class NoticeVO {
	private int notice_id;
	private String email;
	private String nickname;
	private String title;
	private String content;
	private Date post_date;
}
