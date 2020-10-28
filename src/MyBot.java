/********************************************************************************************************************************************************* 
 * MyBot Class:
 * Class that inherits the PircBot parent library. Defines specific details for this use case which includes a custom name, user triggered responses and
 * onConnection messages.
 * 
 * Created by Nathan Williams
*********************************************************************************************************************************************************/

import org.jibble.pircbot.*;

public class MyBot extends PircBot{
	    
	//Define and instantiate constant variable for the channel name.
	final private String channel = "#ultrabotchannel";
	
	//No arg constructor that sets the name of the bot.
    public MyBot() {
        this.setName("CS2336UltraBot");
    }
    
    //Method that triggers when a connection to the server is established.
    //Once a connection is established, the bot will join the #ultrabotchannel by way of joinChannel(channel).
    //An initial set of messages will be sent to the channel describing the bot's purpose after it connects to the channel.
    public void onConnect() {
    	
    	joinChannel(channel);
    	sendMessage(channel, "Hello! My name is CS2336UltraBot.");
    	sendMessage(channel, "I can answer some basic questions about the weather and statistics on the recent pandemic. Just ask me things"
    			+ " like,\"What is the weather in the city of Dallas\" or \"What are the Covid statistics in the United States of America?\"");
    	sendMessage(channel, "If you would like a full list of commands, just type \"UltraBot Help\"");
    }
    
    //Method that triggers after each method sent to the IRC chat.
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
    	/********************************************************************************************************************************************************
    	 * Returns a response based on the following message parameters:
    	 * 
    	 * Message contains "time"
    	 * ----------------------------------------------------
    	 * Return the current time to the chat.
    	 * 
    	 * Message contains the bot's name and "help"
    	 * ----------------------------------------------------
    	 * Return helpful FAQs on how to use the bot.
    	 * 
    	 * Message contains "weather" and ("<city>" or "<zip>")
    	 * ----------------------------------------------------
    	 * Return the current weather for the specified city or zip. If the message only contains "weather" then the response will ask the user
    	 * specify a city or zip.
    	 * 
    	 * Message contains "covid"
    	 * ----------------------------------------------------
    	 * Return the to date covid statistics based on the location specified by the user. If the location is invalid, then the program will prompt
    	 * the user to input a valid location. Location can also be global.
    	 * 
    	 * 
    	 * Error Handling:
    	 *    NumberFormatException - Request the user to enter the proper zip code.
    	 *    Exception - Since this is a general catch-all exception, notify the user that something went wrong.
    	 ********************************************************************************************************************************************************/
    	try {
	        if (message.equalsIgnoreCase("time")) {
	            String time = new java.util.Date().toString();
	            sendMessage(channel, sender + ": The time is now " + time);
	        }
	        else if(message.toLowerCase().contains("ultrabot") && message.toLowerCase().contains("help")){
	        	sendMessage(channel,"Weather:");
	        	sendMessage(channel,"You can get details about the current weather, temperature, humidity and wind speed by city and zipcode. "
	        			+ "To get the weather by city, ask \"What is the weather in the city of <city>?\" To get the weather by zip code, "
	        			+ "ask \"What is the weather in zip <zip code>?\"");
	        	sendMessage(channel,"Pandemic:");
	        	sendMessage(channel,"You can get details about the pandemic for each country or around the whole world. To get data by country, ask "
	        			+ "\"What is the current covid data in the United States of America\" or \"What is the current covid data around the world?\"");
	        }
	        else if (message.toLowerCase().contains("weather") && message.toLowerCase().contains("city")){
	        	String city = message.substring(message.toLowerCase().indexOf("city of") + 7).trim();
	        	sendMessage(channel, Json.getWeather(city));
	        }
	        else if (message.toLowerCase().contains("weather") && message.toLowerCase().contains("zip")){
	        	int zip = Integer.parseInt(message.substring(message.toLowerCase().indexOf("zip") + 3).trim());
	        	sendMessage(channel, Json.getWeather(zip));
	        }
	        else if (message.toLowerCase().contains("weather")) {
	        	sendMessage(channel,"Please specify the city or zipcode. Recommended format is, \"What is the weather in city of <insert name>?\" "
	        			+ "or \"What is the weather in zip <insert zip>?\"");
	        }
	        else if (message.toLowerCase().contains("covid")) {
	        	sendMessage(channel, Json.getCovid(message.toLowerCase()));
	        }
    	}
    	catch(NumberFormatException e){
    		System.out.println(e.getMessage());
    		sendMessage(channel, "Please check to make sure the zip code is entered correctly and in the format,"
    				+ "\"What is the weather in zip <zipcode>?\"");
		
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
			sendMessage(channel, "Oops, looks like something bad happened. We're looking into it and will have it fixed in no time.");
		}
    }
}

