package dhalama.ds730.a5_1;

import java.util.*;
import java.io.*;

public class FilterWords {
    public static void main(String args[]) throws Exception {
        Scanner input = new Scanner(new File("temp.txt"));
        ArrayList<String> words = new ArrayList<>();
        while (input.hasNext()) {
            words.add(input.next());
        }
        TreeSet<String> outputMe = new TreeSet<>();
        for (int index = 0; index < words.size(); index++) {
            if (words.get(index).length() >= 5) {
                outputMe.add(words.get(index));
            }
        }
        // An iterator goes through all elements of a Set, it is a
        // common operation with lists/sets of elements.
        Iterator<String> iter = outputMe.iterator();
        // Open up a file for writing output.
        PrintWriter output = new PrintWriter(new FileWriter("output.txt"));
        while (iter.hasNext()) {
            output.print(iter.next() + " ");
        }
        // Close the file to ensure all values are printed.
        output.close();
    }
}