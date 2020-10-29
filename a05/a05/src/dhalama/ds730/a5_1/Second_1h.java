package dhalama.ds730.a5_1;

import java.util.*;

public class Second_1h {
    public static void main(String args[]) {
        System.out.print("Enter a number: ");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        // a repetitive structure that prints out the integers
        // from number down to 0 without printing out 0.
        while (number > 0) {
            System.out.println(number);
            number--;
        }
        input.close();
    }
}
