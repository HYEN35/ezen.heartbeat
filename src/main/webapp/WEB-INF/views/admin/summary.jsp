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
				        <div class="summary-info">
				            <span class="info">오늘 가입한 유저 : <i id="todayuser">${count_a}</i></span>
				        </div>
				        <div class="summary-info">
				            <span class="info">총 구독자 수 : <i id="totalsub">${count_b}</i></span>
				        </div>
				        <div class="summary-info">
				            <span class="info">가장 많은 구독자를 보유한 아티스트 : <i id="topartist">${count_c.artist_name} 구독자 : ${count_c.email_count}</i></span>
				        </div>
				    </div>
				    <div class="adm-container">
					    <div class="adm-split-row">
							<div class="adm-split-col">
								<h4>구독자 분석</h4>
								<p>
								    회원 총 인원 : ${total}<br>
								    level 0 : ${level0Cnt} &nbsp;<span class="box blue"></span>&nbsp; 
								    | level 1 : ${level1Cnt} &nbsp; <span class="box red"></span>&nbsp; 
								    | level 2 : ${level2Cnt} &nbsp; <span class="box purple"></span>
								</p>
								<div class="progress-result">
								    <progress id="level0Cnt" max="${total}" value="${level0Cnt}" class="progress-blue"></progress>
								    <progress id="level1Cnt" max="${total}" value="${level1Cnt}" class="progress-red"></progress>
								    <progress id="level2Cnt" max="${total}" value="${level2Cnt}" class="progress-purple"></progress>
								    <input type="hidden" id="total" value="${total}">
								    
								    <div class="progress-bar-txt txt1">
								        <strong id="level0-result-txt"></strong> <strong>%</strong>
								    </div>
								    <div class="progress-bar-txt txt2">
								        <strong id="level1-result-txt"></strong> <strong>%</strong>
								    </div>
								    <div class="progress-bar-txt txt3">
								        <strong id="level2-result-txt"></strong> <strong>%</strong>
								    </div>
								</div>
							</div>
						</div>
					</div>
			    </div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>

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
	};
	
	console.log("총 회원 수:", total);
    console.log("레벨 0 회원 수:", level0Cnt);
    console.log("레벨 1 회원 수:", level1Cnt);
    console.log("레벨 2 회원 수:", level2Cnt);
    
	</script>

</body>
</html>