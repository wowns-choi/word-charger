<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>


<html>
<head>
    <!-- naver 로그인 관련 -->
    <script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>



<script type="text/javascript">
  var naver_id_login = new naver_id_login("akLY3ixqz148IBJVFcFD", "http://localhost:8080/naver-callback");
  // 네이버 사용자 프로필 조회
  naver_id_login.get_naver_userprofile("naverSignInCallback()");
  // 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
  function naverSignInCallback() {
    let name = naver_id_login.getProfileData('name');
    let email = naver_id_login.getProfileData('email');
    let id = naver_id_login.getProfileData('id');

    fetch('/make-account', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        'name': name,
        'email': email,
        'id' : id,
      }),
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text();
    })
    .then(data => {
        if(data == 'createAccount' || data == 'alreadyExistsByNaver'){
          window.location.href = '/';
        } else if (data == 'alreadyExistByWordCharger') {
          alert('회원가입 이력이 확인되었습니다. 해당 아이디로 로그인해주세요.');
          window.location.href = '/login-form';
        }
    })
    .catch(error => console.error('There has been a problem with your fetch operation:', error));


  }
</script>



</body>
</html>