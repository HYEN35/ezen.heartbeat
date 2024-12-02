<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/layout.jsp" %>
<% 
	request.setAttribute("artistPage", "artist");
%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/vendor/slick.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/vendor/slick-theme.css">
<script src="${pageContext.request.contextPath}/js/vendor/slick.min.js"></script>

<body>	
	<script>
		$(function(){
			slick();
		});
		function slick(){
			const $slider = $('.slideBx');
			
			$slider.on('init', function (event, slick) {
				adjustSlideWidth(slick);
			});

			$slider.slick({
				slidesToShow: 3, // 기본 슬라이드 표시 개수
				slidesToScroll: 1,
				infinite: true,
				swipe: false,
				arrows: true,
				dots: false,
				draggable: true,
				variableWidth: false,
				adaptiveHeight: true
			});

			function adjustSlideWidth(slick) {
				const totalSlides = slick.slideCount; // 총 슬라이드 개수

				if (totalSlides <= 3) {
					$('.slick-track').css('width', 'auto'); // 너비 자동
				}
			}

			// 슬라이더 업데이트 시 재적용
			$slider.on('setPosition', function (event, slick) {
				adjustSlideWidth(slick);
			});
		};

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
		function popPostArtistHide(){
			$('.pop-post-artist').hide();
			$('.dimmed').hide();
		}

		//팝업 팬포스트작성
		function popPostShow(){
			$('.pop-post').show();
			$('.dimmed').show();
			//uploadFileName();
			multipleUploadFile();
		}
		function popPostHide(){
			$('.pop-post').hide();
			$('.dimmed').hide();
		}

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
	
	<div class="inner service artist-newjeans" data-name="community">
		<%@ include file="../../include/menu.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="section-banner">
						<img src="${pageContext.request.contextPath}/img/artist/blackpink-header.jpg" alt="newjeans" class="thumb">
					</div>
					<div class="section-artist-post">
						<div class="artistWrap">
							<div class="slideBx">
								<c:forEach items="${newjinsPosts}" var="newjinsVO">
									<div class="postBx">
										<form id="postFrm_${PostVO.post_id}" action="/community/getPost" method="post">
							                <input type="hidden" name="post_id" value="${newjinsVO.post_id}"/>
											<a href="javascript:void(0);" onclick="popPostArtistShow('${newjinsVO.post_id}','${UserVO.email }');">
												<div>
													<div class="arti-profile"><img src="${pageContext.request.contextPath}/img/artist/nj_mj.jpeg" onerror=this.src="${pageContext.request.contextPath}/img/user.png" class="arti-thumb" alt="민지"></div>
													<div class="arti-comment">
														<div class="arti-top">
															<span class="arti-mark"><span class="blind">artist</span></span>
															<span class="arti-name">${newjinsVO.nickname }</span>
														</div>
														<div class="arti-cnt">
															<div class="txt">${newjinsVO.content }</div>
														</div>
													</div>
												</div>
											</a>
										</form>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="section-fan-post">
						<div class="fanWrap">
							<div class="posting" onclick="popPostShow(${num});">
								<p>당신의 아티스트에게 포스트를 남겨보세요.</p>
								<i class="i-img"><i class="fa-regular fa-image"></i></i>
							</div>
							<div class="postWrap">
								<c:forEach items="${newjinsfanPosts}" var="PostVO">
									<div class="postBx">
										<form id="postFrm_${PostVO.post_id}" action="/community/getPost" method="post">
							                <input type="hidden" name="post_id" value="${PostVO.post_id}"/>
											<a href="javascript:void(0);" onclick="popPostFanShow(${PostVO.post_id})" >
												<div>
													<div class="fan-profile">
														<img src="#none" onerror=this.src="${pageContext.request.contextPath}/img/user.png" class="fan-thumb" alt="닉네임1">
														<span class="nickname">${PostVO.nickname }</span>
														<div class="date"><fmt:formatDate value="${PostVO.post_date}" pattern="yy-MM-dd HH:mm"/></div>
													</div>
													<div class="fan-comment">
														<div class="fan-cnt">
															<img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"><br><br>
															<div class="txt">${PostVO.content }</div>
														</div>
													</div>
												</div>
											</a>				
										</form>
									</div>
								</c:forEach>
							</div>
						</div>
						<div class="pagination">
							<c:if test="${page.prev }">
							<a href="/community/artist/newjeans?email=${UserVO.email }&num=${page.startPageNum-1 }" class="btn-i-prev"></a>
							</c:if>
							<div class="page">
								<c:forEach begin="${page.startPageNum }" end="${page.endPageNum }" var="num">
									<c:if test="${select != num}">
									<a href="/community/artist/newjeans?email=${UserVO.email }&num=${num }" class="num">${num }</a>
									</c:if>
									<c:if test="${select == num}">
									<a href="/community/artist/newjeans?email=${UserVO.email }&num=${num }"class="num on">${num }</a>
									</c:if>
								</c:forEach>							
							</div>
							<c:if test="${page.next }">
							<a href="/community/artist/newjeans?email=${UserVO.email }&num=${page.endPageNum+1 }" class="btn-i-next"></a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="dimmed" onclick="popPostArtistHide();popPostHide();popPostFanHide();"></div>

	<!-- [D] 팝업 아티스트 포스트 -->
	<div class="popup pop-post-artist"><%@ include file="../../popup/pop-post-artist.jsp" %></div>
	<!-- [D] 팝업 팬 포스트 -->
	<div class="popup pop-post-fan"><%@ include file="../../popup/pop-post-fan.jsp" %></div>
	<!-- [D] 팝업 포스트작성 -->
	<div class="popup pop-post"><%@ include file="../../popup/pop-post.jsp" %></div>
</body>
</html>