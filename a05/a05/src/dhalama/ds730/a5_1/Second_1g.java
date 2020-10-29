package dhalama.ds730.a5_1;

import java.util.Scanner;

public class Second_1g {
    public static void main(String args[]) {
        System.out.print("Enter a number: ");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        // if statement checks if the number was less than 0
        // else if checks if the number was 0
        // else is everything else, i.e. it was positive
        if (number < 0) {
            System.out.println("Number was negative.");
        } else if (number == 0) {
            System.out.println("Number was 0.");
        } else {
            System.out.println("Number was positive.");
        }
        input.close();
    }
}
