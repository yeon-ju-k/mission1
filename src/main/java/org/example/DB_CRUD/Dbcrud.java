package org.example.DB_CRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DB CRUD 기능 구현 클래스
public class Dbcrud {

    // select 기능 구현 메소드 : dataList()
    public static void dataList() {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;


        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select X_SWIFI_MGR_NO as 관리번호, " +
                    "       X_SWIFI_WRDOFC as 자치구, " +
                    "       X_SWIFI_MAIN_NM as 와이파이명, " +
                    "       X_SWIFI_ADRES1 as 도로명주소, " +
                    "       X_SWIFI_ADRES2 as 상세주소, " +
                    "       X_SWIFI_INSTL_FLOOR as 설치위치_층, " +
                    "       X_SWIFI_INSTL_TY as 설치유형, " +
                    "       X_SWIFI_INSTL_MBY as 설치기관, " +
                    "       X_SWIFI_SVC_SE as 서비스구분, " +
                    "       X_SWIFI_CMCWR as 망종류, " +
                    "       X_SWIFI_CNSTC_YEAR as 설치년도, " +
                    "       X_SWIFI_INOUT_DOOR as 실내외구분, " +
                    "       X_SWIFI_REMARS3 as WIFI접속환경, " +
                    "       LNT as X좌표, " +
                    "       LAT as Y좌표, " +
                    "       WORK_DTTM as 작업일자 " +
                    "from PUBLIC_WIFI_INFO; " ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);


            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while ( rs.next() ) {

                String X_SIWI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
                String LNT = rs.getString("LNT");
                String LAT = rs.getString("LAT");
                String WORK_DTTM = rs.getString("WORK_DTTM");

                System.out.println(X_SIWI_MGR_NO + ", " + X_SWIFI_WRDOFC + ", " + X_SWIFI_MAIN_NM + ", "
                        + X_SWIFI_ADRES1 + ", " + X_SWIFI_ADRES2 + ", " + X_SWIFI_INSTL_FLOOR + ", "
                        + X_SWIFI_INSTL_TY + ", " + X_SWIFI_INSTL_MBY + ", " + X_SWIFI_SVC_SE + ", "
                        + X_SWIFI_CMCWR + ", " + X_SWIFI_CNSTC_YEAR + ", " + X_SWIFI_INOUT_DOOR
                        + X_SWIFI_REMARS3 + ", " + LNT + ", " + LAT + ", " + WORK_DTTM);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }

    // DB에 저장된 해당 테이블의 데이터 개수 출력 메소드 : totalCount(table명)
    public static int totalCount(String tableName) {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int count = 0;


        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select count(*) " +
                        " from " + tableName + ";" ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            count = rs.getInt("count(*)");


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return count;
    }

