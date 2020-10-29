package dhalama.ds730.a5_1;

import java.util.*;

public class Second_1o {
    public static void factorial() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number: ");
        int value = input.nextInt();
        // ensure the value read in is non-negative
        while (value < 0) {
            System.out.print("Enter in a positive number: ");
            value = input.nextInt();
        }
        int answer = 1;
        for (int i = 1; i <= value; i++) {
            answer = answer * i;
        }
        System.out.println("Factorial of " + value + " is " + answer);
        input.close();
    }

    public static void main(String args[]) {
        factorial();
        factorial();
        factorial();
    }
}
