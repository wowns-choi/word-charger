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
                <a href="/board-home" class="button-in-middle-container" id="board-home-btn" style="margin-top:0.3vh;"> 글 목록으로 </a>
            </div>
            <div></div>
        </div>

        <div id="second">
            <!-- 작성자(유저 아이디) + 제목 -->
            <div></div>
            <div>
                <div>
                작성자 : ${findPost.userId}
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
                <div style="width: 100%; height: 10%;"></div>
                <div id="thumb-up-div"><span><i class="fa-solid fa-thumbs-up"></i> &nbsp 추천</span> &nbsp &nbsp <span id="like-number">${likeNumber}</span> </div>
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
            <div>
                <c:forEach var="comment" items="${currentPagePosts}" >
                    <div style="width:100%; margin-top: 1vh; border-top: 1px solid lightgray;">
                            <div>
                                <span style="font-weight: bold; font-size: 17px;">
                                    ${comment.userId}
                                </span>
                                <span style="font-size: 9px;">
                                   ${comment.stringCreateDate}
                                </span>
                            </div>
                            <div>

<!-------------------------------------------------------------------------------------------->
                                <!-- 조건문 추가: 로그인한 사용자가 댓글 작성자와 같다면 "삭제" 표시 -->
                                <c:if test="${loginedMemberId.userId == comment.userId}">
                                <div>
                                    <div style="font-size: 12px;" class="comment-content-div">
                                        ${comment.content}
                                    </div>
                                    <div style="display:none;">
                                        <form action="/update-comment" method="post" class="update-form">
                                            <textarea name="content" class="update-comment-textarea">${comment.content}</textarea>
                                            <input type="hidden" name="postId" value="${findPost.id}">
                                            <input type="hidden" name="commentId" value="${comment.id}" >
                                            <button class="update-btn">수정하기</button>
                                        </form>
                                    </div>

                                    <span type="button" style="color:red; font-size: 12px; cursor:pointer;" class="update-comment">수정</span>
                                    <span style="color:red; font-size: 12px; cursor:pointer;" class="delete-comment" data-comment-id="${comment.id}">삭제</span>
                                </div>
                                </c:if>
<!-------------------------------------------------------------------------------------------->
                                <button class="click-me" >댓글 쓰기</button>
                                <div class="textarea-reply" style="display:none;">
                                    <form action="/reply-save" method="post">
                                        <textarea name="content"   style="width:60%;  height: 11vh; resize:none;"></textarea>
                                        <input type="hidden" name="postId" value="${findPost.id}">
                                        <input type="hidden" name="memberId" value="${loginedMemberId.id}">
                                        <input type="hidden" name="parentCommentId" value="${comment.id}">
                                        <button type="submit" class="button-register">등록</button>
                                    </form>
                                </div>

                                <div style="width:100%; height: 1vh;"></div>

                                <c:forEach var="reply" items="${comment.replies}">

                                    <div class="reply" style="margin-left: 2vw; background-color: #fafafa; >
                                         &nbsp  <span style="font-weight: bold; font-size: 17px;">  ${reply.userId}</span> <span style="font-size: 9px; ">${reply.stringCreateDate}</span>
                                    </div>




<!-------------------------------------------------------------------------------------------->
                                <c:if test="${loginedMemberId.userId == reply.userId}">
                                    <div>
                                        <div class="reply" style="margin-left: 2vw; font-size: 12px; background-color: #fafafa; border-bottom:0.5px solid lightgray;">
                                             ${reply.content}
                                        </div>
                                        <div style="display:none;">
                                              <form action="/update-comment" method="post" class="update-form">
                                                  <textarea name="content" class="update-comment-textarea">${reply.content}</textarea>

                                                  <input type="hidden" name="commentId" value="${reply.id}" >
                                                  <button class="update-btn">수정하기</button>
                                              </form>
                                        </div>
                                        <span style="color:red; font-size: 12px; margin-left: 2vw; cursor:pointer;" class="update-reply">수정</span>
                                        <span style="color:red; font-size: 12px; cursor:pointer;" class="delete-comment" data-comment-id="${reply.id}">삭제</span>

                                    </div>
                                </c:if>
