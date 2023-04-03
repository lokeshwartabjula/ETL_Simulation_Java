package com.Assign2ETL.BasePackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CodeA {

	public static String extractionEngine(String[] inputKeywords) {
		
		StringBuilder responseString = new StringBuilder();
		for(String keyword : inputKeywords) {
			
			try {
				responseString = responseString.append(getResponseString(keyword)) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
			
			System.out.println(responseString.toString());
		return responseString.toString();
		
		//PART 1 -- ignoring the following implementation as professor mentioned to go with http request
		
//		newsApiClient.getEverything(
//				  new EverythingRequest.Builder()
//				          .q(keyword)
//				          .build(),
//				  new NewsApiClient.ArticlesResponseCallback() {
//				      
//				      public void onSuccess(ArticleResponse response) {
////				          System.out.println("EVERYTHING  "+response.getArticles().get(0).getTitle());
//				          System.out.println(response);
//
//				      }
//
//				      
//				      public void onFailure(Throwable throwable) {
//				          System.out.println(throwable.getMessage());
//				      }
//				  }
//				);
//        Thread.sleep(1000); // Pause for one second
		
	}
	
	private static String getResponseString(String keyword) throws Exception {
		// TODO Auto-generated method stub
		
		
		HttpURLConnection conObj;
        StringBuffer response = new StringBuffer();
		Integer statusCode = null;
		String url = Constants.NEWS_API_URL;
		url = url.replace("KEYWORD_VALUE", keyword);
		try {
			URL urlObj = new URL(url);
			conObj = (HttpURLConnection) urlObj.openConnection();

			conObj.setRequestMethod("GET");
			
			 statusCode = conObj.getResponseCode();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new Exception("error in forming URL object");
		}
		if(statusCode == 200) {
			// if request is processed successfully
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(conObj.getInputStream()));
            String inputLine;
            while ((inputLine = inputBuffer.readLine()) != null) {
                response.append(inputLine);
            }
            inputBuffer.close();
		}
		
		
		return response.toString();
	}
}
