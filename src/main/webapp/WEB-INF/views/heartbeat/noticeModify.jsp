<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp"%>

<body>



	<div class="inner service mypage" data-name="mypage">
		<%@ include file="../include/menu.jsp"%>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea"></div>
				<form action="/notice/noticeModify" method="post">
					<input type="hidden" name="notice_id" value="${noticeVO.notice_id }">
					<input type="hidden" name="num" value="${num }">
					<div>작성자 : ${noticeVO.nickname }</div>
					<div>
						<span>제목 :</span>
						<input type="text" name="title" value="${noticeVO.title }">
					</div>
					<div>
						<span>내용 : </span>
						<textarea name="content">${noticeVO.content}</textarea>
						
					</div>
					<div>작성일 : <fmt:formatDate value="${noticeVO.post_date}" pattern="yyyy-MM-dd HH:mm"/></div>
					<button type="submit">수정</button>
					<a href="/notice/getPostOne?notice_id=${noticeVO.notice_id }&num=${num}">취소</a>
				</form>
			</div>
		</div>
	</div>






</body>
</html>