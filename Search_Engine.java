package advaAlgo_SearchEng;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class Search_Engine 
{
static logic_search myEngine;
static String []occurrence_list;
static String input[];

public static void main(String[] args) 
{
	occurrence_list = new String[5];
		input = new String[5];
		occurrence_list[0]="https://www.icc-cricket.com/champions-trophy";
		occurrence_list[1]="http://www.mmafighting.com/ufc";
		occurrence_list[2]="https://en.wikipedia.org/wiki/Counter-Strike";
		occurrence_list[3]="https://www.activision.com/";
		occurrence_list[4]="http://www.wwe.com/shows/wrestlemania";
		
		int index = 0;
		for(String ocl : occurrence_list){
			
		
		URL url;
		URLConnection connection;
		BufferedReader br;
		try {
			url = new URL(ocl);
			connection = url.openConnection();

	        // open the stream and put it into BufferedReader
	        br = new BufferedReader(
	                           new InputStreamReader(connection.getInputStream()));
	        String inputLine ="";
            while (br.readLine() != null) {
                    
            	inputLine = inputLine+ ""+ br.readLine();
        }
            input[index] = ""+inputLine;
            br.close();
            index++;
		}
		
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		System.out.println("Please Hold on while it loads...."+"\nYou might have to run again if you type before you are asked to enter the word");
		myEngine = new logic_search(5, input);
		myEngine.insert();
		
		
		Scanner scanner=new Scanner(System.in);
		System.out.println("Please Enter the word:- ");
		String input = "" + scanner.nextLine();
		scanner.close();
			
		ArrayList<String> result = myEngine.search(input.toLowerCase());
		System.out.println("Search Results:");
		if(!result.isEmpty()){
		for(String r: result){
		System.out.println(occurrence_list[Integer.parseInt(r)]);
		}
		
		}
		
		else
		System.out.println("Not Found!");
			
	}
			
	}

