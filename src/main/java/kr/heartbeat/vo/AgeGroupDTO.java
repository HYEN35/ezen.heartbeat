package kr.heartbeat.vo;

import lombok.Data;

@Data
public class AgeGroupDTO {
	private String ageGroup; // 연령대
    private int totalCnt;    // 인원 수
}
