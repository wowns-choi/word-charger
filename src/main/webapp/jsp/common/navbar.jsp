<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <!-- ajax 자바스크립트 시작 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#myForm').submit(function(e) {
                e.preventDefault();

                var voca = $('[name="voca"]').val();

                $.ajax({
                    type: "POST",
                    url: "/findVoca",
                    data: { voca: voca },
                    success: function(response) {
                        $('#modalText').text(response.voca);
                        $('#correctAnswer').text(response.correct);
                        $('#myModal').show();
                    }
                });
            });

            $('.close').click(function() {
                $('#myModal').hide();
            });
        });
    </script>
    <!-- ajax 자바스크립트 종료 -->



    <!--단어충전소 글꼴-->
    <link href="https://fonts.googleapis.com/css2?family=Gugi&display=swap" rel="stylesheet">

    <!-- 영어 글꼴 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Josefin+Sans:wght@300;400&display=swap" rel="stylesheet">

    <style>
         /*이거 한글 글꼴임*/
         @font-face {
                 font-family: 'NanumSquareNeo-Variable';
                 src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
                 font-weight: normal;
                 font-style: normal;
         }




        .navbar{
            height: 6vh;
            border-bottom: 2px solid black;
            position: fixed;
            width: 100%;
        }
        .navbar-brand{
            font-family: 'Gugi', sans-serif;
            font-weight: bold;
        }
        .form-control{
            width : 13vw;
            height : 2 vh;
            margin-top : 15px;
            font-family: 'NanumSquareNeo-Variable';
            border: 2px solid black;
        }
        .btn-outline-success{
            color: #00143d;
            border-color: #00143d;
            margin-top: 15px;
            border-width: 2px;
            font-family: 'NanumSquareNeo-Variable';
        }
        .btn-outline-success:hover {
            color: #fff; /* 호버 상태의 텍스트 색상 */
            background-color: #00143d; /* 호버 상태의 배경 색상 */
            border-color: #00143d; /* 호버 상태의 테두리 색상 */
        }
        .nav-link, .dropdown-item{
            font-family: 'Josefin Sans', sans-serif;
        }
        .nav-link{
            margin-top: 15px;

        }
        .login-button{
            margin-left: 15px;
        }
        .btn-outline-primary{
            border: 2px solid black;
            font-family: 'Gugi', sans-serif;
            font-weight: bold;
        }


    </style>



</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">단어 충전소</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">

      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">Introduction</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Charger</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Contact
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="#">Action</a></li>
            <li><a class="dropdown-item" href="#">Another action</a></li>
            <li><a class="dropdown-item" href="#">Something else here</a></li>
            <li><hr class="dropdown-divider"></li>
          </ul>
        </li>
      </ul>

      <form href="/find" id="myForm" class="d-flex" role="search">
        <input name="voca" class="form-control me-2" type="search" placeholder="단어" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">검색</button>
      </form>

      <a href="/loginForm" class="login-button">  <button type="button" class="btn btn-outline-primary"> Login</button></a>



    </div>
  </div>
</nav>

    <!-- 모달창 시작 -->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <p id="modalText"></p>
                <p id="correctAnswer"></p>
            </div>
        </div>
    <!-- 모달창 종료 -->

    <!-- 부트스트랩 시작 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
    <!-- 부트스트랩 종료 -->

</body>
</html>


