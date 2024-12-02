<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<body>
	<div class="inner admin comment" data-name="comment">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="topCnt">
						<div class="searchWrap">
							<div class="searchBx">
								<select id="searchType" class="sltBx">
									<option value="comment_id">댓글번호</option>
									<option value="nickname">닉네임</option>
									<option value="comment_date">작성일</option>
									<option value="content">내 용</option>
								</select>
								<input type="search" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
								<button id="search-btn" type="button" class="btn-border">검색</button>
							</div>
						</div>
					</div>
					<div class="midCnt">
						<ul class="list">
						<c:forEach items="${coList}" var="cvo">
							<li class="item">
								<div class="infoWrap">
									<span class="info">게시물 번호 : <i>${cvo.post_id}</i></span>
									<span class="info">댓글 번호 : <i id="num">${cvo.comment_id}</i></span>
									<span class="info">작성자 : <i>${cvo.nickname}</i></span>
									<span class="info">작성일 : 
										<i>
											<fmt:formatDate value="${cvo.comment_date}" pattern="yyyy-MM-dd HH:mm:ss" />
										</i>
									</span>
									<span class="info content">내용 : <i class="elps">${cvo.comment}</i></span>
								</div>
								<div class="btnWrap">
									<button type="button" class="btn-border-01" onclick="deleteItem(${cvo.comment_id})">삭제</button>
								</div>
							</li>
						</c:forEach>	
						</ul>
						<%-- Page 객체(DTO)를 사용한 페이징 처리 --%>
						<div class="pagination">
							<ul class="page">
								<c:if test="${page.prev}">
								<li><a href="/admin/comment?num=${page.startPageNum - 1}" class="btn-i-prev"><i class="bi bi-chevron-left"></i></a></li>
								</c:if>
								
								<%-- 페이지 번호 버튼 --%>
								<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
								<li>
									<c:if test="${select != num}">
									<a href="/admin/comment?num=${num}" class="num">${num}</a>
									</c:if>
									<c:if test="${select == num}">
									<a href="/admin/comment?num=${num}" class="num on">${num}</a>
									</c:if>
								</li>
								</c:forEach>
								
								<c:if test="${page.next}">
								<li><a href="/admin/comment?num=${page.endPageNum + 1}" class="btn-i-next"><i class="bi bi-chevron-right"></i></a></li>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

	<div class="dimmed"></div>

	<script>
    // 댓글 삭제
	function deleteItem(commentId) {
	    if (confirm('댓글 번호 ' + commentId + '을 삭제하시겠습니까?')) {
	        // 삭제 요청을 서버로 보냄
	        window.location.href = '/admin/comment/delete?comment_id=' + commentId;
	    }
	}
    
	//검색
	$(function(){
		
		$('#search-btn').click(function(){
			var searchType = $('#searchType').val();
			var keyword = $('#keyword').val();				
			location.href="/admin/comment?num=1&searchType="+searchType+"&keyword="+keyword;
		});
		
	});
	</script>
</body>
</html>