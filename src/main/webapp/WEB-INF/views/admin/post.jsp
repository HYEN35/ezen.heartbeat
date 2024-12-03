<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
								<!-- Role ID 필터 -->
								<label><input type="checkbox" name="role_id" value="1">아티스트</label>
								<label><input type="checkbox" name="role_id" value="2">일반 유저</label>
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
										<a href="javascript:void(0);" class="btn-border" onclick="popPostArtistShow('${pvo.post_id}', '${pvo.email}')">보기</a>
										<button type="button" class="btn-border-01" onclick="deleteItem(${pvo.post_id})">삭제</button>
						            </div>
						        </li>
						    </c:forEach>
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
					</div>
				</div>
			</div>
		</div>
	</div>	

	<div class="dimmed" onclick="popPostFanHide();"></div>
	<!-- [D] 팝업 아티스트 포스트 -->
	<div class="popup pop-post-artist"><%@ include file="../popup/pop-post-artist.jsp" %></div>

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
	
	// 게시글 삭제
	function deleteItem(postId) {
	    if (confirm('게시글 번호 ' + postId + '을 삭제하시겠습니까?')) {
	        // 삭제 요청을 서버로 보냄
	        window.location.href = '/admin/post/delete?post_id=' + postId;
	    }
	}
	
	//검색, 필터(체크박스-아티스트,유저)
	$(function() {
	    $('#search-btn').click(function() {
	        var searchType = $('#searchType').val();
	        var keyword = $('#keyword').val();
	        
	        // 체크된 role_id 값 가져오기
	        var roleIds = [];
	        $('input[name="role_id"]:checked').each(function() {
	            roleIds.push($(this).val());
	        });

	        // role_id 파라미터 추가
	        var roleIdParam = roleIds.length > 0 ? "&role_id=" + roleIds.join(",") : "";

	        location.href = "/admin/post?num=1&searchType=" + searchType + "&keyword=" + keyword + roleIdParam;
	    });
	});
	</script>
</body>
</html>