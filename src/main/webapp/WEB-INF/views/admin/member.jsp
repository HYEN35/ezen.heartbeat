<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	<div class="inner admin member-user" data-name="member">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="tabBtn">
						<ul>
							<li class="tab on"><a href="/admin/member">회원 리스트</a></li>
							<li class="tab"><a href="/admin/staff">직원 리스트</a></li>
						</ul>
					</div>
					<div class="tabCnt">
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
									<!-- Role ID 필터 -->
									<label><input type="checkbox" name="role_id" value="1">아티스트</label>
									<label><input type="checkbox" name="role_id" value="2">일반 유저</label>
									<button id="search-btn" type="button" class="btn-border">검색</button>
								</div>
								<div class="btnBx">
									<!-- [D] 신규등록 미정 -->
									<a href="/admin/adminjoin" class="btn-full">신규등록</a>
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
										<button type="button" class="btn-border-01" onclick="deleteItem('${uvo.email}')">삭제</button>
									</div>
								</li>	
							</c:forEach>
							</ul>
							<%-- Page 객체(DTO)를 사용한 페이징 처리 --%>
							<div class="pagination">
								<ul class="page">
									<c:if test="${page.prev}">
									<li><a href="/admin/member?num=${page.startPageNum - 1}" class="btn-i-prev"><i class="bi bi-chevron-left"></i></a></li>
									</c:if>
									
									<%-- 페이지 번호 버튼 --%>
									<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
									<li>
										<c:if test="${select != num}">
										<a href="/admin/member?num=${num}" class="num">${num}</a>
										</c:if>
										<c:if test="${select == num}">
										<a href="" class="num on">${num}</a>
										</c:if>
									</li>
									</c:forEach>
									
									<c:if test="${page.next}">
									<li><a href="/admin/member?num=${page.endPageNum + 1}" class="btn-i-next"><i class="bi bi-chevron-right"></i></a></li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>

<script>
	// 회원 삭제
	function deleteItem(memberId) {
	    if (confirm('회원 ID ' + memberId + '을(를) 삭제하시겠습니까?')) {
	        // 삭제 요청을 서버로 전송
	        window.location.href = '/admin/member/delete?email=' + memberId;
	    }
	}
   
	//검색
	$(function(){
		
		$('#search-btn').click(function(){
			var searchType = $('#searchType').val();
			var keyword = $('#keyword').val();				
			location.href="/admin/member?num=1&searchType="+searchType+"&keyword="+keyword;
		});
		
	});
</script>
</body>
</html>