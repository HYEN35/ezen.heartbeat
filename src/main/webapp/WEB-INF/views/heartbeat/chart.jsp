<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

<%@ page import="kr.heartbeat.music.config.SpotifyAPI" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="javax.servlet.ServletContext" %>

<%
    // Spring 컨텍스트에서 SpotifyAPI Bean 가져오기
    ServletContext servletContext = application;
    SpotifyAPI spotifyAPI = (SpotifyAPI) WebApplicationContextUtils
                                .getWebApplicationContext(servletContext)
                                .getBean(SpotifyAPI.class);

    // SpotifyAPI를 사용하여 Access Token 가져오기
    String accessToken = spotifyAPI.getAccessToken();
    //Access Token 출력
   // System.out.println ("accessToken: "+accessToken );
%>

<script>
    let isPlaying = false; // 재생 상태 관리 변수
    let currentYouTubeUrl = "";

    async function playTrack(title, artist, e) {

        try {


            if (!isPlaying) {
                const response = await fetch(`/playTrack?trackTitle=` + encodeURIComponent(title) + `&artist=` + encodeURIComponent(artist));
                const youTubeUrl = await response.text();

                if (youTubeUrl === "No URL found") {
                    alert("Unable to find a YouTube link for this track.");
                } else if (youTubeUrl === "Error occurred") {
                    alert("An error occurred while fetching the track.");
                } else {
                    // YouTube URL + 재생 옵션
                    const params = "?autoplay=1&mute=0&controls=1&rel=0";
                    currentYouTubeUrl = youTubeUrl + params;

                    // iframe에 URL 설정
                    $("iframe.playerTest").attr("src", currentYouTubeUrl);
                    //$("iframe.playerTest").css("display", "block");
                    $(".urlTest").text(currentYouTubeUrl);

                    isPlaying = true; // 재생 상태로 전환
                }
            } else {
                // **일시정지: autoplay=0으로 변경하여 동영상 중지**
                const pausedUrl = currentYouTubeUrl.replace("autoplay=1", "autoplay=0");
                $("iframe.playerTest").attr("src", pausedUrl);
                $(".urlTest").text(pausedUrl);

                isPlaying = false; // 정지 상태로 전환
            }
        } catch (error) {
            console.error("Error playing track:", error);
        }
    }
</script>




<style>
	.album-image {
		    width: 50px;  /* 이미지 너비를 50px로 설정 */
		    height: 50px; /* 이미지 높이를 50px로 설정 */
		    object-fit: cover; /* 이미지를 영역에 맞게 자르기 */
		}
</style>

<%--

<body>
    <h1>YouTube 소리만 재생</h1>
    <iframe width="560" height="315" 
        src="https://www.youtube.com/embed/{YOUR_VIDEO_ID}?autoplay=1&mute=0&controls=0&showinfo=0&rel=0" 
        frameborder="0" 
        style="display: none;"
        allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" 
        allowfullscreen>
    </iframe>
</body>



 --%>

<body>
	<div class="inner service chart" data-name="chart">
	<%@ include file="../include/menu.jsp" %>
		<div id="playBar" class="playBar">플레이바</div>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title">차트</h2>
					
				
			    
				<iframe  class="player" 
			    	width="560" height="315" src="" 
			    	frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen  style="display: none;">
			   	</iframe>	
				
				 <iframe  class=" playerTest" width="560" height="315" 
			        src="" 
			        frameborder="0" 
			        style="display: none;"
			        allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" 
			        allowfullscreen>
			    </iframe>
					
					
				<p class="urlTest"></p>
				
				<!-- 트랙 이미지, 노래, 가수 출력 -->						
				<c:forEach var="trackInfo" items="${trackInfoList}" varStatus="status">
				    <c:if test="${status.index < 10}">
						<li class="item">
				            <img src="${trackInfo.albumImageUrl}" alt="Album Image" class="album-image"/>
				            <i class="tit">${trackInfo.title}</i>
				            <i class="name">${trackInfo.artist}</i>
				          	<button class="play-button"   onclick="playTrack('${trackInfo.title}', '${trackInfo.artist}', this)">Play</button>
				          	
			       		</li>
				    </c:if>
				</c:forEach>

				
				<div class="cntArea">
					<div class="section-chart">
						<div class="listBx daily">
							<div class="titWrap">
								<p class="subTit">오늘 Top 10</p>
								<button type="button" class="btn-under">전체듣기</button>
							</div>
							<ul class="itemWrap">
<<<<<<< HEAD
								<li class="item">
									<div class="num"><i>1</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>2</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>3</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>4</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>5</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>6</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>7</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>8</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>9</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>10</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
=======
								<c:forEach var="trackInfo" items="${trackInfoList}" varStatus="status">
								    <c:if test="${status.index < 10}">
										<li class="item">
											<div class="num">${status.index + 1}</div>
								            <div class="album"><img src="${trackInfo.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
								            <div class="arti">
									            <i class="tit">${trackInfo.title}</i>
									            <i class="name">${trackInfo.artist}</i>
								            </div>
								          	<button class="playBtn" onclick="playTrack('${trackInfo.title}', '${trackInfo.artist}', this);"></button>
							       		</li>
								    </c:if>
								</c:forEach>
