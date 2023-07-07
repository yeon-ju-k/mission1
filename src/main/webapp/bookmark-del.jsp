<%@page import="org.example.ApiSet"%>
<%@page import="org.example.DB_CRUD.bDName"%>
<%@page import="org.example.DB_CRUD.Dbcrud"%>
<%@page import="java.util.*"%>
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
        // 삭제 버튼을 클릭하면 실행
        function bDDel(id) {

            if (id != '') {
                var result = confirm("삭제하시겠습니까?");

                if (result) {   // 확인을 누를 경우 = true

                    // AJAX 요청 생성
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/bD-del-servlet");  // 서버 요청을 처리할 서블릿 경로

                    // 서버 응답 처리
                    xhr.onreadystatechange = function() {   // function의 익명함수1
                        if ( xhr.readyState === 4 && xhr.status === 200 ) {
                            // 응답 받은 후의 동작 처리
                            alert("삭제되었습니다.");
                            window.location.href = 'http://localhost:8090/bookmark.jsp';  // 북마크 보기 페이지로 가기
                        }

                    };  // function 익명함수1 종료

                    // 요청 데이터 설정 (전달할 데이터)
                    var requestData = {
                        id : id
                    };

                    // 요청 데이터 전송 (전달할 데이터 requestData 전송)
                    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
                    xhr.send(JSON.stringify(requestData));

                }

            }
        }

    </script>

</head>
<body>

     <h1>북마크 삭제</h1>
            <a href="http://localhost:8090">홈</a> |
            <a href="http://localhost:8090/history.jsp">위치 히스토리 목록</a> |
            <a href="http://localhost:8090/getInfo.jsp">Open API 와이파이 정보 가져오기</a> |
            <a href="http://localhost:8090/bookmark.jsp">북마크 보기</a> |
            <a href="http://localhost:8090/bookmark-group.jsp">북마크 그룹 관리</a>
            <p></p>
            북마크를 삭제하시겠습니까?

     <!-- 세로 표 출력 -->

        <table class="styled-table">
         <thead>

            <%
                // 파라미터 값 받기
                String B_ID = request.getParameter("B_ID");

                // 값을 받아올 리스트 선언
                List<bDName> list = new ArrayList();

                // DB에서 출력값 받기
                Dbcrud dbcrud = new Dbcrud();
                list = dbcrud.bDList(B_ID);

                // 출력한 값을 각 변수에 저장
                String B_NAME = "";
                String B_WIFINAME = "";
                String B_IN_DATE = "";

                for ( bDName a : list ) {
                    B_NAME = a.getB_NAME();
                    B_WIFINAME = a.getB_WIFINAME();
                    B_IN_DATE = a.getB_IN_DATE();
                }
            %>

           <tr>
                <th>북마크 이름</th>
                <td><%= B_NAME %></td>
           </tr>
           <tr>
               <th>와이파이명</th>
               <td><%= B_WIFINAME %></td>
           </tr>
           <tr>
               <th>등록일자</th>
               <td><%= B_IN_DATE %></td>
           </tr>
           <tr>
              <td colspan='2' style='text-align:center;'>
                    <a href='http://localhost:8090/bookmark.jsp'>돌아가기</a> |
                    <input type='button' value='삭제' id='<% out.write(request.getParameter("B_ID")); %>' onclick='bDDel(this.id)'>
              </td>
          </tr>
         </thead>


</body>
</html>