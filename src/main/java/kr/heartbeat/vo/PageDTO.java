package kr.heartbeat.vo;

import lombok.Data;

@Data
public class PageDTO {
	
	private int num; // 현재 페이지 번호 : pageCalc()에서 사용하므로 사용자가 페이지 번호 버튼을 클릭할 때 현재 페이지 번호를 Controller에서 수집해서 설정한다.	
	private int count; 	// 게시물 총 개수 : pageCalc()에서 사용하므로 게시물 총 개수를 Controller에서 얻어서 설정한다.
	// 페이징 처리는 'num 변수'와 'count 변수'의 값에 따라 결정된다.
	
	private int displayPost; // 페이지에 출력할 게시물 시작 번호(limit의 첫번째 값:Controller에서 Persistence까지 전달)	
	private int postNum = 10; // 페이지에 출력할 게시물 개수(limit의 두번째 값:Controller에서 Persistence까지 전달)
	
	private int pageNum; // 블럭 페이지 번호
	private int pageNumCnt = 5; // 블럭 페이지에 표시할 페이지의 개수
	private int startPageNum; // 블럭 페이지의 첫번째 페이지 번호
	private int endPageNum; // 블럭 페이지의 마지막 페이지 번호
	// 이전, 다음 버튼 표시 여부
	private boolean prev;
	private boolean next;
	
	public void setCount(int count) { // 게시물 총 개수를 얻어 설정하고 pageCalc()을 호출한다.
		this.count = count;
		pageCalc();
	}
	
	private void pageCalc() {
		// 게시물 번호가 136번일 때 현재 페이지 번호(num(11.3:올림) = 136/12)는 12이다.
		// num이 12이므로 endPageNum은 15이고, startPageNum은 11이다. 따라서 게시물 번호 136번의 블럭 페이지 번호는 3번이다.
		endPageNum = (int) (Math.ceil((double) num / (double) pageNumCnt)) * pageNumCnt;
		startPageNum = endPageNum - (pageNumCnt - 1);
		
		// 게시물 번호가 136번이 마지막 게시물인 경우 페이지 개수는 11.3개가 필요하므로 페이지 번호는 올림처리하여 페이지 번호 버튼은 12번까지 필요하다. 하지만 페이지 번호 버튼은 15번까지 출력된다. 따라서 블럭 페이지의 마지막 페이지 번호에 대한 재계산이 필요하다.
		// endPageNum은 15이고, endPageNum_tmp는 12이므로 13, 14, 15 페이비 번호 버튼은 불필요하다.(11~15)
		int endPageNum_tmp = (int) (Math.ceil((double) count / (double) postNum)); // 12
		if (endPageNum > endPageNum_tmp) { // 15 > 12 가 true이므로 endPageNum에 12를 저장한다. 
			endPageNum = endPageNum_tmp; // 페이지 번호 버튼은 12번까지 출력된다.
		}
		
		// 이전, 다음 버튼 표시 여부 설정
		prev = startPageNum == 1 ? false : true;
		next = endPageNum * postNum >= count ? false : true;
		
		displayPost = (num - 1) * postNum; // 각 페이지에 출력할 게시물 시작 번호를 얻는다.
	}

}












