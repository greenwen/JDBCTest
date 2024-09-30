package Bank;

public class Client {

    // 필드
    private int cNum;           // 고객번호
    private String cName;       // 고객이름
    private String cAccount;    // 계좌번호
    private int balance;        // 계좌잔액

    // 생성자 (기본 생성자, 생략)

    // 메소드 (getter, setter, toString)
    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAccount() {
        return cAccount;
    }

    public void setcAccount(String cAccount) {
        this.cAccount = cAccount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "고객정보[ " +
                "고객번호 : " + cNum +
                ", 고객이름 : " + cName +
                ", 계좌번호 : " + cAccount +
                ", 계좌잔액 : " + balance +
                "]";
    }
}
