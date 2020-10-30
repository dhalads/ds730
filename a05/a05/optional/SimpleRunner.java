public class SimpleRunner{
    
    private static long parallelSum = 0;

    public synchronized static void addSum(long value){
        parallelSum += value;
    }

    public static void main(String args[]){
        long value = 1;        
        long localSum = 0;        
        long countUpTo = 210857175510l;
        int numberThreads = Integer.parseInt(args[0]);
        
        //run it serially
        long start = System.currentTimeMillis();
        while(value < countUpTo*numberThreads){
            localSum += value;
            value++;
        }
        System.out.println(localSum);
        System.out.println("Time to run serially: "+(System.currentTimeMillis() - start));
        
        //run it in parallel
        start = System.currentTimeMillis();
        MySimpleThread[] threads = new MySimpleThread[numberThreads];
        for(int i=0; i<numberThreads; i++){
            threads[i] = new MySimpleThread(countUpTo*i);
            threads[i].start();
        }
        
        for(int j=0; j<numberThreads; j++){
            try{
                threads[j].join();
            }catch(Exception e){
                System.out.println("something went really wrong");
            }
        }
        
        System.out.println(parallelSum);
        System.out.println("Time to run parallel: "+(System.currentTimeMillis() - start));
    }    
}
