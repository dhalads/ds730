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
        try {
            System.out.println(first + " to " + second);
        } catch (Exception e) {
            throw e;
        }

    }// end method

    public static int getValidInt(String message) {
        int output = 0;
        int count = 0;
        String input = "";
        Scanner read = null;
        try {
            read = new Scanner(System.in);
            while (true && count < 10) {
                System.out.print("Type 'q' to quit. " + message);
                input = read.next();
                if (input.equals("q")) {
                    System.exit(1);
                } else {
                    try {
                        output = Integer.parseInt(input);
                        if (output <= 0) {
                            System.out.println(output + " is not greater than zero.");
                            continue;
                        } else {
                            break;
                        }//end if
                    } catch (Exception e) {
                        System.out.println("'" + input + "' will parse to int.");
                        continue;
                    }//end try catch
                }//end if
                count = count + 1;
            } // end while
            read.close();
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            read.close();
        } // end try-catch-finally
        return output;
    }// end method

    public static void main(String args[]) {
        System.out.println("Compute primes between two integers greater than zero.");
        int int1 = First.getValidInt("Enter first integer:");
        int int2 = First.getValidInt("Enter second integer:");

        First.printPrime(int1, int2);

    }

}
