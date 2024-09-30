package KSTU;

import JDBCTest.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KSTUSQL {

    Connection con;             // DB접속
    // Statement stmt;          // SQL문 작성
    ResultSet rs;               // DB결과

    PreparedStatement pstmt;
    // SQL문을 DB로 전달하기 위한 객체
    // Statement stmt 대신 사용
    // 물음표('?')를 데이터로 인식

    // [0-1] DB접속 메소드
    public void connect() {
        con = DBConnection.DBConnect();

    }

    // [0-2] DB해제 메소드
    public void conClose() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // [1] 학생등록 : stuInsert
    public void stuInsert(KDSTU stu) { // 매개변수

        try {
            // (1) SQL문 먼저 작성
            String sql = "INSERT INTO KSTU VALUES(?, ?, ?, ?, ?, ?, ?)";

            // (2) 화면준비
            // stmt = con.createStatement();
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 데이터 입력
            pstmt.setInt(1, stu.getkNO());
            pstmt.setString(2, stu.getkPW());
            pstmt.setString(3, stu.getkName());
            pstmt.setInt(4, stu.getkAge());
            pstmt.setString(5, stu.getkGender());
            pstmt.setString(6, stu.getkEmail());
            pstmt.setString(7, stu.getkPhone());

            // (4) 실행
            int result = pstmt.executeUpdate(); // sql은 위에 사용했기에 생략

            // (5) 결과

            if (result > 0) {
                System.out.println("학생등록 성공!");
            } else {
                System.out.println("학생등록 실패");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // [2] 학생목록
    public void stuList() {

        //  학생 한명의 정보를 담을 수 있는 stu 객체
        KDSTU stu = null;

        // 학생목록을 만들 수 있는 kdList
        ArrayList<KDSTU> kdList = new ArrayList<>();

        /*
            Collection : (1)Set (2)Map (3)List

            Array(배열), List(목록) => 여러 데이터를 저장하는데 사용되는 구조
            - Array : 크기가 고정되어 있다
                    : 기본타입(정수형, 실수형, 문자형, 논리형)

            - List  : 크기가 변한다
                    : 참조형(객체)

            - List, ArrayList 비교 : List는 인터페이스, ArrayList는 List의 구현클래스
         */


        try {
            // (1) SQL문 작성 (try-catch 문 생성 후 안에 복붙
            String sql = "SELECT * FROM KSTU ORDER BY KNO";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            while (rs.next()) {
//                System.out.print("번호 : " + rs.getInt(1));
//                System.out.print(" \t| 이름 : " + rs.getString(3));
//                System.out.println("\t| 성별 : " + rs.getString(5));

                // 새로운 "종이"에 정보를 담을 수 있는 객체 (초기화)
                stu = new KDSTU();

                //stu 객체에 학생 정보 저장
                stu.setkNO(rs.getInt(1));
                stu.setkPW(rs.getString(2));
                stu.setkName(rs.getString(3));
                stu.setkAge(rs.getInt(4));
                stu.setkGender(rs.getString(5));
                stu.setkEmail(rs.getString(6));
                stu.setkPhone(rs.getString(7));

                // ArrayList kdList에 stu에 저장된 학생 정보를 추가한다
                kdList.add(stu);
            }

            // i가 kdList의 크기 보다 작은 동안 (i는 1씩 증가한다) 아래 내용 줄력한다
            for(int i = 0; i < kdList.size(); i++){
                System.out.print("번호 : " + kdList.get(i).getkNO());
                System.out.print(" | 이름 : " + kdList.get(i).getkName());
                System.out.println(" | 성별 : " + kdList.get(i).getkGender());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // [3] 학생상세보기
    public KDSTU stuDetail(int kNo) {
        KDSTU stu = new KDSTU();

        try {
            // (1) SQL문 작성
            String sql = "SELECT * FROM KSTU WHERE KNO = ?";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스
            pstmt.setInt(1, kNo);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
//                System.out.print("번호 : " + rs.getInt(1));
//                System.out.print(" \t| 비밀번호 : " + rs.getString(2));
//                System.out.print("\t| 이름 : " + rs.getString(3));
//                System.out.print("\t| 나이 " + rs.getInt(4));
//                System.out.print("\t| 성별 : " + rs.getString(5));
//                System.out.print("\t| 이메일 : " + rs.getString(6));
//                System.out.println("\t| 연락처 : " + rs.getString(7));

                //stu 객체에 학생 정보 저장
                stu.setkNO(rs.getInt(1));
                stu.setkPW(rs.getString(2));
                stu.setkName(rs.getString(3));
                stu.setkAge(rs.getInt(4));
                stu.setkGender(rs.getString(5));
                stu.setkEmail(rs.getString(6));
                stu.setkPhone(rs.getString(7));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stu;
    }

    // [4] 학생수정
    public void stuUpdate(KDSTU stu) {
        try {
            // (1) SQL문 작성
            String sql = "SELECT * FROM KSTU";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
                System.out.println("학생수정 성공!");
            } else {
                System.out.println("학생실패 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // [5] 학생삭제
    public void stuDelete(int kNo) {

        try {
            // (1) SQL문 작성
            String sql = "DELETE FROM KSTU WHERE KNO = ?";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스
            pstmt.setInt(1, kNo);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("학생수정 성공!");
            } else {
                System.out.println("학생실패 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
