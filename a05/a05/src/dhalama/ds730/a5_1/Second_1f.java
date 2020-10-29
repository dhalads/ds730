package dhalama.ds730.a5_1;

//This import statement is needed to use the Scanner.
import java.util.*;

public class Second_1f {
    public static void main(String args[]) {
        System.out.print("Enter a number: ");
        // Scanner is used to read in values from the user
        Scanner input = new Scanner(System.in);
        // read in a value from the user and store the value into
        // a variable called number
        int number = input.nextInt();
        System.out.println("Value was: " + number);
        input.close();
    }
}
