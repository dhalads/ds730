public class MySimpleThread extends Thread{

    private long startingValue = 0;

    public MySimpleThread(long startingValue){
        this.startingValue = startingValue;
    }

    public void run(){
        long sum = 0;
        long current = startingValue;
        while(current < startingValue+210857175510l){
            sum += current;
            current++;
        }
        SimpleRunner.addSum(sum);
    }

}
