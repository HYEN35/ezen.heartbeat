<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	
	<script>
		<c:if test="${not empty message}">
		    alert("${message}");
		</c:if>
	</script>
	
	<script>
		$(function(){
			// 검색 버튼 클릭 시
			$('#search-btn1').click(function(){
				var searchType = $('#searchType').val();
				var keyword = $('#keyword').val();
				location.href="/admin/mynotice?&num=1&searchType="+searchType+"&keyword="+keyword;
			});
			
			// 전체 선택 버튼 클릭 시
		    $('#select-all').click(function() {
		        // 모든 체크박스를 선택/해제
		        $('.check').prop('checked', function(_, checked) {
		            return !checked; // 반전시켜서 선택/해제
		        });
		    });

		});
		
		// 삭제 버튼 클릭 시 확인 창 띄우기
	    function confirmDelete() {
	        var checkboxes = document.querySelectorAll('.check:checked');
	        
	        if (checkboxes.length === 0) {
	            alert("삭제할 게시물을 선택해 주세요.");
	            return; // 선택된 게시물이 없으면 삭제하지 않음
	        }

	        // 확인 창 띄우기
	        var confirmed = window.confirm("선택한 게시물을 삭제하시겠습니까?");

	        if (confirmed) {
	            // 선택된 게시물 ID를 배열로 만들어서 폼에 추가
	            var noticeIds = [];
	            checkboxes.forEach(function(checkbox) {
	                noticeIds.push(checkbox.value);
	            });

	            // 선택된 게시물 ID를 hidden input에 추가하여 폼에 포함시킴
	            var input = document.createElement('input');
	            input.type = 'hidden';
	            input.name = 'notice_ids'; // 서버에서 받는 파라미터 이름
	            input.value = noticeIds.join(','); // 콤마로 구분된 문자열로 저장
	            document.getElementById('deleteForm').appendChild(input);

	            // 폼 제출
	            document.getElementById('deleteForm').submit();
	        }
	    }
		
	</script>	
	
	<div class="inner admin my-notice" data-name="notice">
		<%@ include file="../include/admin.jsp" %>
		
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="tabBtn">
						<ul>
							<li data-tab="tab-notice" class="tab"><a href="/admin/notice?num=1">전체 공지 및 문의</a></li>
							<li data-tab="tab-notice" class="tab on"><a href="/admin/mynotice?num=1">내 문의글 확인</a></li>
						</ul>
					</div>					
					<div class="tabCnt">
						<div > <!-- class="cntBx tab-post" -->
							<div class="cnt">
								<div class="searchWrap">
								    <div class="searchBx">
								        <select class="sltBx" id="searchType" name="searchType">
								            <option value="title">제목</option>
								            <option value="content">내용</option>
								        </select>
								        <input type="text" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
								        <button type="button" id="search-btn1" class="btn-border">검색</button>
								    </div>
								    <div class="btnBx">
								        <button type="button" class="btn-full" id="select-all">전체선택</button>
								        <button type="button" class="btn-border" id="delete-btn" onclick="confirmDelete()">삭제</button>
								    </div>
								</div>
								<form id="deleteForm" action="/mypage/deleteNotice" method="POST">
								    <ul class="itemWrap">
								    	<c:forEach items="${userNoticeList}" var="userNotice">
								            <li class="item">
								                <input type="checkbox" class="check" value="${userNotice.notice_id}">
								                <div class="num"><i>${userNotice.notice_id}</i></div>
								                <a href="/admin/getMyPostOne?notice_id=${userNotice.notice_id }&num=${select}" class="tit"><i>${userNotice.title}</i></a>
								            </li>
								    	</c:forEach>
								    </ul>
								</form>
								<div class="pagination">
									<c:if test="${page.prev }">
										<a href="/admin/mynotice?num=${page.startPageNum-1 }" class="btn-i-prev"></a>
										</c:if>
											<div class="page">
												<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
												<c:if test="${select != num}">
												<a href="/admin/mynotice?num=${num }" class="num">${num }</a>
												</c:if>
												<c:if test="${select == num}">
												<a href="/admin/mynotice?num=${num }" class="num on">${num }</a>
												</c:if>
												</c:forEach>
											</div>
										<c:if test="${page.next }">
										<a href="/admin/mynotice?num=${page.endPageNum+1 }" class="btn-i-next"></a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>