package com.Assign2ETL.BasePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CodeC {
	
	public static void transformationEngine(ArrayList<String> fileList) {
		
		MongoClient client = MongoClients.create("mongodb+srv://lokeshwartabjula:tabjula9729@democlusterloki.vkzinut.mongodb.net/?retryWrites=true&w=majority");
	    MongoDatabase db = client.getDatabase("articlesDB");
	    MongoCollection collection = db.getCollection("articles");
		Map<String,String> titleContentMap = new HashMap<String,String>();

	    for(String fileName : fileList) {
	    	File file = new File(fileName);
		    BufferedReader br;
		    String line;
		    String title=null;
		    String content=null;
			try {
				br = new BufferedReader(new FileReader(file));
//				line = br.readLine();
				while((line = br.readLine())!=null) {
					if(line.startsWith("Title: ")) {
						title = line.substring(7);
					}
					if(line.startsWith("Content: ")) {
						content = line.substring(9);
					}
					if(line!=null && content!=null) {
						titleContentMap.put(title, content);
						line=null;
						content=null;
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
        for(Map.Entry<String, String> singleEntry : titleContentMap.entrySet()) {
        	Document document = new Document("Title", singleEntry.getKey())
                    .append("Content", singleEntry.getValue());
        	collection.insertOne(document);
        	System.out.println("Inserted successfully");
        }
        
        client.close();

	    
	   

	}
}
	
	