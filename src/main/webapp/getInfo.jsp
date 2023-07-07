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

</head>
<body>


        <!-- 동적 내용 - 데이터베이스에 API 정보 저장하기 -->
        <%

            ApiSet apiset = new ApiSet();
            int loadNum = 0;
             try {
                loadNum = apiset.insertData();
             } catch (IOException e) {
                e.printStackTrace();
             }

        %>

        <h1 style="text-align:center">
            <% out.print(loadNum); %>개의 WIFI 정보를 정상적으로 저장하였습니다.
        </h1>
        <center><a href="http://localhost:8090">홈으로 가기</a></center>

</body>
</html>

