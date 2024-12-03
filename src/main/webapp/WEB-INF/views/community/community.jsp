<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

<body>
	<c:if test="${not empty artist_name}">
	    <script type="text/javascript">
	        alert("구독중인 아티스트는 ${artist_name}입니다. ${artist_name} 페이지를 이용해주세요");
	    </script>
	</c:if>

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
									<a href="${pageContext.request.contextPath}/community/artist/newjeans?num=1">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/newjeans.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/newjeans-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>뉴진스</i></div>
									</a>
								</li>
								<li class="item">
									<a href="${pageContext.request.contextPath}/community/artist/itzy?num=1">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/itzy.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/Itzy_logo.png" alt="newjeans"></div>
										<div class="artistName"><i>있지</i></div>
									<a href="${pageContext.request.contextPath}/community/artist/itzy?num=1">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/itzy.jpg" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/Itzy_logo.png" alt="newjeans"></div>
										<div class="artistName"><i>있지</i></div>
									</a>
								</li>
								<li class="item">
									<a href="${pageContext.request.contextPath}/community/artist/blackpink?num=1">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/blackpink.png" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/blackpink-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>블랙핑크</i></div>
									<a href="${pageContext.request.contextPath}/community/artist/blackpink?num=1">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/blackpink.png" alt="newjeans"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/blackpink-logo.png" alt="newjeans"></div>
										<div class="artistName"><i>블랙핑크</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/aespa.jpg" alt="aespa"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/aespa-logo.jpg" alt="aespa"></div>
										<div class="artistName"><i>에스파</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/ive.jpg" alt="ive"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/ive-logo.jpg" alt="ive"></div>
										<div class="artistName"><i>아이브</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/bts.jpg" alt="bts"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/bts-logo.jpg" alt="bts"></div>
										<div class="artistName"><i>BTS</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/nct127.jpg" alt="nct127"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/nct127-logo.jpg" alt="nct127"></div>
										<div class="artistName"><i>NCT127</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/nctdream.jpg" alt="nctdream"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/nctderam-logo.jpg" alt="nctdream"></div>
										<div class="artistName"><i>NCT-Dream</i></div>
									</a>
								</li>
								<li class="item">
									<a href="#none">
										<div class="artist"><img src="${pageContext.request.contextPath}/img/artist/seventeen.jpg" alt="seventeen"></div>
										<div class="artistLogo"><img src="${pageContext.request.contextPath}/img/artist/seventeen-logo.jpg" alt="seventeen"></div>
										<div class="artistName"><i>세븐틴</i></div>
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