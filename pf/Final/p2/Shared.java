import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Shared {

    /*
     * 
     * file="filename" : input file. default is input2.txt 
     * MTT="1" : use MultiThread true or false, default is yes
     * NumMT="8" : max thread in thread pool, default is 8
     * DPart="1" , what data level to split for MTT, default is 1
     * 
     */
    public static String file = "input2.txt";
    public static boolean MTT = true;
    public static int NumMT = 8;
    public static int DPart = 1;

    public static ExecutorService executor = null ;

    public static Input input = null;
    public static Output output = null;

    public static List<List<Integer>> minRoutes = new ArrayList<>();
    public static int minTime = -1;
    public static int solutionsChecked = 0;
    public static int numOutputs = 0;
    public static ArrayList<Output> workers = new ArrayList<>();

    public static void loadArgs(String[] args) throws Exception {
        String[] splits = null;
        String setting = null;
        String value = null;
        try {
            if (args.length > 0) {

                for (String arg : args) {
                    splits = arg.split("=");
                    setting = splits[0];
                    value = splits[1];
                    if (setting.equals("file")) {
                        Shared.file = value;
                    } else if (setting.equals("MTT")) {
                        Shared.MTT = Boolean.parseBoolean(value);
                    } else if (setting.equals("NumMT")) {
                        Shared.NumMT = Integer.parseInt(value);
                    } else if (setting.equals("DPart")) {
                        Shared.DPart = Integer.parseInt(value);
                    } else {
                        throw new Exception("Failed to parse " + arg);
                    }
                } // end for
            } else {
                System.out.println("No arguments passed!");
            }
            if (Shared.MTT) {
                Shared.executor = Executors.newFixedThreadPool(Shared.NumMT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public synchronized static void addOutput(int time, List<List<Integer>> routes, int numChecked) {
        try {
            if (minRoutes.size() == 0) {
                minRoutes.addAll(routes);
                minTime = time;
            } else if (time == minTime) {
                minRoutes.addAll(routes);
            } else if (time < minTime) {
                minRoutes.clear();
                minRoutes.addAll(routes);
                minTime = time;
            }
            solutionsChecked = solutionsChecked + numChecked;
            numOutputs = numOutputs + 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }// end method

    public static void finish() {
        try {
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public static void finish(){
    // try {
    // for (Output pft : workers) {
    // if (pft.isAlive()) {
    // pft.join(); // make sure to wait for all threads to finish
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    public static void printOutput() {
        try {
            printRoutes(minRoutes);
            System.out.println("mintime=" + minTime);
            System.out.println("num minRoutes=" + minRoutes.size());
            System.out.println("solutionsChecked=" + solutionsChecked);
            System.out.println("numOutputs=" + numOutputs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printRoutes(List<List<Integer>> container) {
        try {
            for (int i = 0; i < container.size(); ++i) {
                printRoute(container.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printRoute(List<Integer> route) {
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
                addTime = Shared.input.RouteTimes[currentStop][nextStop];
                time = time + addTime;
                routeString.append("->").append(nextStop);
                timeString.append(addTime).append("+");
                currentStop = nextStop;
            } // end for
            addTime = Shared.input.RouteTimes[currentStop][0];
            time = time + addTime;
            routeString.append("->").append(0);
            timeString.append(addTime).append("=").append(time);
            System.out.println(routeString.toString() + " : " + timeString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}// end class
