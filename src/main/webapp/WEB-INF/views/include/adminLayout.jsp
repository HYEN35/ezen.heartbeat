<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	request.setCharacterEncoding("UTF-8");

    String pageName = (String) request.getAttribute("pageName");
    String pageTitle = (String) request.getAttribute("pageTitle");
	String pageRequest = request.getRequestURI();

	if (pageRequest.endsWith("/admin/adminjoin.jsp")) pageName = "계정생성 - HeartBeat 관리자";
    else if (pageRequest.endsWith("/admin/edit.jsp")){ pageName = "회원정보 수정 - HeartBeat 관리자"; }
	else if (pageRequest.endsWith("/admin/summary.jsp")){
		pageName = "요약정보 - HeartBeat 관리자";
		pageTitle = "요약정보";
	}
	else if (pageRequest.endsWith("/admin/member.jsp")){
		pageName = "회원 리스트 - HeartBeat 관리자";
		pageTitle = "회원 리스트";
	}
	else if (pageRequest.endsWith("/admin/staff.jsp")){
		pageName = "회원 리스트 - HeartBeat 관리자";
		pageTitle = "직원 리스트";
	}
	else if (pageRequest.endsWith("/admin/post.jsp")){
		pageName = "게시글 확인 - HeartBeat 관리자";
		pageTitle = "게시글 확인";
	}
	else if (pageRequest.endsWith("/admin/comment.jsp")){
        pageName = "댓글 확인 - HeartBeat 관리자";
        pageTitle = "댓글 확인";
    }
	else if (pageRequest.endsWith("/admin/notice.jsp") || pageRequest.endsWith("/admin/mynotice.jsp") || pageRequest.endsWith("/admin/myNoticeShow.jsp") || pageRequest.endsWith("/admin/noticeShow.jsp")){
        pageName = "공지 및 문의 - HeartBeat 관리자";
        pageTitle = "공지 및 문의";
    }
	else if (pageRequest.endsWith("/admin/noticePost.jsp")){
        pageName = "공지 및 문의 - HeartBeat 관리자";
        pageTitle = "공지 및 문의 작성";
    }
	else if (pageRequest.endsWith("/admin/noticeModify.jsp") || pageRequest.endsWith("/admin/myNoticeModify.jsp")){
        pageName = "공지 및 문의 - HeartBeat 관리자";
        pageTitle = "공지 및 문의 수정";
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title><%=pageName %></title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="title" content="HeartBeat">
	<meta name="description" content="당신의 심장을 뛰게 하는 음악 사이트, It makes your HeartBeat">
	<meta name="keywords" content="HeartBeat,음악스트리밍,음악검색,음악듣기,팬커뮤니티,팬소통,팬소통앱,팬커뮤니티소통앱">
	<meta name="og:title" content="HeartBeat">
	<meta name="og:url" content="heartbeat.kr/">
	<meta name="og:site_name" content="HeartBeat">
	<meta name="og:description" content="당신의 심장을 뛰게 하는 음악 사이트, It makes your HeartBeat">
	<meta name="og:image" content="#none">
	<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css">
	<script src="https://kit.fontawesome.com/dda279453f.js"></script>
	<script src="${pageContext.request.contextPath}/js/vendor/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/common.js"></script>
</head>