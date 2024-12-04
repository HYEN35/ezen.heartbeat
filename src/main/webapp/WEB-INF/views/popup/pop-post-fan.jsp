<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	function deletePost(post_id){
		// 사용자에게 삭제 확인 메세지 띄우기
		var ifconfrimed = confirm("게시물을 삭제하시겠습니까?");
		
		// 확인을 누르면 폼을 제출
		if (ifconfrimed	) {
			// AJAX 요청을 통해 댓글 삭제
			$.ajax({
				type : "POST",
				url : "/community/deletePost",
				data : {post_id : post_id},
				success : function(response) {
					window.location.reload();
				}
			});
		}
	}
	
	// 게시물 수정 버튼
	function popPostEditShow(button) {
	    var postDiv = button.closest('.postBx');
	    var fanPostDiv = postDiv.querySelector('.arti-cnt');
	    var fanButtonDiv = postDiv.querySelector('.arti-top');

	    var postText = fanPostDiv.querySelector('.txt');
	    var postInput = fanPostDiv.querySelector('.post-txtBx');
	    var postImgFileInput = fanPostDiv.querySelector('#postImgFile');
	    var postImage = fanPostDiv.querySelector("img.thumb");

	    postText.style.display = 'none'; // 기존 텍스트 숨김
	    postInput.style.display = 'block'; // 텍스트 박스 표시
	    postImgFileInput.style.display = 'block'; // 파일 입력 표시

	    postInput.value = postText.innerText.trim(); // 기존 텍스트를 입력 박스로 복사

	    // 파일 선택 시 미리보기 업데이트
	    postImgFileInput.addEventListener('change', function () {
	        if (postImgFileInput.files && postImgFileInput.files[0]) {
	            var reader = new FileReader();
	            reader.onload = function (e) {
	                postImage.src = e.target.result; // 새로운 이미지로 업데이트
	            };
	            reader.readAsDataURL(postImgFileInput.files[0]);
	        }
	    });

	    var editButton = fanButtonDiv.querySelector('.btn-i-edit');
	    var saveButton = fanButtonDiv.querySelector('.btn-i-save');
	    editButton.style.display = 'none';
	    saveButton.style.display = 'inline-block';
	}

	// 게시물 수정 저장 버튼
	function popPostSaveShow(button) {
	    var postDiv = button.closest('.postBx');
	    var fanPostDiv = postDiv.querySelector('.arti-cnt');
	    var fanButtonDiv = postDiv.querySelector('.arti-top');

	    var postText = fanPostDiv.querySelector('.txt');
	    var postInput = fanPostDiv.querySelector('.post-txtBx');
	    var postImgFileInput = fanPostDiv.querySelector('#postImgFile');

	    var newPostText = postInput.value.trim();
	    var post_id = postDiv.getAttribute('data-post-id');
	    var postImgFile = postImgFileInput.files[0];

	    var formData = new FormData();
	    formData.append('post_id', post_id);
	    formData.append('content', newPostText);
	    if (postImgFile) {
	        formData.append('post_img_name', postImgFile);
	    }

	    $.ajax({
	        type: "POST",
	        url: "/community/modifyPost",
	        data: formData,
	        processData: false,
	        contentType: false,
	        success: function (data) {
	            // 텍스트 업데이트
	            postText.innerText = newPostText;
	            postText.style.display = 'block';
	            postInput.style.display = 'none';

	            // 이미지 업데이트
	            if (data.post_img) {
	                var postImage = fanPostDiv.querySelector("img.thumb");
	                if (postImage) {
	                    postImage.src = "/upload/" + data.post_img;
	                }
	            }

	            // 파일 입력 숨기기
	            postImgFileInput.style.display = 'none';

	            // 버튼 상태 복구
	            var editButton = fanButtonDiv.querySelector('.btn-i-edit');
	            var saveButton = fanButtonDiv.querySelector('.btn-i-save');
	            editButton.style.display = 'inline-block';
	            saveButton.style.display = 'none';

	            postImgFileInput.value = '';
	        },
	        error: function () {
	            alert("게시물 수정 중 오류가 발생했습니다.");
	        }
	    });
	}


	
	//댓글 작성
	function submitComment() {
		var button = event.target; // 클릭한 버튼을 가져온다.
		var form = button.closest('.submitCommentTest');
	    console.log(form);  // 댓글 내용 확인 (디버깅용)
	    
	    var post_id = form.querySelector('input[name="post_id"]').value
	    var email = form.querySelector('input[name="email"]').value
	    var nickname = form.querySelector('input[name="nickname"]').value
	    var comment = form.querySelector('input[name="comment"]').value
	    console.log(post_id);  // 댓글 내용 확인 (디버깅용)
	    console.log(email);  // 댓글 내용 확인 (디버깅용)
	    console.log(nickname);  // 댓글 내용 확인 (디버깅용)
	    console.log(comment);  // 댓글 내용 확인 (디버깅용)
		
	    // 댓글이 비어있는 경우
	    if (!comment.trim()) {
	        alert("댓글 내용을 입력해주세요.");
	        return;
	    }

	    // AJAX 요청을 통해 댓글 작성
	    $.ajax({
	        url: "/community/commentWrite",  // 댓글 작성 처리 URL
	        type: "POST",
	        data: {
	            post_id: post_id,
	            email: email,
	            nickname: nickname,
	            comment: comment
	        },
	        dataType: 'json',
	        success: function(response) {
	    	    console.log("바보야");  // 댓글 내용 확인 (디버깅용)

	        	$.ajax({
	    			url: '/community/getUserPost',  // 서버 URL을 여기에 넣으세요
	    	      	method: 'POST',  // 서버에서 데이터를 가져오는 방식 (GET, POST 등)
	    	 		data : {post_id : post_id},
	    	      	success: function(response) {
	    	           // 서버에서 받은 응답으로 팝업 내용 업데이트
	    	           $('.pop-post-fan').html(response); // 응답 받은 내용을 팝업에 표시
	    	           $('.pop-post-fan').show(); // 팝업 다시 보이게 하기
	    	       	},
	    	       error: function(error) {
	    	           console.error('에러 발생:', error);
	    	       }
	    		});
	        }
	    });

	}
	
	// 댓글 수정 버튼 
	function popCommentEditShow(button) {
	    var commentDiv = button.closest('.postBx'); // 댓글을 포함한 가장 가까운 div인 .postBx를 찾기
	    var fanCommentDiv = commentDiv.querySelector('.fan-comment'); // .fan-comment를 찾기
	    var fanProfileDiv = commentDiv.querySelector('.fan-profile'); // .fan-profile를 찾기


	    if (!fanCommentDiv) {
	        console.error('팬 댓글 요소를 찾을 수 없습니다.');
	        return;
	    }

	    var commentText = fanCommentDiv.querySelector('.comment'); // 기존 댓글 텍스트
	    var commentInput = fanCommentDiv.querySelector('.txtBx'); // 수정용 텍스트박스
	    
	    // 댓글 텍스트와 입력란이 없으면 오류
	    if (!commentText || !commentInput) {
	        console.error('댓글 텍스트 또는 입력란을 찾을 수 없습니다.');
	        return;  // commentText나 commentInput이 없으면 실행 중단
	    }

	    // 댓글 텍스트 숨기고, 입력란 보이기
	    commentText.style.display = 'none';
	    commentInput.style.display = 'block';
	    
	    // 수정 버튼을 숨기고 저장 버튼을 보이도록 설정
	    var editButton = fanProfileDiv.querySelector('.btn-i-edit');
	    var saveButton = fanProfileDiv.querySelector('.btn-i-save');
	    

	    editButton.style.display = 'none';  
	    saveButton.style.display = 'inline-block';  // 저장 버튼 보이기


	    // 기존 댓글 내용을 입력란에 설정
	    commentInput.value = commentText.innerText.trim();
	}

	// 댓글 수정 저장
	function popCommentSaveShow(button) {
	    var commentDiv = button.closest('.postBx'); // 댓글을 포함한 .postBx를 찾기
	    var fanCommentDiv = commentDiv.querySelector('.fan-comment'); // .fan-comment를 찾기
	    var fanProfileDiv = commentDiv.querySelector('.fan-profile'); // .fan-profile를 찾기
	       

	    if (!fanCommentDiv) {
	        console.error('팬 댓글 요소를 찾을 수 없습니다.');
	        return;
	    }

	    var commentInput = fanCommentDiv.querySelector('.txtBx');  // 수정된 댓글 내용
	    var commentText = fanCommentDiv.querySelector('.comment'); // 기존 댓글 텍스트

	    // 수정된 댓글 내용 가져오기
	    var newCommentText = commentInput.value.trim();  // 수정된 댓글 텍스트

	    // 댓글 내용이 비어 있으면 수정하지 않음
	    if (newCommentText === "") {
	        alert("댓글 내용이 비어 있습니다.");
	        return;
	    }

	    // 댓글 ID 찾기
	    var comment_id = commentDiv.getAttribute('data-comment-id');  // data-comment-id 속성에서 댓글 ID 가져오기

	    // 수정된 댓글을 서버로 전송하는 AJAX 요청
	    $.ajax({
	        type: "POST",
	        url: "/community/modifyComment",  // 댓글 수정 처리 URL
	        data: {
	            comment_id: comment_id,
	            comment: newCommentText
	        },
	        success: function(data) {
	            console.log('서버 응답:', data);  // 서버 응답 로그 추가
	            if (data === "success") {
	                // 수정 후 댓글 내용을 업데이트하고, 버튼 전환
	                commentText.innerText = newCommentText;  // 댓글 텍스트 업데이트
	                commentInput.style.display = 'none';  // 수정 텍스트 박스 숨기기
	                commentText.style.display = 'block';  // 댓글 텍스트 보이게

	                // 수정 버튼 보이기, 저장 버튼 숨기기
	                var editButton = fanProfileDiv.querySelector('.btn-i-edit');
	                var saveButton = fanProfileDiv.querySelector('.btn-i-save');
	                
	                if (editButton) {
	                    editButton.style.display = 'inline-block';  // 수정 버튼 보이기
	                }
	                
	                if (saveButton) {
	                    saveButton.style.display = 'none';  // 저장 버튼 숨기기
	                }
	            } else {
	                alert('댓글 수정에 실패했습니다.');
	            }
	        },
	        error: function(xhr, status, error) {
	            console.error('AJAX 요청 실패:', error);
	            alert('댓글 수정 중 오류가 발생했습니다.');
	        }
	    });
	}

	function deleteComment(comment_id,totalComment,post_id) {
		// 사용자에게 삭제 확인 메세지 띄우기
		var isConfirmed = confirm("댓글을 삭제하시겠습니까?");
		
		// 확인을 누르면 폼을 제출
	    if (isConfirmed) {
	        // AJAX 요청을 통해 댓글 삭제
	        $.ajax({
	            type: "POST",
	            url: "/community/commentdelete",  // 댓글 삭제 처리 URL
	            data: { 
	            		comment_id: comment_id, 
	            		post_id: post_id
	            	},
	            success: function(response) {
	                if (response.status === "success") {
	                    // 댓글이 성공적으로 삭제되었으면, DOM에서 해당 댓글을 제거
	                    $("div[data-comment-id='" + comment_id + "']").remove();  // data-comment-id로 댓글 찾기
	                    
	                    // totalComment 값을 서버로 부터 전달 받음
	                    var newTotalComment = response.totalComment; // 서버에서 전달받은 댓글 개수
	                    // 댓글 수가 표시된 .comm 요소의 .num 부분을 업데이트
	                    $(".comm .num").text(newTotalComment); // .comm 안에 있는 .num 요소의 텍스트 업데이트
	                    
	                    alert("댓글이 삭제되었습니다.");
	                } else {
	                    alert("댓글 삭제에 실패했습니다.");
	                }
	            },
	            error: function() {
	                alert("서버 오류가 발생했습니다.");
	            }
	        });
	    }
	}
	
	//게시물 이미지 효과 추가 
	function showPopup(img) {
        let darkBackground = document.getElementById("dark-background");

        // 어두운 배경이 없다면 새로 생성
        if (!darkBackground) {
            darkBackground = document.createElement("div");
            darkBackground.id = "dark-background";
            darkBackground.className = "dark-background";
            darkBackground.style.position = "fixed";
            darkBackground.style.top = "0";
            darkBackground.style.left = "0";
            darkBackground.style.width = "100%";
            darkBackground.style.height = "100%";
            darkBackground.style.backgroundColor = "rgba(0, 0, 0, 0.7)";
            darkBackground.style.zIndex = "9999";
            darkBackground.style.display = "block";
            darkBackground.onclick = closePopup;
            document.body.appendChild(darkBackground);
        } else {
            darkBackground.style.display = "block";
        }

        // 이미지 팝업 생성
        let popupImg = document.getElementById("popup-img");
        if (!popupImg) {
            popupImg = document.createElement("img");
            popupImg.id = "popup-img";
            popupImg.src = img.src;
            popupImg.style.maxWidth = "100%";
            popupImg.style.maxHeight = "100%";
            popupImg.style.position = "fixed";
            popupImg.style.top = "50%";
            popupImg.style.left = "50%";
            popupImg.style.transform = "translate(-50%, -50%)";
            popupImg.style.zIndex = "10000";
            popupImg.style.display = "block";
            document.body.appendChild(popupImg);
        } else {
            popupImg.src = img.src;
            popupImg.style.display = "block";
        }
    }

	function closePopup() {
	    // 어두운 배경과 팝업 이미지를 숨깁니다.
	    const darkBackground = document.getElementById("dark-background");
	    if (darkBackground) {
	        darkBackground.style.display = "none";
	    }
	
	    const popupImg = document.getElementById("popup-img");
	    if (popupImg) {
	        popupImg.style.display = "none";
	    }
	}
	
	// 새로고침 버튼 
	function resetPopup(post_id) {
	    // AJAX 요청을 통해 서버에서 데이터를 불러온 후 팝업 업데이트
	    $.ajax({
	        url: '/community/getUserPost',  // 서버 URL을 여기에 넣으세요
	        method: 'POST',  // 서버에서 데이터를 가져오는 방식 (GET, POST 등)
	    	data : {post_id : post_id},
	        success: function(response) {
	            // 서버에서 받은 응답으로 팝업 내용 업데이트
	            $('.pop-post-fan').html(response); // 응답 받은 내용을 팝업에 표시
	            $('.pop-post-fan').show(); // 팝업 다시 보이게 하기
	        },
	        error: function(error) {
	            console.error('에러 발생:', error);
	        }
	    });
	}

	function popPostFanHide() {
        // 팝업 숨기기
        $('.pop-post-fan').hide().addClass('test');
        $('.dimmed').hide();

        // 페이지 새로 고침
        location.reload(); // 페이지 새로 고침
    }

