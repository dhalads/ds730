package dhalama.ds730.a5_1;

public class StringTest {
    public static void main(String args[]) {
        String first = new String("hello");
        String second = new String("hello");
        if (first == second) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        if (first.equals(second)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
