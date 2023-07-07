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
    <h1>와이파이 정보 구하기</h1>
        <a href="http://localhost:8090">홈</a> |
        <a href="http://localhost:8090/history.jsp">위치 히스토리 목록</a> |
        <a href="http://localhost:8090/getInfo.jsp">Open API 와이파이 정보 가져오기</a> |
        <a href="http://localhost:8090/bookmark.jsp">북마크 보기</a> |
        <a href="http://localhost:8090/bookmark-group.jsp">북마크 그룹 관리</a>
        <p></p>

        <!-- 현재 파일로 전달 -->
        <form action="" method="post">
        LAT : <input type="text" id="yLat" name="yLat"> ,
        LNT : <input type="text" id="xLnt" name="xLnt">

        <input type="button" value="내 위치 가져오기" id="getLocationBtn">

        <!-- 내 위치 가져오기 버튼으로 받은 x,y 좌표를 전달 -->
        <input type="submit" value="근처 WIFI 정보 보기" id="wifiInfoBtn">
        </form>

        <p></p>

        <script>    // 내 위치 정보 가져오기
            document.getElementById('getLocationBtn').addEventListener('click',function() {
            // function() 함수의 내용 (익명함수1) - 클릭하면 발생하는 함수
                // 위치 값이 있으면 가져오기
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition( function(position) {
                    // function(position)의 함수의 내용 (익명함수2) - 좌표를 받아왔을 때 발생하는 함수
                        // 위도와 경도 값 가져오기
                        var latitude = position.coords.latitude;
                        var longitude = position.coords.longitude;

                        // 각각 x,y좌표를 입력하는 input 태그에 위치값 넣기
                        document.getElementById('yLat').value = latitude;
                        document.getElementById('xLnt').value = longitude;

                    }, function(error) {
                        // function(error)의 함수 내용 (익명함수3) - 예러처리
                        switch(error.code) {
                            case error.PERMISSION_DENIED:
                                alert('위치 정보에 대한 권한을 허용하지 않았습니다.');
                                break;
                            case error.POSITION.UNAVAILABLE:
                                alert('위치 정보를 사용할 수 없습니다.');
                                break;
                            case error.TIMEOUT:
                                alert('위치 정보 요청 시간이 초과되었습니다.');
                                break;
                            case error.UNKNOWN_ERROR:
                                alert('알 수 없는 오류가 발생했습니다.');
                                break;
                        }   // 익명함수2종료

                    });
                } else {
                    alert('브라우저에서 위치 정보를 지원하지 않습니다.');
                }

            });   // 익명함수1종료

        </script>


          <!-- 테이블 구성 -->
           <table class="styled-table">
            <thead> <!-- 표 컬럼 -->
              <tr>
                <th>거리(km)</th><th>관리번호</th><th>자치구</th><th>와이파이명</th><th>도로명주소</th>
                <th>상세주소</th><th>설치위치(층)</th><th>설치유형</th><th>설치기관</th><th>서비스구분</th><th>망종류</th>
                <th>설치년도</th><th>실내외구분</th><th>WIFI접속환경</th><th>X좌표</th><th>Y좌표</th><th>작업일자</th>
              </tr>
            </thead> <!-- 데이터 -->
            <tbody>

                <%

                    // 내 위치 좌표 넘겨 받기
                    String lat = request.getParameter("yLat");  // 위도
                    String lnt = request.getParameter("xLnt");  // 경도

                    if (lat == null && lnt == null) {
                        out.write("<tr><td colspan='17' style='text-align:center;'><b>위치 정보를 입력한 후에 조회해 주세요.</b></td></tr>");
                    }


                    if (lat != null && lnt != null) {

                        // 검색한 현재 좌표를 위치 히스토리 목록(HISTORY 테이블)에 저장
                        Dbcrud dbcrud = new Dbcrud();
                        dbcrud.historyInsert(lnt, lat);

                        // 내 위치 좌표와 와이파이 거리를 계산 + 근처의 값 20개만 출력
                        List<WifiList> list = new ArrayList<>();
                        list = dbcrud.selectNear(lat, lnt);
                        int idx = 0;
                        String a = "";
                        for (WifiList wifiList : list) {    // 표에 데이터 출력
                            if (idx == 2) {
                                a = " class='active-row'";
                            }
                            out.write("<tr"+a+">");  // 한줄 값 시작
                            out.write("<td>" + wifiList.getDist() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_MGR_NO() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_WRDOFC() + "</td>");
                            // 와이파이 정보 상세보기 (해당 테이터의 전체 컬럼값을 파라미터로 전달)
                            out.write("<td><a href='http://localhost:8090/wifiDetail.jsp?"
                                + "Dist=" + wifiList.getDist()
                                + "&X_SWIFI_MGR_NO=" + wifiList.getX_SWIFI_MGR_NO()
                                + "&X_SWIFI_WRDOFC=" + wifiList.getX_SWIFI_WRDOFC()
                                + "&X_SWIFI_MAIN_NM=" + wifiList.getX_SWIFI_MAIN_NM()
                                + "&X_SWIFI_ADRES1=" + wifiList.getX_SWIFI_ADRES1()
                                + "&X_SWIFI_ADRES2=" + wifiList.getX_SWIFI_ADRES2()
                                + "&X_SWIFI_INSTL_FLOOR=" + wifiList.getX_SWIFI_INSTL_FLOOR()
                                + "&X_SWIFI_INSTL_TY=" + wifiList.getX_SWIFI_INSTL_TY()
                                + "&X_SWIFI_INSTL_MBY=" + wifiList.getX_SWIFI_INSTL_MBY()
                                + "&X_SWIFI_SVC_SE=" + wifiList.getX_SWIFI_SVC_SE()
                                + "&X_SWIFI_CMCWR=" + wifiList.getX_SWIFI_CMCWR()
                                + "&X_SWIFI_CNSTC_YEAR=" + wifiList.getX_SWIFI_CNSTC_YEAR()
                                + "&X_SWIFI_INOUT_DOOR=" + wifiList.getX_SWIFI_INOUT_DOOR()
                                + "&X_SWIFI_REMARS3=" + wifiList.getX_SWIFI_REMARS3()
                                + "&LNT=" + wifiList.getLNT()
                                + "&LAT=" + wifiList.getLAT()
                                + "&WORK_DTTM=" + wifiList.getWORK_DTTM()
                                + "'>" + wifiList.getX_SWIFI_MAIN_NM() + "</a></td>");

                            out.write("<td>" + wifiList.getX_SWIFI_ADRES1() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_ADRES2() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_INSTL_FLOOR() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_INSTL_TY() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_INSTL_MBY() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_SVC_SE() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_CMCWR() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_CNSTC_YEAR() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_INOUT_DOOR() + "</td>");
                            out.write("<td>" + wifiList.getX_SWIFI_REMARS3() + "</td>");
                            out.write("<td>" + wifiList.getLNT() + "</td>");
                            out.write("<td>" + wifiList.getLAT() + "</td>");
                            out.write("<td>" + wifiList.getWORK_DTTM() + "</td>");
                            out.write("</tr>");  // 한줄 값 종료

                            a ="";
                        }
                    }
                %>


              </tbody>
            </table>

</body>
</html>