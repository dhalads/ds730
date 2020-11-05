public class MyPi extends Thread{
	private int numPoints;

	public MyPi(int numPoints){
		this.numPoints = numPoints;
	}

	public void run(){
		int count = 0;
		while(count < numPoints){
			double x = Math.random(); //a value between 0 and 1
			double y = Math.random(); //a value between 0 and 1
			if(containedInCircle(x, y)){
				MyRunner.addCircle();
			}
			count++;
		}
	}
	public boolean containedInCircle(double x, double y){
		return Math.sqrt(Math.pow(x - .5, 2) + Math.pow(y - .5, 2)) <= .5;
	}
	
}