<!-------------------------------------------------------------------------------------------->

                                </c:forEach>

                            </div>


                    </div>
                </c:forEach>
            </div>


            <div>
            </div>
        </div>


        <!-- pagination -->
        <div >
        </div>


    </div>
    <div id="pagination">
        <c:if test="${currentGroupFirstPage != 1}">
            <a href="/show-writing?page=${currentGroupFirstPage-pageGroupSize}&postId=${findPost.id}"> &laquo; 이전</a>
        </c:if>
        &nbsp &nbsp

        <c:forEach var="i" begin="${currentGroupFirstPage}" end="${currentGroupLastPage}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span>${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="/show-writing?page=${i}&postId=${findPost.id}" style="color:black;">${i}</a>
                </c:otherwise>
            </c:choose>
            &nbsp
        </c:forEach>

        &nbsp &nbsp

        <c:if test="${currentGroupLastPage != totalPageCount}">
            <a href="/show-writing?page=${currentGroupLastPage + 1}&postId=${findPost.id}">다음 &raquo;</a>

        </c:if>
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

//추천 누르면, 숫자 올라가게 하면서, ajax 통신해서 db에 +1 증가시키기.
$(document).ready(function() {
    $("#thumb-up-div").click(function() {
        $.ajax({
            url: "/update-and-find-like-num",
            type: "POST",
            data: {
                postId : ${findPost.id}
            },
            success: function(response) {
                if(response.updatedLikeNumber == -1){
                    alert('좋아요 는 1번만 누르실 수 있습니다');
                }else{
                    $("#like-number").text(response.updatedLikeNumber);
                }

            },
            error: function(xhr, status, error) {
                console.error("Error: " + status + " - " + error);
            }
        });
    });
});

// [comment + reply] update form ajax
document.addEventListener("DOMContentLoaded", function(){
    //지금 form 이 하나가 아니므로, 반복을 돌리면서, form 이 submit 됬을 때를 생각.
    document.querySelectorAll('.update-form').forEach(function(element){
        element.addEventListener('submit', function(e){
            e.preventDefault();
            const formData = new FormData(element);

            //json 통신 시도.
            const data = {};
            formData.forEach( (value, key) => { //(key, value) 가 아님을 주의
                data[key] = value;
            });

            fetch('/update-comment', {
                method: 'POST',
                headers: {
                    'Content-Type' : 'application/json', //서버에 json 형식임을 알리는 것.
                },
                body: JSON.stringify(data), //직렬화된 JSON 문자열
            })
            .then(response =>{
                return response.json();
            })
            .then(data => {
                element.parentElement.style.display = 'none';
                element.parentElement.previousElementSibling.style.display = 'block';
                element.parentElement.previousElementSibling.innerText = data.content;
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
        });
    });
});



// [comment + reply] delete span ajax
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.delete-comment').forEach(function(element) {
        element.addEventListener('click', function() {
            let commentId= element.dataset.commentId;
            const data = {id : commentId};
            fetch('/delete-comment', {
                method: 'POST',
                headers: {
                    'Content-Type' : 'application/json',
                },
                body: JSON.stringify(data),
            })
            .then(response => {
                return response.json();
            })
            .then(data => {
                alert('댓글이 삭제되었습니다.');
                element.innerText = ' ';
                element.previousElementSibling.innerText = ' ';
                element.previousElementSibling.previousElementSibling.previousElementSibling.innerText = data.text;
            })
        });
    });
});

// [comment] 삭제된 댓글입니다 표시
document.querySelectorAll('.comment-content-div').forEach(comment => {
    const content = comment.innerText;
    if (content.trim() === "삭제된 댓글입니다.") {
        comment.nextElementSibling.nextElementSibling.innerText = ' ';
        comment.nextElementSibling.nextElementSibling.nextElementSibling.innerText = ' ';
    }
});

// [reply] 삭제된 댓글입니다 표시
document.querySelectorAll('.reply').forEach(comment => {
    const content = comment.innerText;
    if (content.trim() === "삭제된 댓글입니다.") {
        comment.nextElementSibling.nextElementSibling.innerText = ' ';
        comment.nextElementSibling.nextElementSibling.nextElementSibling.innerText = ' ';
    }
});

</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="../../js/contact/showPost.js"></script>

</body>
</html>