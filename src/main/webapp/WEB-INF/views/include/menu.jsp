<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
	String menuPageRequest = request.getRequestURI();

	String chart = menuPageRequest.endsWith("chart.jsp") ? "on" : "";
	String playlist = menuPageRequest.endsWith("playlist.jsp") ? "on" : "";
	String community = menuPageRequest.endsWith("community.jsp") ? "on" : "";
	String membership =	menuPageRequest.endsWith("purchase.jsp") ? "on" : "";
	String notice =	menuPageRequest.endsWith("notice.jsp") ||
					menuPageRequest.endsWith("noticeModify.jsp") ||
					menuPageRequest.endsWith("noticePost.jsp") ||
					menuPageRequest.endsWith("noticeShow.jsp") ||
					menuPageRequest.endsWith("myNoticeModify.jsp") ||
					menuPageRequest.endsWith("myNoticeShow.jsp") ? "on" : "";
	String mypage =	menuPageRequest.endsWith("mypage.jsp") ||
					menuPageRequest.endsWith("mymembership.jsp") ||
					menuPageRequest.endsWith("mypost.jsp") ? "on" : "";

%>


<div id="menu" class="menu">
	<div class="wrap">
		<h1 class="logo"><a href="${pageContext.request.contextPath}/chart">HeartBeat</a></h1>
		<div class="userCnt">
			<div class="user" onclick="dropMenuShow();">
				<c:if test="${UserVO.profileimg != null && UserVO.profileimg != ''}">
				    <div class="image"><img src="/heartbeat-upload/${UserVO.profileimg}" alt="닉네임"></div>
				</c:if>
				<c:if test="${UserVO.profileimg == null || UserVO.profileimg == ''}">
				    <div class="image"><img id="profileImage" src="${pageContext.request.contextPath}/img/user.png " alt="닉네임"></div>
				</c:if>
				<div class="name"><p>${UserVO.nickname }</p></div>
			</div>
			<div class="dropMenu">
			<c:if test="${UserVO.email eq 'admin' }">
                <a href="/admin/summary" class="btn-under-02">관리자페이지</a>
            </c:if>
			<a href="/logout" class="btn-under-02">로그아웃</a>
			</div>
		</div>
		<div class="menuCnt">
			<ul>
				<li class="item">
					<a href="${pageContext.request.contextPath}/chart" class="<%=chart %>"><i class="fa-brands fa-spotify"></i>차트</a>
				</li>
				<li class="item">
					<a href="${pageContext.request.contextPath}/playlist" class="<%=playlist %>"><i class="fa-solid fa-icons"></i>플레이리스트</a>
				</li>
				<li class="item">
					<a href="${pageContext.request.contextPath}/community/community" class="<%=community %> <%="artist".equals(request.getAttribute("artistPage")) ? "on" : "" %>"><i class="fa-solid fa-comment"></i>커뮤니티</a>
				</li>
				<li class="item">
					<a href="${pageContext.request.contextPath}/purchase" class="<%=membership %>"><i class="fa-solid fa-credit-card"></i>멤버십</a>
				</li>
				<li class="item">
					<a href="${pageContext.request.contextPath}/notice/notice?num=1" class="<%=notice %> <%="notice".equals(request.getAttribute("noticePage")) ? "on" : "" %>"><i class="fa-solid fa-table-list"></i>공지 및 문의</a>
				</li>
				<li class="item">
					<a href="${pageContext.request.contextPath}/mypage" class="<%=mypage %>"><i class="fa-solid fa-user-pen"></i>마이페이지</a>
				</li>
			</ul>
		</div>
	</div>
</div>