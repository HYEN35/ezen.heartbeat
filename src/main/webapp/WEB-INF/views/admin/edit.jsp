<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>
<% 
	request.setAttribute("memberPage", "member");
%>

<body>
	<div class="inner admin editMember" data-name="member">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title">${modify.email} 님 회원정보 수정</h2>
				<div class="cntArea">
					<div class="midCnt">
						<form action="/admin/edit" method="post" autocomplete="off">
							<input type="hidden" name="num" value="${num }">
							<input type="hidden" name="searchType" value="${searchType }">
							<input type="hidden" name="keyword" value="${keyword }">
							<input type="hidden" name="role_id" value="${role_id }">
							<div class="infoWrap">
								<p>이름 </p><input type="text" class="txtBx" name="name" value="${modify.name}" placeholder="이름">
								<p>email </p><input type="email" class="txtBx" name="email" value="${modify.email}" readonly>
								<p>닉네임 </p><input type="text" class="txtBx" name="nickname" value="${modify.nickname}" placeholder="닉네임">
								<p>생년월일 </p><input type="date" class="txtBx" name="birth" value="${modify.birth}" placeholder="생년월일">
								<p>연락처 </p><input type="number" class="txtBx" name="phone" value="${modify.phone}" placeholder="연락처">
								<p>구독등급 </p><input type="text" class="txtBx" name="level" value="${modify.level}" placeholder="구독등급">
								<p>아티스트 아이디 </p><input type="text" class="txtBx" name="artist_id" value="${modify.artist_id}" placeholder="아티스트 아이디">
								<p>최근 수정일 </p><input type="text" class="txtBx" name="up_date" value="${modify.up_date}" disabled>
							</div>
							<div class="btnWrap">
								<button type="submit" class="btn-full">회원수정</button>
								<a href="/admin/member?num=${num}&searchType=${searchType}&keyword=${keyword}&role_id=${role_id}" class="btn-border">닫기</a>
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