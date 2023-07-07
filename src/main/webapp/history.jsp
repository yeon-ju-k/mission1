<%@page import="org.example.DB_CRUD.HistoryName"%>
<%@page import="org.example.DB_CRUD.Dbcrud"%>
<%@page import="org.example.DB_CRUD.WifiList"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>와이파이 정보 구하기</title>

     <!-- 테이블 스타일 -->
     <style>

            .styled-table {
                border-collapse: collapse;
                margin: 25px 0;
                font-size: 0.9em;
                font-family: sans-serif;
                min-width: 1000px;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
            }
            .styled-table thead tr {
                background-color: #009879;
                color: #ffffff;
                text-align: left;
            }
            .styled-table th,
            .styled-table td {
                padding: 12px 15px;
            }
            .styled-table tbody tr {
                border-bottom: 1px solid #dddddd;
            }

            .styled-table tbody tr:nth-child(2n) {
                background-color: #f3f3f3;
            }

            .styled-table tbody tr:last-of-type {
                border-bottom: 2px solid #009879;
            }
            .styled-table tbody tr:nth-child(2n+1) {
                font-weight: bold;
                color: #009879;
                background-color: #f3f3f3;
            }

     </style>

     <script>

        // 삭제버튼을 클릭하면 해당 함수 실행!
        function deleteRow(buttonId) {
            var result = confirm("삭제하시겠습니까?");

            if (result) {   // 확인을 누를 경우 = true

                // AJAX 요청 생성
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/my-servlet");  // 서버 요청을 처리할 서블릿 경로

                // 서버 응답 처리
                xhr.onreadystatechange = function() {   // function의 익명함수1
                    if ( xhr.readyState === 4 && xhr.status === 200 ) {
                        // 응답 받은 후의 동작 처리
                        alert("삭제되었습니다.");
                        location.reload();  // 현재 페이지 재 로딩
                    }

                };  // function 익명함수1 종료

                // 요청 데이터 설정 (전달할 데이터)
                var requestData = {
                    data : buttonId
                };

                // 요청 데이터 전송 (전달할 데이터 requestData 전송)
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(JSON.stringify(requestData));

            }
        }

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

    <!-- 테이블 구성 -->
       <table class="styled-table">
        <thead> <!-- 표 컬럼 -->
          <tr>
            <th>ID</th><th>X좌표</th><th>Y좌표</th><th>조회일자</th><th>비고</th>
          </tr>
        </thead> <!-- 데이터 -->
        <tbody>

            <%

                Dbcrud dbcrud = new Dbcrud();

                // 위치 히스토리 목록이 없을 때
                String tableName = "HISTORY";
                if (dbcrud.totalCount(tableName) == 0) {
                    out.write("<tr><td colspan='5' style='text-align:center;'><b>위치 히스토리 목록이 없습니다.</b></td></tr>");
                }

                else {
                    // 위치 히스토리 목록 출력하기
                    List<HistoryName> list = new ArrayList<>();
                    list = dbcrud.historyList();
                    int idx = 1;
                    String a = "";

                    for (HistoryName hisName : list) {    // 표에 데이터 출력
                        if (idx == 2) {
                            a = " class='active-row'";
                        }

                        out.write("<tr"+a+">");  // 한줄 값 시작
                        out.write("<td>" + hisName.getH_ID() + "</td>");
                        out.write("<td>" + hisName.getMY_X() + "</td>");
                        out.write("<td>" + hisName.getMY_Y() + "</td>");
                        out.write("<td>" + hisName.getHISTORY_DATE() + "</td>");

                        // 삭제 버튼 ( 삭제버튼의 id를 H_ID 값으로 해서 삭제할 데이터 찾기 - 클릭하면 해당 버튼의 id 값을 clickedButtonId 변수에 저장 )
                        out.write("<td>" + "<input type='button' value='삭제' id='" + hisName.getH_ID() + "'" +
                                    "onclick='deleteRow(this.id)'>" + "</td>");
                        out.write("</tr>");  // 한줄 값 종료

                        a ="";
                    }

                }

            %>


          </tbody>
        </table>

</body>
</html>

