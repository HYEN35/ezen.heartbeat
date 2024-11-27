<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	<div class="inner admin member" data-name="member">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title">STAFF LIST</h2>
				<div class="cntArea">
					<div class="topCnt">
						<div class="searchWrap">
							<div class="searchBx">
								<select id="searchType" class="sltBx">
									<option value="name">이 름</option>
									<option value="email">이메일</option>
									<option value="nickname">닉네임</option>
									<option value="phone">연락처</option>
								</select>
								<input type="search" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
								<button id="search-btn" type="button" class="btn-border">검색</button>
							</div>
							<div class="btnBx">
								<a href="/admin/adminjoin" class="btn-full">신규등록</a>
							</div>
						</div>
					</div>
					<div class="midCnt">
						<ul class="list">
						<c:forEach items="${staffList}" var="staffList">
							<li class="item">
								<div class="infoWrap">
									
									<span class="info">이름 : <i id="name">${staffList.name}</i></span>
									<span class="info">이메일 : <i id="email">${staffList.email}</i></span>
									<span class="info">닉네임 : <i id="nickname">${staffList.nickname}</i></span>
									<span class="info">생년월일 : <i id="birth">${staffList.birth}</i></span>
									<span class="info">핸드폰번호 : <i id="phone">${staffList.phone}</i></span>
									<span class="info">구독등급 : <i id="level">${staffList.level}</i></span>
									<span class="info">가입일 : <i id="reg_date">${staffList.reg_date}</i></span>
								</div>
								<div class="btnWrap">
									<a href="/admin/edit?email=${staffList.email}" class="btn-border">수정</a>
									<button type="button" class="btn-border-01" onclick="deleteItem('${staffList.email}')">삭제</button>
								</div>
							</li>	
						</c:forEach>
						</ul>
						<!-- 페이징 처리 -->
						<ul class="pagenation">
							<c:if test="${page.prev}">
							<li><a href="/admin/staff?num=${page.startPageNum - 1}" class="btn-i-prev"><i class="bi bi-chevron-left"></i></a></li>
							</c:if>
	
							<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
							<li><a href="/admin/staff?num=${num}" class="${select == num ? 'num on' : ''}">${num}</a></li>
							</c:forEach>
	
							<c:if test="${page.next}">
							<li><a href="/admin/staff?num=${page.endPageNum + 1}" class="btn-i-next"><i class="bi bi-chevron-right"></i></a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>

<script>
	// 직원 삭제
	function deleteItem(email) {
		if (confirm('직원 이메일 ' + email + '을(를) 삭제하시겠습니까?')) {
		    window.location.href = '/admin/staff/delete?email=' + email;
	  }
	}
	
	// 검색 기능 구현
	$(function() {
	  $('#search-btn').click(function() {
	      var searchType = $('#searchType').val();
	      var keyword = $('#keyword').val();
	      location.href="/admin/staff?num=1&searchType="+searchType+"&keyword="+keyword;
	  });
	});
</script>

</body>
</html>