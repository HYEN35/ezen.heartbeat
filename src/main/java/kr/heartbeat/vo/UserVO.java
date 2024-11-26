package kr.heartbeat.vo;

import lombok.Data;

@Data
public class UserVO {
	private String email;
	private int artist_id;
	private String pwd;
	private String name;
	private String nickname;
	private String birth;
	private String phone;
	private String profileimg;
	private int level;
	private String reg_date;
	private String up_date;
}
