package JDBCTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection { // 자바와 연결하기 위한 클래스

    public static Connection DBConnect(){ // connection method; static (객체 없이 사용하는 메소드)

        // DB접속 정보 저장하기 위한 Connection 객체 con 선언 (리턴 값을 먼저 선언)
        Connection con = null;

        // DB접속 계정정보
        String user = "ICIA";       // 아이디
        String password = "1111";   // 비밀번호

        // DB접속 주소정보
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        // String url = "jdbc:oracle:thin:@192.168.0.5:1521:XD";   // localhost대신에 IP주소로 할 수 있다.

        /*
           try - catch - finally (예외처리)
            예외처리 : 프로그램 실행 중에 발생할 수 있는 예외적인 상호앙을 관리하고 대응하기 위한 방법

            기본구조:

            try {
                // 예외가 발생 할 수 있는 코드 작성
                // 예외가 발생하지 않으면 try 구문안에 있는 내용 실행

            } catch(ExceptionType1 e 예외 타입) {
                // ExceptionType1을 처리하는 코드

            } catch(ExceptionType2 e 예외 타입) {
                // ExceptionType2을 처리하는 코드
                if-else if문 처럼 여러 개 쓰기 가능

            } finally {
                // 예외 발생 여부에 상관없이 항상 실행되는 코드(선택적)

            }
        */

        try {
            // (1) 오라클 데이터베이스 드라이버(ojdbc8)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // (2) 오라클 데이터베이스 접속정보
            // get.Connection은 메소드 오버로딩 (같은 코드, 다른 사용)
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("DB접속 실패 : 드라이버 로딩 실패");
            throw new RuntimeException(e); // 안 적으면 왜 틀렸는지 알 수 없다
            // e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("DB접속 실패 : 접속 정보 오류");
            throw new RuntimeException(e);
        }


        return con; // 주어진 데이터 타입과 동일한 타입이 나와한다 (Connection 타입이니 Connection 타입)
    }

}
