<%@page import="org.example.ApiSet"%>
<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>와이파이 정보 구하기</title>
    <h1 style="text-align:center">

     <style>

            .styled-table {
                border-collapse: collapse;
                margin: 25px 0;
                font-size: 0.9em;
                font-family: sans-serif;
                min-width: 80%;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
            }
            .styled-table thead tr {
                background-color: #009879;
                color: #ffffff;
                text-align: center;
                border: 1px solid #FFFFF0;
            }

            .styled-table thead td {
                border-bottom: 1px solid #dddddd;
                color: #000000;
                text-align: left;
                background-color: #f3f3f3;
            }

            .styled-table th,
            .styled-table td {
                padding: 12px 15px;
            }


     </style>

    <script>

        var nameValue = "";
        var priValue = "";

        // 수정 버튼을 클릭하면 해당 함수 실행!
        function bGUpdate() {

            var name = document.getElementById('BG_NAME').value;
            var pri = document.getElementById('BG_PRI').value;

            if (name != '' && pri != '') {
                var result = confirm("수정하시겠습니까?");

                if (result) {   // 확인을 누를 경우 = true

                    // AJAX 요청 생성
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/bg-update-servlet");  // 서버 요청을 처리할 서블릿 경로

                    // 서버 응답 처리
                    xhr.onreadystatechange = function() {   // function의 익명함수1
                        if ( xhr.readyState === 4 && xhr.status === 200 ) {
                            // 응답 받은 후의 동작 처리
                            alert("수정되었습니다.");
                            window.location.href = document.referrer; // 이전 페이지주소로 이동

                        }

                    };  // function 익명함수1 종료

                    // 요청 데이터 설정 (전달할 데이터)
                    var requestData = {
                        newName : name,
                        newPri : pri,
                        oldName : nameValue,
                        oldPri : priValue
                    };

                    // 요청 데이터 전송 (전달할 데이터 requestData 전송)
                    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
                    xhr.send(JSON.stringify(requestData));

                }

            } else {
                alert('수정할 이름과 순서를 입력해주세요.');
            }
        }



        // 수정 전 값을 input text 상자에 불러오기
        //     ㄴ DOMContentLoaded (= 페이지의 컨텐츠가 전부 로딩되었을 때)
        document.addEventListener("DOMContentLoaded", function() {      // function의 익명함수

            // input text 상자에 넣을 변수를 url 파라미터에서 파싱하기
            var urlParams = new URLSearchParams(window.location.search);
            nameValue = urlParams.get('BG_NAME');
            priValue = urlParams.get('BG_PRI');

            // input text 상자에 변수 넣기
            document.getElementById('BG_NAME').value = nameValue;
            document.getElementById('BG_PRI').value = priValue;

        });



    </script>

</head>
<body>

     <h1>와이파이 정보 구하기</h1>
            <a href="http://localhost:8090">홈</a> |
            <a href="http://localhost:8090/history.jsp">위치 히스토리 목록</a> |
            <a href="http://localhost:8090/getInfo.jsp">Open API 와이파이 정보 가져오기</a> |
            <a href="http://localhost:8090/bookmark.jsp">북마크 보기</a> |
            <a href="http://localhost:8090/bookmark-group.jsp">북마크 그룹 관리</a>
            <p></p>

     <!-- 북마크 그룹 선택 + 북마크 추가하기 버튼 -->

        <!-- 세로 표 출력 -->
            <table class="styled-table">
             <thead>

                <!-- form 설명 : onsubmit 이벤트 핸들러에서 envent.preventDefault()를 호출해서 기본 제출 동작을 막고,
                                대신 자바스크립트의 bGInsert() 함수를 호출로 제출하는 방식으로 처리! -->
                <form action="" method="post" onsubmit="event.preventDefault(); bGUpdate();">

                   <tr>
                        <th>북마크 이름</th>
                        <td><input type="text" id="BG_NAME" name="BG_NAME"></td>
                   </tr>
                   <tr>
                       <th>순서</th>
                       <td><input type="text" id="BG_PRI" name="BG_PRI"></td>
                   </tr>
                   <tr>
                        <td colspan='2' style='text-align:center'>
                        <a href="javascript:history.back()">돌아가기</a> |
                        <input type="submit" value="수정" id="bGUpBtn"></td>
                   </tr>

                </form>

             </thead>



</body>
</html>