<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	<div class="inner admin member" data-name="member">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="topCnt">
						<div class="searchWrap">
							<div class="searchBx">
								<select class="sltBx">
									<option value="#name">이름</option>
									<option value="#email">이메일</option>
									<option value="#nickname">닉네임</option>
								</select>
								<input type="text" class="txtBx" placeholder="검색어 입력">
								<button type="button" class="btn-border">검색</button>
							</div>
							<div class="btnBx">
								<!-- [D] 신규등록 미정 -->
								<!-- <button type="button" class="btn-full">신규등록</button> -->
							</div>
						</div>
					</div>
					<div class="midCnt">
						<ul class="list">
						<c:forEach items="${urList}" var="uvo">
							<li class="item">
								<div class="infoWrap">
									
									<span class="info">이름 : <i id="name">${uvo.name}</i></span>
									<span class="info">이메일 : <i id="email">${uvo.email}</i></span>
									<span class="info">닉네임 : <i id="nickname">${uvo.nickname}</i></span>
									<span class="info">생년월일 : <i id="birth">${uvo.birth}</i></span>
									<span class="info">핸드폰번호 : <i id="phone">${uvo.phone}</i></span>
									<span class="info">구독등급 : <i id="level">${uvo.level}</i></span>
									<span class="info">가입일 : <i id="reg_date">${uvo.reg_date}</i></span>
								</div>
								<div class="btnWrap">
									<a href="/admin/edit?email=${uvo.email}" class="btn-border">수정</a>
									<button type="button" class="btn-border-01" onclick="deleteMember(this);">삭제</button>
								</div>
							</li>	
						</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>

	<script>
		//회원삭제
		function deleteMember(e){
			var name = $(e).closest('.item').find('#name').text();
			if(confirm(name + ' 회원을 삭제하시겠습니까?')){
				alert("삭제가 완료되었습니다.");
			}
		}
	</script>
</body>
</html>