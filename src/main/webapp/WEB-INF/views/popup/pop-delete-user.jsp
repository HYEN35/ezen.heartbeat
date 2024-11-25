<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script>
	function validityCheck() {
		if(document.deleteFrm.pwd.value != document.deleteFrm.pwdCheck.value) {
			alert("입력한 비밀번호가 서로 일치하지 않습니다. 다시 확인해주세요.");
			document.deleteFrm.pwd.focus();
			return false;
		}
		document.deleteFrm.submit();
	}
</script>
<div class="wrap">
	<div class="topArea">
		<div class="title">회원 탈퇴</div>
	</div>
	<div class="cntArea">
		<form action="/mypage/delete" method="post" name="deleteFrm" autocomplete="off" >
			<div class="listBx">
				<input type="text" name="email" class="txtBx" placeholder="이메일 입력">
				<input type="password" name="pwd" class="txtBx" placeholder="비밀번호 입력">
				<input type="password" name="pwdCheck" class="txtBx" placeholder="비밀번호 확인">
			</div>
			<div class="btnBx">
				<button type="button" class="btn-border" onclick="validityCheck()">탈퇴하기</button>
				<button type="button" class="btn-full" onclick="popDeleteUserHide();">닫기</button>
			</div>
		</form>
	</div>
</div>