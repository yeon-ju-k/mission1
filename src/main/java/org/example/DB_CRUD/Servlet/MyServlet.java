package org.example.DB_CRUD.Servlet;

import org.example.DB_CRUD.Dbcrud;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/my-servlet")
public class MyServlet extends HttpServlet {

    // 다른 서블릿 라이프사이클 메소드나 사용자 정의 메소드를 추가로 작성할 수 있습니다.

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GET 요청 처리 로직을 작성합니다.
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POST 요청 처리 로직을 작성합니다.

        // 클라이언트로부터 전달받은 요청 데이터 읽기
        String requestData = request.getReader().readLine();

        // 요청 데이터 파싱 (json-simple 라이브러리 사용)
        JSONParser parser = new JSONParser();
        JSONObject result;
        try {
            result = (JSONObject) parser.parse(requestData);
            String data = (String) result.get("data");

            // 받은 데이터를 기반으로 원하는 작업 수행
            Dbcrud dbcrud = new Dbcrud();
            dbcrud.dataDel(data);   // 자바클래스의 메소드 호출

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 응답 생성
        response.setStatus(HttpServletResponse.SC_OK);  // 200 출력
    }


}
