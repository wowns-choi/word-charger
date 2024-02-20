<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="stylesheet" href="../../css/board/writingPage.css">
</head>
<body>


<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <div id="writing-area">
        <div id="page-title">
            글 작성
            <form:form modelAttribute="postGenerateDTO" method="post">

        </div>

        <div id="first">
            <!-- 비밀글로 할건지 여부 + 비밀번호 input, 등록 버튼 -->
            <div></div>
            <div>

                <div>
                    비밀글로 작성하기
                    <form:checkbox path="is_private" id="secretWritingCheckBox"/>
                </div>
            </div>
            <div>
                <div style="width:100%; ${show ? 'display:block;' : 'display:none;'} " id="hiddenPasswordInput">
                            비밀번호 :
                           <form:input type="password" path="postPassword" id="password-input"/>
                           <span style="color:#ff6b3f; background-color:#fff;" >${notInsertPassword}</span>
                </div>
            </div>
            <div>
                <button type="submit" class="button-in-middle-container" id="writing-button" style="margin-top:0.3vh;"> 글 작성 </button>
            </div>
            <div></div>
        </div>

        <div id="second">
            <!-- 작성자(유저 아이디) + 제목 -->
            <div></div>
            <div>
                <div>
                    작성자 : ${userId}
                    <form:hidden path="memberId" value="${id}"/>
                    <input type="hidden" name="userId" value="${userId}">
                </div>
                <div>
                    <form:input path="title" style="width:70%; height: 50%; border: 1px solid black;border-radius:5px;" placeholder="제목을 입력해주세요"/>
                    <span style="color:#ff6b3f; background-color:#fff;"><form:errors path="title" /></span>
                </div>
            </div>
            <div></div>
        </div>

        <div id="third">
            <div></div>
            <div>
                <form:textarea path="content" style="width:100%; height:60%; border:1px solid lightgray; border-radius:5px; resize: none;"/>
            </div>
            <div></div>
        </div>

        </form:form>
    </div>

<script>
    document.getElementById('secretWritingCheckBox').addEventListener('change', function(){
        var hiddenPasswordInput = document.getElementById('hiddenPasswordInput');
        if(this.checked){
            hiddenPasswordInput.style.display = 'block';
        } else{
            hiddenPasswordInput.style.display = 'none';
        }
    });
</script>
</body>
</html>