package dhalama.ds730.a5_1;

import java.util.*;
import java.io.*;

public class Third_1q {
    public static void main(String args[]) throws Exception {
        Scanner input = new Scanner(new File("temp.csv"));
        while (input.hasNext()) {
            String curLine = input.nextLine();
            String[] values = curLine.split(",");
            // values is an array where the first column of the
            // curLine is stored in values[0], the second column
            // of the curLine is stored in values[1] and so on.
            System.out.println(values);
        }
    }
}