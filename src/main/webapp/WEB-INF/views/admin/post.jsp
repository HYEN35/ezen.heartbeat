<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	<div class="inner admin post" data-name="post">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="topCnt">
						<div class="searchWrap">
							<div class="searchBx">
								<select class="sltBx">
									<option value="#name">작성자</option>
									<option value="#contents">내용</option>
								</select>
								<input type="text" class="txtBx" placeholder="검색어 입력">
								<button type="button" class="btn-border">검색</button>
							</div>
						</div>
					</div>
					<div class="midCnt">
						<ul class="list">
						<c:forEach items="${poList}" var="pvo">
							<li class="item">
								<div class="infoWrap">
									<span class="info">게시물 번호 : <i id="num">${pvo.post_id}</i></span>
									<span class="info">작성자 : <i>${pvo.nickname}</i></span>
									<span class="info">작성일 : <i>${pvo.post_date}</i></span>
									<span class="info content">내용 : <i class="elps">${pvo.content}</i></span>
								</div>
								<div class="btnWrap">
									<button type="button" class="btn-border-01" onclick="deleteItem(${pvo.post_id})">삭제</button>
								</div>
							</li>
						</c:forEach>
						</ul>
						<div class="pagination">
							<button type="button" class="btn-i-prev"></button>
							<ul class="page">
								<li class="num on">1</li>
								<li class="num">2</li>
								<li class="num">3</li>
								<li class="num">4</li>
								<li class="num">5</li>
							</ul>
							<button type="button" class="btn-i-next"></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>

	<script>
	// 게시글 삭제
	function deleteItem(postId) {
	    if (confirm('게시글 번호 ' + postId + '을 삭제하시겠습니까?')) {
	        // 삭제 요청을 서버로 보냄
	        window.location.href = '/admin/post/delete?post_id=' + postId;
	    }
	}
	</script>
</body>
</html>