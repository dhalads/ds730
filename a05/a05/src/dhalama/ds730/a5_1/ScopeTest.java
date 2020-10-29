package dhalama.ds730.a5_1;

public class ScopeTest {
    public void someMethod() {
        int y = 10;
        int x = 0;
        if (y > 5) {
            x = 20;
            x = y + x;
            System.out.println(x);
        }
        System.out.println(x); // error!
    }
}
