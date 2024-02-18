<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/jsp/bootstrapconfig/index.jsp"/>

<html>
<head>
    <link rel="stylesheet" href="../../css/charger/chargerHome.css">
</head>
<body >

<!--네브 바 -->
    <c:import url="/jsp/common/loginedNavbar2.jsp" />
<!--네브 바 종료 -->

    <div id="blue-container">
        <div id="inner-container">
            <div style="font-size: 50px;">Charge Your Brain</div>
            <div style="font-size: 30px; ">최대한 <span style="color:#e06500;">&#34;빠르게&#34;</span> 문제를 풀어주세요.</div>
            <div style="border-bottom:1px solid black; width:60%; margin-top:1vh; margin-bottom:1vh;"></div>
            <div style="font-size: 12px;">수학문제는 깊게 고민하면 풀 수 있는 방법이 생각날지도 모릅니다.</div>
            <div style="font-size: 12px;">그러나, 모르는 영어단어를 5분동안 고민하면 갑자기 생각날까요?</div>
            <div style="font-size: 12px;">최대한 빠르게, 모르면 다시 반복한다는 생각으로 풀어주세요.</div>
            <div id="btn-container">
                <div id="top-area">
                    <div class="card">
                        <a href="/get-words-for-study?startWordId=1&endWordId=2">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${a} 개
                            </div>
                        </a>
                        <div>1~100</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${b} 개
                            </div>
                        </a>
                        <div>101~200</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${c} 개
                            </div>
                        </a>
                        <div>201~300</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${d} 개
                            </div>
                        </a>
                        <div>301~400</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${e} 개
                            </div>
                        </a>
                        <div>401~500</div>
                    </div>
                </div>
                <div id="bottom-area">
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${f} 개
                            </div>
                        </a>
                        <div>501~600</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${g} 개
                            </div>
                        </a>
                        <div>601~700</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${h} 개
                            </div>
                        </a>
                        <div>701~800</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${i} 개
                            </div>
                        </a>
                        <div>801~900</div>
                    </div>
                    <div class="card">
                        <a href="/home">
                            <div>오늘 외워야할 단어</div>
                            <div>
                                ${j} 개
                            </div>
                        </a>
                        <div>901~1000</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>