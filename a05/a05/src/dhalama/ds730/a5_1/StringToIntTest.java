package dhalama.ds730.a5_1;

public class StringToIntTest {
    public static void main(String args[]) {
        String first = new String("22");
        String second = new String("45");
        // doesn’t work: int total = first+second;
        // rather you must convert first and second to ints
        int firstInt = Integer.parseInt(first);
        int secondInt = Integer.parseInt(second);
        int total = firstInt + secondInt;
        // another common issue to converting part of a
        // string to an int, for example, a date
        String date = new String("05/11/2004");
        // in order to pull out the 11, we use substring
        // similar to the str[3:5] syntax of Python
        String day = date.substring(3, 5);
        System.out.println(day);
        System.out.println(total);
    }
}