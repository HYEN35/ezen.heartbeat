package kr.heartbeat.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostVO {
	private int post_id;
	private String email;
	private int artist_id;
	private String nickname;
	private String content;
	private MultipartFile post_img_name;
	private Date post_date;
	private String post_img;
	private String profileimg;
}
