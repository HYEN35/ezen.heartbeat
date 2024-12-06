<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="https://cdn.portone.io/js/portone.js"></script>

<script>

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

    function atristkakaoPay() {
    	// jQuery의 Ajax를 사용하여 이메일을 가져옵니다.
    	$.ajax({
    	    url: '/purchase/getEmail',  // 이메일을 가져오는 서버 URL
    	    type: 'GET',       // GET 방식으로 서버에 요청
    	    success: function(data) {
    	        console.log(data);  // 서버에서 반환된 데이터를 확인

    	        var email = data.email;  // 서버에서 받아온 이메일 값
    	        var name = data.name;    // 서버에서 받아온 이름
    	        var phone = data.phone;  // 서버에서 받아온 전화번호

    	        console.log("Email:", email);  // 이메일 값 확인

    	        if (email == null || email.trim() === "") {
    	            alert("세션이 만료되었습니다. 로그인 페이지로 이동합니다.");
    	            window.location.href = '/login'; // 로그인 페이지로 이동
    	            return false; // 더 이상 진행하지 않도록 막음
    	        }

    	        // select 박스에서 선택된 값 가져오기
    	        var selectedArtist = document.getElementById('artistSelect').value;
    	        if (confirm("구매 하시겠습니까?")) {
    	            getAccessToken().then(function(accessToken) {
    	                if (accessToken) {
    	                    IMP.init("imp72743015");  // 아이엠포트 가맹점 식별 코드

    	                    IMP.request_pay({
    	                        pg: 'kakaopay.TC0ONETIME',  // 카카오페이 결제 방식
    	                        pay_method: 'card',         // 결제 방식 (카드 결제)
    	                        merchant_uid: makeMerchantUid(),  // 고유 결제 ID
    	                        custom_data : selectedArtist,
    	                        name: '스트리밍 + '+selectedArtist+' 구독권(1개월)',              // 상품명
    	                        amount: 6900,               // 결제 금액
    	                        buyer_email: email,         // 구매자 이메일
    	                        buyer_name: name,           // 구매자 이름
    	                        buyer_tel : phone,          // 구매자 번호 
    	                        access_token: accessToken   // 엑세스 토큰 추가
    	                    }, function(rsp) {
    	                        if (rsp.success) {
    	                            // 결제 성공 시 처리 함수 호출
    	                            artistPaymentSuccess(rsp); 
    	                        } else {
    	                            alert(rsp.error_msg);  // 결제 실패 시 에러 메시지 출력
    	                        }
    	                    });
    	                }
    	            })
    	        } else {
    	            return false;  // 구매 취소
    	        }
    	    },
    	    error: function(xhr, status, error) {
    	        // 에러 발생 시 처리
    	        console.error("Ajax 요청 실패: ", status, error);
    	        alert("서버 요청 중 오류가 발생했습니다.");
    	    }
    	});

    }


    // 결제 성공 처리 함수
    function artistPaymentSuccess(rsp) {
	
        // 결제 정보 서버로 전송 (AJAX 요청)
        $.ajax({
            type: 'POST',
            url: '/purchase/artistPay',  // 결제 정보 처리 서버 URL
            contentType: 'application/json',
            data: JSON.stringify({
            	custom_data : rsp.custom_data,
                merchant_uid: rsp.merchant_uid,
                amount: rsp.paid_amount,
                buyer_email: rsp.buyer_email,
                buyer_name: rsp.buyer_name,
                level : 2,
                
            }),
            success: function(data) {
                    
                } 
            }),
            
            alert('결제가 완료되었습니다.');
            payhide(); // 결제 완료 후 팝업 닫기
            window.location.reload();  // 페이지 새로고침
        };
    
		
        function atristNicePay() {
            // jQuery의 Ajax를 사용하여 이메일을 가져옵니다.
            $.ajax({
                url: '/purchase/getEmail',  // 이메일을 가져오는 서버 URL
                type: 'GET',       // GET 방식으로 서버에 요청
                success: function(data) {
                    // 서버에서 받은 데이터를 콘솔로 출력하여 확인합니다.
                    console.log(data);

                    var email = data.email;  // 서버에서 받아온 이메일 값
                    var name = data.name;    // 서버에서 받아온 이름
                    var phone = data.phone;  // 서버에서 받아온 전화번호

                    console.log("Email:", email);  // 이메일 값 확인

                    if (email == null || email.trim() === "") {
                        alert("세션이 만료되었습니다. 로그인 페이지로 이동합니다.");
                        window.location.href = '/login'; // 로그인 페이지로 이동
                        return false; // 더 이상 진행하지 않도록 막음
                    }

                    // select 박스에서 선택된 값 가져오기
                    var selectedArtist = document.getElementById('artistSelect').value;

                    // 결제 전 확인 창
                    if (confirm("구매 하시겠습니까?")) {
                        getAccessToken().then(function(accessToken) {
                            var transactionId = generateUniqueTransactionId();  // 고유 TID 생성 (서버에서 처리)

                            if (accessToken) {
                                IMP.init("imp72743015");  // 아이엠포트 가맹점 식별 코드

                                // 결제 요청
                                IMP.request_pay({
                                    pg: 'nice_v2',  // 결제 PG사 (NicePay)
                                    storeId: "store-d6a10a43-829f-4a33-bf02-a85ce1acbcf4",  // 고객사 storeId
                                    paymentId: transactionId,  // 결제 ID (고유한 TID로 변경)
                                    name: '스트리밍 + ' + selectedArtist + ' 구독권(1개월)',  // 상품명
                                    amount: 6900,  // 결제 금액
                                    custom_data: selectedArtist,
                                    currency: "CURRENCY_KRW",  // 통화 설정
                                    channelKey: "channel-key-bb343318-fb81-4bd2-b9b1-aa96b8a14a3a",  // 채널 키
                                    payMethod: "CARD",  // 결제 방식 (카드)
                                    buyer_email: email,  // 구매자 이메일
                                    buyer_name: name,    // 구매자 이름
                                    buyer_tel: phone,    // 구매자 전화번호
                                    level: 2,            // 사용자 레벨
                                    TID: transactionId   // 고유한 TID 생성 (결제 트랜잭션 ID)
                                }, function(rsp) {
                                    // 결제 응답 성공 시 처리
                                    if (rsp.error_msg) {
                                        alert('결제가 취소되었습니다.');  // 결제 취소 알림
                                        console.log("결제 실패:", rsp);
                                        payhide();  // 결제 팝업 숨기기
                                    } else {
                                        artistNicePaySuccess(rsp, selectedArtist);  // 결제 성공 처리 함수 호출
                                    }
                                });
                            }
                        });
                    } else {
                        return false;  // 사용자가 구매를 취소한 경우
                    }
                },
                error: function(xhr, status, error) {
                    // 에러 발생 시 처리
                    console.error("Ajax 요청 실패: ", status, error);
                    alert("서버 요청 중 오류가 발생했습니다.");
                }
            });
        }


        // 서버 측에서 TID 생성 함수 (예시)
        function generateUniqueTransactionId() {
            // 서버에서 관리되는 고유 ID 생성 로직
            return 'TID-' + new Date().getTime();  // 예시로 현재 시간을 기반으로 TID 생성
        }
        
        function artistNicePaySuccess(rsp, selectedArtist) {
            // 결제 결과를 서버로 전송 (AJAX 요청)
            $.ajax({
                type: 'POST',
                url: '/purchase/artistPay',  // 결제 정보 처리 서버 URL
                contentType: 'application/json',
                data: JSON.stringify({
                    custom_data: selectedArtist,
                    amount: 6900,
                    buyer_email: email,
                    buyer_name: name,
                    level: 2
                }),
                success: function(data) {
                    alert('결제가 완료되었습니다.');
                    payhide(); // 결제 완료 후 팝업 닫기
                    window.location.reload();  // 페이지 새로고침
                }
            });
        }
