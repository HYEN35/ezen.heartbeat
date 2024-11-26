<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp"%>

<script>
	// 게시물 삭제 확인 함수
	function confirmDelete() {
	    // 확인 대화 상자 띄우기
	    return confirm("정말로 이 게시물을 삭제하시겠습니까?");
	}
	// 댓글 작성시 내용이 비어있나 검증
    function validateCommentForm() {
        var comment = document.getElementById("comment").value.trim();  // 댓글 입력값 가져오기

        if (comment === "") {  // 댓글 내용이 비어있으면
            alert("댓글 내용을 입력해주세요!");  // 경고창 띄우기
            return false;  // 폼 제출을 막기
        }
        
        return true;  // 댓글 내용이 있으면 폼 제출을 허용
    }


	// 댓글 수정 버튼
	function commentEditShow(button) {
		// 클릭된 버튼의 부모 요소에서 댓글과 관련된 요소를 찾음
	    var parentDiv = button.closest('div');
	    
	    // 수정할 부분과 저장 버튼을 찾음
	    var comment = parentDiv.querySelector('.comment');
	    var newcomment = parentDiv.querySelector('.newComment');
	    var saveButton = parentDiv.querySelector('.saveButton');
	    var editButton = parentDiv.querySelector('.editButton');

	    // 수정 버튼을 클릭하면 기존 댓글 숨기고, 수정 입력창 보이기
	    comment.style.display = 'none';
	    newcomment.style.display = 'block';
	    
	    // 수정 버튼은 숨기고 저장 버튼을 보이게 함
	    saveButton.style.display = 'block';
	    editButton.style.display = 'none';
		
	}
	// 댓글 수정
	function commentsaveShow(notice_comment_id) {
	    // 수정된 댓글 값을 가져옵니다.
	    var parentDiv = event.target.closest('div');  // 버튼을 클릭한 부모 div를 찾음
	    var newcomment = parentDiv.querySelector('.newComment');  // 해당 댓글의 input 요소
	    var newCommentValue = newcomment.value;  // 수정된 댓글 내용
	 	// 댓글 내용이 없으면 경고창을 띄우고 다시 돌아가도록 처리
	    if (newCommentValue.trim() === "") {  // 내용이 비어있으면
	        alert("댓글 내용을 입력해주세요.");  // 경고창 띄우기
	        return;  // 함수 종료, 댓글 수정 작업 진행 안함
	    }
	    console.log(newCommentValue);

	    // AJAX 요청을 보내기
	    $.ajax({
	        url: '/notice/commentUpdate',  // 서버에서 처리할 URL
	        type: 'POST',  // HTTP 요청 방식 (POST)
	        data: {
	            notice_comment_id: notice_comment_id,  // 댓글 ID
	            comment: newCommentValue              // 수정된 댓글 내용
	        },
	        success: function(response) {
	            // 서버 응답이 성공적이면 처리
	            console.log('댓글 수정 성공:', response);

	            // 수정 후 화면 업데이트 (예: 댓글 내용 갱신)
	            var commentText = parentDiv.querySelector('.comment'); // 댓글 내용
	            commentText.textContent = newCommentValue; // 댓글 내용 갱신

	            // 수정 후 수정/저장 버튼 상태 변경
	            newcomment.style.display = 'none'; // 입력창 숨기기
	            commentText.style.display = 'block'; // 댓글 내용 보이기
	            parentDiv.querySelector('.saveButton').style.display = 'none'; // 저장 버튼 숨기기
	            parentDiv.querySelector('.editButton').style.display = 'block'; // 수정 버튼 보이기
	        },
	        
	        
	    });
	    
	}
	
	// 댓글 삭제
	function commentDelete(notice_comment_id) {
		var isConfirm = confirm("댓글을 삭제하시겠습니까?");
		
		if (isConfirm) {
			$.ajax({
				url : '/notice/commentDelete',
				type : 'post',
				data : {notice_comment_id : notice_comment_id},
				success:function(response){
					alert('댓글이 삭제되었습니다');
					location.reload();
				}
			})
		}
	}

</script>

<body>
	<div class="inner service mypage" data-name="mypage">
		<%@ include file="../include/menu.jsp"%>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle%></h2>
				<div class="cntArea"></div>
				<form action="/notice/noticeModifyShow" method="post">
					<input type="hidden" name="notice_id" value="${noticeVO.notice_id }">
					<input type="hidden" name="num" value="${num }">
					<div>작성자 : ${noticeVO.nickname }</div>
					<div>
						<span>제목 : ${noticeVO.title }</span>
					</div>
					<div>
						<span>내용 : ${noticeVO.content } </span>
						
					</div>
					<div>작성일 : <fmt:formatDate value="${noticeVO.post_date}" pattern="yyyy-MM-dd HH:mm"/></div>
					<c:if test="${UserVO.email eq noticeVO.email }">
					<button type="submit">수정하기</button>
					<a href="/notice/noticeDelete?notice_id=${noticeVO.notice_id }" onclick="return confirmDelete()">삭제</a>
					</c:if>
					<a href="/notice/notice?num=${num }">목록</a>
				</form>
				
				<div>댓글 목록</div>
				<c:forEach items="${commentVO}" var="cvo">
				<div>
					<div>${cvo.nickname }</div>
					<div>
						<div class="comment">${cvo.comment }</div>
						<input type="text" class="newComment" name="comment" style="display:none;" value="${cvo.comment }">
					</div>
					<div><fmt:formatDate value="${cvo.comment_date}" pattern="yy-MM-dd HH:mm"/></div>
					<c:if test="${UserVO.email eq cvo.email }">
					<button type="button" class="editButton" onclick="commentEditShow(this)">수정</button>
					<button type="button" class="saveButton" onclick="commentsaveShow(${cvo.notice_comment_id})" style="display:none;">저장</button>
					<button type="button" onclick="commentDelete(${cvo.notice_comment_id})">삭제</button>
					</c:if>
				</div>
				</c:forEach>
				<form action="/notice/commentWrite" method="post" onsubmit="return validateCommentForm()">
					<input type="hidden" name="notice_id" value="${noticeVO.notice_id }">
					<input type="hidden" name="email" value="${UserVO.email }">
					<input type="hidden" name="nickname" value="${UserVO.nickname }">
					<input type="hidden" name="num" value="${num }">
					<input type="text" name="comment" id="comment" placeholder="댓글 내용을 입력하세요">
					<button type="submit">댓글 작성</button>
				</form>
			</div>
		</div>
	</div>






</body>
</html>