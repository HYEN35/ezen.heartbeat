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
		
		// 이메일 중복 확인
		function idCheck(email) {
		    if (email === '') {
		        alert('이메일을 입력하세요.');
		        document.adminjoin.email.focus();
		        return false;
		    }
		
		    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		    if (!emailPattern.test(email)) {
		        alert('이메일 형식이 유효하지 않습니다. @와 .com을 포함해야 합니다.');
		        document.adminjoin.email.focus();
		        return false;
		    }
		
		    $.ajax({
		        url: '/admin/adminjoin/checkEmail',
		        type: 'POST',
		        data: { email: email },
		        success: function(data) {
		            isDuplicateChecked = true; // 중복 확인 버튼 클릭 여부 설정
		
		            if (data === 'success') { // 사용 가능한 경우
		                $('.msg').text(email + '은/는 사용 가능합니다.');
		                isIdAvailable = true; // 사용 가능 상태 업데이트
		            } else { // 사용 불가능한 경우
		                $('.msg').text(email + '은/는 사용 불가능합니다.');
		                isIdAvailable = false; // 사용 불가능 상태 업데이트
		            }
		
		            popAlertCheckShow(); // 팝업 표시
		        },
		        error: function() {
		            alert('서버와의 통신에 문제가 발생했습니다.');
		        }
		    });
		}
		
		// 전화번호 중복 확인
		function phoneCheck(phone) {
		    if (phone === '') {
		        alert('전화번호를 입력하세요.');
		        document.adminjoin.phone.focus();
		        return false;
		    }
		    if (phone.length !== 11 || !/^\d+$/.test(phone)) {
		        alert('전화번호는 11자리 숫자여야 합니다.');
		        document.adminjoin.phone.focus();
		        return false;
		    }

		    $.ajax({
		        url: '/admin/adminjoin/checkPhone',
		        type: 'POST',
		        data: { phone: phone },
		        success: function(data) {
		            phoneDuplicateChecked = true; // 중복 확인 버튼 클릭 여부 설정

		            if (data === 'success') { // 사용 가능한 경우
		                $('.msg').text(phone + '은/는 사용 가능합니다.');
		                isPhoneAvailable = true;
		            } else { // 사용 불가능한 경우
		                $('.msg').text(phone + '은/는 사용 불가능합니다.');
		                isPhoneAvailable = false;
		            }

		            popAlertCheckShow(); // 팝업 표시
		        },
		        error: function() {
		            alert('서버와의 통신에 문제가 발생했습니다.');
		        }
		    });
		}

		// 닉네임 중복 확인
		function nicknameCheck(nickname) {
		    if (nickname === '') {
		        alert('닉네임을 입력하세요.');
		        document.adminjoin.nickname.focus();
		        return false;
		    }

		    $.ajax({
		        url: '/admin/adminjoin/checkNickname',
		        type: 'POST',
		        data: { nickname: nickname },
		        success: function(data) {
		            nickDuplicateChecked = true; // 중복 확인 버튼 클릭 여부 설정

		            if (data === 'success') { // 사용 가능한 경우
		                $('.msg').text(nickname + '은/는 사용 가능합니다.');
		                isNickAvailable = true;
		            } else { // 사용 불가능한 경우
		                $('.msg').text(nickname + '은/는 사용 불가능합니다.');
		                isNickAvailable = false;
		            }

		            popAlertCheckShow(); // 팝업 표시
		        },
		        error: function() {
		            alert('서버와의 통신에 문제가 발생했습니다.');
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
		    // 이메일 유효성 검사
		    if (document.adminjoin.email.value == '') {
		        alert('이메일을 입력하세요.');
		        document.adminjoin.email.focus();
		        return false;
		    }
		
		    // 비밀번호 유효성 검사
		    if (document.adminjoin.pwd.value == '') {
		        alert('비밀번호를 입력하세요.');
		        document.adminjoin.pwd.focus();
		        return false;
		    }
		
		    // 비밀번호 확인 검사
		    if (document.adminjoin.pwd.value != document.adminjoin.pwdCheck.value) {
		        alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
		        document.adminjoin.pwdCheck.focus();
		        return false;
		    }
		
		    // 이름 유효성 검사
		    if (document.adminjoin.name.value == '') {
		        alert('이름을 입력하세요.');
		        document.adminjoin.name.focus();
		        return false;
		    }
		
		    // 중복 확인 여부 검사
		    if (!isDuplicateChecked) {
		        alert("아이디 중복 확인이 필요합니다.");
		        return false;
		    }
		    if (!isIdAvailable) {
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
		
		    // 모든 유효성 검사를 통과한 경우 폼 제출
		    document.adminjoin.submit();
		}
		<%--
			// 비밀번호 유효성 체크
		    const pwd = document.adminjoin.pwd.value;
		    const pwdVPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,16}$/; 
		    if (!pwdVPattern.test(pwd)) {
		    	document.getElementById('error-pwd').style.display='block';
		        document.adminjoin.pwd.focus();
		        return false;
		    }
			--%>
		
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
					<form action="/admin/adminjoin" method="post" name="adminjoin">
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
							<input type="text"  name="artist_id" placeholder="* 아티스트ID" class="txtBx">
							<input type="date" name="birth"class="txtBx">
							<div class="doubleCheck">
								<input type="tel" maxlength="11" name="phone" placeholder="* 휴대폰번호"  oninput="resetPhoneAvailability()"  class="txtBx">
								<button type="button" class="btn-border" onclick="phoneCheck(this.form.phone.value)">중복확인</button>
							</div>
							<div class="doubleCheck">
								<input type="text" name="nickname" placeholder="* 닉네임" oninput="resetNickAvailability()"  class="txtBx">
								<button type="button" class="btn-border"  onclick="nicknameCheck(this.form.nickname.value)">중복확인</button>
							</div>
							<select class="sltBx" name="level">
							    <option value="">해당 없음</option>
							    <option value="1" <c:if test="${insert.level == 1}">selected</c:if>>level1</option>
							    <option value="2" <c:if test="${insert.level == 2}">selected</c:if>>level2</option>
							</select>
							<input type="date" class="txtBx" name="start_date" value="${insert.start_date}" placeholder="구독권 시작일">
							<input type="date" class="txtBx" name="end_date" value="${insert.end_date}" placeholder="구독권 종료일">
							<select class="sltBx" name="role_id">
							    <option value="0" <c:if test="${insert.role_id == 0}">selected</c:if>>직원</option>
							    <option value="1" <c:if test="${insert.role_id == 1}">selected</c:if>>아티스트</option>
							    <option value="2" <c:if test="${insert.role_id == 2}">selected</c:if>>일반 유저</option>
							</select>					
						</div>
						<button type="submit"  onclick="validityCheck()"class="btn-full">계정생성</button>
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