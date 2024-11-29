<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>



<div class="wrap">
	<div class="topArea">
		<div class="title">
			<p>포스트 쓰기</p>
			<p class="artiName"><%=pageTitle %></p>
		</div>
		<button type="button" class="btn-i-close" onclick="popPostHide();"></button>
	</div>
	<div class="cntArea">
		<form action="/community/postWrite" method="post" autocomplete="off" >
			<input type="hidden" name="email" value="${UserVO.email }">
			<input type="hidden" name="artist_id" value="${UserVO.artist_id}">
			<input type="hidden" name="nickname" value="${UserVO.nickname}">
			<textarea class="txtBx" name="content"></textarea>
			<div class="btmBx">
				<div class="imgBx">
					<input type="file" id="file" name="post_img" accept=".jpg, .jpeg, .png, .gif" hidden multiple>
					<button type="button" class="btn-under" onclick="$('#file').click();">첨부파일 선택</button>
					<div class="fileName"></div>
				</div>
				<button type="submit" class="btn-full">등록</button>
			</div>
		</form>
	</div>
</div>