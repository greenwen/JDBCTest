package Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {

    public static Connection DBConnect(){ // connection method; static (객체 없이 사용하는 메소드)

        // DB접속 정보 저장하기 위한 Connection 객체 con 선언 (리턴 값을 먼저 선언)
        Connection con = null;


        String user = "ICIA";       // 아이디
        String password = "1111";   // 비밀번호
        String url = "jdbc:oracle:thin:@localhost:1521:XE";


        try {
            // (1) 오라클 데이터베이스 드라이버(ojdbc8)
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // (2) 오라클 데이터베이스 접속정보
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            System.out.println("DB접속 실패 : 드라이버 로딩 실패");
            throw new RuntimeException(e); // 안 적으면 왜 틀렸는지 알 수 없다
            // e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("DB접속 실패 : 접속 정보 오류");
            throw new RuntimeException(e);
        }

        return con;



    }
}
