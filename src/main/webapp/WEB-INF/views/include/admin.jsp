<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String menuPageRequest = request.getRequestURI();

	String summary = menuPageRequest.endsWith("summary.jsp") ? "on" : "";
    String member = menuPageRequest.endsWith("member.jsp") ? "on" : "";
    String staff = menuPageRequest.endsWith("staff.jsp") ? "on" : "";
    String post = menuPageRequest.endsWith("post.jsp") ? "on" : "";
    String comment =	menuPageRequest.endsWith("comment.jsp") ? "on" : "";
    String notice =	menuPageRequest.endsWith("notice.jsp") ||
			menuPageRequest.endsWith("noticeModify.jsp") ||
			menuPageRequest.endsWith("noticePost.jsp") ||
			menuPageRequest.endsWith("noticeShow.jsp") ||
			menuPageRequest.endsWith("myNoticeModify.jsp") ||
			menuPageRequest.endsWith("myNoticeShow.jsp") ? "on" : "";
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
					    <div class="image"><img src="${pageContext.request.contextPath}/img/user.png" alt="닉네임"></div>
					</c:if>
	   			<div class="name">${UserVO.nickname}</div>
	   		</div>
	   		<div class="dropMenu">
	   			<a href="/logout" class="btn-under-02">로그아웃</a>
	   		</div>
	   	</div>
	   	<div class="menuCnt">
	   		<ul>
	   			<li class="item"><a href="${pageContext.request.contextPath}/admin/summary" class="<%=summary %>"><i class="fa-solid fa-house"></i>Summary</a></li>
	   			<li class="item">
					<a href="${pageContext.request.contextPath}/admin/member" class="<%=member %> <%="member".equals(request.getAttribute("memberPage")) ? "on" : "" %>"><i class="fa-solid fa-users"></i>회원 리스트
					</a>
				</li>
	   			<li class="item"><a href="${pageContext.request.contextPath}/admin/post?num=1" class="<%=post %>"><i class="fa-solid fa-list-check"></i>게시글 확인</a></li>
	   			<li class="item"><a href="${pageContext.request.contextPath}/admin/comment?num=1" class="<%=comment %>"><i class="fa-solid fa-comments"></i>댓글 확인</a></li>
	   			<li class="item"><a href="${pageContext.request.contextPath}/admin/notice?num=1" class="<%=notice %>"><i class="fa-solid fa-bell"></i>공지 및 문의</a></li>
	   		</ul>
	   	</div>
	</div>
</div>