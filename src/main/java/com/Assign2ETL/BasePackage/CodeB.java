package com.Assign2ETL.BasePackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeB {
	
	public static ArrayList<String> dataProcessingEngine(String responseString) {
		
		Map<String,String> titleContentMap = new HashMap<String,String>();
		
		Pattern titlePattern = Pattern.compile("\"title\":\"([^\"]*)\"");
        Matcher titleMatcher = titlePattern.matcher(responseString);
        
        Pattern contentPattern = Pattern.compile("\"content\":\"([^\"]*)\"");
        Matcher contentMatcher = contentPattern.matcher(responseString);
        
        while (titleMatcher.find() && contentMatcher.find()) {
        	if(contentMatcher.group(1).contains("null"))
        		continue;
        	String cleanContent = cleanContent(contentMatcher.group(1));
        	titleContentMap.put(titleMatcher.group(1), cleanContent);
            
        }
        
        printAllArticlesInConsole(titleContentMap);
        
        return printAllArticlesToFiles(titleContentMap);
			
    
	}

	private static ArrayList<String> printAllArticlesToFiles(Map<String, String> titleContentMap) {
		// TODO Auto-generated method stub
		
		Integer fileNo = 1;
        Integer entryCount = 0;
        ArrayList<String> fileList = new ArrayList<String>();
        
        for(Map.Entry<String, String> singleEntry : titleContentMap.entrySet()) {
        	if(entryCount%5==0) {
        		try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("outputFile"+fileNo+".txt"));
//                    fileList[fileNo-1]="outputFile"+fileNo+".txt";
                    fileList.add("outputFile"+fileNo+".txt");
                    writer.write("Title: "+singleEntry.getKey() + "\n" + "Content: "+singleEntry.getValue() + "\n");
                    writer.close();
                    fileNo++;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
        	}else { //APPEND ENTRIES
        		// Append to current file
                String fileName = "outputFile" + (fileNo - 1) + ".txt";
                try {
                    BufferedWriter buffWriterObj = new BufferedWriter(new FileWriter(fileName, true));
                    buffWriterObj.write("Title: "+singleEntry.getKey() + "\n" + "Content: "+singleEntry.getValue() + "\n");
                    buffWriterObj.close();
                } catch (IOException e) {
                	System.out.println(e.getMessage());
                    System.exit(0);                }
        	}
        	entryCount++;
        }
        
		return fileList;
	}

	private static String cleanContent(String noisyContent) {
		String cleanContent = noisyContent.replaceAll("\\\\r\\\\n","");
//		cleanContent = cleanContent.replaceAll("...",".");
		cleanContent = cleanContent.replaceAll("\\[.*\\]", "");
		
		// TODO Auto-generated method stub
		return cleanContent;
	}

	private static void printAllArticlesInConsole(Map<String, String> titleContentMap) {
		// TODO Auto-generated method stub
		Integer articleNumber = 1;
		for (Map.Entry<String, String> entry : titleContentMap.entrySet()) {
    	    String title = entry.getKey();
    	    String content = entry.getValue();
    	    System.out.println("Article "+articleNumber++);
    	    System.out.println("Title: " + title);
    	    System.out.println("Content: " + content);
    	}	
		
	}

}
