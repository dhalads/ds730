import java.io.FileWriter;
import java.lang.invoke.StringConcatFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Shared {

    /*
     * 
     * file="filename" : input file. default is input2.txt 
     * MTT="1" : use MultiThread true or false, default is true 
     * NumMT="8" : max thread in thread pool, default is 8 
     * DPart="1" , what data level to split for MTT, default is 1
     * 
     */
    public static String file = "input2.txt";
    public static boolean MTT = true;
    public static int NumMT = 8;
    public static int DPart = 1;

    public static ExecutorService executor = null;

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
                System.out.println("No arguments passed! Using defaults:");
            }
            System.out.println("Input file. file=" + Shared.file);
            System.out.println("Enable multithread. MTT=" + Shared.MTT);
            System.out.println("Number of threads to use. NumMT=" + Shared.NumMT);
            System.out.println("Data partition level. DPart=" + Shared.DPart);
 
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

    public static void printOutput() {
        String line = null;
        try {
            System.out.println("Number of route with minimum time=" + minRoutes.size());
            line = routeToString(minRoutes.get(0));
            System.out.println("example route: " + line);
            System.out.println("mintime=" + minTime);
            System.out.println("solutionsChecked=" + solutionsChecked);
            System.out.println("Number data partitions used. numOutputs=" + numOutputs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void outputToFile() {
        FileWriter myWriter = null;
        String filename = null;
        String line = null;
        try {
            filename = "output2.txt";
            myWriter = new FileWriter(filename);
            for (int i = 0; i < minRoutes.size(); ++i) {
                line = routeToString(minRoutes.get(i));
                myWriter.write(line + "\n");
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                myWriter.close();
            } catch (Exception e) {
                // do nothing
            }
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

    public static String routeToString(List<Integer> route) {
        int time = 0;
        StringBuffer routeString = null;
        StringBuffer timeString = null;
        int currentStop = 0;
        int nextStop = 0;
        int addTime = 0;
        String output = null;
        try {
            routeString = new StringBuffer();
            timeString = new StringBuffer();
            routeString.append(Shared.input.BldgNames.get(currentStop));
            for (int i = 1; i < route.size(); ++i) {
                nextStop = route.get(i);
                addTime = Shared.input.RouteTimes[currentStop][nextStop];
                time = time + addTime;
                routeString.append(" ").append(Shared.input.BldgNames.get(nextStop));
                timeString.append(addTime).append("+");
                currentStop = nextStop;
            } // end for
            addTime = Shared.input.RouteTimes[currentStop][0];
            time = time + addTime;
            routeString.append(" ").append(Shared.input.BldgNames.get(0));
            timeString.append(addTime).append("=").append(time);
            // System.out.println(routeString.toString() + " : " + timeString.toString());
            routeString.append(" ").append(time);
            output = routeString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }// end method

}// end class
