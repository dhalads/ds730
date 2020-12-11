public class FinalRunner {
    public static void main(String args[]) throws InterruptedException {
        Input input = null;
        Output output = null;
        try {
            input = new Input();
            input.load();            
            System.out.println(input.BldgNames.toString());
            System.out.println(input.RouteTimes.toString());
            output = new Output();
            output.setPInput(input);
            output.run();


        } catch (Exception e) {
            System.err.println(e.getMessage());
        } // end try-catch

    }// end main

}// end class
