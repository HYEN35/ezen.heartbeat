<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp"%>

<body>
	<script>
		<c:if test="${not empty message}">
		alert("${message}");
		</c:if>
	</script>

	<script>

		//닉네임 중복 확인 여부
		let isNickAvailable = false;
		let nickDuplicateChecked = false;

		//닉네임 중복확인
		function nicknameCheck(nickname) {
			if (nickname == "") {
				alert(' 닉네임을 입력하세요.');
				document.mypageFrm.nickname.focus();
				return false;
			}
			$.ajax({
				url : '/join/nicknamecheck',
				type : 'POST',
				data : {
					nickname : nickname
				},
				success : function(data) {
					nickDuplicateChecked = true;
					if (data === 'success') {
						popAlertCheckShow("사용 불가능합니다.");
						$('.msg').text(nickname + '은/는 사용 불가능합니다. ');
						isNickAvailable = false;
					} else {
						popAlertCheckShow("중복된 사용 가능합니다");
						$('.msg').text(nickname + '은/는 사용 가능합니다.');
						isNickAvailable = true;
					}
					popAlertCheckShow();
				}
			});
		}

		function resetNickAvailability() {
			nickDuplicateChecked = false;
		}

		//유효성 체크
		function mypageValidityCheck() {
			if (document.mypageFrm.pwd.value == '') {
				alert('비밀번호를 입력하세요.');
				document.mypageFrm.pwd.focus();
				return false;
			}
			if (document.mypageFrm.newPwd.value == '') {
				alert('비밀번호를 입력하세요.');
				document.mypageFrm.pwd.focus();
				return false;
			}
			if (document.mypageFrm.newPwdCheck.value == '') {
				alert('비밀번호를 입력하세요.');
				document.mypageFrm.pwd.focus();
				return false;
			}

			// 비밀번호 유효성 체크
			const pwd = document.mypageFrm.newPwd.value;
			const pwdVPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,16}$/;
			if (!pwdVPattern.test(pwd)) {
				alert("비밀번호는 대문자 영어, 특수문자, 숫자를 포함하여 8~16자로 해주세요.");
				document.mypageFrm.pwd.focus();
				return false;
			}

			if (document.mypageFrm.newPwd.value != document.mypageFrm.newPwdCheck.value) {
				alert("입력한 새 비밀번호가 서로 일치하지 않습니다. 다시 확인해주세요.");
				document.mypageFrm.pwd.focus();
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

			document.mypageFrm.submit();
		}
		// 닉네임 팝업
		function popAlertCheckShow() {
			$('.pop-alert-check').show();
			$('.dimmed').show();
		}
		function popAlertCheckHide() {
			$('.pop-alert-check').hide();
			$('.dimmed').hide();
		}

		// 탈퇴 팝업
		function popDeleteUserShow() {
			$('.pop-delete-user').show();
			$('.dimmed').show();
		}
		function popDeleteUserHide() {
			$('.pop-delete-user').hide();
			$('.dimmed').hide();
		}
	</script>


	<div class="inner service my-page" data-name="mypage">
		<%@ include file="../include/menu.jsp"%>

		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea">
					<div class="tabBtn">
						<ul>
							<li data-tab="tab-mypage" class="tab on"><a href="/mypage">내 정보 변경</a></li>
							<li data-tab="tab-membership" class="tab"><a href="/mymembership">멤버십 변경</a></li>
							<li data-tab="tab-post" class="tab"><a href="/mypost?num=1">내 작성글 확인</a></li>
						</ul>
					</div>
					<div class="tabCnt">
						<form action="/mypage/modify" method="post" name="mypageFrm">
							<div class="cntBx tab-mypage on">
								<div class="cnt">
									<ul class="itemWrap">
										<li class="item password">
											<p class="dt">비밀번호 변경</p>
											<div class="dd">
												<input type="password" name="pwd" class="txtBx"
													placeholder="현재 비밀번호 입력"> <input type="password"
													name="newPwd" class="txtBx" placeholder="새 비밀번호 입력">
												<input type="password" name="newPwdCheck" class="txtBx"
													placeholder="새 비밀번호 확인">
											</div>
										</li>
										<li class="item nickname">
											<p class="dt">닉네임 변경</p>
											<div class="dd">
												<input type="text" name="nickname"
													oninput="resetNickAvailability()" class="txtBx"
													placeholder="닉네임 입력">
												<button type="button"
													onclick="nicknameCheck(this.form.nickname.value)"
													class="btn-border">중복확인</button>
											</div>
										</li>
										<li class="item image">
											<p class="dt">프로필 사진 변경</p>
											<div class="dd">
												<input type="file" hidden>
												<button type="button" class="btn-border"
													onclick="$(this).siblings('input').click();">사진 변경</button>
												<p class="file">
													<i>filenme.jpg</i>
												</p>
											</div>
										</li>
										<li class="item">
											<p class="dt">회원 탈퇴</p>
											<div class="dd">
												<button type="button" class="btn-under-01"
													onclick="popDeleteUserShow()">탈퇴하기</button>
											</div>
										</li>
									</ul>
								</div>
								<div class="btnWrap">
									<button type="button" class="btn-full"
										onclick="mypageValidityCheck()">저장</button>
									<button type="button" class="btn-border">취소</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed" onclick="popAlertCheckHide()"></div>
	<div class="dimmed" onclick="popDeleteUserHide();"></div>

	<!-- [D] 팝업 중복확인 -->
	<div class="popup pop-alert-check"><%@ include file="../popup/pop-alert-check.jsp"%></div>
	<!-- [D] 팝업 회원 탈퇴 -->
	<div class="popup pop-delete-user"><%@ include file="../popup/pop-delete-user.jsp"%></div>
</body>
</html>