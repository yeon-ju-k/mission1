package org.example;

import org.example.DB_CRUD.Dbcrud;
import org.example.DB_CRUD.WifiList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


public class ApiSet {

    /*
         # 1. API 데이터 총 개수 받아오기 (결과값은 데이터의 총개수 출력하게 설정)
                ㄴ 총 개수인 "list_total_count" 값만 불러오기때문에 가져올 데이터는 1개만 가져오기
     */
    public int getTotal() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("4d516e4d6379656f36334b4d475076","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.


        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);

        }
        rd.close();
        conn.disconnect();


        // 총 데이터 개수 값 파싱
        // ㄴ JSONObject 는 HashMap 타입
        int maxValue = 0;   // 리턴 값
        String num = "";    // 받아온 문자열을 저장할 변수

        try {
            JSONObject result = (JSONObject) new JSONParser().parse(sb.toString());
            JSONObject max = (JSONObject) result.get("TbPublicWifiInfo");
            num = max.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 총 개수의 끝 인덱스 번호 구하기
        int numIdx = -1;
        for (int i = 0; i < 30; i++) {
            if (num.charAt(i) == ','){
                numIdx = i;
                break;
            }
        }

        maxValue = Integer.parseInt(num.toString().substring(20,numIdx));

        return maxValue;
    }


    /*
        # 2. API 데이터 받아서 + DB저장
    */
    public int insertData() throws IOException {

        // 총 데이터 개수 저장
        int totalCount = getTotal();
        int idx = 1;     // 요청 시작 위치
        int loadNum = 0;    // 저장한 데이터 행의 수

        // 데이터가 추가됐으면 -> 추가된 데이터만 저장한 뒤 종료
        String tableName = "PUBLIC_WIFI_INFO";
        int dbCount = Dbcrud.totalCount(tableName);
        if (totalCount - dbCount == 0){
            return totalCount;
        }

        if (totalCount != dbCount && totalCount > dbCount) {
            idx = totalCount - dbCount + 1;
            return updateData(idx, totalCount, dbCount);
        }

        // 한번 반복할 때마다 데이터 1000개씩 가져오기
        for (int i = 0; i < totalCount / 1000 + 1; i++) {

            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + URLEncoder.encode("4d516e4d6379656f36334b4d475076", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(idx), "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(idx+999), "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
            // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            //System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
            BufferedReader rd;

            // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            // json 파싱 - JSONParser().parse() 가 예외가 발생하기 때문에 -> 예외처리
            try {
                JSONObject result = (JSONObject) new JSONParser().parse(sb.toString());
                JSONObject resultIn = (JSONObject) result.get("TbPublicWifiInfo");
                JSONArray array = (JSONArray) resultIn.get("row");  // row안에 있는 데이터를 배열로 저장

                JSONObject rowData;     // row안의 데이터 한개를 저장 (하나씩 DB에 넣어야하기 때문에)

                for (int j = 0; j < array.size(); j++) {    // 배열의 개수만큼 반복 (데이터가 들은 개수많큼)
                    // DB CRUD 클래스 객체 선언
                    WifiList wifiList = new WifiList();
                    rowData = (JSONObject) array.get(j);    // 한줄씩 가져오기
                    System.out.println(rowData.toString());
                    // WifiList 클래스타입에 column의 값들 저장하기
                    wifiList.setX_SWIFI_MGR_NO((String) rowData.get("X_SWIFI_MGR_NO"));
                    wifiList.setX_SWIFI_WRDOFC((String) rowData.get("X_SWIFI_WRDOFC"));
                    wifiList.setX_SWIFI_MAIN_NM((String) rowData.get("X_SWIFI_MAIN_NM"));
                    wifiList.setX_SWIFI_ADRES1((String) rowData.get("X_SWIFI_ADRES1"));
                    wifiList.setX_SWIFI_ADRES2((String) rowData.get("X_SWIFI_ADRES2"));
                    wifiList.setX_SWIFI_INSTL_FLOOR((String) rowData.get("X_SWIFI_INSTL_FLOOR"));
                    wifiList.setX_SWIFI_INSTL_TY((String) rowData.get("X_SWIFI_INSTL_TY"));
                    wifiList.setX_SWIFI_INSTL_MBY((String) rowData.get("X_SWIFI_INSTL_MBY"));
                    wifiList.setX_SWIFI_SVC_SE((String) rowData.get("X_SWIFI_SVC_SE"));
                    wifiList.setX_SWIFI_CMCWR((String) rowData.get("X_SWIFI_CMCWR"));
                    wifiList.setX_SWIFI_CNSTC_YEAR((String) rowData.get("X_SWIFI_CNSTC_YEAR"));
                    wifiList.setX_SWIFI_INOUT_DOOR((String) rowData.get("X_SWIFI_INOUT_DOOR"));
                    wifiList.setX_SWIFI_REMARS3((String) rowData.get("X_SWIFI_REMARS3"));
                    wifiList.setLNT((String) rowData.get("LNT"));
                    wifiList.setLAT((String) rowData.get("LAT"));
                    wifiList.setWORK_DTTM((String) rowData.get("WORK_DTTM"));

                    // DB에 저장
                    Dbcrud dbcrud = new Dbcrud();
                    loadNum += dbcrud.dataInsert(wifiList);    // 위에서 wifiList타입으로 저장한 값을 DB에 추가하기

                }

            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                rd.close();
                conn.disconnect();
            }


            idx += 1000;
        }

        return totalCount;
    }


    /*
        # 2-1. 추가된 데이터만 받아서 저장
    */
    public int updateData(int idx, int totalCount, int dbCount) throws IOException {
        // 매개변수 ( idx = 시작인덱스 , totalCount = API 데이터 총개수 )

        int loadNum = 0;    // 저장한 데이터 행의 수


        // 한번 반복할 때마다 데이터 1개씩 가져오기
        for (int i = 0; i < (totalCount - dbCount) / 1000 + 1; i++) {

            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + URLEncoder.encode("4d516e4d6379656f36334b4d475076", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(idx), "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/" + URLEncoder.encode(String.valueOf(idx+999), "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
            // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            //System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
            BufferedReader rd;

            // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            // json 파싱 - JSONParser().parse() 가 예외가 발생하기 때문에 -> 예외처리
            try {
                JSONObject result = (JSONObject) new JSONParser().parse(sb.toString());
                JSONObject resultIn = (JSONObject) result.get("TbPublicWifiInfo");
                JSONArray array = (JSONArray) resultIn.get("row");  // row안에 있는 데이터를 배열로 저장

                JSONObject rowData;     // row안의 데이터 한개를 저장 (하나씩 DB에 넣어야하기 때문에)

                for (int j = 0; j < array.size(); j++) {    // 배열의 개수만큼 반복 (데이터가 들은 개수많큼)
                    // DB CRUD 클래스 객체 선언
                    WifiList wifiList = new WifiList();
                    rowData = (JSONObject) array.get(j);    // 한줄씩 가져오기
                    System.out.println(rowData.toString());
                    // WifiList 클래스타입에 column의 값들 저장하기
                    wifiList.setX_SWIFI_MGR_NO((String) rowData.get("X_SWIFI_MGR_NO"));
                    wifiList.setX_SWIFI_WRDOFC((String) rowData.get("X_SWIFI_WRDOFC"));
                    wifiList.setX_SWIFI_MAIN_NM((String) rowData.get("X_SWIFI_MAIN_NM"));
                    wifiList.setX_SWIFI_ADRES1((String) rowData.get("X_SWIFI_ADRES1"));
                    wifiList.setX_SWIFI_ADRES2((String) rowData.get("X_SWIFI_ADRES2"));
                    wifiList.setX_SWIFI_INSTL_FLOOR((String) rowData.get("X_SWIFI_INSTL_FLOOR"));
                    wifiList.setX_SWIFI_INSTL_TY((String) rowData.get("X_SWIFI_INSTL_TY"));
                    wifiList.setX_SWIFI_INSTL_MBY((String) rowData.get("X_SWIFI_INSTL_MBY"));
                    wifiList.setX_SWIFI_SVC_SE((String) rowData.get("X_SWIFI_SVC_SE"));
                    wifiList.setX_SWIFI_CMCWR((String) rowData.get("X_SWIFI_CMCWR"));
                    wifiList.setX_SWIFI_CNSTC_YEAR((String) rowData.get("X_SWIFI_CNSTC_YEAR"));
                    wifiList.setX_SWIFI_INOUT_DOOR((String) rowData.get("X_SWIFI_INOUT_DOOR"));
                    wifiList.setX_SWIFI_REMARS3((String) rowData.get("X_SWIFI_REMARS3"));
                    wifiList.setLNT((String) rowData.get("LNT"));
                    wifiList.setLAT((String) rowData.get("LAT"));
                    wifiList.setWORK_DTTM((String) rowData.get("WORK_DTTM"));

                    // DB에 저장
                    Dbcrud dbcrud = new Dbcrud();
                    loadNum += dbcrud.dataInsert(wifiList);    // 위에서 wifiList타입으로 저장한 값을 DB에 추가하기

                }

            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                rd.close();
                conn.disconnect();
            }


            idx += 1000;
        }

        return totalCount;
    }





}





