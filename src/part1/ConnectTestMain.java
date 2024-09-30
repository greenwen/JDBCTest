package part1;

public class ConnectTestMain {
    public static void main(String[] args) {
//        ConnectTest crud = new ConnectTest();
//        crud.connect();
////        crud.select();
//        System.out.println("==============");
//        crud.login("aaa", "1111");
//        System.out.println("==============");
////        crud.insert("eee", "1111", "이이", 22, "여자");
//        crud.update("aaa", "2222", "여자");   // aaa외원의 비번과 성별을 변경
//        crud.select(); // 확인
//        crud.delete("bbb"); // bbb회원 삭제
//        crud.select(); // 확인
//        crud.close();

        ConnectTest crud = new ConnectTest();
//        crud.connect();
        crud.txTest();
//        crud.close();


    }

}
