package dhalama.ds730.a5_1;

import java.util.*;

public class Third_1k {
    public static void main(String args[]) {
        ArrayList<Integer> myList = new ArrayList<>();
        myList.add(4);
        myList.add(4);
        myList.add(4);
        HashSet<Integer> mySet = new HashSet<>();
        mySet.add(4);
        mySet.add(4);
        mySet.add(4);
        System.out.println("In list:");
        for (int value : myList) {
            System.out.println(value);
        }
        System.out.println("In set:");
        for (int value : mySet) {
            System.out.println(value);
        }
    }
}