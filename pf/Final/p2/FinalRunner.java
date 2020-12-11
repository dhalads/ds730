

public class FinalRunner {
    public static void main(String args[]) throws InterruptedException {
        Input input = null;
        Output output = null;
        long start = 0;
        long end = 0;
        try {
            Shared.loadArgs(args);
            start = System.currentTimeMillis();
            input = new Input();
            input.file = Shared.file;
            input.load();
            Shared.input = input;
            System.out.println(input.BldgNames.toString());
            System.out.println(input.RouteTimes.toString());
            output = new Output();
            output.prefix = Shared.input.getPrefix();
            output.inventory = Shared.input.getInventory();
            output.enableMultiThread = Shared.MTT;
            output.partLevel = Shared.DPart;
            output.run();
            Shared.finish();
            end = System.currentTimeMillis();
            Shared.printOutput();            
            System.out.println("FinalRunner " + Shared.file + " time:" + (end - start));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // end try-catch

    }// end main

}// end class
