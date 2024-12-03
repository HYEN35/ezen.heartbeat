<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>
<% 
	request.setAttribute("noticePage", "notice");
%>

<body>
	<script>
		$(function(){			
			$('#search-btn').click(function(){
				var searchType = $('#searchType').val();
				var keyword = $('#keyword').val();				
				location.href="/admin/notice?num=1&searchType="+searchType+"&keyword="+keyword;
			});
			
		});
	</script>

	<div class="inner admin notice-list" data-name="notice">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea">
					<div class="tabBtn">
						<ul>
							<li data-tab="tab-notice" class="tab on"><a href="/admin/notice?num=1">전체 공지 및 문의</a></li>
							<li data-tab="tab-notice" class="tab"><a href="/admin/mynotice?num=1">내 문의글 확인</a></li>
						</ul>
					</div>
					<div class="tabCnt">
						<div class="searchWrap">
							<div class="searchBx">
								<select name="searchType" id="searchType" class="sltBx">
									<option value="title">제목</option>
									<option value="nickname">작성자</option>
									<option value="content">내용</option>
								</select> 
								<input type="search" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
								<button id="search-btn" type="button" class="btn-border">검색</button>
								<a href="/admin/postNotice" class="btn-full">작성</a>
							</div>
						</div>
						<ul class="itemWrap">
							<c:forEach items="${adminPost }" var="avo">
								<li class="item">
									<a href="/admin/getPostOne?notice_id=${avo.notice_id }&num=${select }" class="post adminNotice"> 
										<i class="title">${avo.title }</i>
										<i class="writer">${avo.nickname }</i>
									</a>
								</li>
							</c:forEach>									
							<c:forEach items="${userPost }" var="uvo">
								<li class="item">
									<a href="/admin/getPostOne?notice_id=${uvo.notice_id }&num=${select}" class="post"> 
										<i class="title">${uvo.title }</i>
										<i class="writer">${uvo.nickname }</i>
									</a>
								</li>
							</c:forEach>
						</ul>
						<div class="pagination">
							<c:if test="${page.prev }">
								<a href="/admin/notice?num=${page.startPageNum-1 }" class="btn-i-prev"></a>
								</c:if>
									<div class="page">
										<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
										<c:if test="${select != num}">
										<a href="/admin/notice?num=${num }" class="num">${num }</a>
										</c:if>
										<c:if test="${select == num}">
										<a href="/admin/notice?num=${num }" class="num on">${num }</a>
										</c:if>
										</c:forEach>
									</div>
								<c:if test="${page.next }">
								<a href="/admin/notice?num=${page.endPageNum+1 }" class="btn-i-next"></a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>
</body>
</html>