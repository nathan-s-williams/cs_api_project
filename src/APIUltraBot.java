/*********************************************************************************************************************************************************
 * APIUltraBot Class:
 * Main method used to run the PircBot program. Connects to freenode to the #ultrabotchannel channel.
 * 
 * Created by Nathan Williams
*********************************************************************************************************************************************************/

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class APIUltraBot {
	
	public static void main(String[] args) throws NickAlreadyInUseException, IOException, IrcException {
		
		//Declare and instantiate PircBot by using the MyBot class that extends pircbot.
		MyBot bot = new MyBot();
        
        //Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC (irc.freenode.net). Output connection error message if error occurs while trying to connect.
        try {
        	bot.connect("irc.freenode.net");
        }
        catch(Exception e){
        	System.out.println(e.getMessage());
        	System.out.println("Can't connect: " + e);
        }
	}
}
