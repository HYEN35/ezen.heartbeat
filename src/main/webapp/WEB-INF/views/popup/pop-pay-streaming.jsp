<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://nsp.pay.naver.com/sdk/js/naverpay.min.js"></script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>

<script>
    // 사용자 이메일과 이름을 서버에서 JSP로 전달받아 변수로 설정합니다.
    var email = "${UserVO.email}"; 
    var name = "${UserVO.name}"; 
    var phone = "${UserVO.phone}";

    // 엑세스 토큰을 서버에서 받아오는 함수
    function getAccessToken() {
	    return new Promise((resolve, reject) => {
	        $.ajax({
	            url: '/getAccessToken',  // 서버에서 엑세스 토큰을 반환하는 엔드포인트
	            type: 'GET',
	            success: function(response) {
	                if (response && response) {
	                    resolve(response);  // 엑세스 토큰 반환
	                } else {
	                    reject("엑세스 토큰을 받을 수 없습니다1.");
	                }
	            },
	            error: function(xhr, status, error) {
	                reject("엑세스 토큰 요청에 실패했습니다: " + error);
	            }
	        });
	    });
	}

    // 고유한 결제 ID 생성
    function makeMerchantUid() {
        return "IMP" + crypto.randomUUID();
    }

    function kakaoPay() {
    	// select 박스에서 선택된 값 가져오기
        if (confirm("구매 하시겠습니까?")) { 
            getAccessToken().then(function(accessToken) {
                if (accessToken) {
                    IMP.init("imp72743015");  // 아이엠포트 가맹점 식별 코드

                    IMP.request_pay({
                        pg: 'kakaopay.TC0ONETIME',  // 카카오페이 결제 방식
                        pay_method: 'card',         // 결제 방식 (카드 결제)
                        merchant_uid: makeMerchantUid(),  // 고유 결제 ID
                        name: '스트리밍 구독권(1개월)',  // 상품명
                        amount: 3900,               // 결제 금액
                        buyer_email: email,         // 구매자 이메일
                        buyer_name: name,           // 구매자 이름
                        buyer_tel : phone,			// 구매자 번호 
                        access_token: accessToken   // 엑세스 토큰 추가
                    }, function(rsp) {
                        if (rsp.success) {
                            // 결제 성공 시 처리 함수 호출
                            streamingPaymentSuccess(rsp); 
                        } else {
                            alert(rsp.error_msg);  // 결제 실패 시 에러 메시지 출력
                        }
                    });
                } 
            })
        } else {
            return false;  // 구매 취소
        }
    }

    // 결제 성공 처리 함수
    function streamingPaymentSuccess(rsp) {

        // 결제 정보 서버로 전송 (AJAX 요청)
        $.ajax({
            type: 'POST',
            url: '/purchase/streamingPay',  // 결제 정보 처리 서버 URL
            contentType: 'application/json',
            data: JSON.stringify({
            	custom_data : 0,
                merchant_uid: rsp.merchant_uid,
                amount: rsp.paid_amount,
                buyer_email: rsp.buyer_email,
                buyer_name: rsp.buyer_name,
                level : 1,
                
            }),
            success: function(data) {
                    
                } 
            }),
            
            alert('결제가 완료되었습니다.');
            payhide(); // 결제 완료 후 팝업 닫기
            window.location.reload();  // 페이지 새로고침
        };
        
        function nicePay() {
            // 결제 전 확인 창
            if (confirm("구매 하시겠습니까?")) {
                getAccessToken().then(function(accessToken) {
				var selectedArtist = document.getElementById('artistSelect').value;
                    if (accessToken) {
                        IMP.init("imp72743015");  // 아이엠포트 가맹점 식별 코드

                        // 결제 요청
                        IMP.request_pay({
                            storeId: "store-d6a10a43-829f-4a33-bf02-a85ce1acbcf4",  // 고객사 storeId
                            paymentId: `payment-${crypto.randomUUID()}`,  // 결제 ID
                            name: '스트리밍 구독권(1개월)',  // 상품명
                            amount: 3900,  // 결제 금액
                            custom_data : selectedArtist,
                            currency: "CURRENCY_KRW",  // 통화 설정
                            channelKey: "channel-key-bb343318-fb81-4bd2-b9b1-aa96b8a14a3a",  // 채널 키
                            payMethod: "CARD",  // 결제 방식 (카드)
                            custom_data: selectedArtist,  // 선택된 아티스트 정보
                            buyer_email: email,  // 구매자 이메일
                            buyer_name: name,    // 구매자 이름
                            buyer_tel: phone,    // 구매자 전화번호
                            TID: `TID-${crypto.randomUUID()}`  // 고유한 TID 생성 (결제 트랜잭션 ID)
                        }, function(rsp) {
                            // 결제 응답 성공 시 처리
                                nicePaySuccess(rsp,selectedArtist);  // 결제 성공 처리 함수 호출
                        });
                    }
                });
            } else {
                return false;  // 사용자가 구매를 취소한 경우
            }
        }
        
        function nicePaySuccess(rsp,selectedArtist) {
        	var selectedArtist = selectedArtist;
            // 결제 결과를 서버로 전송 (AJAX 요청)
            $.ajax({
                type: 'POST',
                url: '/purchase/streamingPay',  // 결제 정보 처리 서버 URL
                contentType: 'application/json',
                data: JSON.stringify({
                	custom_data : 0,
                    amount: 3900,
                    buyer_email: email,
                    buyer_name: name,
                    level: 1
                }),
				success: function(data) {
                    
                } 
	            }),
	            
	            alert('결제가 완료되었습니다.');
	            payhide(); // 결제 완료 후 팝업 닫기
	            window.location.reload();  // 페이지 새로고침
	        };
        

</script>

<div class="wrap">
	<div class="topArea">
		<div class="title">멤버십 결제</div>
		<button type="button" class="btn-i-close" onclick="payhide();"></button>
	</div>
	<div class="cntArea">
		<div class="btnWrap">
			<input type="button" class="btn-pay-nice" onclick="nicePay()" value="결제 버튼">
			<input type="button" class="btn-pay-kakao" onclick="kakaoPay()" value="카카오페이 결제 버튼">
		</div>
	</div>
</div>