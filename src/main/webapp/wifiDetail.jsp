<%@page import="org.example.ApiSet"%>
<%@page import="org.example.DB_CRUD.bGName"%>
<%@page import="java.util.*"%>
<%@page import="org.example.DB_CRUD.Dbcrud"%>
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
        // 북마크 추가하기 버튼을 누르면 실행 되는 함수
        function bDInsert() {
            // bookmark_data 에 들어갈 변수 설정 (url 파라미터에서 파싱하기)
            var urlParams = new URLSearchParams(window.location.search);

            // 콤보박스에서 선택된 값 가져오기
            var combo = document.getElementById('bookmarkCombo');
            var comboOption = combo.options[combo.selectedIndex];

            var name = comboOption.value;
            var wifiName = urlParams.get('X_SWIFI_MAIN_NM');
            var num = urlParams.get('X_SWIFI_MGR_NO');
            var dist = urlParams.get('Dist');

            // AJAX
            if (name != "none") {
                var result = confirm("추가하시겠습니까?");

                if (result) {   // 확인을 누를 경우 = true

                    // AJAX 요청 생성
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/bd-insert-servlet");  // 서버 요청을 처리할 서블릿 경로

                    // 서버 응답 처리
                    xhr.onreadystatechange = function() {   // function의 익명함수1
                        if ( xhr.readyState === 4 && xhr.status === 200 ) {
                            // 응답 받은 후의 동작 처리
                            alert("저장되었습니다.");
                            location.reload();  // 현재 페이지 재 로딩
                        }

                    };  // function 익명함수1 종료

                    // 요청 데이터 설정 (전달할 데이터)
                    var requestData = {
                        name : name,
                        wifiName : wifiName,
                        num : num,
                        dist : dist
                    };

                    // 요청 데이터 전송 (전달할 데이터 requestData 전송)
                    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
                    xhr.send(JSON.stringify(requestData));

                }

            } else {
                alert('북마크 그룹 이름을 선택해주세요.');
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



     <!-- 북마크 그룹 선택 + 북마크 추가하기 버튼 -->
     <form name='myForm' action="" method="post" onsubmit="bDInsert();">
        <select id = "bookmarkCombo" name='bookmarkCombo'>
            <option value="none">북마크 그룹 이름 선택</option>
            <%
                // bookmark-data 테이블에 있는 북마크 그룹명을 모두 받아오기
                Dbcrud dbcrud = new Dbcrud();

                List<bGName> list = new ArrayList();
                list = dbcrud.bGList();

                for (bGName name : list) {

                    String a = name.getBG_NAME();

            %>
                    <!-- 콤보박스 목록에 넣기 -->
                    <option value="<%= a %>"><%= a %></option>

            <%  }  // for문 종료 %>

        </select>

            <!-- 북마크 추가하기 버튼 -->
            <input type='submit' value='북마크 추가하기' id='bDInsertBtn'>


     </form>



     <!-- 세로 표 출력 -->

        <table class="styled-table">
         <thead>

            <%
            // 파라미터 값 받기
            String Dist = request.getParameter("Dist");
            String X_SWIFI_MGR_NO = request.getParameter("X_SWIFI_MGR_NO");
            String X_SWIFI_WRDOFC = request.getParameter("X_SWIFI_WRDOFC");
            String X_SWIFI_MAIN_NM = request.getParameter("X_SWIFI_MAIN_NM");
            String X_SWIFI_ADRES1 = request.getParameter("X_SWIFI_ADRES1");
            String X_SWIFI_ADRES2 = request.getParameter("X_SWIFI_ADRES2");
            String X_SWIFI_INSTL_FLOOR = request.getParameter("X_SWIFI_INSTL_FLOOR");
            String X_SWIFI_INSTL_TY = request.getParameter("X_SWIFI_INSTL_TY");
            String X_SWIFI_INSTL_MBY = request.getParameter("X_SWIFI_INSTL_MBY");
            String X_SWIFI_SVC_SE = request.getParameter("X_SWIFI_SVC_SE");
            String X_SWIFI_CMCWR = request.getParameter("X_SWIFI_CMCWR");
            String X_SWIFI_CNSTC_YEAR = request.getParameter("X_SWIFI_CNSTC_YEAR");
            String X_SWIFI_INOUT_DOOR = request.getParameter("X_SWIFI_INOUT_DOOR");
            String X_SWIFI_REMARS3 = request.getParameter("X_SWIFI_REMARS3");
            String LNT = request.getParameter("LNT");
            String LAT = request.getParameter("LAT");
            String WORK_DTTM = request.getParameter("WORK_DTTM");
            %>

           <tr>
                <th>거리(km)</th>
                <td><%= Dist %></td>
           </tr>
           <tr>
               <th>관리번호</th>
               <td><%= X_SWIFI_MGR_NO %></td>
           </tr>
           <tr>
               <th>자치구</th>
               <td><%= X_SWIFI_WRDOFC %></td>
           </tr>
           <tr>
               <th>와이파이명</th>
               <td><%= X_SWIFI_MAIN_NM %></td>
           </tr>
           <tr>
               <th>도로명주소</th>
               <td><%= X_SWIFI_ADRES1 %></td>
           </tr>
           <tr>
               <th>상세주소</th>
               <td><%= X_SWIFI_ADRES2 %></td>
           </tr>
           <tr>
               <th>설치위치(층)</th>
               <td><%= X_SWIFI_INSTL_FLOOR %></td>
           </tr>
           <tr>
               <th>설치유형</th>
               <td><%= X_SWIFI_INSTL_TY %></td>
           </tr>
           <tr>
               <th>설치기관</th>
               <td><%= X_SWIFI_INSTL_MBY %></td>
           </tr>
           <tr>
               <th>서비스구분</th>
               <td><%= X_SWIFI_SVC_SE %></td>
           </tr>
           <tr>
               <th>망종류</th>
               <td><%= X_SWIFI_CMCWR %></td>
           </tr>
           <tr>
                <th>설치년도</th>
               <td><%= X_SWIFI_CNSTC_YEAR %></td>
           </tr>
           <tr>
                <th>실내외구분</th>
               <td><%= X_SWIFI_INOUT_DOOR %></td>
           </tr>
           <tr>
               <th>WIFI접속환경</th>
               <td><%= X_SWIFI_REMARS3 %></td>
           </tr>
           <tr>
                <th>X좌표</th>
               <td><%= LNT %></td>
           </tr>
           <tr>
                <th>Y좌표</th>
               <td><%= LAT %></td>
           </tr>
           <tr>
                <th>작업일자</th>
               <td><%= WORK_DTTM %></td>
           </tr>
         </thead>


</body>
</html>