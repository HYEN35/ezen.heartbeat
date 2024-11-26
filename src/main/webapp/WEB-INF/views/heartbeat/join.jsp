<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/layout.jsp" %>

<body>
   <div class="inner service join">
      <div class="container">
         <div class="wrap">
            <h1 class="logo">HeartBeat</h1>
            <div class="joinCnt">
               <form action="/register" method="Post" enctype="multipart/form-data" name="joinPro" onsubmit="return validateForm()">
                  <div class="inputBx">
                       <p class="noti">*필수 입력 사항</p>
                       <div class="doubleCheck">
                           <input type="text" id="email" name="email" class="txtBx" placeholder="* 이메일" required oninput="checkEmailFormat()">
						   <button type="button" class="btn-border" onclick="checkEmail()">중복 확인</button>
                       </div>
                       <p class="error">*비밀번호는 특수문자, 숫자를 포함하여 8~16자로 해주세요.</p>
                         <input type="password" placeholder="* 비밀번호" class="txtBx" name="pwd" id="password" required >
                       <p class="error">*입력하신 비밀번호가 틀립니다. 비밀번호를 확인하세요.</p>
                          <input type="password" placeholder="* 비밀번호 확인" class="txtBx" id="confirm_password" required onblur="checkPasswordMatch()">
                       <input type="text" placeholder="* 이름" class="txtBx" name="name" required>
                       <div class="birthBx">
                           <label for="birth">생년월일</label>
                           <input type="date" id="birth" class="txtBx" value='2000-01-01' name="birth">
                       </div>
                       <div class="doubleCheck">
                           <input type="number" placeholder="* 휴대폰번호" class="txtBx" name="phone" id="phone" required>
                           <button type="button" class="btn-border" onclick="checkPhone()">중복 확인</button>
                       </div>
                       <div class="doubleCheck">
                           <input type="text" placeholder="* 닉네임" class="txtBx" name="nickname" id="nickname" required>
                           <button type="button" class="btn-border" onclick="checkNickname()">중복 확인</button>
                       </div>               
                   </div>
                   <div class="inputBx">
                       <p class="noti">*선택 입력 사항</p>
                       <div class="imgBx">
                           <input type="file" id="file" name="profileimgf" accept=".jpg, .jpeg, .png" hidden>
                           <button type="button" class="btn-under" onclick="$('#file').click();">프로필 사진 선택</button>
                           <div class="fileName"><i>파일명이길어질때는말줄임표가나옵니다나옵니다나옵니다.png</i></div>
                       </div>
                   </div>
                   <button type="submit" id="joinBtn" class="btn-full" >회원가입</button>
               </form>
            </div>
         </div>
      </div>
   </div>

   <div class="dimmed" onclick="popAlertCheckHide()"></div>
   
   <!-- [D] 팝업 중복확인 -->
   <div class="popup pop-alert-check" data-html="../popup/pop-alert-check.html"></div>

   <script>
      function popAlertCheckShow(){
         $('.pop-alert-check').show();
         $('.dimmed').show();
      }
      function popAlertCheckHide(){
         $('.pop-alert-check').hide();
         $('.dimmed').hide();
      }
      
      var isEmailAvailable = false;
      var isPhoneAvailable = false;
      var isNicknameAvailable = false;

      // 비밀번호 확인이 일치하는지 확인하는 함수
      function checkPasswordMatch() {
          var password = document.getElementById("password").value;
          var confirmPassword = document.getElementById("confirm_password").value;
          
          // 비밀번호와 비밀번호 확인이 다를 경우 경고창 표시
          if (password !== confirmPassword) {
              // 비밀번호 확인 필드에 포커스를 맞추어 다시 입력하도록 유도
              document.getElementById("confirm_password").setCustomValidity("비밀번호가 일치하지 않습니다.");
          } else {
              document.getElementById("confirm_password").setCustomValidity('');
          }
      }

      // 이메일 중복 확인
      function checkEmail() {
          var email = document.getElementById("email").value;

          // 이메일 형식 정규 표현식
          var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

          // 이메일 형식이 올바른지 확인
          if (!emailPattern.test(email)) {
              alert("유효한 이메일 주소를 입력해주세요.");
              return; // 이메일 형식이 올바르지 않으면 함수 종료
          }

          // 이메일 형식이 올바르면 중복 확인 요청
          fetch('/check-email', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ email: email })
          })
          .then(function(response) {  
              return response.json();
          })
          .then(function(data) {  
              if (data.exists) {
                  alert("이미 존재하는 이메일입니다.");
                  isEmailAvailable = false; // 이메일 사용 불가
              } else {
                  alert("사용 가능한 이메일입니다.");
                  isEmailAvailable = true; // 이메일 사용 가능
              }
          });
      }

      // 전화번호 중복 확인
      function checkPhone() {
          var phone = document.getElementById("phone").value;
          fetch('/check-phone', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ phone: phone })
          })
          .then(response => response.json())
          .then(data => {
              if (data.exists) {
                  alert("이미 존재하는 전화번호입니다.");
                  isPhoneAvailable = false; // 전화번호 사용 불가
              } else {
                  alert("사용 가능한 전화번호입니다.");
                  isPhoneAvailable = true; // 전화번호 사용 가능
              }
          });
      }

      // 닉네임 중복 확인
      function checkNickname() {
          var nickname = document.getElementById("nickname").value;
          fetch('/check-nickname', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ nickname: nickname })
          })
          .then(response => response.json())
          .then(data => {
              if (data.exists) {
                  alert("이미 존재하는 닉네임입니다.");
                  isNicknameAvailable = false; // 닉네임 사용 불가
              } else {
                  alert("사용 가능한 닉네임입니다.");
                  isNicknameAvailable = true; // 닉네임 사용 가능
              }
          });
      }

      // 비밀번호 확인 후 폼 제출을 막고 비밀번호 확인을 체크하는 함수
      function validateForm(event) {
          var password = document.getElementById("password").value;
          var confirmPassword = document.getElementById("confirm_password").value;

          // 비밀번호와 비밀번호 확인이 일치하지 않으면 폼 제출을 막음
          if (password !== confirmPassword) {
              alert("비밀번호가 일치하지 않습니다. 회원가입을 진행할 수 없습니다.");
              event.preventDefault(); // 폼 제출을 막음
              return false; 
          }

          // 이메일, 전화번호, 닉네임 중복 확인 체크
          if (!isEmailAvailable) {
              alert("이메일 중복 확인을 먼저 해주세요.");
              event.preventDefault(); // 폼 제출을 막음
              return false;
          }

          if (!isPhoneAvailable) {
              alert("전화번호 중복 확인을 먼저 해주세요.");
              event.preventDefault(); 
              return false;
          }

          if (!isNicknameAvailable) {
              alert("닉네임 중복 확인을 먼저 해주세요.");
              event.preventDefault(); 
              return false;
          }

          return true; // 모든 조건이 만족되면 폼 제출 허용
      }
      
      
   </script>
</body>
</html>