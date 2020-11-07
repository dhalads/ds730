import java.util.TreeSet;

public class testTreeMap {
    public static void main(String[] args) {

        // creating a TreeSet 
        TreeSet <Integer>treeadd = new TreeSet<Integer>();
        int testNumber = 15;
        int testNumber2 = 15;
        // adding in the tree set
        treeadd.add(12);
        treeadd.add(13);
        treeadd.add(14);
        treeadd.add(Integer.valueOf(testNumber));
  
        // check existence of 15  
        System.out.println("Checking existence of 15 ");
        System.out.println("Is 15 there in the set: "+treeadd.contains(testNumber2));
     }    
}
