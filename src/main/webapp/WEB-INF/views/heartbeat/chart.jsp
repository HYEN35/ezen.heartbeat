<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

<%@ page import="kr.heartbeat.music.config.SpotifyAPI" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="javax.servlet.ServletContext" %>

<body>
	<div class="inner service chart" data-name="chart">
	<%@ include file="../include/menu.jsp" %>
		<div id="playBar" class="playBar">
			<div id="player" style="display:none"></div>
		    <div class="stateCnt">
				<span class="state ready"><i class="fas fa-music"></i> Ready to play a Music</span>
				<span class="state now" style="display:none"><i class="fa-solid fa-music"></i> Now playing Music</span>
				<div class="playInfo" style="display:none">
					<button id="play" class="playBtn play" onclick="playOn();"><i class="fa-solid fa-circle-play"></i></button>
					<button	ton id="pause" class="playBtn pause" onclick="playPause();"><i class="fa-solid fa-circle-pause"></i></button>
					<div class="nowPlayInfo"><i id="nowPlayArtist"></i> - <i id="nowPlayMusic"></i></div>
				</div>
		    </div>
		</div>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title">차트</h2>
				<div class="cntArea">
					<div class="section-chart">
						<div class="listBx daily">
							<div class="titWrap">
								<p class="subTit">오늘 Top 10</p>
							</div>
							<ul class="itemWrap">
								<c:forEach var="trackInfo" items="${trackInfoList}" varStatus="status">
								    <c:if test="${status.index < 10}">
										<li class="item">
											<div class="num">${status.index + 1}</div>
								          	<button class="playBtn" onclick="playTrack('${trackInfo.title}', '${trackInfo.artist}', this);"></button>
								            <div class="album"><img src="${trackInfo.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
								            <div class="arti">
									            <i class="tit">${trackInfo.title}</i>
									            <i class="name">${trackInfo.artist}</i>
								            </div>
							       		</li>
								    </c:if>
								</c:forEach>
							</ul>
						</div>
						<div class="listBx weekly">
							<div class="titWrap">
								<p class="subTit">이번주 Top 10</p>
							</div>
							<ul class="itemWrap">
								<c:forEach var="trackInfoListWeek" items="${trackInfoListWeek}" varStatus="statusWeek">
								    <c:if test="${statusWeek.index < 10}">
										<li class="item">
											<div class="num">${statusWeek.index + 1}</div>
								          	<button class="playBtn" onclick="playTrack('${trackInfoListWeek.title}', '${trackInfoListWeek.artist}', this)"></button>
								            <div class="album"><img src="${trackInfoListWeek.albumImageUrl}" alt="Album Image" class="album-image"/></div>
							               <div class="arti">
									            <i class="tit">${trackInfoListWeek.title}</i>
									            <i class="name">${trackInfoListWeek.artist}</i>
								            </div>
							       		</li>
								    </c:if>
								</c:forEach>
							</ul>
						</div>
						<div class="listBx monthly">
							<div class="titWrap">
								<p class="subTit">Popular Top 10</p>
							</div>
							<ul class="itemWrap">
								<c:forEach var="trackInfoListViral" items="${trackInfoListViral}" varStatus="statusViral">
								    <c:if test="${statusViral.index < 10}">
										<li class="item">
											<div class="num">${statusViral.index + 1}</div>
								          	<button class="playBtn" onclick="playTrack('${trackInfoListViral.title}', '${trackInfoListViral.artist}', this)"></button>
								            <div class="album"><img src="${trackInfoListViral.albumImageUrl}" alt="Album Image" class="album-image"/></div>
								            <div class="arti">
								            	<i class="tit">${trackInfoListViral.title}</i>
								            	<i class="name">${trackInfoListViral.artist}</i>
								            </div>
							       		</li>
								    </c:if>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="section-station">
						<p>스테이션</p>
						<div class="stationBx">
							<ul>
								<li class="item" onclick="popStationListShow(this, 'drive');">
									<p class="tit">드라이브 하면서 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this, 'health');">
									<p class="tit">운동하면서 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this, 'goto');">
									<p class="tit">출퇴근 할 때 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this, 'study');">
									<p class="tit">공부 할 때 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this, 'work');">
									<p class="tit">일 할 때 들으면 좋은 노래</p>
								</li>
								<li class="item" onclick="popStationListShow(this, 'sleep');">
									<p class="tit">자기 전 들으면 좋은 노래</p>
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
	<div class="popup pop-alert-logout" data-html="../popup/pop-alert-logout.jsp"></div>
	<!-- [D] 팝업 스테이션 추천 리스트 -->
	<div class="popup pop-station-list"><%@ include file="../popup/pop-station-list.jsp" %></div>

	<script src="https://www.youtube.com/iframe_api"></script>
	<script>	
		let isPlaying = false; // 재생 상태 관리 변수
		let youtubeVideoId = "";

		async function playTrack(title, artist, e) {

			try {
				const response = await fetch(`/playTrack?trackTitle=` + encodeURIComponent(title) + `&artist=` + encodeURIComponent(artist));
				const youTubeUrl = await response.text();

				if (youTubeUrl === "No URL found") {
					alert("Unable to find a YouTube link for this track.");
				} else if (youTubeUrl === "Error occurred") {
					alert("An error occurred while fetching the track.");
				} else {
					youtubeVideoId = youTubeUrl;
					console.log(youtubeVideoId);
					onYouTubeIframeAPIReady(youtubeVideoId);
					
					$(".nowPlayInfo").show();
					$(".playBtn").removeClass('playing');
					$(".playBtn").removeClass('pause');
					$(".state.now").show().siblings(".state.ready").hide();
					$(e).addClass('playing');				
					$(".playBar .playInfo").show();				

					$("#nowPlayArtist").html(artist);
					$("#nowPlayMusic").html(title);
				}
			} catch (error) {
				console.error("Error playing track:", error);
			}
		}

		//음악 재생, 일시정지, 멈춤
		let player;

		// IFrame Player
		function onYouTubeIframeAPIReady(youTubeUrl) {
			const videoId = youTubeUrl;
			//기존 플레이어가 있으면 제거
			if (player) {
		        player.destroy();
		    }

		    player = new YT.Player('player', {
		        width: '0',
		        height: '0',
		        videoId: videoId, // YouTube 영상 ID
		        playerVars: {
		            autoplay: 1,   // 자동 재생
		            mute: 0,       // 음소거 해제
		            controls: 1,   // 컨트롤 표시
		            rel: 0         // 관련 동영상 비활성화
		        },
		        events: {
		            onReady: onPlayerReady,
		            onStateChange: onPlayerStateChange
		        }
		    });
		}
		
		function onPlayerReady(event) {
		    console.log("플레이어 준비 완료");
		}
		
		function onPlayerStateChange(event) {
		    console.log("플레이어 상태 변경: ", event.data);
		}
		
		// 버튼 클릭 이벤트

		function playOn(){
			player.playVideo();
			$(".playing").removeClass("pause");
			$(".state.now").show().siblings(".state.ready").hide();
		}

		function playPause(){
			player.pauseVideo();
			$(".playing").addClass("pause");
			$(".state.now").hide().siblings(".state.ready").show();
		}
	</script>

	<script>
		$(function(){
			colorRandom();
		});

		//팝업 스테이션			
		function popStationListShow(e, type) {
			$('.pop-station-list').show();
			$('.dimmed').show();
			
	        // 스테이션 목록을 숨깁니다.
	        $('#driveList').hide();
	        $('#healthList').hide();
	        $('#gotoList').hide();
	        $('#studyList').hide();
	        $('#workList').hide();
	        $('#sleepList').hide();

	        // 선택된 항목의 타이틀을 출력
	        var stationTitle = $(e).children('.tit').text();
	        $('#stationTitle').html(stationTitle);

	        // 선택된 타입에 따라 해당 리스트만 보여줍니다.
	        if (type === 'drive') {
	            $('#driveList').show();
	        } else if (type === 'health') {
	            $('#healthList').show();
	        } else if (type === 'goto') {
	        	$('#gotoList').show();
	        } else if (type==='study') {
	        	$('#studyList').show();
	        } else if (type === 'work') {
	        	 $('#workList').show();
	        } else if (type ==='sleep') {
	        	$('#sleepList').show();
	        }
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