<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
	<div class="inner service playlist" data-name="playlist">
		<%@ include file="../include/menu.jsp" %>
		<div id="playBar" class="playBar">플레이바</div>
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

	<div class="dimmed" onclick="popAlertCheckHide()"></div>
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

            console.log("******선택된 해시태그 :", selectedTagArray);

            var wrappedTagArray = selectedTagArray.map(function(tagTxt) {
                return '<i>' + tagTxt + '</i>';
            });

            $('#listTitle').empty().append(wrappedTagArray.join(' '));  // 중복 방지 및 새로 갱신

            if (selectedTags < 1) {
                alert('해시태그를 선택해 주세요! 해시태그는 3개를 선택해주세요.');
            }
            else if (selectedTags < 1) {
                alert('해시태그는 1개를 선택해주세요.');
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
                        //console.log('************Response:', response);

                        var playlist = response.playlist;

                        if (Array.isArray(playlist)) {
                            // 선택된 해시태그에 해당하는 데이터만 필터링
                            var filteredResponse = playlist.filter(item =>
                                selectedTagArray.includes(item.hashtag_name) // selectedTagArray 사용
                            );

                            var html = '';
                            filteredResponse.forEach(function(music) {
                                html += '<li class="item">'
                                html += '	<div class="album"><i></i></div>'
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