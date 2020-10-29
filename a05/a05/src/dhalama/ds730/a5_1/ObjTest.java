package dhalama.ds730.a5_1;

import java.util.*;

public class ObjTest {
    private ArrayList<Integer> myList;

    public void someMethod() {
        myList = new ArrayList<>();
        myList.add(10);
        myList.add(20);
    }

    public ArrayList<Integer> getList() {
        // myList is null and has nothing stored in it
        return myList;
    }
}