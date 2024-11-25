<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

<body>
	<script>
		// 입력값 사용 가능 여부
		let isIdAvailable = false;
		let isPhoneAvailable = false;
		let isNickAvailable = false;
		// 중복 버튼 확인 여부
		let isDuplicateChecked = false; 
		let phoneDuplicateChecked = false; 
		let nickDuplicateChecked = false; 
		
		
		//이메일 중복확인
		function idCheck(email) {
		    if (email === '') {
		        alert('이메일을 입력하세요.');
		        document.joinFrm.email.focus();
		        return false;
		    }
		    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		    if (!emailPattern.test(email)) {
		        alert('이메일 형식이 유효하지 않습니다. @와 .com을 포함해야 합니다.');
		        document.joinFrm.email.focus();
		        return false;
		    }
	    	$.ajax({
	       		url: '/join/emailcheck',
	        	type: 'POST',
	        	data: { email: email },
	        	success: function(data) {
	        		isDuplicateChecked = true;
		           	if (data === 'success') {
		                       	$('.msg').text(email+ '은/는 사용 불가능합니다. ');
		                       	isIdAvailable= false;
		                   	} else {
		                       	$('.msg').text(email+ '은/는 사용 가능합니다.');	
		                       	isIdAvailable = true;	                       	
		                   	}
	           	popAlertCheckShow(); 
	        	}
	    	});
	    	
		}
		
		//전화번호 중복확인
		function phoneCheck(phone) {
			if(phone =="") {
				alert('번호를 입력하세요.');
				document.joinFrm.phone.focus();
				return false;
			}		
			// 전화번호가 11자리인지 확인
		    if (phone.length !== 11) {
		        alert('전화번호는 11자리여야 합니다.');
		        document.joinFrm.phone.focus();
		        return false;
		    }
		    // 전화번호가 숫자인지 확인
		    if (!/^\d+$/.test(phone)) {
		        alert('전화번호는 숫자만 포함해야 합니다.');
		        document.joinFrm.phone.focus();
		        return false;
		    }
		    $.ajax({
	       		url: '/join/phonecheck',
	        	type: 'POST',
	        	data: { phone: phone },
	        	success: function(data) {
	        		phoneDuplicateChecked = true;
		           	if (data === 'success') {
		                       	$('.msg').text(phone+ '은/는 사용 불가능합니다. ');
		                       	isPhoneAvailable = false;
		                       	
		                   	} else {
		                       	$('.msg').text(phone+ '은/는 사용 가능합니다.');
		                       	isPhoneAvailable = true;
		                   	}
	           	popAlertCheckShow(); 
	        	}
	    	});
		}
		
		//닉네임 중복확인
		function nicknameCheck(nickname) {
			if(nickname =="") {
				alert(' 닉네임을 입력하세요.');
				document.joinFrm.nickname.focus();
				return false;
			}		
			 $.ajax({
		       		url: '/join/nicknamecheck',
		        	type: 'POST',
		        	data: { nickname: nickname },
		        	success: function(data) {
		        		nickDuplicateChecked = true;
			           	if (data === 'success') {
			                       	$('.msg').text(nickname+ '은/는 사용 불가능합니다. ');
			                       	isNickAvailable = false;
			                   	} else {
			                       	$('.msg').text(nickname+ '은/는 사용 가능합니다.');
			                       	isNickAvailable = true;
			                   	}
		           	popAlertCheckShow(); 
		        	}
		    	});
		}
		
		// 중복 확인 초기화 함수
	    function resetIdAvailability() {
	    	isDuplicateChecked = false;
	    }
	    function resetPhoneAvailability() {
	    	phoneDuplicateChecked = false;
	    }
	    function resetNickAvailability() {
	    	nickDuplicateChecked = false;
	    }
		
		
		//유효성 체크
		function validityCheck() {
			if (document.joinFrm.email.value == '') {
				alert('이메일을 입력하세요.');
				document.joinFrm.email.focus();
				return false; 
			}
			
			if (document.joinFrm.pwd.value == '') {
				alert('비밀번호를 입력하세요.');
				document.joinFrm.pwd.focus();
				return false;
			}
			
			// 비밀번호 유효성 체크
		    const pwd = document.joinFrm.pwd.value;
		    const pwdVPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,16}$/; 
		    if (!pwdVPattern.test(pwd)) {
		    	document.getElementById('error-pwd').style.display='block';
		        document.joinFrm.pwd.focus();
		        return false;
		    }
			
			if(document.joinFrm.pwd.value != document.joinFrm.pwdCheck.value) {
				document.getElementById('error-pwdCheck').style.display='block';
				document.joinFrm.pwd.focus();
				return false;
			}
			
			if (document.joinFrm.name.value == '') {
				alert('이름을 입력하세요.');
				document.joinFrm.name.focus();
				return false;
			}
			
			//중복버튼 확인
		    if (!isDuplicateChecked) {
		        alert("아이디 중복 확인이 필요합니다.");
		        return false;
		    } if (!isIdAvailable) {
		        alert("중복된 아이디입니다. 아이디를 변경해주세요.");
		        return false;
		    }
		    if (!phoneDuplicateChecked) {
		        alert("전화번호 중복 확인이 필요합니다.");
		        return false;
		    }
		    if (!isPhoneAvailable) {
		        alert("중복된 전화번호입니다. 전화번호를 변경해주세요.");
		        return false;
		    }
		    if (!nickDuplicateChecked) {
		        alert("닉네임 중복 확인이 필요합니다.");
		        return false;
		    }
		    if (!isNickAvailable) {
		        alert("중복된 닉네임입니다. 닉네임을 변경해주세요.");
		        return false;
		    }
		    
			document.joinFrm.submit();
		}
		
		function popAlertCheckShow(){
			$('.pop-alert-check').show();
			$('.dimmed').show();
		}
		function popAlertCheckHide(){
			$('.pop-alert-check').hide();
			$('.dimmed').hide();
		}
	</script>
	
	<div class="inner service join">
		<div class="container">
			<div class="wrap">
				<h1 class="logo">HeartBeat</h1>
				<div class="joinCnt">
					<form action="/join" method="post" name="joinFrm">
						<div class="inputBx">
							<p class="noti">*필수 입력 사항</p>
							<div class="doubleCheck">
								<input type="text" name="email" placeholder="* 이메일" oninput="resetIdAvailability()" class="txtBx" >
								<button type="button" class="btn-border" onclick="idCheck(this.form.email.value)">중복확인</button>
							</div>
							<p class="error" id="error-pwd">*비밀번호는 대문자 영어, 특수문자, 숫자를 포함하여 8~16자로 해주세요.</p>
							<input type="password" name="pwd" placeholder="* 비밀번호" class="txtBx">
							<p class="error" id="error-pwdCheck">*입력하신 비밀번호가 틀립니다. 비밀번호를 확인하세요.</p>
							<input type="password"  name="pwdCheck" placeholder="* 비밀번호 확인" class="txtBx">
							<input type="text"  name="name" placeholder="* 이름" class="txtBx">
							<input type="date" name="birth"class="txtBx">
							<div class="doubleCheck">
								<input type="tel" maxlength="11" name="phone" placeholder="* 휴대폰번호"  oninput="resetPhoneAvailability()"  class="txtBx">
								<button type="button" class="btn-border" onclick="phoneCheck(this.form.phone.value)">중복확인</button>
							</div>
							<div class="doubleCheck">
								<input type="text" name="nickname" placeholder="* 닉네임" oninput="resetNickAvailability()"  class="txtBx">
								<button type="button" class="btn-border"  onclick="nicknameCheck(this.form.nickname.value)">중복확인</button>
							</div>					
						</div>
						<div class="inputBx">
							<p class="noti">*선택 입력 사항</p>
							<div class="imgBx">
								<input type="file" id="file" accept=".jpg, .jpeg, .png" hidden>
								<button type="button" class="btn-under" onclick="$('#file').click();">프로필 사진 선택</button>
								<div class="fileName"><i>파일명이길어질때는말줄임표가나옵니다나옵니다나옵니다.png</i></div>
							</div>
						</div>
						<button type="button"  onclick="validityCheck()"class="btn-full">회원가입</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="dimmed" onclick="popAlertCheckHide()"></div>

	<!-- [D] 팝업 중복확인 -->
	<div class="popup pop-alert-check"><%@ include file="../popup/pop-alert-check.jsp" %></div>
</body>
</html>