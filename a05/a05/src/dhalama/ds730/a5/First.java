package dhalama.ds730.a5;

import java.util.Scanner;

public class First {

    public static boolean isPrime(int value) {
        boolean output = true;
        int maxValue = value / 2;
        int divisor = 2;
        int remainder = 0;
        remainder = value % 2;
        if (remainder == 0 && value != 2) {
            output = false;
            System.out.println("divisor=" + divisor);
            return output;
        } // end if
        divisor = divisor + 1;
        while (divisor <= maxValue) {
            remainder = value % divisor;
            if (remainder == 0) {
                output = false;
                System.out.println("divisor=" + divisor);
                break;
            } // end if
            divisor = divisor + 2;
        } // end while
        return output;
    }// end method

    public static void printPrime(int first, int second) {

    }// end method

    public static void getInputs() {
        Scanner read = null;
        String input = null;

        try {
            read = new Scanner(System.in);
            int number = input.nextInt();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }// end method

    public static int getValidInt(String message) {
        int output = 0;
        int count = 0;
        String input = "";
        Scanner read = null;
        try {
        read = new Scanner(System.in);
            while(true && count<10){
                System.out.print(message);
                input = read.next();
                if(input.equals("q"))
                count = count +1;
            }//end while
        } catch (Exception e) {
            //TODO: handle exception
        }
        
        return output;
    }

    public static void main(String args[]) {
        System.out.print("Enter first integer greater than zero: ");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        boolean output = false;
        // if statement checks if the number was less than 0
        // else if checks if the number was 0
        // else is everything else, i.e. it was positive
        if (number >= 0) {
            output = isPrime(number);
            System.out.println(number + " :Number is prime:" + output);

        } else if (number == 0) {
            System.out.println("Reenter integer greatr than zero. Type q to quit.");
        } else {
            System.out.println("Number was positive.");
        }
        input.close();
    }

}
