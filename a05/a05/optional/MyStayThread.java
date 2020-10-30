import java.util.*;
import java.io.*;

public class MyStayThread extends Thread{
	
	private long numSimulations;
	
	public MyStayThread(long n){
		numSimulations = n;
	}
	
	public void run(){
		long numWins = 0;
		for(int test=0; test<numSimulations; test++){
			try{
                Thread.currentThread().sleep(1);
            }catch(Exception e){}
			int[] doors = {0,0,0};
			doors[(int)(Math.random()*3)] = 1;
			//doors is an array that represents the goats and the prize, the array 
			//will be {0,0,1}, {0,1,0} or {1,0,0} with the 1 being the prize
			int choice = (int)(Math.random()*3); //player chooses door 0, 1 or 2
			if(doors[choice]==1){
				//The player chose the winning door initially.
				//The host will open either door and the player will switch doors.
				//The player will win.
				numWins++;
			} else{
				//The player chose a door with a goat.
				//The remaining 2 doors have a prize and a goat behind them respectively.
				//The host will open up the door with the goat leaving the other door
				//to contain the prize. The player will not switch. The player loses.
			}
		}
		//Once finished, tell the Runner thread how many winners there were.
		ProblemTwoRunner.addStayWinner(numWins);
	}
}

