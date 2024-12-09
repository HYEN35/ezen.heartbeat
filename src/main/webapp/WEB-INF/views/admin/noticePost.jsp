<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>
<% 
	request.setAttribute("noticePage", "notice");
%>

<body>
	<div class="inner admin notice-post" data-name="notice">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea">
					<form action="/admin/noticeWrite" method="post">
						<input type="hidden" name="email" value="${UserVO.email }">
						<input type="hidden" name="nickname" value="${UserVO.nickname}">
						<div class="title">
							<span class="tit">제목</span>
							<input type="text" name="title" class="txtBx">
						</div>
						<div class="cnts">
							<span class="tit">내용</span>
							<textarea name="content" class="txtBx"></textarea>
						</div>
						<div class="btnWrap">
							<button type="submit" class="btn-full">등록</button>
							<a href="/admin/notice?num=1" class="btn-border">취소</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>