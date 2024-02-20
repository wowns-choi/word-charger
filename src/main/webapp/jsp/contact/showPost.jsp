<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="stylesheet" href="../../css/board/showPost.css">
</head>
<body>


<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <div id="writing-area">
        <div id="page-title">
            ${findPost.title}
        </div>

        <div id="first">
            <!-- 비밀글로 할건지 여부 + 비밀번호 input, 등록 버튼 -->
            <div></div>
            <div>

                <div>
                </div>
            </div>
            <div>
                <div style="width:100%; ${show ? 'display:block;' : 'display:none;'} " id="hiddenPasswordInput">

                </div>
            </div>
            <div>
                <button type="submit" class="button-in-middle-container" id="writing-button" style="margin-top:0.3vh;"> 글 목록으로 </button>
            </div>
            <div></div>
        </div>

        <div id="second">
            <!-- 작성자(유저 아이디) + 제목 -->
            <div></div>
            <div>
                <div>
                작성자 : ${loginedMemberId.userId}
                </div>
                <div>
                </div>
            </div>
            <div></div>
        </div>

        <div id="third">
            <div></div>
            <div>
                <textarea path="content" style="width:100%; height:60%; border:1px solid lightgray;  resize: none;" readOnly>${findPost.content}</textarea>
            </div>
            <div></div>
        </div>
    </div>

    <!-- ---------------------------comment------------------------------ -->
    <div id="comment-container">

        <div id="comment-insert-form-div">
            <div></div>
            <div>
                <div id="comment-id-div">
                    <div>

                    </div>
                    <div>
                        ${loginedMemberId.userId}
                    </div>
                </div>
                <div id="comment-content-writing-div">
                    <form action="/insert-comment" method="post">
                    <textarea name="content" id="comment-textarea" style="resize:none;"></textarea>
                    <input type="hidden" name="postId" value="${findPost.id}"/>
                    <input type="hidden" name="memberId" value="${loginedMemberId.userId}"/>
                </div>
                <div id="comment-register-btn-div">
                    <div>
                        <button type="submit" class="button-register" id="comment-btn" style="margin-top:0.3vh;"> 등록 </button>
                    </div>
                    </form>
                </div>
            </div>
            <div></div>
        </div>

        <!-- 댓글 대댓글 표시하기 -->
        <div id="comment-reply-area">
            <div></div>
            <div >
                <c:forEach var="comment" items="${currentPagePosts}" >
                    <div style="width:100%; background-color: #ededed;">
                            <div>
                                <span>
                                ${comment.userId}
                                </span>
                            </div>
                            <div>
                                <div>
                                    ${comment.content}
                                </div>

                                <button class="click-me" >댓글 쓰기</button>
                                <div class="textarea-reply" style="display:none;">
                                    <form action="/reply-save" method="post">
                                        <textarea name="content"   style="width:60%; background-color: yellow; height: 11vh;"></textarea>
                                        <input type="hidden" name="postId" value="${findPost.id}">
                                        <input type="hidden" name="memberId" value="${loginedMemberId.id}">
                                        <input type="hidden" name="parentCommentId" value="${comment.id}">
                                        <button type="submit">등록</button>
                                    </form>
                                </div>


                                <c:forEach var="reply" items="${comment.replies}">
                                    <div style="height:10px; width: 50%; background-color: #ededed;"></div>

                                    <div class="reply">
                                            ${reply.userId}
                                    </div>
                                    <div class="reply">
                                            ${reply.content}
                                    </div>
                                </c:forEach>



                            </div>
                    </div>
                </c:forEach>
            </div>
            <div></div>

        </div>

        <!-- 페이지 네이션 -->
        <div></div>


    </div>



<script>
// 모든 'click-me' 버튼에 대해 이벤트 리스너를 설정합니다.
document.querySelectorAll('.click-me').forEach(function(button) {
    button.addEventListener('click', function() {
        // 현재 클릭된 버튼과 관련된 'textarea-reply' 요소만 선택합니다.
        // 이 예제에서는 버튼이 'textarea-reply' 요소 바로 전에 있다고 가정합니다.
        let textareaReply = this.nextElementSibling;

        // 'textarea-reply' 요소의 display 상태를 토글합니다.
        textareaReply.style.display = textareaReply.style.display === 'block' ? 'none' : 'block';
    });
});


</script>

</body>
</html>