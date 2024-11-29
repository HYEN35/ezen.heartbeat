<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function searchPwd() {
    var email = document.findPwdFrm.email.value;
    var name = document.findPwdFrm.name.value;
    var birth = document.findPwdFrm.birth.value;
    
    $.ajax({
        url: '/login/findPwd',
        type: 'POST',
        data: {
        	email : email,
            name: name,
            birth: birth
        },
        success: function(data) {
            console.log("비밀번호 찾기 : ",data);  // 응답 전체를 로그로 출력하여 제대로 받았는지 확인
            
            if (data.result === "successpwd") {
                resultShow(); 
                $("#userReName").text(name); 
                $("#userPwd").text(data.pwd);
            } 
        }
    });
    
}
</script>
<div class="wrap">
	<div class="topArea">
		<div class="title">비밀번호 찾기</div>
		<button type="button" class="btn-i-close" onclick="popFindPasswordHide();"></button>
	</div>
	<div class="cntArea">
		<div class="findCnt">
			<form name="findPwdFrm" autocomplete="off">
				<input type="text" name="email" class="txtBx" placeholder="이메일 입력">
				<input type="text" name="name" class="txtBx" placeholder="이름 입력">
				<input type="number"  name="birth" class="txtBx" placeholder="생년월일 6글자 입력">
				<button type="button" class="btn-border" onclick="searchPwd()" >비밀번호 찾기</button>
			</form>
		</div>
		<div class="resultCnt">
			<p class="txt">
				<i id="userReName"></i>님의 비밀번호 입니다. <br><i id="userPwd"></i>
			</p>
		</div>
	</div>
</div>