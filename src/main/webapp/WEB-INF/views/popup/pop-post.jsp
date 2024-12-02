<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>



<div class="wrap">
	<div class="topArea">
		<div class="title">
			<p>포스트 쓰기</p>
			<p class="artiName">Newjeans</p>
		</div>
		<button type="button" class="btn-i-close" onclick="popPostHide();"></button>
	</div>
	<div class="cntArea">
		<form action="/community/newjeansPostWrite" method="post" enctype="multipart/form-data" name="popPost">
			<input type="hidden" name="email" value="${UserVO.email }">
			<input type="hidden" name="artist_id" value="${UserVO.artist_id}">
			<input type="hidden" name="nickname" value="${UserVO.nickname}">
			<textarea class="txtBx" name="content"></textarea>
			<div class="btmBx">
            <div class="imgBx">
                <input type="file" id="file2" name="post_Img" accept=".jpg, .jpeg, .png" hidden>
                <!-- <input type="file" id="file" name="postImg1" accept=".jpg, .jpeg, .png" > -->  <!-- id가 file이면 안됨 --> 
                <button type="button" class="btn-under" onclick="$('#file2').click();">첨부파일 선택</button>
            </div>
            <!-- 파일 리스트 영역 -->
        		<div id="fileListContainer" class="file-list-container"></div>
	            <button type="submit" class="btn-full">등록</button>
	        </div>
				<div class="imgBx">
					<input type="file" id="file2" name="post_Img" accept=".jpg, .jpeg, .png" hidden>
					<!-- <input type="file" id="file" name="postImg1" accept=".jpg, .jpeg, .png" > -->  <!-- id가 file이면 안됨 --> 
					<button type="button" class="btn-under" onclick="$('#file2').click();">첨부파일 선택</button>
					<div class="fileName">
						<i class="name">${file.name}</i>
					</div>
				</div>
				<!-- 파일 리스트 영역 -->
        		<div id="fileListContainer" class="file-list-container"></div>
	            <button type="submit" class="btn-full">등록</button>
	        </div>
		</form>
	</div>
</div>
<script>
	// 파일 선택 후 검증 및 목록 추가
	document.getElementById('file2').addEventListener('change', function(event) {
		var fileInput = event.target;
		console.log(fileInput);
		var files = fileInput.files;
		console.log(files);

		var fileListContainer = document.getElementById('fileListContainer');
		var fileNameContainer = document.querySelector('.fileName .name');

		// 기존 파일 목록 초기화
		fileListContainer.innerHTML = '';
		
		// 파일명 초기화
		fileNameContainer.textContent = '';

		var validExtensions = ['.jpg', '.jpeg', '.png', '.gif'];
		var maxFiles = 1;
		var invalidFiles = [];

		for (var i = 0; i < files.length; i++) {
			var file = files[i];
			var fileName = file.name;
			var fileExtension = fileName.slice(((fileName.lastIndexOf(".") - 1) >>> 0) + 2).toLowerCase();
			console.log('파일 이름: '+file.name  +', 크기: '+file.size +'bytes');

			// 파일 확장자 확인
			if (validExtensions.indexOf('.' + fileExtension) === -1) {
				invalidFiles.push(fileName);
			} else {
				// 파일 이름이 5자 이상일 경우 잘라서 '...' 추가
				if (fileName.length > 5) {
					fileName = fileName.substring(0, 5) + '...';
				}

				// 파일명을 파일명 표시 영역에 추가
				fileNameContainer.textContent = file.name;

				// 파일 목록에 추가
				var listItem = document.createElement('div');
				//listItem.textContent = fileName;
				//fileListContainer.appendChild(listItem);
			}

			// 4개 이상의 파일을 선택했을 경우 경고
			if (files.length > maxFiles) {
				alert('1개의 사진 파일만 첨부할 수 있습니다.');
				fileInput.value = ''; // 파일 입력 초기화
				fileListContainer.innerHTML = ''; // 파일 목록 초기화
				
				fileNameContainer.textContent = ''; // 파일명 초기화
				return;
			}
		}

		// 잘못된 파일 확장자가 있을 경우 경고
		if (invalidFiles.length > 0) {
			alert('다음 파일은 지원하지 않는 형식입니다: ' + invalidFiles.join(', '));
			fileInput.value = ''; // 파일 입력 초기화
			fileListContainer.innerHTML = ''; // 파일 목록 초기화
			fileNameContainer.textContent = ''; // 파일명 초기화
		}
	});
</script>