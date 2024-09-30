package part1;

import part1.common.JdbcUtil;

import java.sql.*;

public class ConnectTest {


    Connection con;
    PreparedStatement statement;
    ResultSet resultSet;

    public void select() {
        con = JdbcUtil.getConnection(); // DB 접속
        try {
            String sql = "select * from members";
            statement = con.prepareStatement(sql); // 전달 --> 파싱
            resultSet = statement.executeQuery();// 실행
            while (resultSet.next()) {
                System.out.print("아이디 : " + resultSet.getString("id"));
                System.out.print(", 비번 : " + resultSet.getString("pw"));
                System.out.print(", 이름 : " + resultSet.getString("name"));
                System.out.print(", 나이 : " + (resultSet.getInt("age") - 1));
                System.out.print(", 성별 : " + resultSet.getString("gender"));
                System.out.println(); // 줄바꿈
            }
        } catch (SQLException e) {
            System.out.println("select 예외 발생");
        } finally {
            //JdbcUtil.close(resultSet,statement,con);
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }
    }

    public void login(String id, String pw) {
        con = JdbcUtil.getConnection();

        String sql = "select * from members where id = ? and pw = ?";
        try {
            statement = con.prepareStatement(sql); // sql 전달 -> 파싱(분석)
            statement.setString(1, id);
            statement.setString(2, pw);
            resultSet = statement.executeQuery();// 실행
//            while (resultSet.next()) {
//                System.out.print("아이디 : " + resultSet.getString("id"));
//                System.out.print(", 비번 : " + resultSet.getString("pw"));
//                System.out.print(", 이름 : " + resultSet.getString("name"));
//                System.out.print(", 나이 : " + (resultSet.getInt("age") - 1));
//                System.out.print(", 성별 : " + resultSet.getString("gender"));
//                System.out.println(); // 줄바꿈
//            }
            if (resultSet.next()) { // 검색하는 'id'가 하나면 그냥 if문 돌린다 (yes/no question)
                System.out.print("아이디 : " + resultSet.getString("id"));
                System.out.print(", 비번 : " + resultSet.getString("pw"));
                System.out.print(", 이름 : " + resultSet.getString("name"));
                System.out.print(", 나이 : " + (resultSet.getInt("age") - 1));
                System.out.print(", 성별 : " + resultSet.getString("gender"));
                System.out.println(); // 줄바꿈
            }

        } catch (SQLException e) {
            System.out.println("select 예외 발생");
        } finally {
            //JdbcUtil.close(resultSet,statement,con);
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }

    }

    public void insert(String id, String pw, String name, int age, String gender) {
        con = JdbcUtil.getConnection();
        String sql = "insert into members values(?, ?, ?, ?, ?)";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, pw);
            statement.setString(3, name);
            statement.setInt(4, age);
            statement.setString(5, gender);
            //resultSet=statement.executeQuery(); //select만
            int result = statement.executeUpdate(); // insert, update, delete
            if (result > 0) {
                System.out.println("insert 성공");
            } else {
                System.out.println("insert 실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // 에러 타입 메세지
            e.printStackTrace();                // 에러 위치 메세지
            System.out.println("insert 예외 발생");
        } finally {
            //JdbcUtil.close(resultSet,statement,con);
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }

    }

    public void update(String id, String pw, String gender) {
        con = JdbcUtil.getConnection();
        String sql = "update members set pw = ?, gender = ? where id = ?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, pw);
            statement.setString(2, gender);
            statement.setString(3, id);
            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("성공");
            } else {
                System.out.println("실패");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage()); // 에러 타입 메세지
            e.printStackTrace();                // 에러 위치 메세지
            System.out.println("update 예외 발생");
        } finally {
            //JdbcUtil.close(resultSet,statement,con);
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }

    }
    public void delete(String id) {
        con = JdbcUtil.getConnection();
        String sql = "delete from members where id = ?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, id);
            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("성공");
            } else {
                System.out.println("실패");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // 에러 타입 메세지
            e.printStackTrace();                // 에러 위치 메세지
            System.out.println("delete 예외 발생");
        } finally {
            //JdbcUtil.close(resultSet,statement,con);
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }
    }

    // 이체 TX
    public void txTest() {
        con = JdbcUtil.getConnection();

        boolean isTxOk = false;
        String sql = "update account set balance=balance-1000 where hostid=?";
        try {
            statement = con.prepareStatement(sql);
            statement.setString(1, "AAA");
            statement.executeUpdate();
            sql = "update acc set balance=balance+1000 where hostid=?";
            statement = con.prepareStatement(sql);
            statement.setString(1, "BBB");
            statement.executeUpdate();
            isTxOk = true;

        } catch (SQLException e) {
            isTxOk = false;
            System.out.println("이체tx 예외 발생");
        } finally {
            if (isTxOk) {
                JdbcUtil.commit(con);
            } else {
                JdbcUtil.rollback(con);
            }
            JdbcUtil.close(resultSet);
            JdbcUtil.close(statement);
            JdbcUtil.close(con);
        }
    }
}

