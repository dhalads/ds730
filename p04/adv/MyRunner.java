public class MyRunner{
	public static double totalInCircle = 0;
	
	public synchronized static void addCircle(){
		totalInCircle++;
	}
	
	public static void main(String args[]) throws InterruptedException{
		int numPoints = Integer.parseInt(args[0]);
		int numThreads = Integer.parseInt(args[1]);
		MyPi[] theWorkers = new MyPi[numThreads];
		for(int i=0; i<numThreads; i++){
			theWorkers[i] = new MyPi(numPoints);
			theWorkers[i].start();
		}

		for(MyPi mp : theWorkers){
			if(mp.isAlive()){
				mp.join(); //make sure to wait for all threads to finish
			} 
		}
		double answer = 4 * totalInCircle / (numPoints*numThreads*1.0);
		System.out.println(answer+" "+totalInCircle+" "+numPoints+" "+numThreads);
	}
}
