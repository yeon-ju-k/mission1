<%@page import="org.example.DB_CRUD.Dbcrud"%>
<%@page import="org.example.DB_CRUD.bGName"%>
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
                min-width: 80%;
                box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
            }
            .styled-table thead tr {
                background-color: #009879;
                color: #ffffff;
                text-align: left;
                border: 1px solid #FFFFF0;
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
                color: #009879;
                background-color: #f3f3f3;
            }

     </style>

     <script>

         // 삭제버튼을 클릭하면 해당 함수 실행!
                function bGDel(pri) {

                    var result = confirm("삭제하시겠습니까?");

                    if (result) {   // 확인을 누를 경우 = true

                        // AJAX 요청 생성
                        var xhr = new XMLHttpRequest();
                        xhr.open("POST", "/bg-del-servlet");  // 서버 요청을 처리할 서블릿 경로

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
                            data : pri
                        };

                        // 요청 데이터 전송 (전달할 데이터 requestData 전송)
                        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
                        xhr.send(JSON.stringify(requestData));

                    }
                }


     </script>

</head>
<body>
    <h1>북마크 그룹</h1>
        <a href="http://localhost:8090">홈</a> |
        <a href="http://localhost:8090/history.jsp">위치 히스토리 목록</a> |
        <a href="http://localhost:8090/getInfo.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="http://localhost:8090/bookmark.jsp">북마크 보기</a> |
        <a href="http://localhost:8090/bookmark-group.jsp">북마크 그룹 관리</a>
        <p></p>

        <input type="button" value="북마크 그룹 이름 추가" id="bGInsertBtn" +
            onclick="window.location.href = 'http://localhost:8090/bookmark-group-insert.jsp';">

          <!-- 테이블 구성 -->
           <table class="styled-table">
            <thead> <!-- 표 컬럼 -->
              <tr>
                <th>ID</th><th>북마크 이름</th><th>순서</th><th>등록일자</th><th>수정일자</th><th>비고</th>
              </tr>
            </thead> <!-- 데이터 -->
            <tbody>

                <%
                    List<bGName> list = new ArrayList();

                    Dbcrud dbcrud = new Dbcrud();
                    list = dbcrud.bGList();   // 북마크 그룹 select 기능

                    if (list == null || list.isEmpty() ) {
                        out.write("<tr><td colspan='6' style='text-align:center;'><b>북마크 그룹을 추가한 후에 조회해 주세요.</b></td></tr>");
                    }

                    else {
                        for (bGName name : list) {
                            out.write("<tr>");
                            out.write("<td>" + name.getBG_ID() + "</td>");
                            out.write("<td>" + name.getBG_NAME() + "</td>");
                            out.write("<td>" + name.getBG_PRI() + "</td>");
                            out.write("<td>" + name.getBG_IN_DATE() + "</td>");
                            out.write("<td>" + name.getBG_UPDATE() + "</td>");
                            out.write("<td>");
                            out.write("<input type='button' value='수정' id='bGUpdateBtn'" +
                                        " onclick=\"location.href='http://localhost:8090/bookmark-group-up.jsp?BG_NAME=' + encodeURIComponent('" + name.getBG_NAME() + "') + '&BG_PRI=' + encodeURIComponent('" + name.getBG_PRI() + "')\">"
                                            + "<input type='button' value='삭제' id='bGDelBtn' onclick=\"bGDel('" + name.getBG_PRI() + "')\"></td>");
                            out.write("</tr>");
                        }
                    }
                %>


              </tbody>
            </table>

</body>
</html>