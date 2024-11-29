<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

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
				location.href="/mypost?&num=1&searchType="+searchType+"&keyword="+keyword;
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
	            var postIds = [];
	            checkboxes.forEach(function(checkbox) {
	                postIds.push(checkbox.value);
	            });

	            // 선택된 게시물 ID를 hidden input에 추가하여 폼에 포함시킴
	            var input = document.createElement('input');
	            input.type = 'hidden';
	            input.name = 'post_id'; // 서버에서 받는 파라미터 이름
	            input.value = postIds.join(','); // 콤마로 구분된 문자열로 저장
	            document.getElementById('deleteForm').appendChild(input);

	            // 폼 제출
	            document.getElementById('deleteForm').submit();
	        }
	    }
		
	</script>

	<script>
		$(function(){
			mypageTab();
			tabListShow();
		});
	
		//팝업 팬포스트
		function popPostFanShow(post_id){
			 // AJAX 요청으로 데이터를 가져옵니다.
		    $.post("/community/getUserPost", { post_id: post_id }, function(data) {
		    	// 기존의 cntArea를 비우지 않고 데이터를 추가하거나 수정합니다.
		        const newContent = $(data).find('.cntArea').html(); // JSP에서 cntArea만 가져오기
		        $('.pop-post-fan .cntArea').html(newContent);
		        
		        
		        // 팝업을 보여줍니다.
		    }).fail(function() {
		        console.error('Error loading post data.');
		    });
		        $('.pop-post-fan').show();
		        $('.dimmed').show();
			
			//uploadFileName();
			multipleUploadFile();
		}
		function popPostFanHide(){
			$('.pop-post-fan').hide();
			$('.dimmed').hide();
		}
		
	</script>
	
	
	<div class="inner service mypage" data-name="mypage">
		<%@ include file="../include/menu.jsp" %>
		
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="tabBtn">
						<ul>
							<li data-tab="tab-post" class="tab">작성글 확인</li>
						</ul>
					</div>				
					<div class="tabCnt">

						<div > <!-- class="cntBx tab-post" -->
							<div class="cnt">
								<div class="searchWrap">
								    <div class="searchBx">
								        <select class="sltBx" id="searchType" name="searchType">
								            <option value="content">내용</option>
								            <option value="nickname">작성자</option>
								        </select>
								        <input type="text" name="keyword" id="keyword" class="txtBx" placeholder="검색어 입력">
								        <button type="button" id="search-btn1" class="btn-border">검색</button>
								    </div>
								    <div class="btnBx">
								        <button type="button" class="btn-full" id="select-all">전체선택</button>
								        <button type="button" class="btn-border" id="delete-btn" onclick="confirmDelete()">삭제</button>
								    </div>
								</div>
								<form id="deleteForm" action="/mypost/deletePost" method="POST">
								    <c:forEach items="${userPostList}" var="userPost">
								        <ul class="itemWrap">
								            <li class="item">
								                <input type="checkbox" class="check" value="${userPost.post_id}">
								                <div class="num">게시물 번호 : <i>${userPost.post_id}</i></div>
								                <a href="#none" class="tit" onclick="popPostFanShow(${userPost.post_id})">
								                  	  게시물 내용 : <i>${userPost.content}</i>
								                </a>
								            </li>
								        </ul>
								    </c:forEach>
								</form>
								<div class="pagination">
									<c:if test="${page.prev }">
										<a href="/mypost?num=${page.startPageNum-1 }" class="btn-i-prev"></a>
										</c:if>
											<div class="page">
												<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
												<c:if test="${select != num}">
												<a href="/mypost?num=${num }" class="num">${num }</a>
												</c:if>
												<c:if test="${select == num}">
												<a href="/mypost?num=${num }" class="num on">${num }</a>
												</c:if>
												</c:forEach>
											</div>
										<c:if test="${page.next }">
										<a href="/mypost?num=${page.endPageNum+1 }" class="btn-i-next"></a>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${not empty addClass}">
	    <script>        
	        // 모든 탭에서 on 클래스 제거
	        $('.tabBtn .tab').removeClass('on');
	        
	        // 작성글 확인 탭에 on 클래스 추가
	        $('.tabBtn .tab[data-tab="tab-post"]').addClass('on');
	        
	        // 모든 콘텐츠 영역에서 on 클래스 제거
	        $('.tabCnt .cntBx').removeClass('on');
	        
	        // 작성글 확인 콘텐츠 영역에 on 클래스 추가
	        $('.cntBx.tab-post').addClass('on');
	    </script>
	</c:if>
	
	<div class="dimmed" onclick="popAlertCheckHide()"></div>
	<div class="dimmed" onclick="popDeleteUserHide();"></div>
    <div class="dimmed" onclick="payhide();"></div>
    <div class="dimmed" onclick="popPostFanHide();"></div>
	

	<!-- [D] 팝업 중복확인 -->
	<div class="popup pop-alert-check"><%@ include file="../popup/pop-alert-check.jsp" %></div>
	<!-- [D] 팝업 회원 탈퇴 -->
	<div class="popup pop-delete-user"><%@ include file="../popup/pop-delete-user.jsp" %></div>
	<div class="popup pop-pay artist"><%@ include file="../popup/pop-pay-artist.jsp" %></div>
    <div class="popup pop-pay streaming"><%@ include file="../popup/pop-pay-streaming.jsp" %></div>
    <!-- [D] 팝업 팬 포스트 -->
	<div class="popup pop-post-fan"><%@ include file="../popup/pop-post-fan.jsp" %></div>
	
</body>
</html>