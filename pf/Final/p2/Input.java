import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Input {

    public HashMap<Integer, String> BldgNames = null;
    public int[][] RouteTimes = null;

    public void load() {
        Scanner myReader = null;
        String line = null;
        long start = 0;
        long end = 0;
        long startOutput = 0;
        long endOutput = 0;
        String[] bldg = null;
        String[] routeTimes = null;
        int bldgCount = 0;
        int numBuildings = 0;
        try {
            this.BldgNames = new HashMap<Integer, String>();
            start = System.currentTimeMillis();
            myReader = new Scanner(new File("input11.txt"));
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                // System.out.println(line);
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                bldg = line.split(":");
                this.BldgNames.put(bldgCount, bldg[0].trim());
                routeTimes = bldg[1].trim().split(" ");
                if (this.RouteTimes == null) {
                    numBuildings = routeTimes.length;
                    int size[] = { numBuildings, numBuildings };
                    this.RouteTimes = (int[][]) Array.newInstance(int.class, size);
                } // end if
                for (int i = 0; i < numBuildings; i++) {
                    this.RouteTimes[bldgCount][i] = Integer.parseInt(routeTimes[i]);
                } // end for
                bldgCount = bldgCount + 1;
            } // end while
            System.out.println(" output:" + (endOutput - startOutput));
            myReader.close();
            end = System.currentTimeMillis();
            System.out.println(" output:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            myReader.close();
        } // end try-catch
    }// end method

    public List<Integer> getPrefix() {
        List<Integer> prefix = new ArrayList<>();
        prefix.add(0);
        return prefix;
    }

    public List<Integer> getInventory() {
        Set<Integer> keySet = null;
        keySet = this.BldgNames.keySet();
        List<Integer> inventory = new ArrayList<>(keySet);
        inventory.remove(0);
        return inventory;
    }

}// end class
