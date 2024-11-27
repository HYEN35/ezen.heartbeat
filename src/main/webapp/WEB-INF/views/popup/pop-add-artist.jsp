<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<div class="wrap">
	<div class="topArea">
		<div id="stationTitle" class="title">아티스트 추가 요청</div>
	</div>
	<div class="cntArea">
		<ul class="listBx">
			<li class="item">
				<span class="tit">아티스트 이름</span>
				<input type="text" class="txtBx">
			</li>
			<li class="item">
				<span class="tit">요청 이유</span>
				<input type="text" class="txtBx">
			</li>
		</ul>
		<div class="btnBx">
			<button type="button" class="btn-full">요청하기</button>
			<button type="button" class="btn-border" onclick="popAddArtistHide();">닫기</button>
		</div>
	</div>
</div>