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
    let nickname = naver_id_login.getProfileData('nickname');
    let email = naver_id_login.getProfileData('email');
    let id = naver_id_login.getProfileData('id');
    let phone = naver_id_login.getProfileData('phone');

    window.location.href = '/?name=' + name + "&nickname=" + nickname + "&email=" + email +"&id=" + id;


  }
</script>



</body>
</html>