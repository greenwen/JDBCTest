package part2;

import part1.common.JdbcUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class JdbcCrudTest {
    //    public static final Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {
//        JdbcCrud jc = new JdbcCrud();
//        Connection con = JdbcUtil.getConnection();
//        jc.select(con);
////        jc.insert(con);
//        JdbcUtil.close(con);


        // 실행예 :
        // 아이디 입력: aaa
        // 비번 입력: 123456
        // 로그인 시도 -> 실패, 다시 입력
        // 아이디 입력: aaa
        // 비번 입력: 1111
        // 로그인 시도 -> 성공, 회원 정보 출력

        JdbcCrud jc = new JdbcCrud();
//        System.out.println("로그인 될 때까지 무한루프 실행");
//        while(true) {
//            System.out.print("아이디 입력 : ");
//            String id = JdbcUtil.SC.next();
//
//            System.out.print("비번 입력 : ");
//            String pw = JdbcUtil.SC.next();
//
////            Members mb = jc.login(id, pw);
//            // for admin to see all ids and passwords:
//            ArrayList<Members> list = jc.login(id, pw);
//            if (list != null && list.size() != 0) {
//                // login successful
//                for(Members mb : list) {
//                    System.out.println(mb);
//                }
////                System.out.println(mb); // toString
//                break;
//            } else {
//                // login unsuccessful
//                System.out.println("로그인 실패");
//            }
//        }

//        System.out.print("수장할 아이디 입력 : ");
//        String id = JdbcUtil.SC.next();
//
//        System.out.print("수정할 컬럼명 입력 : ");
//        String colName = JdbcUtil.SC.next();    // pw or name or gender
//
//        System.out.print("수정할 컬럼 값 입력 : ");
//        String colValue = JdbcUtil.SC.next();
//
//        Members member = jc.memberUpdate(id, colName, colValue);
//        if (member != null) {
//            System.out.println("수정 성공");
//            System.out.println();
//        } else {
//            System.out.println("수정 실패");
//        }

        // ========================================================

        System.out.print("삭제할 아이디 입력 : ");
        String id = JdbcUtil.SC.next();

        System.out.println("삭제할 비번 입력 : ");
        String pw = JdbcUtil.SC.next();

        boolean result = jc. memberDelete(id, pw);
        if(result) {
            System.out.println("삭제 성공");
            ArrayList<Members> list = jc.select();
            for(Members mb : list) {
                System.out.println(mb);
            }
        } else {
            System.out.println("삭제 실패");

        }










        // 키보드로 아이디, 비번, 이름, 나이, 성별, 입력후 리턴
//        Members mb = getMemberInfo();
//        boolean result = jc.join(mb);  // insert into
//        if (result) {
//            System.out.println("회원가입 성공");
//        } else {
//            System.out.println("회원가입 실패");
//        }
//        ArrayList<Members> mbList = jc.select();

//        JdbcUtil.getConnection();
//        jc.select(); // db 연결 -> select -> db 종료
        // String[] idList = jc.select();
        // for-each
        // for (String s: idList) {
        //      System.out.println(id);
        // }
//        for (int i = 0; i < mbList.size(); i++) {
//            System.out.println("아이디 : " + mbList.get(i));
//        }
//        for (int i = 0; i < mbList.size(); i++) {
//            mb.showInfo(); // 아이디, 이름, 나이, 성별
//            System.out.println(mbList.get(i)); // toString 재정의
    }

    //        for (int i = 0; i < mbList.size(); i++) {
//            mbList.get(i).showInfo();
//            System.out.println(mbList.get(i));
//        }
//        jc.insert();
//        jc.select();
//        JdbcUtil.close();
    private static Members getMemberInfo() {
        Members mb = new Members();

        System.out.println("아이디 입력 : ");
        mb.setId(JdbcUtil.SC.next());

        System.out.println("비번 입력 : ");
        mb.setPw(JdbcUtil.SC.next());

        System.out.println("이름 입력 : ");
        mb.setName(JdbcUtil.SC.next());

        System.out.println("나이 입력 : ");
        mb.setAge(JdbcUtil.SC.nextInt());

        System.out.println("성별 입력 : ");
        mb.setGender(JdbcUtil.SC.next());

        return mb;
    }

}



