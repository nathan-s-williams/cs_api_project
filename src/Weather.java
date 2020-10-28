/********************************************************************************************************************************************************* 
 * Weather Class:
 * Class that is used to store the json data from the server. Each data member corresponds to a field in the json string.
 * 
 * Created by Nathan Williams
*********************************************************************************************************************************************************/

public class Weather {
	
	//Declare variables that correspond to json data fields.
	private TodaysTemperature main;
	private TodaysWeather[] weather;
	private Wind wind;
	private String name;
		
	private class TodaysTemperature{
		double temp;
		double feels_like;
		int humidity;
		
	}
	
	//Private class used to capture nested json data members
	private class TodaysWeather{
		String description;
	}
	
	//Private class used to capture nested json data members.
	private class Wind{
		double speed;
		int deg;
		
	}
	
	//Return the current weather conditions for the specified location. Variables returned are: location, description, temperature,
	//feels like, humidity, wind speed and wind direction.
	public String toString(){
		return "Current weather for " + name + ": " + weather[0].description + ". The temperature is " + main.temp + " and feels like "
				+ main.feels_like + " with a humidity of " + main.humidity + ". Wind speed is " + wind.speed + "mph and blowing at "
				+ wind.deg + " degrees.";
	}
	
	
}