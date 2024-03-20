<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="stylesheet" href="../../css/board/writingPage.css">

    <script>
    document.addEventListener("DOMContentLoaded", function(){

    let updateForm = document.querySelector('#update-form');
        updateForm.addEventListener('submit', function(e){
            e.preventDefault();

            //1. 만약 체크박스가 체크되어 있는데, 공백이라면 return 해서 끝낼것.
                let checkbox = document.querySelector('#secretWritingCheckBox');
                console.log(checkbox.checked); //체크되어있으면 true 를, 아니면 false를 반환하는 걸 확인.
                let password = document.querySelector('#password-input');

                if(checkbox.checked){
                    // 만약 체크박스가 체크되어 있는데,
                    if(password.value.trim() == ''){
                        // 공백이라면, return 해서 끝냄.
                        alert('비밀번호가 공백입니다.');
                        return;
                    }
                }

            //2. 체크박스가 체크되어 있지 않거나, 체크되어 있더라도 비밀번호가 작성되어 있는 경우
                const formData = new FormData(updateForm);
                // FormData를 JSON으로 변환
                const data = {};
                formData.forEach((value, key) => {
                    data[key] = value;
                });

                fetch('/update-post', {
                    method:'POST',
                    headers: {
                        'Content-Type' : 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                .then(response => response.text())
                .then(data => {
                    console.log(data)
                    window.location.href="/show-writing?postId=${post.id}";
                })
                .catch(error => console.error('Error:', error));

        });
    })
    </script>

</head>
<body>


<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <div id="writing-area">
        <div id="page-title">
            글 수정
            <form  method="post" id="update-form">
        </div>

        <div id="first">
            <!-- 비밀글로 할건지 여부 + 비밀번호 input, 등록 버튼 -->
            <div></div>
            <div>
                <div>
                    비밀글로 작성하기
                    <input type="checkbox"  name="is_private_string" id="secretWritingCheckBox" ${post.isPrivate == 1 ? 'checked' : ''} />
                </div>
            </div>
            <div>
                <div style="width:100%; ${show ? 'display:block;' : 'display:none;'} " id="hiddenPasswordInput">
                            비밀번호 :
                            <input type="hidden" name="id" value="${post.id}">
                           <input type="password" name="postPassword" id="password-input" value="${post.postPassword}">
                           <span style="color:#ff6b3f; background-color:#fff;" >${notInsertPassword}</span>
                </div>
            </div>
            <div>
                <button type="submit" class="button-in-middle-container" id="writing-button" style="margin-top:0.3vh;"> 글 수정 </button>
            </div>
            <div></div>
        </div>

        <div id="second">
            <!-- 작성자(유저 아이디) + 제목 -->
            <div></div>
            <div>
                <div>
                    작성자 : ${userId}
                    <input type="hidden" name="memberId" value="${id}"/>
                    <input type="hidden" name="userId" value="${userId}">
                </div>
                <div>
                    <input type="text" name="title" value="${post.title}" style="width:70%; height: 50%; border: 1px solid black;border-radius:5px;" placeholder="제목을 입력해주세요"/>
                    <span style="color:#ff6b3f; background-color:#fff;"></span>
                </div>
            </div>
            <div></div>
        </div>

        <div id="third">
            <div></div>
            <div>
                <textarea name="content"  style="width:100%; height:60%; border:1px solid lightgray; border-radius:5px; resize: none;">${post.content}</textarea>
            </div>
            <div></div>
        </div>

        </form>
    </div>













<script>
    document.getElementById('secretWritingCheckBox').addEventListener('change', function(){
        var hiddenPasswordInput = document.getElementById('hiddenPasswordInput');
        if(this.checked){
            hiddenPasswordInput.style.display = 'block';
        } else{
            let passwordInput = document.querySelector('#password-input');
            passwordInput.value = '';
            hiddenPasswordInput.style.display = 'none';

        }
    });
</script>
</body>
</html>