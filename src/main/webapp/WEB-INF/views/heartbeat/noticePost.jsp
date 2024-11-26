<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp"%>

<body>



	<div class="inner service mypage" data-name="mypage">
		<%@ include file="../include/menu.jsp"%>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea">게시물 작성</div>
				<form action="/notice/noticeWrite" method="post">
					<input type="hidden" name="email" value="${UserVO.email }">
					<input type="hidden" name="nickname" value="${UserVO.nickname}">
					<div>
						<span>제목 입력 :</span> <input type="text" name="title">
					</div>
					<div>
						<span>내용 입력 : </span>
						<textarea name="content"></textarea>
					</div>
					<button type="submit">등록</button>
					<a href="#">취소</a>
				</form>
			</div>
		</div>
	</div>






</body>
</html>