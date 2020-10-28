/*********************************************************************************************************************************************************
 * Json Class:
 * Used to handle API calls and deserialize the returned Json data.
 * 
 * APIs used
 * -------------
 * Weather api:
 * Url - http://api.openweathermap.org/data/2.5/weather?q=London&units=imperial&appid=aea61168b6e196541f64c8d876e5440e
 * 
 * COVID api site:
 * Url - https://api.covid19api.com/summary
 * 
 * Created by Nathan Williams
*********************************************************************************************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;


public class Json {
	
	//Set constant variable for API key
	//User key to the weather API. Needed to pull data in Json format.
	private final static String weatherAPIKey = "aea61168b6e196541f64c8d876e5440e";
	
	
	/********************************************************************************************************************************************************
	 * Method used to deserialize json. Connects to the specified url passed through its parameter list and returns the json data line by line appending
	 * it to a string variable. That string is returned to the calling method.
	 * 
	 * Error Handling:
	 *    MalformedURLException: Throws exception to the calling method.
	 *    IOException: Throws exception to the calling method
	 ********************************************************************************************************************************************************/
	private static String deserializeJsonString(String s) throws MalformedURLException,IOException{
		
		//Declare object variables for connecting to API and reading in the Json string data.
		HttpURLConnection connection;
		BufferedReader reader;
		StringBuffer response;
		String responseLine;
		String json = " ";
		
		//Declare and instantiate URL object using the s parameter and connect to the server.
		URL urlString = new URL(s);
		connection = (HttpURLConnection) urlString.openConnection();
		
		//connection parameters:
		//	-set request method
		//	-set timers for 30 seconds
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
		
		//Get the status of the connection.
		int iStatus = connection.getResponseCode();
		
		/********************************************************************************************************************************************************
		 * If the connection is bad as represented by an iStatus greater than 299, read in the error response from the server into the json string variable.
		 * If the connection is good then read in the data as a Json string into the json variable.
		 * Disconnect the server connection after the Json string has been returned.
		 ********************************************************************************************************************************************************/
		if(iStatus > 299) {
			reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			response = new StringBuffer();
			
			//Loop through each line of the returned Json string and append to the response StringBuffer. End while loop when there are no more lines to read.
			while((responseLine = reader.readLine()) != null) {
				response.append(responseLine);
			}//end while
			
			json = response.toString();
			
			connection.disconnect();
		}
		else {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			response = new StringBuffer();
			
			//Loop through each line of the returned Json string and append to the response StringBuffer. End while loop when there are no more lines to read.
			while((responseLine = reader.readLine()) != null) {
				response.append(responseLine);
			}//end while
			reader.close();
			
			json = response.toString();
			
			connection.disconnect();
	
		}
		
		return json;
	}
	
	/********************************************************************************************************************************************************
	 * Method used to get the weather responses for user questions. Uses zip code to pull data from server. Returns data in imperial units and not the
	 * the default kelvin.
	 * 
	 * Pass the API url to the deserializeJsonString method and return the json string to the json variable.
	 * The web url is sent along with the city parameter and API key from the weatherAPIKey constant variable.
	 * Use the gson object to parse the data into the Weather class (using fromJson) collecting all the relevant data into each of the class' data fields.
	 * Return the string to the calling method.
	 * 
	 * Error Handling:
	 *    NullPointerException: If the API call returns null, return message to user warning that the city may have been misspelled or the format wrong.
	 *    MalformedURLException: Thrown from deserializeJsonString method. Return message to user that the connection to the server might be bad.
	 *    IOException: Thrown from deserializeJsonString method. Return message to user that the connection to the server might be bad.
	 ********************************************************************************************************************************************************/
	public static String getWeather(String city){
		
		//Declare and instantiate Gson object
		Gson gson = new Gson();
		
		
		try {
			String json = deserializeJsonString("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=imperial&appid=" + weatherAPIKey);
			Weather w = gson.fromJson(json, Weather.class);
			
			return w.toString();
		
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return "Oops, looks like something went wrong. Please check to make sure the city name is spelled correctly "
					+ "and that the format is like \"What is the weather in the city of <name>?\"";
		}
		catch(MalformedURLException e){
			System.out.println(e.getMessage());
			return "Looks like there was trouble connecting to the server. Please try again or adjust your query";
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			return "Looks like there was trouble connecting to the server. Please try again or adjust your query";
		}

	}
	
	/********************************************************************************************************************************************************
	 * Method used to get the weather responses for user questions. Uses zip code to pull data from server. Returns data in imperial units and not the
	 * the default kelvin. 
	 * 
	 * Pass the API url to the deserializeJsonString method and return the json string to the json variable.
	 * The web url is sent along with the zip code parameter and API key from the weatherAPIKey constant variable.
	 * Use the gson object to parse the data into the Weather class (using fromJson) collecting all the relevant data into each of the class' data fields.
	 * Return the string to the calling method.
	 * 
	 * Error Handling:
	 *    NullPointerException: If the API call returns null, return message to user warning that the zip code may be incorrect or the format wrong.
	 *    MalformedURLException: Thrown from deserializeJsonString method. Return message to user that the connection to the server might be bad.
	 *    IOException: Thrown from deserializeJsonString method. Return message to user that the connection to the server might be bad.
	 ********************************************************************************************************************************************************/
	public static String getWeather(int zip){
		
		//Declare and instantiate Gson object
		Gson gson = new Gson();
		
		try {
			String json = deserializeJsonString("http://api.openweathermap.org/data/2.5/weather?zip=" + zip + "&units=imperial&appid=" + weatherAPIKey);
			Weather w = gson.fromJson(json, Weather.class);
			
			return w.toString();
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return "Oops, looks like something went wrong. Please check to make sure the zip code is entered correctly "
					+ "and that the format is like \"What is the weather in zip <name>?\"";
		}
		catch(MalformedURLException e){
			System.out.println(e.getMessage());
			return "Looks like there was trouble connecting to the server. Please try again or adjust your query";
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			return "Looks like there was trouble connecting to the server. Please try again or adjust your query";
		}
	}
	
	
	/********************************************************************************************************************************************************
	 * Method used to get the pandemic data. Uses the location passed by the message parameter to get data for that specific region. Also returns the data
	 * for all cases around the world if asked by user. Location value passed is checked against the available countries listed on the server by use of
	 * a helper method called getLocationFromMessage.
	 * 
	 * Pass the API url to the deserializeJsonString method and return the json string to the json variable.
	 * The web url for country summaries is sent to the deserializeJsonString method. No additional variables are passed since the server returns a running list (array)
	 * of countries with various covid statistics.
	 * 
	 * The web url for the list of available contries is sent to the deserializeJsonString method. This returns the valid list of countries for which the
	 * method can return data for.
	 * 
	 * Use the gson object to parse the summary data into the Covid class (using fromJson) collecting all the relevant data into each of the class' data fields.
	 * Use the gsonCountryList object to parse all the available countries into a second Covid class.
	 * 
	 * Pass the parsed country list (countryList) and the location value from the method parameter to the getLocationFromMessage method. If the location is
	 * verified then it will be returned, otherwise null will be returned.
	 * 
	 * Call the toString method from the Covid object holding the summary data. Use the verified location (or null) to retrieve the appropriate response.
	 * Return the response to the calling method.
	 * 
	 * Error Handling:
	 *    NullPointerException/ArrayIndexOutOfBoundsException: Return message to user warning that the location may be incorrect or the format wrong.
	 *    MalformedURLException: Thrown from deserializeJsonString method. Return message to user that the connection to the server might be bad.
	 *    IOException: Thrown from deserializeJsonString method. Return message to user that the connection to the server might be bad.
	 ********************************************************************************************************************************************************/
	public static String getCovid(String message) {
		
		//Declare and instantiate two gson objects. One for the summary data and the other for verifying the location.
		Gson gson = new Gson();
		Gson gsonCountryList = new Gson();
		
		try {
			
			//Deserialize and parse the summary data.
			String json = deserializeJsonString("https://api.covid19api.com/summary");
			Covid c = gson.fromJson(json, Covid.class);
			
			//Deserialize and parse the country list data.
			String jsonCountryList = deserializeJsonString("https://api.covid19api.com/countries");
			Covid[] countryList = gsonCountryList.fromJson(jsonCountryList, Covid[].class);
			String location = getLocationFromMessage(countryList, message);
			
			//Return the appropriate response using the location parameter.
			return c.toString(location);
			
		}
		catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			return "Oops, looks like something went wrong. Please make sure you have chosen a valid country or that the spelling"
					+ " is correct.";
		}
		catch(MalformedURLException e){
			System.out.println(e.getMessage());
			return "Looks like there was trouble connecting to the server. Please try again or adjust your query";
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
			return "Looks like there was trouble connecting to the server. Please try again or adjust your query";
		}
	}
	
	
	//Method used to validate the location parameter passed into the getCovid method. Uses a list of available countries from the source to
	//check to see if the location matches one of those countries.
	private static String getLocationFromMessage(Covid[] list, String message) {
		
		//If the location is "Global/global" or "World/world" then return "Global"
		if(message.toLowerCase().contains("global") || message.toLowerCase().contains("world")) {
			return "Global";
		}
		//Compare the location to the list of available countries. Return the country name if there is a match. Otherwise, return null.
		else {
			for(int x = 0; x < list.length; x++) {
				if(message.toLowerCase().contains(list[x].Country.toLowerCase().trim())) {
					return list[x].Country;
				}
			}
		}
		
		return null;
	}
}


