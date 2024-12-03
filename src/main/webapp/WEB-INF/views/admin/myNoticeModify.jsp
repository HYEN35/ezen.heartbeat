<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>
<% 
	request.setAttribute("noticePage", "notice");
%>

<body>
	<div class="inner admin notice-edit" data-name="notice">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea">
					<form action="myNoticeModify" method="post">
						<input type="hidden" name="notice_id" value="${noticeVO.notice_id }">
						<input type="hidden" name="num" value="${num }">
						<div class="writer">${noticeVO.nickname }</div>
						<div class="date"><fmt:formatDate value="${noticeVO.post_date}" pattern="yyyy-MM-dd HH:mm"/></div>
						<div class="title">
							<span class="tit">제목</span>
							<input type="text" name="title" value="${noticeVO.title }" class="txtBx">
						</div>
						<div class="cnts">
							<span class="tit">내용</span>
							<textarea name="content" class="txtBx">${noticeVO.content}</textarea>
						</div>
						<div class="btnWrap">
							<button type="submit" class="btn-full">수정</button>
							<a href="/admin/getMyPostOne?notice_id=${noticeVO.notice_id }&num=${num}" class="btn-border">취소</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>