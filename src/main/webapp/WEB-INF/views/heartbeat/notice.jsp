<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp"%>

<body>
	<script>
	$(function(){
		
		$('#search-btn').click(function(){
			var searchType = $('#searchType').val();
			var keyword = $('#keyword').val();				
			location.href="/notice/notice?num=1&searchType="+searchType+"&keyword="+keyword;
		});
		
	});
	</script>
	<div>

		<div class="inner service mypage" data-name="mypage">
			<%@ include file="../include/menu.jsp"%>

			<div class="container">
				<div class="cntWrap">
					<h2 id="title" class="title"><%=pageTitle%></h2>
					<div class="cntArea">
						<div class="tabBtn">
							<ul>
								<li data-tab="tab-post" class="tab">작성글 확인</li>
							</ul>
						</div>
						<div class="tabCnt">
							<div class="cnt">
								<div class="searchWrap">
									<div class="searchBx">
										<select name="searchType" id="searchType" class="sltBx">
											<option value="title">제목</optoin>
											<option value="nickname">작성자</optoin>
											<option value="content">내용</optoin>
										</select> 
										<input type="search" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
										<button id="search-btn" type="button" class="btn-border">검색</button>
									</div>
									<div class="btnBx">
										<a href="/notice/postNotice" class="btn-full">작성</a>
									</div>
								</div>
								<ul class="itemWrap">
									<c:forEach items="${adminPost }" var="avo">
									<li class="item">
										 <a href="/notice/getPostOne?notice_id=${avo.notice_id }&num=${select }" class="tit" style="color:red;"> <i>${avo.title }</i>
										 <i>${avo.nickname }</i>
										</a>
									</li>
									</c:forEach>
									
									<c:forEach items="${userPost }" var="uvo">
									<li class="item">
										<div class="num">
										</div> 
										<a href="/notice/getPostOne?notice_id=${uvo.notice_id }&num=${select}"> <i>${uvo.title }</i>
										<i>${uvo.nickname }</i>
										</a>
									</li>
									</c:forEach>
								</ul>
								<div class="pagination">
									<c:if test="${page.prev }">
									<a href="/notice/notice?num=${page.startPageNum-1 }" class="btn-i-prev"></a>
									</c:if>
									<div class="page">
										<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
										<c:if test="${select != num}">
										<a href="/notice/notice?num=${num }" class="num">${num }</a>
										</c:if>
										<c:if test="${select == num}">
										<a href="/notice/notice?num=${num }" class="num on">${num }</a>
										</c:if>
										</c:forEach>
									</div>
									<c:if test="${page.next }">
									<a href="/notice/notice?num=${page.endPageNum+1 }" class="btn-i-next"></a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="dimmed" onclick="popAlertCheckHide()"></div>
		<div class="dimmed" onclick="popDeleteUserHide();"></div>
		<div class="dimmed" onclick="payhide();"></div>


		<!-- [D] 팝업 중복확인 -->
		<div class="popup pop-alert-check"><%@ include
				file="../popup/pop-alert-check.jsp"%></div>
		<!-- [D] 팝업 회원 탈퇴 -->
		<div class="popup pop-delete-user"><%@ include
				file="../popup/pop-delete-user.jsp"%></div>
		<div class="popup pop-pay artist"><%@ include
				file="../popup/pop-pay-artist.jsp"%></div>
		<div class="popup pop-pay streaming"><%@ include
				file="../popup/pop-pay-streaming.jsp"%></div>
</body>
</html>