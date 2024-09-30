package part2;

import part1.common.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcCrud {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public ArrayList<Members> select() {
        con = JdbcUtil.getConnection();
//        String sql = "select * from members";
        String sql = "select id, name, age, gender from members order by id asc";
//      ArrayList<String> list = new ArrayList<>(); // create new array list with String type variable
//      ArrayList<String> list = null; //  tip: if in field, there is default value. if not in field, must initialize
        ArrayList<Members> list = null;
        try {
            pstmt = con.prepareStatement(sql);  // SQL parsing
            rs = pstmt.executeQuery();          // select 문 실행
            list = new ArrayList<>();
            Members mb = null;
            while (rs.next()) {
                mb = new Members();
//                list.add(rs.getString("ID")); // add to array list
                mb.setId(rs.getString("ID"));
                mb.setName(rs.getString("name"));
                mb.setAge(rs.getInt("age"));
                mb.setGender(rs.getString("gender"));
                list.add(mb); // 배열에 추가
            }
            ArrayList var4 = list;
            return var4;
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // 예외 발생 메세지
            System.out.println("select 예외 발생");
            e.printStackTrace();                // 예외 발생한 자리
            // System.out.println(e); // 예외 메세지 다 출력
        } finally { // 무조건 실행되는 코드; 위에 다른 코드가 실행 안 되도 finally는 실행된다
            close();
        }
        return null; // in case of error/failure, needs return value
        // when returning reference type, return null;
        // when returning int type error, return -1;
        // if ArrayList is initialized as null earlier, "return list;" is also possible
    }


    public boolean join(Members mb) {
        con = JdbcUtil.getConnection();
        String sql = "insert into members (id, pw, name, age, gender) values(?, ?, ?, ?, ?)";
        boolean var4 = false;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, mb.getId());
            pstmt.setString(2, mb.getPw());
            pstmt.setString(3, mb.getName());
            pstmt.setInt(4, mb.getAge());
            pstmt.setString(5, mb.getGender());
            int result = pstmt.executeUpdate();
            if (result <= 0) {
                return false;
            }

            var4 = true;
        } catch (SQLException e) {
            System.out.println("join 예외 발생");
            e.printStackTrace();
        } finally {
            this.close();
        }
        return var4;
    }

    public ArrayList<Members> login(String id, String pw) {
        con = JdbcUtil.getConnection();
//        String sql = "select * from members where id = ? and pw = ?";
        String sql = "select * from members where id = ?";
        ArrayList<Members> list = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            Members mb = null;
            if (rs.next()) {
                // 아이디 존재함
                if (rs.getString("id").equals("admin")) {
                    // 관리자인 경우
                    if (rs.getString("pw").equals(pw)) {
                        sql = "select * from members";
                        pstmt = con.prepareStatement(sql);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            mb = new Members(); // 레코드 수 만큼
                            mb.setId(rs.getString("id"));
                            mb.setPw(rs.getString("pw"));
                            mb.setName(rs.getString("name"));
                            mb.setAge(rs.getInt("age"));
                            mb.setGender(rs.getString("gender"));
                            list.add(mb);
                        }
                        return list;
                    }

                }
                if (rs.getString("pw").equals(pw)) {
                    sql = "select * from members where id = ?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, id);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        mb = new Members();
                        mb.setId(rs.getString("id"));
                        mb.setPw(rs.getString("pw"));
                        mb.setName(rs.getString("name"));
                        mb.setAge(rs.getInt("age"));
                        mb.setGender(rs.getString("gender"));
                        list.add(mb);
                        return list;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            ;
            System.out.println("login 예외 발생");
            e.printStackTrace();
        } finally {
            this.close();
        }
        return null; // 예외발생 or id가 없을 때
    }

    public Members memberUpdate(String id, String colName, String colValue) {
        con = JdbcUtil.getConnection();
        String sql = "Update members set " + colName + " = ? where id = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, colValue);
            pstmt.setString(2, id);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("update 성공");
                sql = "select * from emmbers where id = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, id);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    Members mb = new Members();
                    mb.setId(rs.getString("id"));
                    mb.setPw(rs.getString("pw"));
                    mb.setName(rs.getString("name"));
                    mb.setAge(rs.getInt("age"));
                    mb.setGender(rs.getString("gender"));
                    return mb;
                }
            }
        } catch (SQLException e) {
            System.out.println("update 예외 발생");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            this.close();
        }
        return null;
    }
    public boolean memberDelete(String id, String pw) {
        con = JdbcUtil.getConnection();
        String sql = "select id, pw from members where id = ?";
        new ArrayList<>();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
           rs = pstmt.executeQuery();
           Members mb = null;
           if (rs.next() && rs.getString("pw").equals(pw)) {
              sql = "delete from members where id =?";
              pstmt = con.prepareStatement(sql);
              pstmt.setString(1, id);
              int result = pstmt.executeUpdate();
              if (result >0) {
                  return true;
              }
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // 에러 타입 메세지
            e.printStackTrace();                // 에러 위치 메세지
            System.out.println("delete 예외 발생");
        } finally {
            this.close();
        }
        return false;
    }

    private void close() {
        JdbcUtil.close(rs);
        JdbcUtil.close(pstmt);
        JdbcUtil.close(con);
    }

}








