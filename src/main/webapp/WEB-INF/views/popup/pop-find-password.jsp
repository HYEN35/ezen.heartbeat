<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="wrap">
	<div class="topArea">
		<div class="title">비밀번호 찾기</div>
		<button type="button" class="btn-i-close" onclick="popFindPasswordHide();"></button>
	</div>
	<div class="cntArea">
		<div class="findCnt">
			<form name="findPwdFrm" action="/login/searchPwd" method="post" autocomplete="off">
				<input type="text" name="email" class="txtBx" placeholder="이메일 입력">
				<input type="text" name="name" class="txtBx" placeholder="이름 입력">
				<input type="number"  name="birth" class="txtBx" placeholder="생년월일 8글자 입력">
				<button type="submit" class="btn-border">비밀번호 찾기</button>
			</form>
		</div>
	</div>
</div>