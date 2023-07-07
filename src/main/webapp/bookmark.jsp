<%@page import="org.example.DB_CRUD.Dbcrud"%>
<%@page import="org.example.DB_CRUD.bDName"%>
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


</head>
<body>
    <h1>북마크 목록</h1>
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
                <th>ID</th><th>북마크 이름</th><th>와이파이명</th><th>등록일자</th><th>비고</th>
              </tr>
            </thead> <!-- 데이터 -->
            <tbody>

                <%
                    List<bDName> list = new ArrayList();

                    Dbcrud dbcrud = new Dbcrud();
                    list = dbcrud.bDList();   // 북마크 select 기능

                    if (list == null || list.isEmpty() ) {
                        out.write("<tr><td colspan='5' style='text-align:center;'><b>북마크를 추가한 후에 조회해 주세요.</b></td></tr>");
                    }

                    else {

                        for (bDName name : list) {
                            out.write("<tr>");
                            out.write("<td>" + name.getB_ID() + "</td>");
                            out.write("<td>" + name.getB_NAME() + "</td>");
                            out.write("<td><a href='http://localhost:8090/wifiinfo.jsp?X_SWIFI_MGR_NO=" + name.getX_SWIFI_MGR_NO() +
                                              "&DIST=" + name.getDIST() + "'>" + name.getB_WIFINAME() + "</a></td>");
                            out.write("<td>" + name.getB_IN_DATE() + "</td>");
                            out.write("<td><a href='http://localhost:8090/bookmark-del.jsp?B_ID=" + name.getB_ID() + "'> 삭제 </a></td>");
                            out.write("</tr>");
                        }
                    }

                %>


              </tbody>
            </table>

</body>
</html>