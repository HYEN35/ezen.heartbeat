<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>
<%@ page import="kr.heartbeat.music.config.SpotifyAPI" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="javax.servlet.ServletContext" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<script>
		$(function(){
			popAlertPurchaseShow();
		})
		
		//팝업 얼럿 멤버십구매
		function popAlertPurchaseShow(){
			if (${uvo.level}  == 0) {
				$('.pop-alert-purchase').show();
				$('.dimmed').show();				
			}
		}
		function popAlertPurchaseHide(){
			$('.pop-alert-purchase').hide();
			$('.dimmed').hide();
		}
	</script>

	<div class="inner service playlist" data-name="playlist">
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
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<div class="section-tag">
                        <p class="noti">해시태그를 3개 선택해주세요! <br>해시태그를 선택하면 당신에게 맞는 노래를 선곡해드립니다♬</p>
                        <ul class="tagList">
                            <li class="tag" data-id="100">#신나는</li>
                            <li class="tag" data-id="101">#비오는날</li>
                            <li class="tag" data-id="102">#차분한</li>
                            <li class="tag" data-id="103">#조용한</li>
                            <li class="tag" data-id="104">#파티</li>
                            <li class="tag" data-id="105">#운동</li>
                            <li class="tag" data-id="106">#명상</li>
                            <li class="tag" data-id="107">#휴식</li>
                            <li class="tag" data-id="108">#졸릴때</li>
                            <li class="tag" data-id="109">#노동요</li>
                        </ul>
                        <div class="btnCnt">
                            <button type="submit" class="btn-full" onclick="playListShow();">뮤직리스트 보기</button>
                            <button type="submit" class="btn-border" onclick="playListReset();">RESET</button>
                        </div>
					</div>
					<div class="section-list" style="display:none">
						<div>
							<div id="listTitle" class="listName"></div>
							<div class="listBx">
								<ul class="itemWrap" id="musicList"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed" onclick="popAlertPurchaseHide();"></div>

	<!-- [D] 팝업 멤버십구매메세지 -->
	<div class="popup pop-alert-purchase"><%@ include file="../popup/pop-alert-purchase.jsp" %></div>
	
	<script src="https://www.youtube.com/iframe_api"></script>
	<script>	
		let isPlaying = false; // 재생 상태 관리 변수
		let youtubeVideoId = "";

		async function playTrack(button) {

			const music_name = button.getAttribute("data-music-name");
			const art_name = button.getAttribute("data-art-name");

			try {
				const response = await fetch(`/playTrack?trackTitle=` + encodeURIComponent(music_name) + `&artist=` + encodeURIComponent(art_name));
				const youTubeUrl = await response.text();

				if (youTubeUrl === "No URL found") {
					alert("Unable to find a YouTube link for this track.");
				} else if (youTubeUrl === "Error occurred") {
					alert("An error occurred while fetching the track.");
				} else {
					youtubeVideoId = youTubeUrl;
					onYouTubeIframeAPIReady(youtubeVideoId);
					
					$(".nowPlayInfo").show();
					$(".playBtn").removeClass('playing');
					$(".playBtn").removeClass('pause');
					$(".state.now").show().siblings(".state.ready").hide();
					$(button).addClass('playing');				
					$(".playBar .playInfo").show();				

					$("#nowPlayArtist").html(art_name);
					$("#nowPlayMusic").html(music_name);
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
		$(function() {
			colorRandom();
			tagSelect();
			selectAll();
		});

		function playListReset(){
		    $('#listTitle').empty();
		    $('#musicList').empty();
		    $('.section-list').hide();
		    $('.tagList .tag').removeClass('on');
		}

		function playListShow() {
			var selectedTags = $('.tagList .tag.on').length;
            var selectedTagArray = [];
            var selectedTagIdArray = [];

            $('.tagList .tag.on').each(function() {
                var tagTxt = $(this).text();
                var tagId = $(this).data('id');
                selectedTagArray.push(tagTxt);
                selectedTagIdArray.push(tagId);
            });


            var wrappedTagArray = selectedTagArray.map(function(tagTxt) {
                return '<i>' + tagTxt + '</i>';
            });

            $('#listTitle').empty().append(wrappedTagArray.join(' '));  // 중복 방지 및 새로 갱신

            if (selectedTags < 1) {
                alert('해시태그를 선택해 주세요! 해시태그는 3개를 선택해주세요.');
            }
            else if (selectedTags < 3) {
                alert('해시태그는 3개를 선택해주세요.');
            }
            else {
                $.ajax({
                    type: 'GET',
                    url: '/music/playlist',
                    data: {
                        'hashtag1': selectedTagArray[0], // 첫 번째 해시태그
                        'hashtag2': selectedTagArray[1], // 두 번째 해시태그
                        'hashtag3': selectedTagArray[2]  // 세 번째 해시태그
                    },
					contentType: 'application/json',
                    success: function(response) {

                        var playlist = response.playlist;

                        if (Array.isArray(playlist)) {
                            // 선택된 해시태그에 해당하는 데이터만 필터링
                            var filteredResponse = playlist.filter(item =>
                                selectedTagArray.includes(item.hashtag_name) // selectedTagArray 사용
                            );

                            var html = '';
                            filteredResponse.forEach(function(music) {
                                html += '<li class="item">'
                                html += '	<button type="button" id="play" class="playBtn" '
                                html +=         'data-art-name="' + music.art_name + '" '
                                html +=         'data-music-name="' + music.music_name + '" '
                                html +=         'onclick="playTrack(this)">';
                                html += '	</button>'
                                html += '	<div class="arti">'
                                html += '		<i class="name">'+music.art_name+'</i>'
                                html += '		<i class="tit">'+music.music_name+'</i>'
                                html += '	</div>'
                                html += '</li>'
                            });

                            $('#musicList').empty().append(html);
                            $('.section-list').show();
                        } else {
                            console.log("응답이 배열이 아닙니다:", response);
                        }
                    },
                    error: function(xhr, status, error) {
                        console.log('****** 요청 실패 : ' + status + ' ***** 에러 : ' + error);
                        console.error("Error Details:", xhr.responseText);
                    }
                });
            }
		}
		// 해시태그 랜덤 컬러 설정
		function colorRandom() {
			function getRandomDarkColor() {
				const letters = '0123456789ABCDEF';
				let color = '#';
				let r, g, b;

				// 어두운 색상을 얻기 위해 반복해서 시도
				do {
					color = '#';
					for (let i = 0; i < 6; i++) {
						color += letters[Math.floor(Math.random() * 16)];
					}
					r = parseInt(color.slice(1, 3), 16);
					g = parseInt(color.slice(3, 5), 16);
					b = parseInt(color.slice(5, 7), 16);
				} while ((r > g && r > b) || getLuminance(color) > 0.5);  
				// 분홍색 계열 제외 및 어두운 색상만 허용

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
				$('.tag').each(function() {
					const color = getRandomDarkColor(); // 어두운 랜덤 색상 얻기
					$(this).css('background-color', color);

					const luminance = getLuminance(color);
					const textColor = luminance > 0.5 ? '#000000' : '#FFFFFF';
					$(this).css('color', textColor);
				});
			}

			setRandomColors();

			$('#tagReset').on('click', function() {
				setRandomColors();
				$('.tagList .tag').removeClass('on');
				$('#listTitle').empty();  // 선택된 태그 초기화
			});
		}
		
		// 해시태그 3개까지 선택 가능하도록 제한
		function tagSelect() {
			var maxSelection = 3;

			$('.tagList .tag').on('click', function() {
				if ($(this).hasClass('on')) {
					$(this).removeClass('on');
				} else {
					if ($('.tagList .on').length < maxSelection) {
						$(this).addClass('on');
					} else {
						alert('해시태그는 최대 3개까지만 선택할 수 있습니다.');
					}
				}
			});
		}

		function selectAll() {
			$('#allBtn').on('click', function () {
				$('.check').prop('checked', $(this).prop('checked'));
			});
		}
	</script>
</body>
</html>