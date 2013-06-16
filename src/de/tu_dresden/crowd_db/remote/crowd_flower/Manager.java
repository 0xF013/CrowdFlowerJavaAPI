package de.tu_dresden.crowd_db.remote.crowd_flower;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.tu_dresden.crowd_db.remote.crowd_flower.config.ApiConfig;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.MalformedRequestException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.PaymentRequiredException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.RecordNotFoundException;



public class Manager {

	private String secretKey;

	private TypeManager typeManager;
	
	private ApiConfig config;

	
	public Manager(ApiConfig config) {
		this.config = config;
		this.secretKey = config.getSecretKey();
		this.typeManager = new TypeManager(
			config.getJobId(JobType.RELATIONSHIP), 
			config.getJobId(JobType.DISAMBIGUATION), 
			config.getJobId(JobType.TYPE_MATCHING)
		);
	}	
	
	private void log(Object message) {
		System.out.println(message.toString());
	}

	
	private JSONObject makeRequest(String urlLocation) throws APIError {
		return makeRequest(urlLocation, null);
	}
	
	private JSONObject makeRequest(String urlLocation, String data) throws APIError {
		URL url;
		log("Request for: " + urlLocation);
		log("Data: " + data);
		try {
			url = new URL(urlLocation);
		} catch (MalformedURLException e) {
			throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.MalformedURLException(e);
		}
		
		try {
			HttpURLConnection connection;
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestProperty("charset", "utf-8");

			OutputStreamWriter wr = null;
			if (data != null) {
				connection.setDoOutput(true);
				wr = new OutputStreamWriter(connection.getOutputStream());
				wr.write(data);
				wr.flush();				
			}
			
			log("Response code: " + connection.getResponseCode());
			log("Response message: " + connection.getResponseMessage());
			switch (connection.getResponseCode() ) {
			 case 402: throw new PaymentRequiredException();
				
			 case 406: throw new MalformedRequestException();
			 
			 case 404: throw new RecordNotFoundException();
			}		
			
			
			BufferedReader rd;
	
			rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			JSONObject jsonResponse = (JSONObject) JSONValue.parse(rd);
			if (data != null) {
				wr.close();
			}			
			rd.close();
			return jsonResponse;
		} catch (IOException e) {
			throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.IOException(e);
		}
	}
	

	private void makeRequest(String urlLocation, String contenttype, File file) throws APIError {
		URL url;
		try {
			System.out.println(urlLocation);
			url = new URL(urlLocation);
		} catch (MalformedURLException e) {
			throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.MalformedURLException(e);
		}
		
		try {
			HttpURLConnection connection;
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Type", "text/csv");
			 
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			
	        BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		   
		    int i;
		    // read byte by byte until end of stream
		    while ((i = bis.read()) >= 0) {
		     bos.write(i);
		    }
		    
		    bos.close();
		    bis.close();
		    connection.disconnect();
		  
		    System.out.println(((HttpURLConnection)connection).getResponseCode());
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	private String getChannelsString(List<String> channels) {
		StringBuilder sb = new StringBuilder();
		for (String c:channels) {
			sb.append("&channels[]=");
			sb.append(c);
		}
		return sb.substring(1);
	}

	public void placeOrder(JobType type) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/orders.json?key=" + secretKey;
		System.out.println(urlLocation);
		JSONObject jsonResponse = makeRequest(urlLocation, "&debit[units_count]=" 
				+ String.valueOf(config.getUnitsCount(type)) + "&" + getChannelsString(config.getChannels(type)));
		System.out.println(jsonResponse);
		
	}	

	public boolean getAnswer(QuestionData questionData) throws APIError {
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(questionData.getTypeId()) + "/units/"
				+ questionData.getId() + ".json?key=" + secretKey;
		JSONObject jsonResponse = makeRequest(urlLocation);
		System.out.println(jsonResponse);
		Object agg = ((JSONObject) ((JSONObject) jsonResponse.get("results"))
				.get("answer")).get("agg");
		return agg != null && agg.toString() == "1";
	}

	public void setUnitsPerAssignment(JobType type, int payment) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + ".json?key=" + secretKey;
		//JSONObject jsonResponse = 
		makeRequest(urlLocation,"&job[payment_cents]=" + payment);
    		
	}
	
	public QuestionData createJobUnit(JobType type, String term1,
			String term2, String instr) throws APIError, UnsupportedEncodingException {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/units.json?key=" + secretKey;
		String data = "&unit[data][term1]=" + URLEncoder.encode(term1, "UTF-8")
					+ "&unit[data][term2]=" + URLEncoder.encode(term2, "UTF-8")
					+ "&job[instructions]=" + URLEncoder.encode(instr, "UTF-8");
		
				
		JSONObject jsonResponse = makeRequest(urlLocation, data);
		System.out.println(jsonResponse);
		return new QuestionData(Integer.valueOf(jsonResponse.get("id")
				.toString()), typeId);
	}	
	
	public void pauseJob(JobType type) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/pause.json?key=" + secretKey;
		System.out.println(urlLocation);
		makeRequest(urlLocation);					    		
	}
	
	public void resumeJob(JobType type) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/resume.json?key=" + secretKey;
		makeRequest(urlLocation);					    		
	}
	
	
	public void cancelJob(JobType type) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/cancel.json?key=" + secretKey;
		makeRequest(urlLocation);					    		
	}
	
	public void getJobChannels(JobType type) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/channels?key=" + secretKey;
		JSONObject jsonResponse = makeRequest(urlLocation);		
		for (Iterator<Map.Entry> it = jsonResponse.entrySet().iterator(); it.hasNext();) {
			  Map.Entry entry = it.next();
			  System.out.println(entry.getKey() + " " + entry.getValue());
			}
	}
	
	public void getJobStatus(JobType type) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/ping?key=" + secretKey;
		JSONObject jsonResponse = makeRequest(urlLocation);		
		for (Iterator<Map.Entry> it = jsonResponse.entrySet().iterator(); it.hasNext();) {
			  Map.Entry entry = it.next();
			  System.out.println(entry.getKey() + " " + entry.getValue());
			}
	}
	
	public void uploadData(JobType type, File file) throws APIError {
		int typeId = typeManager.getTypeId(type);
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(typeId) + "/"
				+ "upload?key=" + secretKey;
		makeRequest(urlLocation, "text/csv", file);		
	}
	
	
	
	public void getAccountInformation() throws APIError {
		String urlLocation = "https://api.crowdflower.com/v1/account.json?key="
				+ secretKey;
		JSONObject jsonResponse = makeRequest(urlLocation);
				
        for (Iterator<Map.Entry> it = jsonResponse.entrySet().iterator(); it.hasNext();) {
		  Map.Entry entry = it.next();
		  System.out.println(entry.getKey() + " " + entry.getValue());
		}
		
	}



}
