<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<body>
	<div class="inner admin summary" data-name="summary">
		<%@ include file="../include/admin.jsp" %>
		<div class="container">
			<div class="cntWrap">
				<h2 id="title" class="title"><%=pageTitle %></h2>
				<div class="cntArea">
					<!-- 상단 정보 -->
				    <div class="summary-info">
				        <div class="item">
				            <span class="info">오늘 가입한 유저 : <i id="todayuser">${count_a}</i></span>
				        </div>
				        <div class="item">
				            <span class="info">오늘 탈퇴한 유저 : <i id="todayuser">${todayDeleteUser}</i></span>
				        </div>
				        <div class="item">
   				            <span class="info"> 회원 총 인원: <i id="totalsub">${total} </i></span>
				        </div>
				        <div class="item">
				            <span class="info">가장 많은 구독자를 보유한 아티스트 : <i id="topartist">${count_c.art_name} 구독자 : ${count_c.email_count}</i></span>
				        </div>
				    </div>
				    <div class="adm-container section-grade">
					    <div class="adm-split-row">
							<div class="adm-split-col">
								<h4 class="tit">회원 등급 분석</h4>
								<p class="total">총 구독자 수 : ${count_b}</p>
								<input type="hidden" id="total" value="${total}">
								<div class="itemWrap">
									<div class="item lev-00">
										<div class="count">level 0 <b>${level0Cnt}명</b></div>
										<progress id="level0Cnt" max="${total}" value="${level0Cnt}" class="progressBar"></progress>
										<strong id="level0-result-txt"></strong> <strong>%</strong>
									</div>
									<div class="item lev-01">
										<div class="count">level 1 <b>${level1Cnt}명</b></div>
										<progress id="level1Cnt" max="${total}" value="${level1Cnt}" class="progressBar"></progress>
										<strong id="level1-result-txt"></strong> <strong>%</strong>
									</div>
									<div class="item lev-02">
										<div class="count">level 2 <b>${level2Cnt}명</b></div>
										<progress id="level2Cnt" max="${total}" value="${level2Cnt}" class="progressBar"></progress>
										<strong id="level2-result-txt"></strong> <strong>%</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="adm-container section-age">
					    <div class="adm-split-row">
							<div class="adm-split-col">
								<h4 class="tit">회원 연령대별 분석 </h4>
								<div class="itemWrap">
								<c:forEach items="${ageGroup}" var="ageGroup" varStatus="status">
									<div class="item lev-00">
										<div class="count">${ageGroup.ageGroup }<b> ${ageGroup.totalCnt}명</b></div>
										<progress id="level0Cnt" max="${total}" value="${ageGroup.totalCnt}" class="progressBar"></progress>
										<strong id="ageGroup-result-txt-${status.index}"></strong> <strong>%</strong>
									</div>
								</c:forEach>	
								</div>
							</div>
						</div>
					</div>
					<div class="adm-container section-sales">
					    <div class="adm-split-row">
							<div class="adm-split-col">
								<h4 class="tit">매출 분석</h4>
								<p class="total">목표 금액 :<fmt:formatNumber value="${targetAmount}" pattern="##,###.##"/>원</p>
								
								<input type="hidden" id="targetAmount" value="${targetAmount}">
								<div class="itemWrap">
									<div class="item lev-00">
										<div class="count">level 1 <b><fmt:formatNumber value="${level1Price}" pattern="##,###.##"/>원</b></div>
										<progress id="level0Cnt" max="${targetAmount}" value="${level1Price}" class="progressBar"></progress>
										<strong id="level1-amount-result-txt"></strong> <strong>%</strong>
									</div>
									<div class="item lev-01">
										<div class="count">level 2 <b><fmt:formatNumber value="${level2Price}" pattern="##,###.##"/>원</b></div>
										<progress id="level1Cnt" max="${targetAmount}" value="${level2Price}" class="progressBar"></progress>
										<strong id="level2-amount-result-txt"></strong> <strong>%</strong>
									</div>
									<div class="item lev-02">
										<div class="count">total <b><fmt:formatNumber value="${totalPrice}" pattern="##,###.##"/>원</b></div>
										<progress id="level2Cnt" max="${targetAmount}" value="${totalPrice}" class="progressBar"></progress>
										<strong id="total-amount-result-txt"></strong> <strong>%</strong>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="adm-container section-like">
						<div class="adm-split-row">
							<div class="adm-split-col">
								<h4 class="tit">좋아요가 가장 많은 게시물 top5</h4>
								<div class="itemWrap">
									<c:forEach items="${lvo }" var="lvo">
										<a href="javascript:void(0);" onclick="adminPopPostArtistShow('${lvo.post_id}')" class="post">게시물 번호 ${lvo.post_id } | 작성자 ${lvo.nickname} | 좋아요 ${lvo.like_count }</a>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
			    </div>
			</div>
		</div>
	</div>

	
	<div class="dimmed" onclick="popPostArtistHide()"></div>

	<!-- [D] 팝업 아티스트 포스트 -->
	<div class="popup pop-post-artist"><%@ include file="../popup/pop-post-artist.jsp" %></div>

	<script>
		window.onload = function() {
			var total = parseInt("${total}") || 0;
			var level0Cnt = parseInt("${level0Cnt}") || 0;
			var level1Cnt = parseInt("${level1Cnt}") || 0;
			var level2Cnt = parseInt("${level2Cnt}") || 0;

			function calculatePercentage(count) {
				if (total === 0) return "0.00"; // 총 회원 수가 0이면 0% 반환
				return (count / total * 100).toFixed(2);
			}

			var level0Percentage = calculatePercentage(level0Cnt);
			var level1Percentage = calculatePercentage(level1Cnt);
			var level2Percentage = calculatePercentage(level2Cnt);

			document.getElementById('level0-result-txt').textContent = level0Percentage;
			document.getElementById('level1-result-txt').textContent = level1Percentage;
			document.getElementById('level2-result-txt').textContent = level2Percentage;

			
			var targetAmount = parseInt("${targetAmount}"); // 목표 금액 :100만원
			var level1Price =  parseInt("${level1Price}");
			var level2Price =  parseInt("${level2Price}");
			var totalPrice =  parseInt("${totalPrice}"); //level1 + level2 합계
			
			function calculateAmountPercentage(count) {
				return (count/targetAmount * 100).toFixed(2);
			}
			
			var level1PricePercent = calculateAmountPercentage(level1Price);
			var level2PricePercent = calculateAmountPercentage(level2Price);
			var totalPricePercent = calculateAmountPercentage(totalPrice);
			
			document.getElementById('level1-amount-result-txt').textContent = level1PricePercent;
			document.getElementById('level2-amount-result-txt').textContent = level2PricePercent;
			document.getElementById('total-amount-result-txt').textContent = totalPricePercent;
			
			//회원 연령대별 분석
			var ageGroups = [];	
		 	var total = ${total};
		 	 // AgeGroupDTO 객체의 속성을 JavaScript 객체로 변환
		 	<c:forEach items="${ageGroup}" var="ageGroup">
		        ageGroups.push({
		            ageGroup: '${ageGroup.ageGroup}', 
		            totalCnt: ${ageGroup.totalCnt}   
		        });
   	 		</c:forEach>
   	 	 
			 ageGroups.forEach(function(ageGroup, index) {
				    var ageTotal = ageGroup.totalCnt;
				    var percentage = calculateAgeAmountPercentage(ageTotal, total);
					
				    function calculateAgeAmountPercentage(count, total) {
				        if (total === 0) return 0; 
				        return ((count / total) * 100).toFixed(2);
				    }
				    
				    // 각 연령대별 비율을 출력할 요소를 id로 찾아서 비율 삽입
				    var elementId = "ageGroup-result-txt-" + index;
				    document.getElementById(elementId).textContent = percentage;
				});



		};
		

		
		//팝업 아티스트포스트
		function adminPopPostArtistShow(post_id){
			 // AJAX 요청으로 데이터를 가져옵니다.
			 
		    $.post("/community/getArtistPost", {  post_id: post_id }, function(data) {
		    	// 기존의 cntArea를 비우지 않고 데이터를 추가하거나 수정합니다.
		        const newContent = $(data).find('.cntArea').html(); // JSP에서 cntArea만 가져오기
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
	</script>

</body>
</html>