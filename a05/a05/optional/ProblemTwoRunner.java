import java.util.*;

public class ProblemTwoRunner{

	private static long numberSwitchingWinners = 0;
	private static long numberStayWinners = 0;	

	public synchronized static void addSwitchingWinner(long numWins){
		numberSwitchingWinners += numWins;
	}	

	public synchronized static void addStayWinner(long numWins){
		numberStayWinners += numWins;
	}

	public static void main(String args[]) throws Exception{
		long start = System.currentTimeMillis();
		long numSimulations = Long.parseLong(args[0]);

		//since we have 8 cores on our EC2 instance, I will split up the work 8 ways.		
		int numCores = 8;
		MySwitchingThread[] workers = new MySwitchingThread[numCores];
		for(int i=0; i<numCores; i++){
			//create a new thread and start it
			workers[i] = new MySwitchingThread(numSimulations/numCores);
			workers[i].start();
		}
		
		//make sure the workers are finished before the master finishes.
		for(int i=0; i<workers.length; i++){
			//if the worker thread is already done, this will just continue on to the next thread.
			workers[i].join();
		}
	
		System.out.println("If switched: "+numberSwitchingWinners/(numSimulations*1.0));

		MyStayThread[] workersStay = new MyStayThread[numCores];
        	for(int i=0; i<numCores; i++){
			//create a new thread and start it
			workersStay[i] = new MyStayThread(numSimulations/numCores);
			workersStay[i].start();
		}

        	//make sure the workers are finished before the master finishes.
	        for(int i=0; i<workersStay.length; i++){
        	    //if the worker thread is already done, this will just continue on to the next thread.
	            workersStay[i].join();
        	}

        	System.out.println("If switched: "+numberStayWinners/(numSimulations*1.0));
	

		long end = System.currentTimeMillis();
		System.out.println("Time taken: "+(end-start));
	}
}

