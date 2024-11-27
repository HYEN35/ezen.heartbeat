<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="wrap">
	<div class="topArea">
		<div id="stationTitle" class="title"></div>
		<button type="button" class="btn-i-close" onclick="popStationListHide();"></button>
	</div>
	<div class="cntArea">
		<!-- 드라이브 -->
		 <ul class="listBx" id="driveList">
	        	<c:forEach var="trackInfoListDrive" items="${trackInfoListDrive}" varStatus="status">
		            <c:if test="${status.index < 15}">
		                <li class="item">
		                    <div class="num">${status.index + 1}</div>
		                    <div class="album"><img src="${trackInfoListDrive.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
		                    <div class="arti">
		                        <i class="tit">${trackInfoListDrive.title}</i>
		                        <i class="name">${trackInfoListDrive.artist}</i>
		                    </div>
		                    <button class="playBtn" onclick="playTrack('${trackInfoListDrive.title}', '${trackInfoListDrive.artist}', this);"></button>
		                </li>
		            </c:if>
		        </c:forEach>		
	    </ul>
		<!-- 운동 -->	
		 <ul class="listBx" id="healthList" >
	        <c:forEach var="trackInfoListHealth" items="${trackInfoListHealth}" varStatus="status">
	            <c:if test="${status.index < 15}">
	                <li class="item">
	                    <div class="num">${status.index + 1}</div>
	                    <div class="album"><img src="${trackInfoListHealth.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
	                    <div class="arti">
	                        <i class="tit">${trackInfoListHealth.title}</i>
	                        <i class="name">${trackInfoListHealth.artist}</i>
	                    </div>
	                    <button class="playBtn" onclick="playTrack('${trackInfoListHealth.title}', '${trackInfoListHealth.artist}', this);"></button>
	                </li>
	            </c:if>
	        </c:forEach>		
	   	 </ul>
		<!-- 출퇴근 -->	
		 <ul class="listBx" id="gotoList">
	        <c:forEach var="trackInfoListGoto" items="${trackInfoListGoto}" varStatus="status">
	            <c:if test="${status.index < 15}">
	                <li class="item">
	                    <div class="num">${status.index + 1}</div>
	                    <div class="album"><img src="${trackInfoListGoto.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
	                    <div class="arti">
	                        <i class="tit">${trackInfoListGoto.title}</i>
	                        <i class="name">${trackInfoListGoto.artist}</i>
	                    </div>
	                    <button class="playBtn" onclick="playTrack('${trackInfoListGoto.title}', '${trackInfoListGoto.artist}', this);"></button>
	                </li>
	            </c:if>
	        </c:forEach>		
	   	 </ul>		
   		 <!-- 공부 -->
	   	  <ul class="listBx" id="studyList" >
	        <c:forEach var="trackInfoListStudy" items="${trackInfoListStudy}" varStatus="status">
	            <c:if test="${status.index < 15}">
	                <li class="item">
	                    <div class="num">${status.index + 1}</div>
	                    <div class="album"><img src="${trackInfoListStudy.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
	                    <div class="arti">
	                        <i class="tit">${trackInfoListStudy.title}</i>
	                        <i class="name">${trackInfoListStudy.artist}</i>
	                    </div>
	                    <button class="playBtn" onclick="playTrack('${trackInfoListStudy.title}', '${trackInfoListStudy.artist}', this);"></button>
	                </li>
	            </c:if>
	        </c:forEach>		
   	 	</ul>		
   	 	<!-- 일/작업 -->
	   	  <ul class="listBx" id="workList">
	        <c:forEach var="trackInfoListWork" items="${trackInfoListWork}" varStatus="status">
	            <c:if test="${status.index < 15}">
	                <li class="item">
	                    <div class="num">${status.index + 1}</div>
	                    <div class="album"><img src="${trackInfoListWork.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
	                    <div class="arti">
	                        <i class="tit">${trackInfoListWork.title}</i>
	                        <i class="name">${trackInfoListWork.artist}</i>
	                    </div>
	                    <button class="playBtn" onclick="playTrack('${trackInfoListWork.title}', '${trackInfoListWork.artist}', this);"></button>
	                </li>
	            </c:if>
	        </c:forEach>		
	   	 </ul>		
   		 <!-- 잠 -->
  		<ul class="listBx" id="sleepList" >
	        <c:forEach var="trackInfoListSleep" items="${trackInfoListSleep}" varStatus="status">
	            <c:if test="${status.index < 15}">
	                <li class="item">
	                    <div class="num">${status.index + 1}</div>
	                    <div class="album"><img src="${trackInfoListSleep.albumImageUrl}" alt="Album Image" class="album-image" style="width:100%;height:100%" /></div>
	                    <div class="arti">
	                        <i class="tit">${trackInfoListSleep.title}</i>
	                        <i class="name">${trackInfoListSleep.artist}</i>
	                    </div>
	                    <button class="playBtn" onclick="playTrack('${trackInfoListSleep.title}', '${trackInfoListSleep.artist}', this);"></button>
	                </li>
	            </c:if>
	        </c:forEach>		
	   	 </ul>		
	</div>
</div>