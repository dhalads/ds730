
public class FirstTest {

    public static void main(String args[]) {

        try {
            First.printPrime(9,99);
        } catch (Exception e) {
            System.out.println("Exiting. Exception=" + e.getMessage());
            System.exit(2);
        } // end try-catch
    }// end main

}
