<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function searchEmail() {
	    var name = document.findIdFrm.name.value;
	    var birth = document.findIdFrm.birth.value;
	    var phone = document.findIdFrm.phone.value;
	    
	    $.ajax({
	        url: '/login/findId',
	        type: 'POST',
	        data: {
	            name: name,
	            birth: birth,
	            phone: phone
	        },
	        success: function(data) {  
	        	console.log("아이디 찾기 : ",data);
	            if (data.result === "success") {
	                resultShow(); 
	                $("#userName").text(name); 
	                $("#userEmail").text(data.email);
	         		
	                $("#successMessage").show();
	                $("#failureMessage").hide();
	            }
	            else {
	            	resultShow(); 
	            	 $("#failUserName").text(name); 
	                $("#successMessage").hide();
	                $("#failureMessage").show();
	            }
	        }
	    });
	    
	}

</script>
<div class="wrap">
    <div class="topArea">
        <div class="title">이메일 찾기</div>
        <button type="button" class="btn-i-close" onclick="popFindIdHide();"></button>
    </div>
    <div class="cntArea">
        <div class="findCnt">
            <form name="findIdFrm" autocomplete="off">
                <input type="text" name="name" class="txtBx" placeholder="이름 입력">
                <input type="number" name="birth" class="txtBx" placeholder="생년월일 8글자 입력">
                <input type="tel" maxlength="11" name="phone" class="txtBx" placeholder="핸드폰번호 입력">
                <button type="button" class="btn-border" onclick="searchEmail()">이메일 찾기</button>
            </form>
        </div>
        <div class="resultCnt">
            <p class="txt" id="successMessage">
                <i id="userName"></i>님의 이메일은 <i id="userEmail"></i>입니다.
            </p>
             <p class="txt"  id="failureMessage">	  
             	<i id="failUserName"></i>님의 이메일을 찾지 못했습니다. 다시 입력해주세요.
           	</p>
        </div>
    </div>
</div>
