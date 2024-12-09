<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>
<body>
	
	<script>
		<c:if test="${not empty message}">
		    alert("${message}");
		</c:if>


	</script>

	<script>
		// 맴버십 결제
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
	</script>	
	
	<div class="inner service my-membership" data-name="mypage">
		<%@ include file="../include/menu.jsp" %>
		
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="tabBtn">
						<ul>
							<li data-tab="tab-myinfo" class="tab"><a href="/mypage">내 정보 변경</a></li>
							<li data-tab="tab-membership" class="tab on"><a href="/mymembership">멤버십 변경</a></li>
							<li data-tab="tab-post" class="tab"><a href="/mypost?num=1">내 작성글 확인</a></li>
						</ul>
					</div>					
					<div class="tabCnt">
						<div><!--class="cntBx tab-membership"-->
							<div class="cnt">
								<div class="current">
									<div>현재 사용하고 계신 이용권은 <i id="nowLevel">Level  ${UserVO.level }</i>입니다. <p><c:if test="${not empty startDate and not empty endDate}">
																													(이용기간 : <i>${startDate} ~ ${endDate}</i>)
																													</c:if></p></div>								</div>
									<ul class="itemWrap">
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
											<button type="button" class="btn-full" onclick="artistPayShow()"  ${UserVO.level == 2 ? 'disabled' : ''}>구매하기</button>
										</li>
									</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

    <div class="dimmed" onclick="payhide();"></div>

	<div class="popup pop-pay artist"><%@ include file="../popup/pop-pay-artist.jsp" %></div>
    <div class="popup pop-pay streaming"><%@ include file="../popup/pop-pay-streaming.jsp" %></div>
</body>
</html>