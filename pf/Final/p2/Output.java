import java.util.ArrayList;
import java.util.List;

public class Output  extends Thread{

    List<List<Integer>> minRoutes = new ArrayList<>();
    public int minTime = -1;
    public int solutionsChecked = 0;

    public List<Integer> prefix = null;
    public List<Integer> inventory = null;
    public boolean enableMultiThread = false;
    public int partLevel = 2; 

    public void run() {
        List<List<Integer>> container = null;
        long start = 0;
        long end = 0;
        try {
            start = System.currentTimeMillis();
            container = getItemCombinations(this.prefix, this.inventory);
            end = System.currentTimeMillis();
            // System.out.println("container time:" + (end - start));
            for (int i = 0; i < container.size(); ++i) {
                this.checkRoute(container.get(i));
            } // end for
            if(this.enableMultiThread == false){
                Shared.addOutput(this.minTime, this.minRoutes, this.solutionsChecked);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkRoute(List<Integer> route) {
        int time = 0;
        // StringBuffer routeString = null;
        // StringBuffer timeString = null;
        int currentStop = 0;
        int nextStop = 0;
        int addTime = 0;
        try {
            // routeString = new StringBuffer();
            // timeString = new StringBuffer();
            // routeString.append(currentStop);
            for (int i = 1; i < route.size(); ++i) {
                nextStop = route.get(i);
                addTime = Shared.input.RouteTimes[currentStop][nextStop];
                time = time + addTime;
                // routeString.append("->").append(nextStop);
                // timeString.append(addTime).append("+");
                currentStop = nextStop;
            } // end for
            addTime = Shared.input.RouteTimes[currentStop][0];
            time = time + addTime;
            if (this.minRoutes.size() == 0) {
                this.minRoutes.add(route);
                this.minTime = time;
            } else if (time == this.minTime) {
                this.minRoutes.add(route);
            } else if (time < this.minTime) {
                this.minRoutes.clear();
                this.minRoutes.add(route);
                this.minTime = time;
            }
            this.solutionsChecked = this.solutionsChecked +1;
            // routeString.append("->").append(0);
            // timeString.append(addTime).append("=").append(time);
            // System.out.println(routeString.toString() + " : " + timeString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    // https://stackoverflow.com/questions/16982635/generate-all-possible-combinations-of-items
    public List<List<Integer>> getItemCombinations(List<Integer> prefix, List<Integer> inventory) {
        List<List<Integer>> container = new ArrayList<>();
        List<Integer> remainder;
        remainder = new ArrayList<>(inventory);
        getItemCombinations(prefix, remainder, container);
        return container;
    }

    private void getItemCombinations(List<Integer> prefix, List<Integer> remainder, List<List<Integer>> container) {
        int n = remainder.size();

        if (remainder.isEmpty()) {
            // container.add(prefix);
            this.checkRoute(prefix);
        }

        else {
            for (int i = 0; i < n; i++) {
                List<Integer> r = new ArrayList<>();
                List<Integer> p = new ArrayList<>(prefix);
                p.add(remainder.get(i));
                for (int j = 0; j < remainder.size(); j++) {
                    if (j == i)
                        continue;
                    r.add(remainder.get(j));
                }
                if(this.enableMultiThread == true && prefix.size() == this.partLevel ){
                    Output partOutput = new Output();
                    partOutput.prefix = p;
                    partOutput.inventory = r;
                    partOutput.enableMultiThread = false;
                    Shared.workers.add(partOutput);
                    partOutput.start();
                }else{
                    getItemCombinations(p, r, container);
                }
                
            }
        }
    }

    public static void main(String[] args) {
        // System.out.println(getItemCombinations(null)+"\n");
        Output output2 = new Output();
        List<List<Integer>> output = null;
        List<Integer> prefix = new ArrayList<>();
        prefix.add(0);

        List<Integer> playerINV = new ArrayList<>();
        playerINV.add(1);
        playerINV.add(2);
        playerINV.add(3);
        // playerINV.add(5);
        // playerINV.add(6);
        // playerINV.add(7);
        output = output2.getItemCombinations(prefix, playerINV);
        System.out.println(output);
        System.out.println(output.size());

    }
}
