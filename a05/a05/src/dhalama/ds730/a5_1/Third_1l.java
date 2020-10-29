package dhalama.ds730.a5_1;

import java.util.*;

public class Third_1l {
    public static void main(String args[]) {
        HashMap<Integer, String> codes = new HashMap<>();
        codes.put(715, "WI");
        codes.put(319, "IA");
        codes.put(920, "WI");
        codes.put(319, "TX"); // For 1m
        Set<Integer> keys = codes.keySet();
        for (Integer key : keys) {
            if (codes.containsKey(key)) {
                System.out.println(key + " : " + codes.get(key));
            }
        }
    }
}
