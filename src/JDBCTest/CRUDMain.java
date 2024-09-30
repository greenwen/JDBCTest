package JDBCTest;

import java.util.Scanner;

public class CRUDMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean run = true;
        int menu = 0;

        CRUD crud = new CRUD();

        while (run) {
            System.out.println("===========================================");
            System.out.println("[1]connect\t\t[2]insert\t\t[3]select");
            System.out.println("[4]update\t\t[5]delete\t\t[6]close\t\t[7]EMP");
            System.out.println("===========================================");
            System.out.print("선택 >> ");

            menu = sc.nextInt();

            switch (menu) {
                case 1:     // DB 접속
                    crud.connect();
                    break;
                case 2:     // C : insert()
                    crud.insert();
                    break;
                case 3:     // R : select()
                    crud.select();
                    break;
                case 4:     // U : update()
                    crud.update();
                    break;
                case 5:      // D : delete()
                    crud.delete();
                    break;
                case 6:
                    run = false;
                    crud.close();
                    break;
                case 7:
                    crud.selectEmp();
                    break;
                default:
                    System.out.println("다시 입력하세요");
                    break;
            }
        }


    }


}
