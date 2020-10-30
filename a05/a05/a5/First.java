import java.util.ArrayList;
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
            // System.out.println("divisor=" + divisor);
            return output;
        } // end if
        divisor = divisor + 1;
        while (divisor <= maxValue) {
            remainder = value % divisor;
            if (remainder == 0) {
                output = false;
                // System.out.println("divisor=" + divisor);
                break;
            } // end if
            divisor = divisor + 2;
            maxValue=value/divisor;
        } // end while
        return output;
    }// end method

    public static void printPrime(int first, int second) {
        int max = 0;
        ArrayList<Integer> primeList = null;
        StringBuffer output = null;
        try {
            if(first>second){
                max = first;
                first = second;
                second = max;
            }//end if
            primeList= new ArrayList<>();
            // System.out.println("Primes " + first + " to " + second);
            for(int i = first +1; i < second; ++i){
                if(isPrime(i)){
                    primeList.add(i);
                }//end if
            }//end for
            output = new StringBuffer();
            if(primeList.size()>0){
                for(int i = 0; i<primeList.size(); ++i){
                    output.append(primeList.get(i));
                    if(i+1<primeList.size()){
                        output.append(":");
                    }//end if
                }//end for
            }else{
                output.append("No primes");
            }//end if
            System.out.println(output.toString());
            // System.out.println("Number primes=" + primeList.size());
        } catch (Exception e) {
            throw e;
        }//end try-catch

    }// end method

    public static int getValidInt(String message, Scanner read) {
        int output = 0;
        int count = 0;
        String input = "";
        // Scanner read = null;
        try {
            // read = new Scanner(System.in);
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
                        } // end if
                    } catch (Exception e) {
                        System.out.println("'" + input + "' will not parse to int.");
                        continue;
                    } // end try catch
                } // end if
                count = count + 1;
            } // end while
              // read.close();
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            // read.close();
        } // end try-catch-finally
        return output;
    }// end method

    public static void main(String args[]) {
        Scanner read = null;
        int int1 = 0;
        int int2 = 0;
        long start = 0;
        long end = 0;

        try {
            read = new Scanner(System.in);
            System.out.println("Compute primes between two integers greater than zero.");
            int1 = First.getValidInt("Enter first integer:", read);
            int2 = First.getValidInt("Enter second integer:", read);
            start = System.currentTimeMillis();
            First.printPrime(int1, int2);
            end = System.currentTimeMillis();
            // System.out.println("time:" + ((end-start)/1));
        } catch (Exception e) {
            System.out.println("Exiting. Exception=" + e.getMessage());
            System.exit(2);
        }//end try-catch
    }//end main

}//end class
