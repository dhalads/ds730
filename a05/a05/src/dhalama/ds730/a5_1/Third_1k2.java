package dhalama.ds730.a5_1;

import java.util.*;

public class Third_1k2 {
    public static void main(String args[]) {
        TreeSet<Integer> myTree = new TreeSet<>();
        for (int i = 0; i < 100; i += 10) {
            myTree.add(i);
        }
        HashSet<Integer> mySet = new HashSet<>();
        for (int i = 0; i < 100; i += 10) {
            mySet.add(i);
        }
        System.out.println("In tree:");
        for (int value : myTree) {
            System.out.println(value);
        }
        System.out.println("In hash:");
        for (int value : mySet) {
            System.out.println(value);
        }
    }
}
