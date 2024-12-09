
//common.js
$(function(){
	uploadFileName();

	$('.dimmed').on('click', function(){
		popLogoutHide();
	});
});

function menu(){
	$('body').on('click', '#menu .item' , function (){
		$(this).addClass('on');
	});
}

//메뉴>닉네임 클릭 시 드랍다운메뉴
function dropMenuShow(){
	$('.dropMenu').toggle();
}

//팝업 로그아웃
function popLogoutShow(){
	$('.pop-alert-logout').show();
	$('.dimmed').show();
}
function popLogoutHide(){
	$('.pop-alert-logout').hide();
	$('.dimmed').hide();
}

//파일업로드
function uploadFileName(){
	$('#file').on('change',function(){
		var fileName = $('#file').val().split('/').pop().split('\\').pop();
		$('.fileName i').html(fileName);
	});
}

//파일 다중 선택
function multipleUploadFile(){
	var selectedFiles = [];  // 선택된 파일들을 저장할 배열

	$('#file').on('change', function() {
		var files = this.files;

		// 선택된 파일들을 배열에 추가 (최대 3개까지)
		for (var i = 0; i < files.length; i++) {
			if (selectedFiles.length < 3) {
				selectedFiles.push(files[i]);
			} else {
				alert("최대 3개의 파일만 첨부할 수 있습니다.");
				break;
			}
		}

		// 파일 목록 갱신
		updateFileList();

		// 선택 초기화 (같은 파일도 다시 선택할 수 있게 하기 위함)
		this.value = "";
	});

	// 파일 목록 갱신 및 삭제 버튼 추가
	function updateFileList() {
		$('.fileName').empty(); // 기존 파일 목록 초기화

		selectedFiles.forEach(function(file, index) {
			// 파일 이름과 삭제 버튼을 추가
			$('.fileName').append(
				'<div>'
				+	'<i class="fName">' + file.name + '</i>'
				+	'<button type="button" class="btn-i-del" data-index="' + index + '"><i class="fa-regular fa-circle-xmark"></i></button>'
				+'</div>'
			);
		});

		// 삭제 버튼 이벤트 핸들러
		$('.btn-i-del').on('click', function() {
			var fileIndex = $(this).data('index');
			selectedFiles.splice(fileIndex, 1);  // 선택한 파일을 배열에서 삭제
			updateFileList();  // 목록을 다시 갱신
		});
	}
}

//커뮤니티>아티스트>댓글 수정 및 저장
//function popCommentEditShow(e){
//	$(e).hide().siblings().show();
//	$(e).parent('.fan-profile').siblings('.fan-comment').children().find('.comment').hide().siblings('.txtBx').show();
//}
//function popCommentSaveShow(e){
//	$(e).hide().siblings().show();
//	$(e).parent('.fan-profile').siblings('.fan-comment').children().find('.comment').show().siblings('.txtBx').hide();
//}



