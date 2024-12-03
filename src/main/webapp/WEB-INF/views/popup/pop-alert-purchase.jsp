<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	function popAlertPurchaseHide() {
		window.location.href = "/chart";
	}
</script>

<div class="wrap">
	<div class="topArea">
		<div class="title">알림</div>
		<button type="button" class="btn-i-close" onclick="popAlertPurchaseHide();"></button>
	</div>
	<div class="cntArea">
		<div class="messageBx">
			<p>멤버십 level ${level }  구매 후 이용 가능합니다.</p>
			<a href="${pageContext.request.contextPath}/purchase" class="btn-under">멤버십 구매하기</a>
		</div>
	</div>
</div>