import java.util.ArrayList;
import java.util.List;

public class Output {

    public Input PInput = null;

    public Input getPInput() {
        return this.PInput;
    }

    public void setPInput(Input PInput) {
        this.PInput = PInput;
    }

    public void run() {
        List<List<Integer>> container = null;
        try {
            container = getItemCombinations(this.PInput.getPrefix(), this.PInput.getInventory());
            for (int i = 0; i < container.size(); ++i) {
                this.checkRoute(container.get(i));
            } // end for

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkRoute(List<Integer> route) {
        int time = 0;
        StringBuffer routeString = null;
        StringBuffer timeString = null;
        int currentStop = 0;
        int nextStop = 0;
        int addTime = 0;
        try {
            routeString = new StringBuffer();
            timeString = new StringBuffer();
            routeString.append(currentStop);
            for (int i = 1; i < route.size(); ++i) {
                nextStop = route.get(i);
                addTime = this.PInput.RouteTimes[currentStop][nextStop];
                time = time + addTime;
                routeString.append("->").append(nextStop);
                timeString.append(addTime).append("+");
                currentStop = nextStop;
            } // end for
            addTime = this.PInput.RouteTimes[currentStop][0];
            time = time + addTime;
            routeString.append("->").append(0);
            timeString.append(addTime).append("=").append(time);
            System.out.println(routeString.toString() + " : " + timeString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printRoute(List<Integer> route) {
        int time = 0;
        StringBuffer routeString = null;
        StringBuffer timeString = null;
        int currentStop = 0;
        int nextStop = 0;
        int addTime = 0;
        try {
            routeString = new StringBuffer();
            timeString = new StringBuffer();
            routeString.append(currentStop);
            for (int i = 1; i < route.size(); ++i) {
                nextStop = route.get(i);
                addTime = this.PInput.RouteTimes[currentStop][nextStop];
                time = time + addTime;
                routeString.append("->").append(nextStop);
                timeString.append(addTime).append("+");
                currentStop = nextStop;
            } // end for
            addTime = this.PInput.RouteTimes[currentStop][0];
            time = time + addTime;
            routeString.append("->").append(0);
            timeString.append(addTime).append("=").append(time);
            System.out.println(routeString.toString() + " : " + timeString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<List<Integer>> getItemCombinations(List<Integer> prefix, List<Integer> inventory) {
        List<List<Integer>> container = new ArrayList<>();
        List<Integer> remainder;
        remainder = new ArrayList<>(inventory);
        getItemCombinations(prefix, remainder, container);
        return container;
    }

    private static void getItemCombinations(List<Integer> prefix, List<Integer> remainder,
            List<List<Integer>> container) {
        int n = remainder.size();

        if (remainder.isEmpty()) {
            container.add(prefix);
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
                getItemCombinations(p, r, container);
            }
        }
    }

    public static void main(String[] args) {
        // System.out.println(getItemCombinations(null)+"\n");
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
        output = getItemCombinations(prefix, playerINV);
        System.out.println(output);
        System.out.println(output.size());

    }
}
