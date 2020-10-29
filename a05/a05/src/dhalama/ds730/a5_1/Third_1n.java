package dhalama.ds730.a5_1;

import java.util.*;

public class Third_1n {
    public static void main(String args[]) {
        TreeMap<String, ArrayList<Integer>> codes = new TreeMap<>();
        ArrayList<Integer> forWI = new ArrayList<>();
        forWI.add(715);
        forWI.add(920);
        codes.put("WI", forWI);
        ArrayList<Integer> forIA = new ArrayList<>();
        forIA.add(319);
        forIA.add(651);
        codes.put("IA", forIA);
        for (Map.Entry<String, ArrayList<Integer>> values : codes.entrySet()) {
            for (Integer code : values.getValue()) {
                System.out.println(values.getKey() + " " + code);
            }
        }
    }
}