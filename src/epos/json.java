package epos;

import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class json {
	
	 public static void main(String[] args) throws JSONException{

		   //create json object and put values in
		   JSONObject jsonObject = new JSONObject();
		   
		   jsonObject.put("Burger names", "Chicken burger");
		   jsonObject.put("Chicken Burger", "ulhaq");

		   
		   //create json array and add values
		   JSONArray jsonArray = new JSONArray();
		   jsonArray.put("chicken");
		   jsonArray.put("beef");
		   jsonArray.put("cheese");


		   // add json array to json object
		   jsonObject.put("Burger Names", jsonArray);
		   
		   //write all to a file
		   try {
			   FileWriter fw = new FileWriter("my_jsonEpos.json");
			   fw.write(jsonObject.toString());
			   fw.flush();
			   fw.close();
			   System.out.println("done");
			   
		   }
		   catch (Exception e) {
			   e.printStackTrace();
		   }

		   
	   }
	}