<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
	//팝업 아티스트포스트
	function popPostArtistShow(post_id,email){
		 // AJAX 요청으로 데이터를 가져옵니다.
		 
	    $.post("/community/getArtistPost", { post_id: post_id, email : email }, function(data) {
	    	console.log(data); // 반환된 데이터 확인
	    	// 기존의 cntArea를 비우지 않고 데이터를 추가하거나 수정합니다.
	        const newContent = $(data).find('.cntArea').html(); // JSP에서 cntArea만 가져오기
	        console.log(newContent); // newContent 확인
	        $('.pop-post-artist .cntArea').html(newContent);
	        
	
	        
	
	        // 팝업을 보여줍니다.
	    }).fail(function() {
	        console.error('Error loading post data.');
	    });
		
		$('.pop-post-artist').show();
		$('.dimmed').show();
	
	}
	//팝업 팬포스트
	function popPostFanShow(post_id){
		 // AJAX 요청으로 데이터를 가져옵니다.
	    $.post("/community/getUserPost", { post_id: post_id }, function(data) {
	    	// 기존의 cntArea를 비우지 않고 데이터를 추가하거나 수정합니다.
	        const newContent = $(data).find('.cntArea').html(); // JSP에서 cntArea만 가져오기
	        $('.pop-post-fan .cntArea').html(newContent);
	        //txtBx, btn-i-send 관리자 페이지에서는 입력할 필요 없으므로 히든처리(예정) 
	        
	        // 팝업을 보여줍니다.
	    }).fail(function() {
	        console.error('Error loading post data.');
	    });
	        $('.pop-post-fan').show();
	        $('.dimmed').show();
		
		//uploadFileName();
		multipleUploadFile();
	}
<<<<<<< HEAD
	function popPostFanHide(){
		$('.pop-post-fan').hide();
	    $('.dimmed').hide();
	}
=======
>>>>>>> origin/kimhonghyun
</script>

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
								<select name="searchType" id="searchType" class="sltBx">
									<option value="post_id">게시물 번호</option>
									<option value="nickname">닉네임</option>
									<option value="post_date">작성일</option>
									<option value="content">내 용</option>
								</select>
								<input type="search" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
								<button id="search-btn" type="button" class="btn-border">검색</button>
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
						                <span class="info">작성일 : 
						                    <i>
						                        <fmt:formatDate value="${pvo.post_date}" pattern="yyyy-MM-dd HH:mm:ss" />
						                    </i>
						                </span>
						                <span class="info content">내용 : <i class="elps">${pvo.content}</i></span>
						            </div>
						            <div class="btnWrap">
<<<<<<< HEAD
						         	   <a href="javascript:void(0);" class="btn-border" onclick="popPostFanShow(${pvo.post_id})" >보기</a>
=======
						         	   <a href="javascript:void(0);" onclick="popPostFanShow(${pvo.post_id})" >보기</a>
>>>>>>> origin/kimhonghyun
						                <button type="button" class="btn-border-01" onclick="deleteItem(${pvo.post_id})">삭제</button>
						            </div>
						        </li>
						    </c:forEach>
<<<<<<< HEAD
						</ul>
						<%-- Page 객체(DTO)를 사용한 페이징 처리 --%>
						<div class="pagination">
							<ul class="page">
								<c:if test="${page.prev}">
								<li><a href="/admin/post?num=${page.startPageNum - 1}" class="btn-i-prev"><i class="bi bi-chevron-left"></i></a></li>
								</c:if>
								
								<%-- 페이지 번호 버튼 --%>
								<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
								<li>
									<c:if test="${select != num}">
									<a href="/admin/post?num=${num}" class="num">${num}</a>
									</c:if>
									<c:if test="${select == num}">
									<a href="" class="num on">${num}</a>
									</c:if>
								</li>
								</c:forEach>
								
								<c:if test="${page.next}">
								<li><a href="/admin/post?num=${page.endPageNum + 1}" class="btn-i-next"><i class="bi bi-chevron-right"></i></a></li>
								</c:if>
							</ul>
						</div>
=======
						</ul>
						<%-- Page 객체(DTO)를 사용한 페이징 처리 --%>
						<ul class="pagenation">
							<c:if test="${page.prev}">
							<li><a href="/admin/post?num=${page.startPageNum - 1}" class="btn-i-prev"><i class="bi bi-chevron-left"></i></a></li>
							</c:if>
							
							<%-- 페이지 번호 버튼 --%>
							<c:forEach begin="${page.startPageNum}" end="${page.endPageNum}" var="num">
							<li>
								<c:if test="${select != num}">
								<a href="/admin/post?num=${num}">${num}</a>
								</c:if>
								<c:if test="${select == num}">
								<a href="" class="num on">${num}</a>
								</c:if>
							</li>
							</c:forEach>
							
							<c:if test="${page.next}">
							<li><a href="/admin/post?num=${page.endPageNum + 1}" class="btn-i-next"><i class="bi bi-chevron-right"></i></a></li>
							</c:if>
						</ul>
>>>>>>> origin/kimhonghyun
					</div>
				</div>
			</div>
		</div>
<<<<<<< HEAD
	</div>	

	<div class="dimmed" onclick="popPostFanHide();"></div>

=======
	</div>
	
	
	

	<div class="dimmed"></div>
>>>>>>> origin/kimhonghyun
	<!-- [D] 팝업 팬 포스트 -->
	<div class="popup pop-post-fan"><%@ include file="../popup/pop-post-fan.jsp" %></div>
	<!-- [D] 팝업 아티스트 포스트 -->
	<div class="popup pop-post-artist"><%@ include file="../popup/pop-post-artist.jsp" %></div>

	<script>
	// 게시글 삭제
	function deleteItem(postId) {
	    if (confirm('게시글 번호 ' + postId + '을 삭제하시겠습니까?')) {
	        // 삭제 요청을 서버로 보냄
	        window.location.href = '/admin/post/delete?post_id=' + postId;
	    }
	}
	
	//검색
	$(function(){
		
		$('#search-btn').click(function(){
			var searchType = $('#searchType').val();
			var keyword = $('#keyword').val();				
			location.href="/admin/post?num=1&searchType="+searchType+"&keyword="+keyword;
		});
		
	});
	</script>
</body>
</html>