>>>>>>> e026f34 (1. 블랙핑크, 있지 헤더 이미지 추가)
							</ul>
						</div>
						<div class="listBx weekly">
							<div class="titWrap">
								<p class="subTit">이번주 Top 10</p>
								<button type="button" class="btn-under">전체듣기</button>
							</div>
							<ul class="itemWrap">
<<<<<<< HEAD
								<li class="item">
									<div class="num"><i>1</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>2</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>3</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>4</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>5</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>6</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>7</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>8</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>9</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>10</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
=======
								<c:forEach var="trackInfoListWeek" items="${trackInfoListWeek}" varStatus="statusWeek">
								    <c:if test="${statusWeek.index < 10}">
										<li class="item">
											<div class="num">${statusWeek.index + 1}</div>
								            <div class="album"><img src="${trackInfoListWeek.albumImageUrl}" alt="Album Image" class="album-image"/></div>
							               <div class="arti">
									            <i class="tit">${trackInfoListWeek.title}</i>
									            <i class="name">${trackInfoListWeek.artist}</i>
								            </div>
								          	<button class="playBtn" onclick="playTrack('${trackInfoListWeek.title}', '${trackInfoListWeek.artist}', this)"></button>
							       		</li>
								    </c:if>
								</c:forEach>
>>>>>>> e026f34 (1. 블랙핑크, 있지 헤더 이미지 추가)
							</ul>
						</div>
						<div class="listBx monthly">
							<div class="titWrap">
								<p class="subTit">이번달 Top 10</p>
								<button type="button" class="btn-under">전체듣기</button>
							</div>
							<ul class="itemWrap">
<<<<<<< HEAD
								<li class="item">
									<div class="num"><i>1</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>2</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>3</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>4</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>5</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>6</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>7</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>8</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>9</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
								<li class="item">
									<div class="num"><i>10</i></div>
									<div class="album"><i></i></div>
									<div class="arti">
										<i class="tit">노래제목</i>
										<i class="name">가수</i>
									</div>
								</li>
=======
								<c:forEach var="trackInfoListViral" items="${trackInfoListViral}" varStatus="statusViral">
								    <c:if test="${statusViral.index < 10}">
										<li class="item">
											<div class="num">${statusViral.index + 1}</div>
								            <div class="album"><img src="${trackInfoListViral.albumImageUrl}" alt="Album Image" class="album-image"/></div>
								            <div class="arti">
								            	<i class="tit">${trackInfoListViral.title}</i>
								            	<i class="name">${trackInfoListViral.artist}</i>
								            </div>
								          	<button class="playBtn" onclick="playTrack('${trackInfoListViral.title}', '${trackInfoListViral.artist}', this)"></button>
							       		</li>
								    </c:if>
								</c:forEach>
>>>>>>> e026f34 (1. 블랙핑크, 있지 헤더 이미지 추가)
							</ul>
						</div>
					</div>
					<div class="section-station">
						<p>스테이션</p>
						<div class="stationBx">
							<ul>
								<li class="item" onclick="popStationListShow(this);">
									<p class="tit">드라이브 하면서 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this);">
									<p class="tit">운동하면서 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this);">
									<p class="tit">출퇴근 할 때 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this);">
									<p class="tit">공부 할 때 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this);">
									<p class="tit">노동요</p>
								</li>
								<li class="item" onclick="popStationListShow(this);">
									<p class="tit">잘 준비 할 때 들으면 좋은 노래</p>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed" onclick="popStationListHide();"></div>
	
	<!-- [D] 팝업 로그아웃-->
	<div class="popup pop-alert-logout" data-html="../popup/pop-alert-logout.html"></div>
	<!-- [D] 팝업 스테이션 추천 리스트 -->
	<div class="popup pop-station-list" data-html="../popup/pop-station-list.html"></div>

	<script>
		$(function(){
			colorRandom();
		});

		//팝업 스테이션
		function popStationListShow(e){
			$('.pop-station-list').show();
			$('.dimmed').show();

			var stationTitle = $(e).children('.tit').text();
			$('#stationTitle').html(stationTitle);
		}
		function popStationListHide(){
			$('.pop-station-list').hide();
			$('.dimmed').hide();
		}
		
		//스테이션 배경컬러 랜덤
		function colorRandom(){
			function getRandomColor() {
				const letters = '0123456789ABCDEF';
				let color = '#';
				for (let i = 0; i < 6; i++) {
					color += letters[Math.floor(Math.random() * 16)];
				}
				return color;
			}

			function getLuminance(hex) {
				const r = parseInt(hex.slice(1, 3), 16) / 255;
				const g = parseInt(hex.slice(3, 5), 16) / 255;
				const b = parseInt(hex.slice(5, 7), 16) / 255;

				const a = [r, g, b].map(function (v) {
					return v <= 0.03928 ? v / 12.92 : Math.pow((v + 0.055) / 1.055, 2.4);
				});

				const luminance = a[0] * 0.2126 + a[1] * 0.7152 + a[2] * 0.0722;

				return luminance;
			}

			function setRandomColors() {
				$('.stationBx .item').each(function() {
					const color = getRandomColor();
					$(this).css('background-color', color);

					const luminance = getLuminance(color);
					const textColor = luminance > 0.5 ? '#000000' : '#FFFFFF';
					$(this).css('color', textColor);
				});
			}

			setRandomColors();
		}
	</script>
</body>
</html>