</script>

<div class="wrap">
	<div class="topArea">
		<div class="title">멤버십 결제</div>
		<button type="button" class="btn-i-close" onclick="payhide();"></button>
	</div>
	<div class="cntArea">
		<div class="infoWrap">
			<i>구독 아티스트 선택</i>
			<select class="sltBx" id="artistSelect">
				<option value="aespa">AESPA</option>
				<option value="블랙핑크 (BLACKPINK)">BLACKPINK</option>
				<option value="bts">BTS</option>
				<option value="ive">IVE</option>
				<option value="있지 (ITZY)">ITZY</option>
				<option value="뉴진스 (NewJeans)">NewJeans</option>
				<option value="nct127">NCT-127</option>
				<option value="nctdream">NCT-DREAM</option>
				<option value="seventeen">SEVENTEEN</option>
			</select>
		</div>
		<div class="btnWrap">
			<input type="button" class="btn-pay-nice" onclick="atristNicePay()" value="일반 결제 버튼">
			<input type="button" class="btn-pay-kakao" onclick="atristkakaoPay()" value="카카오페이 결제 버튼">
		</div>
	</div>
</div>

<!--  <div class="wrap">
    <div>결제 페이지</div>
    <div>${UserVO.email }</div>
    <input type="button" id="naverPayBtn" value="네이버페이 결제 버튼">
    <input type="button" onclick="kakaoPay()" value="카카오페이 결제 버튼">
    <input type="button" value="토스 결제 버튼">
</div>-->