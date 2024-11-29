<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/adminLayout.jsp" %>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery.min.js"></script>
<!-- google charts -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

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
				            <span class="info">오늘 가입한 유저 <i id="todayuser">${count_a}</i></span>
				        </div>
				        <div class="summary-info">
				            <span class="info">총 구독자 수 <i id="totalsub">${count_b}</i></span>
				        </div>
				        <div class="summary-info">
				            <span class="info">가장 많은 구독자를 보유한 아티스트 <i id="topartist">${count_c}</i></span>
				        </div>
				    </div>
				    <!-- 차트 영역 -->
				    <div class="chart-area">
			        	<h4>사이트 방문자 성별 현황 그래프</h4>
					    <div id="Line_Controls_Chart">
					      <!-- 라인 차트 생성할 영역 -->
					  		<div id="lineChartArea" style="padding:0px 20px 0px 0px;"></div>
					      <!-- 컨트롤바를 생성할 영역 -->
					  		<div id="controlsArea" style="padding:0px 20px 0px 0px;"></div>
							</div>
				    </div>
				</div>
			</div>
		</div>
	</div>

	<div class="dimmed"></div>

	<script>
	var chartDrowFun = {
		    chartDrow: function() {
		        var chartDateformat = 'yyyy년MM월dd일';
		        var chartLineCount = 10;
		        var controlLineCount = 10;

		        function drawDashboard() {
		            var data = new google.visualization.DataTable();
		            data.addColumn('datetime', ${monthCounts.month });
		            data.addColumn('number', ${monthCounts.count });

		            for (var i = 0; i <= 33; i++) {
		                var total = Math.floor(Math.random() * 300) + 1;
		                var userTotal = Math.floor(Math.random() * total) + 1;
		                dataRow = [new Date('2024', '08', i, '10'), userTotal];
		                data.addRow(dataRow);
		            }

		            var chart = new google.visualization.ChartWrapper({
		                chartType: 'LineChart',
		                containerId: 'lineChartArea',
		                options: {
		                    isStacked: 'percent',
		                    focusTarget: 'category',
		                    height: 500,
		                    width: '100%',
		                    legend: { position: "top", textStyle: { fontSize: 13 } },
		                    pointSize: 5,
		                    tooltip: { textStyle: { fontSize: 12 }, showColorCode: true, trigger: 'both' },
		                    hAxis: { 
		                        format: 'yyyy년MM월',
		                        gridlines: {
		                            count: 4,
		                            units: {
		                                months: { format: ['yyyy년MM월'] }
		                            }
		                        },
		                        textStyle: { fontSize: 12 } 
		                    },
		                    vAxis: { minValue: 100, viewWindow: { min: 0 }, gridlines: { count: -1 }, textStyle: { fontSize: 12 } },
		                    animation: { startup: true, duration: 1000, easing: 'in' },
		                    annotations: { pattern: chartDateformat, textStyle: {
		                        fontSize: 15,
		                        bold: true,
		                        italic: true,
		                        color: '#871b47',
		                        auraColor: '#d799ae',
		                        opacity: 0.8,
		                        pattern: chartDateformat
		                    } }
		                }
		            });

		            var control = new google.visualization.ControlWrapper({
		                controlType: 'ChartRangeFilter',
		                containerId: 'controlsArea',
		                options: {
		                    ui: {
		                        chartType: 'LineChart',
		                        chartOptions: {
		                            chartArea: { width: '60%', height: 80 },
		                            hAxis: { baselineColor: 'none', format: chartDateformat, textStyle: { fontSize: 12 }, gridlines: { count: controlLineCount, units: {
		                                years: { format: ['yyyy년'] },
		                                months: { format: ['MM월'] },
		                                days: { format: ['dd일'] },
		                                hours: { format: ['HH시'] }
		                            } } }
		                        }
		                    },
		                    filterColumnIndex: 0
		                }
		            });

		            var dashboard = new google.visualization.Dashboard(document.getElementById('Line_Controls_Chart'));
		            window.addEventListener('resize', function() { dashboard.draw(data); }, false);
		            dashboard.bind([control], [chart]);
		            dashboard.draw(data);
		        }
		        google.charts.setOnLoadCallback(drawDashboard);
		    }
		}

		$(document).ready(function() {
		    google.charts.load('current', { packages: ['line', 'controls'] });
		    chartDrowFun.chartDrow();
		});
	</script>
</body>
</html>