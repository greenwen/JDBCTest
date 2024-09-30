package Naver;

import java.util.Scanner;

public class NaverMain {
    public static void main(String[] args) {

        // NaverMember 객체 생성
        NaverMember nm = new NaverMember();

        // NaverSQL 객체 생성
        NaverSQL sql = new NaverSQL();

        // 프로그램 실행에 필요한 객체, 변수
        Scanner sc = new Scanner(System.in);

        // 초기 메뉴
        boolean run1 = true;
        int menu1 = 0;

        // 로그인 성공시 메뉴
        boolean run2 = true;
        int menu2 = 0;

        // 프로그램 실행 시 DB접속
        sql.connect();

        String nId;
        String nPw;

        // 메인 메뉴
        // [1] 회원가입 [2] 로그인
        while (run1) {

            System.out.println("========================================");
            System.out.println("[1]회원가입\t\t[2]로그인\t\t[3]나가기");
            System.out.println("========================================");
            System.out.print("선택 >> ");
            menu1 = sc.nextInt();

            switch (menu1) {
                case 1:
                    // [1] 회원가입
                    // 회원 등록하는 작업 실행
                    System.out.println("회원정보를 입력하세요");
                    nm = new NaverMember();

                    System.out.print("사용하실 아이디 >> ");
                    nm.setnId(sc.next());

                    System.out.print("비밀번호 >> ");
                    nm.setnPw(sc.next());

                    System.out.print("이름 >> ");
                    nm.setnName(sc.next());

                    System.out.print("생년월일 >> ");
                    nm.setnBirth(sc.next());

                    System.out.print("성별 >> ");
                    nm.setnGender(sc.next());

                    System.out.print("이메일 >> ");
                    nm.setnEmail(sc.next());

                    System.out.print("연락처 >> ");
                    nm.setnPhone(sc.next());

                    sql.signUp(nm);
                    break;
                case 2:
                    // [2] 로그인
                    // 아이디와 비밀번호를 입력받아서 일치여부 확인

                    // 로그아웃하면 run이 false가 된다;
                    // 로그인 실행할 때 run2를 다시 true로 만들어줘야 프로그램이 정상적으로 돌아간다
                    run2 = true;

                    System.out.print("아이디 : ");
                    nId = sc.next();

                    System.out.print("비밀번호 : ");
                    nPw = sc.next();

                    // 괄호안 nId, nPw가 boolean login 메소드의 리턴 값
//                    boolean login = sql.login(nId, nPw);

                     nm = sql.login(nId, nPw);

                    if(nm.getnId() != null) { // 로그인 성공시
                        while (run2) {
                            System.out.println("==========================================");
                            System.out.println("[1]회원목록\t\t[2]내 정보\t\t[3]정보수정");
                            System.out.println("[4]회원탈퇴\t\t[5]로그아웃");
                            System.out.println("==========================================");
                            System.out.print("선택 >> ");
                            menu2 = sc.nextInt();

                            switch (menu2) {
                                case 1: // 회원목록
                                    sql.memberList();
                                    break;
                                case 2: // 내 정보
                                    System.out.println(nm);
                                    break;
                                case 3: // 정보수정
                                    System.out.println("==============================================");
                                    System.out.println("[1]비밀번호\t\t[2]이  름\t\t[3]생년월일");
                                    System.out.println("[4]성  별 \t\t[5]이메일\t\t[6]연락처");
                                    System.out.println("==============================================");
                                    System.out.print("선택 >> ");
                                    int menu3 = sc.nextInt();

                                    switch (menu3) {
                                        case 1:
                                            System.out.print("비밀번호 >> ");
                                            nm.setnPw(sc.next());
                                            break;
                                        case 2:
                                            System.out.print("이름 >> ");
                                            nm.setnName(sc.next());
                                            break;
                                        case 3:
                                            System.out.print("생년월일 >> ");
                                            nm.setnBirth(sc.next());
                                            break;
                                        case 4:
                                            System.out.print("성별 >> ");
                                            nm.setnGender(sc.next());
                                            break;
                                        case 5:
                                            System.out.print("이메일 >> ");
                                            nm.setnEmail(sc.next());
                                            break;
                                        case 6:
                                            System.out.print("연락처 >> ");
                                            nm.setnPhone(sc.next());
                                            break;
                                        default:
                                            System.out.println("다시 입력하세요");
                                            break;
                                    }
                                    sql.update(nm);
                                    System.out.println("수정 후 정보 : " + nm);
                                    break;
                                case 4: // 회원탈퇴
                                    System.out.print("정말 탈퇴하시겠습니까? (y/n)");
                                    String checkDelete = sc.next();

                                    switch (checkDelete) {
                                        case "y":
                                        case "Y":
                                            sql.delete(nId);
                                            run2 = false;
                                            nId = "";
                                            nPw = "";
                                            break;
                                        case "n":
                                        case "N":
                                            System.out.println("삭제를 취소합니다.");
                                            break;
                                        default:
                                            System.out.println("y와 n중에 입력하세요.");
                                            break;
                                    }
                                    break;
                                case 5: // 로그아웃
                                    run2 = false;
                                    System.out.println("로그아웃 합니다");
                                    nId = "";
                                    nPw = "";
                                    break;
                                default:
                                    System.out.println("잘못 선택하셨습니다. 다시 입력해주세요.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("아이디와 비밀번호를 확인하세요");
                    }

                    break;
                case 3: // 종료
                    run1 = false;
                    sql.conClose();
                    break;
                default:
                    System.out.println("잘못 선택하셨습니다. 다시 선택해 주세요.");
                    break;
            }
        }

    }
}
