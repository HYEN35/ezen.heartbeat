package kr.heartbeat.vo;



import lombok.Data;

@Data
public class SubscriptionVO {
	private String email;
	private int level;
	private int artist_id;
	private String start_date;
	private String end_date;
}
