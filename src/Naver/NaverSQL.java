package Naver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NaverSQL {

    // DB연동 3객체
    Connection con;             // 접속
    PreparedStatement pstmt;    // SQL문 작성
    ResultSet rs;               // 결과

    // (0-1) DB접속 메소드
    public void connect() {
        con = DBC.DBConnect();
    }

    // (0-2) DB접속 해제 메소드
    public void conClose() {
        try {
            con.close();
            System.out.println("프로그램을 종료합니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // (1-1) 회원가입 메소드 : NaverMain 에서 입력받은 정보(memeber)를 매개변수로 사용
    public void signUp(NaverMember nm) {

        try {
            // (1) SQL문 작성 : 입력할 데이터 대신 '?' 작성
            String sql = "INSERT INTO NAVERMEMBER VALUES (?, ?, ?, ?, ?, ?, ?)";

            // (2) DB "화면" 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 입력데이터 처리
            pstmt.setString(1, nm.getnId());
            pstmt.setString(2, nm.getnPw());
            pstmt.setString(3, nm.getnName());
            pstmt.setString(4, nm.getnBirth());
            pstmt.setString(5, nm.getnGender());
            pstmt.setString(6, nm.getnEmail());
            pstmt.setString(7, nm.getnPhone());

            // (4) 실행 : insert, update, delete => int result
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("회원가입 완료. 로그인 해주세요.");
            } else {
                System.out.println("회원가입 실패. 정보를 확인해주세요.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // (1-2) 로그인 메소드
    public NaverMember login(String nId, String nPw) {
        NaverMember nm = new NaverMember();

        // 로그인 실패시
        // 메인 메뉴로 돌아가기

        // 로그인 성공시
        // [1] 회원목록 [2] 내 정보보기 [3] 내 정보수정 [4] 탈퇴

        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM NAVERMEMBER WHERE NID = ? AND NPW = ?";

            // (2) DB 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력 ('?' 없으면 패스)
            pstmt.setString(1, nId);
            pstmt.setString(2, nPw);

            // (4) 실행 : select => rs
            rs = pstmt.executeQuery();

            // (5) 결과
            // '만약 검색된 결과가 있다면'
            if (rs.next()) {

                return nm;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // (2-1) 회원 목록
    public void memberList() {
        //  회원 한명의 정보를 담을 객체
        NaverMember nm;

        // 회원목록을 만들 수 있는 객체
        ArrayList<NaverMember> memberList = new ArrayList<>();

        try {
            // (1) SQL문 작성 (try-catch 문 생성 후 안에 복붙
            String sql = "SELECT * FROM NAVERMEMBER";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            while (rs.next()) {

                // nm객체 초기화 (새로운 종이 가져오기)
                nm = new NaverMember();

                // nm 객체에 회원 정보 저장
                nm.setnId(rs.getString(1));
                nm.setnPw(rs.getString(2));
                nm.setnName(rs.getString(3));
                nm.setnBirth(rs.getString(4));
                nm.setnGender(rs.getString(5));
                nm.setnEmail(rs.getString(6));
                nm.setnPhone(rs.getString(7));

                // ArrayList memberList에 nm에 저장된 학생 정보를 추가한다
                memberList.add(nm);
            }
//            for (int i = 0; i < memberList.size(); i++) {
//                System.out.print("아이디 : " + memberList.get(i).getnId());
//                System.out.print(" | 이름 : " + memberList.get(i).getnName());
//                System.out.println(" | 성별 : " + memberList.get(i).getnGender());
//            }
            // 향상된 for문 : for-each문; 순서를 알 수 없음
            for (NaverMember list : memberList) {
                System.out.print("아이디 : " + list.getnId());
                System.out.print(" | 이름 : " + list.getnName());
                System.out.println(" | 성별 : " + list.getnGender());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // (2-2) 정보 상세 보기
    public NaverMember memberDetail(String nId) {
        NaverMember nm = new NaverMember();

        try {
            // (1) SQL문 작성
            String sql = "SELECT * FROM NAVERMEMBER WHERE NID = ?";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스
            pstmt.setString(1, nId);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
//
                //nm 객체에 학생 정보 저장
                nm.setnId(rs.getString(1));
                nm.setnPw(rs.getString(2));
                nm.setnName(rs.getString(3));
                nm.setnBirth(rs.getString(4));
                nm.setnGender(rs.getString(5));
                nm.setnEmail(rs.getString(6));
                nm.setnPhone(rs.getString(7));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nm;
    }

    // (2-3) 내 정보 수정
    public void update(NaverMember nm) {
        try {
            // (1) SQL문 작성
            String sql = "UPDATE NAVERMEMBER SET NPW = ?, NNAME = ?, " +
                    "NBIRTH = ?, NGENDER = ?, NEMAIL = ?, NPHONE = ? WHERE NID = ?";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스
            pstmt.setString(1, nm.getnPw());
            pstmt.setString(2, nm.getnName());
            pstmt.setString(3, nm.getnBirth());
            pstmt.setString(4, nm.getnGender());
            pstmt.setString(5, nm.getnEmail());
            pstmt.setString(6, nm.getnPhone());
            pstmt.setString(7, nm.getnId());

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("정보수정 완료");
            } else {
                System.out.println("수정 실패. 입력하신 정보를 확인해주세요");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // (2-4) 회원 탈퇴
    public void delete(String nId) {
        try {
            // (1) SQL문 작성
            String sql = "DELETE FROM NAVERMEMBER WHERE NID = ?";

            // (2) 화면준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 안에 데이터 입력, '?'가 없으면 패스
            pstmt.setString(1, nId);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("회원탈퇴 성공");
            } else {
                System.out.println("회원탈퇴 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