</script>

<div class="wrap">
	<div class="topArea">
		<button type="button" class="btn-i-close" onclick="popPostFanHide();"></button>
	</div>
	<div class="cntArea">
		<div class="postBx" data-post-id="${PostVO.post_id}">
			<div class="arti-comment">
				<div class="arti-top">
					<div class="arti-profile"><img src="/upload/${PostVO.profileimg}" onerror=this.src="${pageContext.request.contextPath}/img/user.png" class="arti-thumb" alt="닉네임1"></div>
					<span class="arti-name"> ${PostVO.nickname }</span>
					<span class="arti-date"><fmt:formatDate value="${PostVO.post_date}" pattern="yy-MM-dd HH:mm"/></span>
					<c:if test="${PostVO.nickname eq UserVO.nickname}">
						<!-- 게시물 삭제 버튼 -->
						<button type="button" class="btn-i-trash" onclick="deletePost('${PostVO.post_id}')"></button>
						<!--  게시물 수정 버튼 -->
						<button type="button" class="btn-i-edit" onclick="popPostEditShow(this)"></button>
						<!--  게시물 저장 버튼 -->
						<button type="button" class="btn-i-save" onclick="popPostSaveShow(this)" style="display:none;"></button>
					</c:if>
				</div>
				<div class="arti-cnt">
					<div class="txt">${PostVO.content} </div>
					<div id="overlay" class="overlay" onclick="closeModal()"></div>
					 <form id="modifyPostForm" action="/community/modifyPost" method="POST" enctype="multipart/form-data">	
				    	<textarea class="post-txtBx" name="content" style="display:none;">${PostVO.content}</textarea>
				    	<input type="file" id="postImgFile" class="post-img-upload" style="display:none; padding:10px 0;" accept="image/*">
				    </form>
					<c:if test="${not empty PostVO.post_img}">
				    	<img id="fan-img" src="/upload/${PostVO.post_img}" alt="newjeans" class="thumb" onclick="showPopup(this)">
				    </c:if>
				</div>
			</div>
		</div>
		<div class="replyBx">
			<div class="top">
				<div class="count">
					<div class="comm" ><i class="num">${totalComment }</i>개의 댓글</div>
					<button type="button" class="btn-i-reset" onclick="resetPopup('${PostVO.post_id}')"><i class="fa-solid fa-rotate-right"></i></button>
				</div>
			</div>
			<div class="reply">
				<div class="list" >
				<c:forEach items="${commentList }" var="commentVO">
						<div class="postBx" data-comment-id="${commentVO.comment_id}">
							<div class="fan-profile">
								<img src="#none" onerror=this.src="${pageContext.request.contextPath}/img/user.png" class="fan-thumb" alt="닉네임1">
								<c:if test="${
									commentVO.nickname eq '로제' 
										or commentVO.nickname eq '리사'
										or commentVO.nickname eq '지수'
										or commentVO.nickname eq '제니'
										or commentVO.nickname eq '유나'
										or commentVO.nickname eq '예지'
										or commentVO.nickname eq '류진'
										or commentVO.nickname eq '리아'
										or commentVO.nickname eq '채령'
										or commentVO.nickname eq '혜인'
										or commentVO.nickname eq '하니'
										or commentVO.nickname eq '다니엘'
										or commentVO.nickname eq '해린'
										or commentVO.nickname eq '민지'
									}">
								    <span class="arti-mark"><span class="blind">artist</span></span>
								</c:if>	
								<span class="nickname">${commentVO.nickname }</span>
								<div class="date"><fmt:formatDate value="${commentVO.comment_date}" pattern="yy-MM-dd HH:mm"/></div>
								<c:if test="${commentVO.nickname eq UserVO.nickname}">
									<!-- 댓글 삭제 버튼 -->
									<button type="button" class="btn-i-trash" onclick="deleteComment('${commentVO.comment_id}','${totalComment }','${PostVO.post_id }')"></button>
									<!-- 댓글 수정 버튼 -->
				                    <button type="button" class="btn-i-edit" onclick="popCommentEditShow(this)"></button>
				                    <!-- 댓글 저장 버튼 -->
				                    <button type="button" class="btn-i-save" onclick="popCommentSaveShow(this)" style="display:none;"></button>

								</c:if>
							</div>
							<div class="fan-comment">
				                <div>
				                    <div class="comment">${commentVO.comment }</div>
				                    <input type="text" class="txtBx" name="comment" style="display:none;" value="${commentVO.comment }">
				                </div>
				            </div>
						</div>
				</c:forEach>	
				</div>			
			</div>			
			<div class="submitCommentTest">
			    <input type="hidden" name="post_id" value="${PostVO.post_id}" />
			    <input type="hidden" name="email" value="${UserVO.email}" />
			    <input type="hidden" name="nickname" value="${UserVO.nickname}" />
			    <div class="input">
			        <input type="text" class="txtBx" name="comment" placeholder="댓글을 입력하세요." autocomplete="off">
			        <button type="button" class="btn-i-send" onclick="submitComment()"><i class="fa-regular fa-paper-plane"></i></button>
			    </div>
			</div>
		</div>
	</div>
</div>