    // DB의 PUBLIC_WIFI_INFO 에 Insert 기능 구현 메소드 : dataInsert()
    public static int dataInsert(WifiList wifiList) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " insert into PUBLIC_WIFI_INFO " +
                            " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, " +
                            " X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, " +
                            " X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, " +
                            " X_SWIFI_REMARS3, LNT, LAT, WORK_DTTM) " +
                            " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,wifiList.X_SWIFI_MGR_NO);
            preparedStatement.setString(2,wifiList.X_SWIFI_WRDOFC);
            preparedStatement.setString(3,wifiList.X_SWIFI_MAIN_NM);
            preparedStatement.setString(4,wifiList.X_SWIFI_ADRES1);
            preparedStatement.setString(5,wifiList.X_SWIFI_ADRES2);
            preparedStatement.setString(6,wifiList.X_SWIFI_INSTL_FLOOR);
            preparedStatement.setString(7,wifiList.X_SWIFI_INSTL_TY);
            preparedStatement.setString(8,wifiList.X_SWIFI_INSTL_MBY);
            preparedStatement.setString(9,wifiList.X_SWIFI_SVC_SE);
            preparedStatement.setString(10,wifiList.X_SWIFI_CMCWR);
            preparedStatement.setString(11,wifiList.X_SWIFI_CNSTC_YEAR);
            preparedStatement.setString(12,wifiList.X_SWIFI_INOUT_DOOR);
            preparedStatement.setString(13,wifiList.X_SWIFI_REMARS3);
            preparedStatement.setString(14,wifiList.LNT);
            preparedStatement.setString(15,wifiList.LAT);
            preparedStatement.setString(16,wifiList.WORK_DTTM);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 저장 완료 ");
            } else {
                System.out.println(" 저장 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }

    // 내 위치와 가까운 위치에 있는 와이파이 정보 20개 출력 : selectNear(경도,위도)
    public static List<WifiList> selectNear(String myLNT, String myLAT) {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 출력된 정보를 출력할 List 생성
        List<WifiList> list = new ArrayList<>();


        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select  round(6371*acos(cos(radians( ? ))*cos(radians(LAT))*cos(radians(LNT)" +
                    "       -radians( ? ))+sin(radians( ? ))*sin(radians(LAT))), 4) AS dist, " +
                    "       X_SWIFI_MGR_NO , " +
                    "       X_SWIFI_WRDOFC , " +
                    "       X_SWIFI_MAIN_NM , " +
                    "       X_SWIFI_ADRES1 , " +
                    "       X_SWIFI_ADRES2 , " +
                    "       X_SWIFI_INSTL_FLOOR , " +
                    "       X_SWIFI_INSTL_TY , " +
                    "       X_SWIFI_INSTL_MBY , " +
                    "       X_SWIFI_SVC_SE , " +
                    "       X_SWIFI_CMCWR , " +
                    "       X_SWIFI_CNSTC_YEAR , " +
                    "       X_SWIFI_INOUT_DOOR , " +
                    "       X_SWIFI_REMARS3 , " +
                    "       LNT , " +
                    "       LAT , " +
                    "       WORK_DTTM " +
                    " from PUBLIC_WIFI_INFO " +
                    " order by dist " +
                    " limit 20; ";

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,myLAT);
            preparedStatement.setString(2,myLNT);
            preparedStatement.setString(3,myLAT);


            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while ( rs.next() ) {

                String dist = rs.getString("dist");
                String X_SIWI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
                String LNT = rs.getString("LNT");
                String LAT = rs.getString("LAT");
                String WORK_DTTM = rs.getString("WORK_DTTM");

                // wifiList 정보에 넣기
                WifiList wifiList = new WifiList();
                wifiList.setDist(dist);
                wifiList.setX_SWIFI_MGR_NO(X_SIWI_MGR_NO);
                wifiList.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
                wifiList.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
                wifiList.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
                wifiList.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
                wifiList.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
                wifiList.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
                wifiList.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
                wifiList.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
                wifiList.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
                wifiList.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
                wifiList.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
                wifiList.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                wifiList.setLNT(LNT);
                wifiList.setLAT(LAT);
                wifiList.setWORK_DTTM(WORK_DTTM);

                list.add(wifiList);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return list;
    }

    // DB에 Insert 기능 구현 메소드 (위치 히스토리 목록에 저장) : historyInsert()
    public static int historyInsert(String myX, String myY) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " insert into HISTORY " +
                    " (MY_X, MY_Y, HISTORY_DATE) " +
                    " values (?, ?, datetime('now')); ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,myX);
            preparedStatement.setString(2,myY);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 저장 완료 ");
            } else {
                System.out.println(" 저장 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }

    // select 기능 구현 메소드(위치 히스토리 목록) : historyList()
    public static List historyList() {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 결과값을 저장 + 리턴할 List 생성
        List<HistoryName> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select H_ID, " +
                    "       MY_X, " +
                    "       MY_Y, " +
                    "       HISTORY_DATE " +
                    "       from HISTORY; " ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);


            // 쿼리 실행
            rs = preparedStatement.executeQuery();


            while ( rs.next() ) {

                String H_ID = rs.getString("H_ID");
                String MY_X = rs.getString("MY_X");
                String MY_Y = rs.getString("MY_Y");
                String HISTORY_DATE = rs.getString("HISTORY_DATE");

                // HistoryName 타입으로 저장
                HistoryName his = new HistoryName();
                his.setH_ID(H_ID);
                his.setMY_X(MY_X);
                his.setMY_Y(MY_Y);
                his.setHISTORY_DATE(HISTORY_DATE);

                list.add(his);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return list;
    }

    // 삭제 delete 기능 구현 메소드 : dataDel(H_ID컬럼의 삭제할데이터값)
    public static int dataDel(String data) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " delete " +
                        " from HISTORY " +
                        " where H_ID = ? ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,data);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 삭제 완료 ");
            } else {
                System.out.println(" 삭제 실패 ");
                System.out.println(data);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }



    // ----------------- bookmark_group 테이블 관련 기능 ----------------

    // DB의 bookmark_group 테이블 select 기능 구현 메소드(북마크그룹 목록) : bGList()
    public static List bGList() {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 결과값을 저장 + 리턴할 List 생성
        List<bGName> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select BG_ID, " +
                    "       BG_NAME, " +
                    "       BG_PRI, " +
                    "       BG_IN_DATE, " +
                    "       BG_UPDATE " +
                    "       FROM `BOOKMARK_GROUP`; " ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);


            // 쿼리 실행
            rs = preparedStatement.executeQuery();



            // 쿼리 실행
            rs = preparedStatement.executeQuery();


            while ( rs.next() ) {

                String BG_ID = rs.getString("BG_ID");
                String BG_NAME = rs.getString("BG_NAME");
                String BG_PRI = rs.getString("BG_PRI");
                String BG_IN_DATE = rs.getString("BG_IN_DATE");
                String BG_UPDATE = rs.getString("BG_UPDATE");

                // bGName 타입으로 저장
                bGName his = new bGName();
                his.setBG_ID(BG_ID);
                his.setBG_NAME(BG_NAME);
                his.setBG_PRI(BG_PRI);
                his.setBG_IN_DATE(BG_IN_DATE);
                his.setBG_UPDATE(BG_UPDATE);

                list.add(his);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return list;
    }

    // DB의 bookmark_group 테이블 삭제 delete 기능 구현 메소드 : bGDel(BG_ID컬럼의 삭제할데이터값)
    public static int bGDel(String data) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " delete " +
                    " from BOOKMARK_GROUP " +
                    " where BG_PRI = ? ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,data);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 삭제 완료 ");
            } else {
                System.out.println(" 삭제 실패 ");
                System.out.println(data);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }

    // DB에 bookmark_group 테이블 Insert 기능 구현 메소드 : bGInsert()
    public static int bGInsert(String name, String pri) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " insert into BOOKMARK_GROUP " +
                    " (BG_NAME, BG_PRI, BG_IN_DATE, BG_UPDATE) " +
                    " values (?, ?, datetime('now'), ''); ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,pri);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 저장 완료 ");
            } else {
                System.out.println(" 저장 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }

    // DB에 bookmark_group 테이블 Update 기능 구현 메소드 : bGUpdate()
    public static int bGUpdate(String newName, String newPri, String oldName, String oldPri) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " update BOOKMARK_GROUP " +
                        "   set " +
                        "      BG_NAME = ? , " +
                        "      BG_PRI = ? , " +
                        "      BG_UPDATE = datetime('now') " +
                        "   where BG_NAME = ? and BG_PRI = ? " ;


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,newName);
            preparedStatement.setString(2,newPri);
            preparedStatement.setString(3,oldName);
            preparedStatement.setString(4,oldPri);


            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 수정 완료 ");
            } else {
                System.out.println(" 수정 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }



    // ----------------- bookmark-data 테이블 관련 기능 ----------------

    // DB의 bookmark_data 테이블내의 정보 select 기능 구현 메소드 : bDList()
    public static List bDList(String id) {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 결과값을 저장 + 리턴할 List 생성
        List<bDName> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select B_ID, " +
                    "       B_NAME, " +
                    "       B_WIFINAME, " +
                    "       B_IN_DATE, " +
                    "       X_SWIFI_MGR_NO," +
                    "       DIST " +
                    "       FROM `BOOKMARK_DATA`" +
                    "       where B_ID = ? ; " ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 변수 설정
            preparedStatement.setString(1,id);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();



            while ( rs.next() ) {

                String B_ID = rs.getString("B_ID");
                String B_NAME = rs.getString("B_NAME");
                String B_WIFINAME = rs.getString("B_WIFINAME");
                String B_IN_DATE = rs.getString("B_IN_DATE");
                String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String DIST = rs.getString("DIST");

                // bGName 타입으로 저장
                bDName his = new bDName();
                his.setB_ID(B_ID);
                his.setB_NAME(B_NAME);
                his.setB_WIFINAME(B_WIFINAME);
                his.setB_IN_DATE(B_IN_DATE);
                his.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
                his.setDIST(DIST);

                list.add(his);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return list;
    }

    // DB의 bookmark_data 테이블내의 정보 select 기능 구현 메소드 : bDList()
    public static List bDList() {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 결과값을 저장 + 리턴할 List 생성
        List<bDName> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select B_ID, " +
                    "       B_NAME, " +
                    "       B_WIFINAME, " +
                    "       B_IN_DATE, " +
                    "       X_SWIFI_MGR_NO," +
                    "       DIST " +
                    "       FROM `BOOKMARK_DATA` ;" ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);


            // 쿼리 실행
            rs = preparedStatement.executeQuery();



            while ( rs.next() ) {

                String B_ID = rs.getString("B_ID");
                String B_NAME = rs.getString("B_NAME");
                String B_WIFINAME = rs.getString("B_WIFINAME");
                String B_IN_DATE = rs.getString("B_IN_DATE");
                String X_SWIFI_MGR_NO = rs.getString("X_SWIFI_MGR_NO");
                String DIST = rs.getString("DIST");

                // bGName 타입으로 저장
                bDName his = new bDName();
                his.setB_ID(B_ID);
                his.setB_NAME(B_NAME);
                his.setB_WIFINAME(B_WIFINAME);
                his.setB_IN_DATE(B_IN_DATE);
                his.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
                his.setDIST(DIST);

                list.add(his);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return list;
    }

    // DB에 bookmark_data 테이블 Insert 기능 구현 메소드 : bDInsert()
    public static int bDInsert(String name, String wifiname, String num, String dist) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " insert into BOOKMARK_DATA " +
                    " (B_NAME, B_WIFINAME, B_IN_DATE, X_SWIFI_MGR_NO, DIST) " +
                    " values (?, ?, datetime('now'), ?, ?); ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,wifiname);
            preparedStatement.setString(3,num);
            preparedStatement.setString(4,dist);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 저장 완료 ");
            } else {
                System.out.println(" 저장 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }

    // DB의 bookmark_data 테이블 + public_wifi_info테이블 의 정보 select 기능 구현 메소드 : bDPublicList()
    public static List bDPublicList(String num) {

        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 결과값을 저장 + 리턴할 List 생성
        List<WifiList> list = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);

            // sql 문 작성
            String sql = " select * " +
                    "       FROM `PUBLIC_WIFI_INFO`" +
                    "       where X_SWIFI_MGR_NO = ? " ;

            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 변수설정
            preparedStatement.setString(1, num);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while ( rs.next() ) {

                String X_SWIFI_WRDOFC = rs.getString("X_SWIFI_WRDOFC");
                String X_SWIFI_MAIN_NM = rs.getString("X_SWIFI_MAIN_NM");
                String X_SWIFI_ADRES1 = rs.getString("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = rs.getString("X_SWIFI_ADRES2");
                String X_SWIFI_INSTL_FLOOR = rs.getString("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_TY = rs.getString("X_SWIFI_INSTL_TY");
                String X_SWIFI_INSTL_MBY = rs.getString("X_SWIFI_INSTL_MBY");
                String X_SWIFI_SVC_SE = rs.getString("X_SWIFI_SVC_SE");
                String X_SWIFI_CMCWR = rs.getString("X_SWIFI_CMCWR");
                String X_SWIFI_CNSTC_YEAR = rs.getString("X_SWIFI_CNSTC_YEAR");
                String X_SWIFI_INOUT_DOOR = rs.getString("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_REMARS3 = rs.getString("X_SWIFI_REMARS3");
                String LAT = rs.getString("LAT");
                String LNT = rs.getString("LNT");
                String WORK_DTTM = rs.getString("WORK_DTTM");

                // bGName 타입으로 저장
                WifiList his = new WifiList();
                his.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
                his.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
                his.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
                his.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
                his.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
                his.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
                his.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
                his.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
                his.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
                his.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
                his.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
                his.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
                his.setLAT(LAT);
                his.setLNT(LNT);
                his.setWORK_DTTM(WORK_DTTM);

                list.add(his);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( rs != null && !rs.isClosed() ) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return list;
    }

    // DB의 bookmark_data 테이블 삭제 delete 기능 구현 메소드 : bDDel(B_ID컬럼의 삭제할데이터값)
    public static int bDDel(String data) {
        // 전달 url 양식 = "jdbc:DB벤더:SQLite db경로";
        String url = "jdbc:sqlite:C:\\dev\\dynamic-web\\SQLite\\mission1_db.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 커넥션 객체 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;   // 리턴값 (저장한 행의 수)

        try {

            connection = DriverManager.getConnection(url);

            // sql 문 작성 - DB에 저장
            String sql = " delete " +
                    " from BOOKMARK_DATA " +
                    " where B_ID = ? ";


            // statement 객체 생성
            preparedStatement = connection.prepareStatement(sql);

            // 쿼리에서 작성한 ? 변수 지정하기 (parameterindex는 1부터 시작)
            preparedStatement.setString(1,data);

            // 쿼리 실행
            affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println(" 삭제 완료 ");
            } else {
                System.out.println(" 삭제 실패 ");
                System.out.println(data);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {     // 연결 해제

            try {
                if ( preparedStatement != null && !preparedStatement.isClosed() ) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if ( connection != null && connection.isClosed() ) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return affectedRows;
    }

}
