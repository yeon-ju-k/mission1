<%@page import="org.example.ApiSet"%>
<%@page import="org.example.DB_CRUD.WifiList"%>
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

</head>
<body>

     <h1>북마크 삭제</h1>
            <a href="http://localhost:8090">홈</a> |
            <a href="http://localhost:8090/history.jsp">위치 히스토리 목록</a> |
            <a href="http://localhost:8090/getInfo.jsp">Open API 와이파이 정보 가져오기</a> |
            <a href="http://localhost:8090/bookmark.jsp">북마크 보기</a> |
            <a href="http://localhost:8090/bookmark-group.jsp">북마크 그룹 관리</a>
            <p></p>


     <!-- 세로 표 출력 -->

        <table class="styled-table">
         <thead>

            <%
                // 파라미터 값 받기
                String Dist = request.getParameter("DIST");
                String X_SWIFI_MGR_NO = request.getParameter("X_SWIFI_MGR_NO");

                // 값을 받아올 리스트 선언
                List<WifiList> list = new ArrayList();

                // DB에서 출력값 받기
                Dbcrud dbcrud = new Dbcrud();
                list = dbcrud.bDPublicList(X_SWIFI_MGR_NO);

                // 출력한 값을 각 변수에 저장
                String X_SWIFI_WRDOFC = "";
                String X_SWIFI_MAIN_NM = "";
                String X_SWIFI_ADRES1 = "";
                String X_SWIFI_ADRES2 = "";
                String X_SWIFI_INSTL_FLOOR = "";
                String X_SWIFI_INSTL_TY = "";
                String X_SWIFI_INSTL_MBY = "";
                String X_SWIFI_SVC_SE = "";
                String X_SWIFI_CMCWR = "";
                String X_SWIFI_CNSTC_YEAR = "";
                String X_SWIFI_INOUT_DOOR = "";
                String X_SWIFI_REMARS3 = "";
                String LAT = "";
                String LNT = "";
                String WORK_DTTM = "";

                for ( WifiList wifiList : list ) {
                    X_SWIFI_WRDOFC = wifiList.getX_SWIFI_WRDOFC();
                    X_SWIFI_MAIN_NM = wifiList.getX_SWIFI_MAIN_NM();
                    X_SWIFI_ADRES1 = wifiList.getX_SWIFI_ADRES1();
                    X_SWIFI_ADRES2 = wifiList.getX_SWIFI_ADRES2();
                    X_SWIFI_INSTL_FLOOR = wifiList.getX_SWIFI_INSTL_FLOOR();
                    X_SWIFI_INSTL_TY = wifiList.getX_SWIFI_INSTL_TY();
                    X_SWIFI_INSTL_MBY = wifiList.getX_SWIFI_INSTL_MBY();
                    X_SWIFI_SVC_SE = wifiList.getX_SWIFI_SVC_SE();
                    X_SWIFI_CMCWR = wifiList.getX_SWIFI_CMCWR();
                    X_SWIFI_CNSTC_YEAR = wifiList.getX_SWIFI_CNSTC_YEAR();
                    X_SWIFI_INOUT_DOOR = wifiList.getX_SWIFI_INOUT_DOOR();
                    X_SWIFI_REMARS3 = wifiList.getX_SWIFI_REMARS3();
                    LAT = wifiList.getLAT();
                    LNT = wifiList.getLNT();
                    WORK_DTTM = wifiList.getWORK_DTTM();
                }
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