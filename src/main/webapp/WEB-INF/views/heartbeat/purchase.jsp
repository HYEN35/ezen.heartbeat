<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>


<script>
    function artistPayShow() {
        $('.artist').show();
        $('.dimmed').show();
    }

    function payhide() {
        $('.pop-pay').hide();
        $('.dimmed').hide();
    }
    
    function streamingPayShow() {
        $('.streaming').show();
        $('.dimmed').show();
    }

    function pay2hide() {
        $('.streaming').hide();
        $('.dimmed').hide();
    }

    // 부모 페이지에서 pgToken을 받는 함수
    function receivePgToken(pgToken) {
        
        // 서버로 결제 정보 전송
        $.ajax({
            type: 'POST',
            url: '/membership/processPayment',  // 결제 처리 URL
            contentType: 'application/json',
            data: JSON.stringify({ pg_token: pgToken }),
            success: function(response) {
                if (response.success) {
                    alert('결제가 완료되었습니다.');
                    payhide();  // 결제 완료 후 팝업 닫기
                } else {
                    alert('결제 확인에 실패하였습니다.');
                }
            },
            error: function(xhr, status, error) {
                console.error('결제 처리 중 오류 발생:', error);
                alert('결제 처리 중 오류가 발생하였습니다.');
            }
        });
    }
</script>


<body>
<c:if test="${not empty alertMsg}">
    <script type="text/javascript">
        alert("${alertMsg}");  // 서버에서 전달한 경고 메시지 표시
        window.location.href = "${pageContext.request.contextPath}/membership";  // 경고 후 리디렉션
    </script>
</c:if>

    <div class="inner service membership" data-name="purchase">
        <%@ include file="../include/menu.jsp" %>
        <div class="container">
            <div class="cntWrap">
                <h2 id="title" class="title"><%=pageTitle%></h2>
                <div class="cntArea">
                    <div class="section-membership">
                        <div class="tit">지금 멤버십을 구독하세요! <br>Hot한 음악도 마음껏 듣고 내가 좋아하는 아티스트와 소통하고 팬들도 사귈 수 있는 기회!</div>
                        <div class="tit">${alertMsg }</div>
                        <ul class="itemCnt">
				    <li class="item ${UserVO.level == 1 ||UserVO.level == 2 ? 'disabled' : '' }">
				        <p class="thumb"><i class="fa-solid fa-coins"></i></p>
				        <p class="info">
				            <span class="grade">Level 1</span>
				            <span>단일 스트리밍 이용권</span>
				        </p>
				        <p class="price">3,900원</p>
				        <button type="button" class="btn-full" onclick="streamingPayShow()" ${UserVO.level == 1 ||UserVO.level == 2 ? 'disabled' : '' }>구매하기</button>
				    </li>
				    <li class="item ${UserVO.level == 2 ? 'disabled' : ''}">
				        <p class="thumb"><i class="fa-solid fa-coins"></i></p>
				        <p class="info">
				            <span class="grade">Level 2</span>
				            <span>스트리밍 이용권 + 아티스트 구독</span>
				        </p>
				        <p class="price">6,900원</p>
				        <!-- 구매하기 버튼 클릭 시 artistPayShow() 호출 -->
				        <button type="button" class="btn-full" onclick="artistPayShow()" ${UserVO.level == 2 ? 'disabled' : '' }>구매하기</button>
				        <p>${msg}</p>
				    </li>
				</ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="dimmed" onclick="payhide();"></div>
    
    <!-- 팝업 결제 -->
    <div class="popup pop-pay artist"><%@ include file="../popup/pop-pay-artist.jsp" %></div>
    <div class="popup pop-pay streaming"><%@ include file="../popup/pop-pay-streaming.jsp" %></div>
</body>
</html>
	<!-- [D] 팝업 로그아웃-->
	<div class="popup pop-alert-logout" ><%@ include file="../popup/pop-alert-Logout.jsp"%></div>