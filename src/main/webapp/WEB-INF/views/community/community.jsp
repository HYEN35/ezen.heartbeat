<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

<body>
	<script>
		$(function(){
			popAlertPurchaseShow();
		})
		
		//팝업 얼럿 멤버십구매
		function popAlertPurchaseShow(){
			if (${uvo.level} != 2) {
				$('.pop-alert-purchase').show();
				$('.dimmed').show();				
			}
		}
		function popAlertPurchaseHide(){
			$('.pop-alert-purchase').hide();
			$('.dimmed').hide();
		}

		//팝업 아티스트추가요청
		function popAddArtistShow(){
			$('.pop-add-artist').show();
			$('.dimmed').show();
		}
		function popAddArtistHide(){
			$('.pop-add-artist').hide();
			$('.dimmed').hide();
		}
	</script>
	
	<div class="inner service community" data-name="community">
		<%@ include file="../include/menu.jsp" %>
		
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="section-artist">
						<p class="tit">당신의 아티스트를 만나보세요!</p>
						<div class="artistList">
							<button type="button" class="btn-under-01" onclick="popAddArtistShow();">아티스트 추가 요청</button>
							<ul>
								<li class="item">
									<a href="${pageContext.request.contextPath}/community/artist/newjeans?email=${UserVO.email}&num=1">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="dimmed" onclick="popAlertPurchaseHide();popAddArtistHide();"></div>

	<!-- [D] 팝업 멤버십구매메세지 -->
	<div class="popup pop-alert-purchase"><%@ include file="../popup/pop-alert-purchase.jsp" %></div>
	<!-- [D] 팝업 아티스트추가요청 -->
	<div class="popup pop-add-artist"><%@ include file="../popup/pop-add-artist.jsp" %></div>
</body>
</html>