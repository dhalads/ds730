package dhalama.ds730.a5_1;

import java.util.*;

public class Second_1j {
    public static void main(String args[]) {
        System.out.print("How many numbers: ");
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        ArrayList<Integer> values = new ArrayList<>();
        for (int count = 0; count < size; count++) {
            System.out.print("Enter value " + (count + 1) + ": ");
            int temp = input.nextInt();
            values.add(temp);
        }
        for (int value : values) {
            System.out.println(value);
        }
        input.close();
    }
}
