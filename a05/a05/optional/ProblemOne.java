import java.util.*;

public class ProblemOne{

	public static void main(String args[]){
		HashMap<String, TreeMap<String, Integer>> allStates = new HashMap<>();

		Scanner input = new Scanner(System.in);
		String state = "";
		String city = "";
		Integer population = 0;
		while(!state.equals("QUIT")){
			System.out.print("Enter state city population: ");
			state = input.next();
			if(!state.equals("QUIT")){
				city = input.next();
				population = input.nextInt();
				//Check to see if the state was already entered
				if(allStates.containsKey(state)){
					//If yes, then retrieve cities and add current city/population
					TreeMap<String, Integer> tempCities = allStates.get(state);
					tempCities.put(city, population);  
					//If the city already exists, it is overwritten. How could the code 
					//be modified to keep track of a list of populations instead of just 
					//one population?
				} else{
					//It's a new state, add state to HashMap with first city/population
					TreeMap<String, Integer> tempCities = new TreeMap<>();
					tempCities.put(city, population);
					allStates.put(state, tempCities);
				}
			}
		}

		for(Map.Entry<String, TreeMap<String, Integer>> entry : allStates.entrySet()){
			//entry.getKey is a state
			//entry.getValue is a TreeMap<String, Integer>, i.e. a city/population
			for(Map.Entry<String, Integer> cities : entry.getValue().entrySet()){
				System.out.println(entry.getKey()+" "+cities.getKey()+": "+cities.getValue());
			}
		}
	}
}
