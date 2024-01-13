<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>
<!DOCTYPE html>
<html>
<head>
    <title>Title of the page</title>
    <!-- 여기에 CSS 파일 또는 스타일 정의 -->
    <style>
        .full-container{
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #696969;
            height: 100vh;
        }
        .terms-of-use-container{
            width: 35vw;
            height: 70vh;
            background-color: #fff;
            border-radius: 10px;
            padding-left: 20px;
            padding-right: 20px;
            padding-top: 20px;
            padding-bottom: 20px;

            /* flex-box */
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
        }

        input[type="checkbox"]{
            display: none;
        }

        input[type="checkbox"].custom-checkbox + label {
          /* 사용자 정의 스타일 */
          position: relative;
          padding-left: 35px; /* checkbox 와 label 사이의 공간 */
          cursor: pointer;
        }

        input[type="checkbox"].custom-checkbox + label:before {
          /* 체크박스 모양 정의 */
          content: '';
          position: absolute;
          left: 0;
          top: 50%;
          transform: translateY(-50%);
          width: 25px;
          height: 25px;
          border: 2px solid #ddd;
          box-sizing: border-box;
          background-color: #fff;
        }

        input[type="checkbox"].custom-checkbox:checked + label:before {
          /* 체크 표시 스타일 */
          background-color: blue; /* 배경 색상 */
        }

        input[type="checkbox"].custom-checkbox + label:hover:before {
          /* 호버 효과 */
          border-color: #999;
        }


        textarea{
            align-self: center;

        }

    </style>

</head>
<body>
    <div class="full-container">
        <div class="terms-of-use-container">


                <input type="checkbox" id="myCheckbox" class="custom-checkbox">
                <label for="myCheckbox"> 네, 동의합니다. </label>



                <textarea style="resize:none;" placeholder="내용을 입력해주세요" rows="5" cols="40"> </textarea>

        </div>
    </div>


</body>
</html>
