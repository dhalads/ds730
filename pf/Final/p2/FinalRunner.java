public class FinalRunner {
    public static void main(String args[]) throws InterruptedException {
        Input input = null;
        Output output = null;
        long start = 0;
        long end = 0;
        String inputFile = null;
        try {
            if (args.length > 0) {
                inputFile = args[0].trim();
            } else {
                inputFile = "input2.txt";
            }//end if

            start = System.currentTimeMillis();
            input = new Input();
            input.file = inputFile;
            input.load();
            Shared.input = input;
            System.out.println(input.BldgNames.toString());
            System.out.println(input.RouteTimes.toString());
            output = new Output();
            output.prefix = Shared.input.getPrefix();
            output.inventory = Shared.input.getInventory();
            output.enableMultiThread = true;
            output.partLevel = 1;
            output.run();
            Shared.finish();
            end = System.currentTimeMillis();
            Shared.printOutput();            
            System.out.println("FinalRunner " + inputFile + " time:" + (end - start));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // end try-catch

    }// end main

}// end class
