package cadestefwill.ai.checkers;

public class CheckstyleTest {

    int checkStyleCheck;

    /** 
     * JavaDoc
     */
    public CheckstyleTest() {
            checkStyleCheck = 4;
    }

    public int getCheckStyleCheckNum() {
      return checkStyleCheck;
    }

    public static void main(String[] args) {
        CheckstyleTest myTest = new CheckstyleTest();
        System.out.println(myTest.getCheckStyleCheckNum());
        int x = 8;
        System.out.println(x);

    }
}
