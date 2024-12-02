<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

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
	
	<div class="inner admin join">
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
							<select class="sltBx" name="artist_id">
							    <option value="20109">뉴진스 (NewJeans)</option>
							    <option value="20119">블랙핑크 (BLACKPINK)</option>
								<option value="20117">있지 (ITZY)</option>
								<option value="10101">스트레이 키즈 (Stray Kids)</option>
							    <option value="10102">폴킴</option>
							    <option value="10103">허각</option>
							    <option value="10104">멜로망스</option>
							    <option value="10105">NCT 127</option>
							    <option value="10106">SF9</option>
							    <option value="10107">엔하이픈 (ENHYPEN)</option>
							    <option value="10108">온유</option>
							    <option value="10109">위아이 (WEi)</option>
							    <option value="10110">이무진</option>
							    <option value="10111">방탄소년단</option>
							    <option value="10112">적재</option>
							    <option value="10113">정승환</option>
							    <option value="10114">지코</option>
							    <option value="10115">세븐틴 (SEVENTEEN)</option>
							    <option value="10116">트레저 (TREASURE)</option>
							    <option value="10117">김동률</option>
							    <option value="10118">에이티즈 (ATEEZ)</option>
							    <option value="10119">더보이즈 (The Boyz)</option>
							    <option value="10120">몬스타엑스 (MONSTA X)</option>
							    <option value="10121">빅뱅</option>
							    <option value="20101">트와이스</option>
							    <option value="20102">(여자)아이들</option>
							    <option value="20103">아이브 (IVE)</option>
							    <option value="20104">권은비</option>
							    <option value="20105">아이유 (IU)</option>
							    <option value="20106">헤이즈</option>
							    <option value="20107">에스파 (aespa)</option>
							    <option value="20108">김윤아</option>
							    <option value="20110">드림캐쳐</option>
							    <option value="20111">레드벨벳</option>
							    <option value="20112">윤하</option>
							    <option value="20113">르세라핌</option>
							    <option value="20114">이소라</option>
							    <option value="20115">이하이</option>
							    <option value="20116">백아연</option>
							    <option value="20118">백예린</option>
							    <option value="20120">선미</option>
							    <option value="20121">태연</option>
							    <option value="20122">스테이씨</option>
							    <option value="30100">악뮤 (AKMU)</option>
							</select>
							<div class="birthBx">
								<span class="txt">생년월일</span>
								<input type="date" name="birth" value="${insert.birth}" class="txtBx">
							</div>
							<div class="doubleCheck">
								<input type="tel" maxlength="11" name="phone" placeholder="* 휴대폰번호"  oninput="resetPhoneAvailability()"  class="txtBx">
								<button type="button" class="btn-border" onclick="phoneCheck(this.form.phone.value)">중복확인</button>
							</div>
							<div class="doubleCheck">
								<input type="text" name="nickname" placeholder="* 닉네임" oninput="resetNickAvailability()"  class="txtBx">
								<button type="button" class="btn-border"  onclick="nicknameCheck(this.form.nickname.value)">중복확인</button>
							</div>
							<div class="levelBx">
								<span class="txt">등급</span>
								<select class="sltBx" name="level">
									<option value="0"  selected>해당 없음</option>
									<option value="1" >level1</option>
									<option value="2">level2</option>
								</select>
							</div>
							<div class="dateBx">
								<span class="txt">구독기간</span>
								<input type="date" class="txtBx" name="start_date" value="${insert.start_date}">
								<span class="fixed"> ~ </span>
								<input type="date" class="txtBx" name="end_date" value="${insert.end_date}">
							</div>
							<select class="sltBx" name="role_id">
							    <option value="0">직원</option>
							    <option value="1">아티스트</option>
							    <option value="2">일반 유저</option>
							</select>					
						</div>
						<button type="submit" onclick="return validityCheck()" class="btn-full">계정생성</button>
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