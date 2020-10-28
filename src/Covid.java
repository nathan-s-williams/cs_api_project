/********************************************************************************************************************************************************* 
 * Covid Class:
 * Class that is used to store the json data from the server. Each data member corresponds to a field in the json string.
 * 
 * Created by Nathan Williams
*********************************************************************************************************************************************************/

public class Covid {
	
	//Declare variables that correspond to json data fields.
	public String Country;
	private GlobalData Global;
	private CountryData[] Countries;
	
	//Private class used to capture nested json data members
	private class GlobalData{
		int NewConfirmed;
		int TotalConfirmed;
		int TotalRecovered;
	}
	
	//Private class used to capture nested json data members
	private class CountryData{
		String Country;
		int NewConfirmed;
		int TotalConfirmed;
		int TotalRecovered;
	}
	
	//Method used to return the specified location's (country's) index within the array of summary data which is organized by country.
	private int getCountryIndex(String loc) {
		
		for(int x = 0; x < Countries.length; x++) {
			if(Countries[x].Country.equalsIgnoreCase(loc)) {
				return x;
			}
		}
		
		return -1;
	}
	
	//Return the current statistics based on the location passed into the method.
	public String toString(String location) throws ArrayIndexOutOfBoundsException {
		
		//If the location is "global" or "world", return the total statistics around the globe.
		if(location.equalsIgnoreCase("Global")) {
			return "Globally, there are " + Global.NewConfirmed + " newly confirmed cases. The total confirmed cases is "
					+ Global.TotalConfirmed + " and the total recovered cases is " + Global.TotalRecovered + ". The total "
					+ "recovery rate to date is " + Math.round(((float)Global.TotalRecovered / (float)Global.TotalConfirmed) * 100) +"%.";
		}
		//If the location is a specific country, return that location's statistics.
		else {
			int index = getCountryIndex(location);
			return "There are " + Countries[index].NewConfirmed + " newly confirmed cases in " + location 
					+ ". The total confirmed cases is " + Countries[index].TotalConfirmed + " and the total recovered cases "
					+ "is " + Countries[index].TotalRecovered + ". The total recovery rate to date for " + location
					+ " is " + Math.round(((float)Countries[index].TotalRecovered / (float)Countries[index].TotalConfirmed) * 100) +"%.";
		}
	}
}
