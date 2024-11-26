<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	<div class="inner admin editMember" data-name="member">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title">${modify.email} 님 회원정보 수정</h2>
				<div class="cntArea">
					<div class="midCnt">
						<form action="/admin/edit" method="post" autocomplete="off">
							<div class="infoWrap">
								<input type="text" class="txtBx" name="name" value="${modify.name}" placeholder="이름">
								<input type="email" class="txtBx" name="email" value="${modify.email}" readonly>
								<input type="text" class="txtBx" name="nickname" value="${modify.nickname}" placeholder="닉네임">
								<input type="date" class="txtBx" name="birth" value="${modify.birth}" placeholder="생년월일">
								<input type="number" class="txtBx" name="phone" value="${modify.phone}" placeholder="핸드폰번호">
								<input type="text" class="txtBx" name="level" value="${modify.level}" placeholder="구독등급">
								<input type="text" class="txtBx" name="up_date" value="${modify.up_date}" disabled>
							</div>
							<div class="btnWrap">
								<button onclick="location.href='/admin/edit?email=${modify.email}'" type="submit" class="btn-full">회원수정</button>
								<button onclick="location.href='/admin/member'" type="button" class="btn-border">닫기</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>
</body>
</html>