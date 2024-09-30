package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankSQL {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    // DB접속 메소드
    public void connect() {
        con = DBC.DBConnect();
        // con.setAutoCommit(false); // 기본값; 자동커밋(true)
    }

    // DB접속 해제 메소드
    public void conClose() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 고객번호 자동으로 생성하는 메소드
    public int clientNumber() {
        int cNum = 0;

        // (1) BCLIENT 테이블에서 MAX(CNUM)을 조회 : 1 ~ 5 단계
        try {
            // (1) SQL문 작성
            String sql = "SELECT MAX(CNUM) FROM BCLIENT";

            // (2) DB 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 값 저장, 없으면 skip

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
                cNum = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cNum + 1;
    }

    public String accountNumber() {
        String cAccount = null;

        // 카카오뱅크 계좌번호 : 3333-xx(두자리)-xxxxxxx(7자리)
        // Math.random() 메소드 사용해서 무작위 숫자 생성
        // 0부터 9까지 => (int)(Math.random() * 9) 사용하면 0부터 9까지 숫자가 무작위로 나타난다
        cAccount = "3333-";

        // 반복문 두번 돌리기
        for (int i = 1; i <= 2; i++) {
            cAccount += (int) (Math.random() * 9);
        }

        // 3333-xx-
        cAccount += "-";

        // 3333-xx-xxxxxxx
        for (int i = 1; i <= 7; i++) {
            cAccount += (int) (Math.random() * 9);
        }
        return cAccount;
    }

    public void createAccount(Client client) {

        try {
            // (1) SQL문 작성
            String sql = "INSERT INTO BCLIENT VALUES(?, ?, ?, ?)";

            // (2) DB 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 값 넣기
            // 테이블 컬럼 순서 알기
            // CNUM, CNAME, CACCOUNT, BALANCE
            pstmt.setInt(1, client.getcNum());
            pstmt.setString(2, client.getcName());
            pstmt.setString(3, client.getcAccount());
            pstmt.setInt(4, client.getBalance());

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("계좌생성 성공!");
                System.out.println("고객번호 : " + client.getcNum());
                System.out.println("계좌번호 : " + client.getcAccount());
            } else {
                System.out.println("계좌생성 실패!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean checkAccount(String cAccount) {
        boolean checked = false;

        try {
            // (1) SQL문 작성
            String sql = "SELECT * FROM BCLIENT WHERE CACCOUNT = ?";

            // (2) DB 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 값 넣기
            pstmt.setString(1, cAccount);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            // 존재 여부 확인, 있으면 checked 값은 true, 없으면 false
            checked = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checked;
    }

    public void deposit(String cAccount, int amount) {
        try {
            // (1) sql문 작성
            // 쿼리 해석: 입력한 계좌번호에 원래 있었던 금액에 새로 입력한 입금액을 더해서 새로운 금액 값을 저장한다
            String sql = "UPDATE BCLIENT SET BALANCE = BALANCE + ? WHERE CACCOUNT = ?";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 값 저장
            pstmt.setInt(1, amount);
            pstmt.setString(2, cAccount);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void withdraw(String cAccount, int amount) {
        try {
            // (1) sql문 작성
            // 쿼리 해석: 입력한 계좌번호에 원래 있었던 금액에 새로 입력한 출금액을 빼서 새로운 금액 값을 저장한다
            String sql = "UPDATE BCLIENT SET BALANCE = BALANCE - ? WHERE CACCOUNT = ?";

            // (2) db 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 값 저장
            pstmt.setInt(1, amount);
            pstmt.setString(2, cAccount);

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkBalance(String cAccount) {
        int balance = 0;

        try {
            // (1) SQL문 작성
            String sql = "SELECT BALANCE FROM BCLIENT WHERE CACCOUNT = ?";

            // (2) DB 준비
            pstmt = con.prepareStatement(sql);

            // (3) '?'에 값 저장
            pstmt.setString(1, cAccount);

            // (4) 실행
            rs = pstmt.executeQuery();

            // (5) 결과
            if (rs.next()) {
                balance = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }
}

// 회원 가입 => 정보 조회
// 회원 당 계좌 3개
// 일반 계좌, 청약 계좌, 주식 계좌 (계좌 종류)














