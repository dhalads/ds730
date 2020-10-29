package dhalama.ds730.a5_1;

import java.util.*;

public class Second_1i {
    public static void main(String args[]) {
        System.out.print("Enter a number: ");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        // a repetitive structure that prints out the integers
        // from 0 up to number without printing out number.
        for (int count = 0; count < number; count++) {
            System.out.println(count);
        }
        input.close();
    }
}