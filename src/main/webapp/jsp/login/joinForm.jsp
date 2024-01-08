<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Sofia&display=swap" rel="stylesheet">

    <style>
        /*이거 한글 글꼴임*/
        @font-face {
            font-family: 'NanumSquareNeo-Variable';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }

        /* right-WC 한글글꼴 */
        @font-face {
            font-family: 'MYYeongnamnu';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-02@1.0/MYYeongnamnu.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }

        .full-frame{
            width: 100vw;
            min-height: 100vh;
            display: flex;
            flex-direction: column; /* 요소들을 세로로 쌓기 위해 추가 */
            justify-content: center;
            align-items: center;
            text-align: center;
            background: #757575;

        }
        .content-container{
            width: 35vw;
            height: 80vh;
            background: #fff;
            border-radius: 10px; /* 여기를 추가하세요 */
            text-align:left;
            padding-left: 20px; /* 왼쪽 여유 공간 추가 */
            padding-top : 50px;

        }
        .inner-align{


        }


    </style>
</head>
<body>



    <div class="full-frame">
        <div class="content-container">
            <div class="inner-align">
            <form:form modelAttribute="memberDTO" method="post">
                <div>
                아이디
                </div>
                <form:input type="text" path="id" id="user-id"/>
                <div id="user-id-status" style="font-size:11px; color: red;">
                </div>

                <div>
                비밀번호
                </div>
                <form:input type="password" path="password" id="user-password"/>
                <div id="user-password-status" style="font-size:11px; color: red;">
                </div>
                <div>
                비밀번호: 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.
                </div>

                <div>
                이름
                </div>
                <form:input type="text" path="userName"/>
            </form:form>
            </div>
        </div>
    </div>









    <script>
        document.getElementById('user-id').addEventListener('input', function(){
            var userId = this.value;
            //Fetch API를 사용하여 서버에 비동기 요청을 보냄
            fetch('check-user-id', {
                method:'POST',
                body: JSON.stringify({userId: userId}),
                headers: {
                    'Content-Type' : 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                if(data.isAvailable){
                    document.getElementById('user-id-status').innerText='사용 가능한 아이디입니다';
                }else{
                    document.getElementById('user-id-status').innerText='이미 사용 중인 아이디입니다.';
                }
            });
        });




        document.getElementById('user-password').addEventListener('input', function(){
            var userPassword = this.value;
            fetch('check-user-password', {
                method: 'POST',
                body: JSON.stringify({userPassword: userPassword}),
                headers: {
                    'Content-Type' : 'application/json'
                }
            })
            .then(response => response.json())
            .then(data => {
                if(data.insufficientLength){
                    document.getElementById('user-password-status').innerText='8~16글자 사이어야 합니다.';
                }else if(data.englishNotIncluded){
                    document.getElementById('user-password-status').innerText='영문이 포함되지 않았습니다';
                }else if(data.specialCharactersNotIncluded){
                    document.getElementById('user-password-status').innerText='특수문자가 포함되지 않았습니다.';
                }else if(data.numberNotIncluded){
                    document.getElementById('user-password-status').innerText='숫자가 포함되지 않았습니다.';
                }else{
                    document.getElementById('user-password-status').innerText='사용가능한 비밀번호입니다.';
                }
            });



        });
    </script>



</body>
</html>