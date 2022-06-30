package com.jaw.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
//This Class is used for Send the http url and get response
@Component
public class SendURL {
	// Logging
		Logger logger = Logger.getLogger(SendURL.class);
	//Hit the url a
		String sendURLAndGetResponse(String url){
			logger.debug("Sending url"+url);
			
			String output="";
			String encodedURL="";
			//Encoding the url for space and tab
			try {
				//String outputURL=URIUtil.encodeQuery("http://www.google.com?q=a b");
				encodedURL=URIUtil.encodeQuery(url);
				
			} catch (URIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("output urlllllllllllll"+encodedURL);
			logger.debug("Encoded Url"+encodedURL);
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(encodedURL);		
			
			HttpResponse response = null;
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.debug("Response Code : "+response.getStatusLine().getStatusCode());
			System.out.println("Response Code : "  + response.getStatusLine().getStatusCode());
		 
			BufferedReader rd = null;
			try {
				rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 
			StringBuffer result = new StringBuffer();
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			output=result.toString();
			logger.debug("response output : "+result.toString());
			return output;
		}
}
