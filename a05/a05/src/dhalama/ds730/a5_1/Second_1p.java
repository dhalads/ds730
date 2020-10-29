package dhalama.ds730.a5_1;

import java.util.*;
//needed import to use File class
import java.io.*;

public class Second_1p {
    // throws Exception is added because of our usage of File.
    // basically, if something goes wrong, the program quits.
    public static void main(String args[]) throws Exception {
        // instead of Scanner.in, there is: new File("temp.txt")
        Scanner input = null;
        try {
            input = new Scanner(new File("temp.txt")); 
        } catch (Exception e) {
            System.out.println(e);
        }
        
        ArrayList<Integer> values = new ArrayList<>();
        // while there are more values to be read in
        while (input.hasNext()) {
            int temp = input.nextInt();
            values.add(temp);
        }
        int total = 0;
        for (int value : values) {
            total += value;
        }
        double average = total / (double) values.size();
        System.out.println(average);
    }
}
