package de.tu_dresden.crowd_db.remote.crowd_flower.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Entity;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.MalformedRequestException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.PaymentRequiredException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.RecordNotFoundException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.RequiredParameterMissingException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.ServerErrorException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.ValidationException;

public abstract class ServiceImpl<E extends Entity> implements Service<E> {

	
	protected String secretKey;	
	
	public ServiceImpl(String secretKey) {
		this.secretKey = secretKey;
	}
	
	
	protected <J> J makeRequest(String urlLocation) throws APIError {
		return makeRequest(urlLocation, null, "GET");
	}

	protected <J> J makeRequest(String urlLocation, String data, String method)
			throws APIError {
		URL url;
		log("-------------------- Begin request --------------------");
		log("Request for: " + urlLocation);
		log("Data: " + data);
		log("Method: " + method);
		try {
			url = new URL(urlLocation);
		} catch (MalformedURLException e) {
			throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.MalformedURLException(
					e);
		}

		try {
			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("charset", "utf-8");

			OutputStreamWriter wr = null;
			if (method != null ) {
				connection.setRequestMethod(method);
				connection.addRequestProperty("Content-Length", "0");
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
			}			
			if (data != null) {
				connection.setDoOutput(true);


				wr = new OutputStreamWriter(connection.getOutputStream());

				wr.write(data);
				wr.close();

			}

			log("Response code: " + connection.getResponseCode());
			log("Response message: " + connection.getResponseMessage());
			switch (connection.getResponseCode()) {

			case 400:
				throw new RequiredParameterMissingException();

			case 402:
				throw new PaymentRequiredException();

			case 404:
				throw new RecordNotFoundException();

			case 406:
				throw new MalformedRequestException();

			case 500:
				throw new ServerErrorException();

			}

			BufferedReader rd;

			rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			@SuppressWarnings("unchecked")
			J jsonResponse = (J) JSONValue.parse(rd);
			if (data != null) {
				wr.close();
			}
			rd.close();
			try {
				// in cases when the created or updated object is not valid
				// we may get response 200, but a hash with errors instead of the job data
				// so we raise a validation exception that can be intercepted by the createOrUpdate on JobRepository
				// in order to make a find by id request to return a valid object
				JSONObject responseAsJson = ((JSONObject)jsonResponse);
				if (responseAsJson.get("errors") != null) {
					JSONArray errors = (JSONArray) responseAsJson.get("errors");
					throw new ValidationException(errors.get(0).toString());
				}
			} catch (ClassCastException e) {
				
			}
			log("-------------------- Finish request --------------------");
			return jsonResponse;
		} catch (IOException e) {
			throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.IOException(e);
		}
	}

	private void log(Object message) {
		System.out.println(message.toString());
	}

	protected String encodeString(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (Exception e) {
			return s;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected List<Channels> parseChannels(JSONArray jsonArray) {
		List<Channels> ret = new ArrayList<Channels>();
		for (String channel: (List<String>)jsonArray) {
			ret.add(Channels.findByCode(channel));
		}
		return ret;
	}	
	
	protected String channelsToString(List<Channels> channels) {
		if (channels == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		
		for (Channels e : channels) {
			sb.append("&channels[]=" + encodeString(e.getCode()));
		}
		return sb.toString();
	}
	
	
	protected JSONObject makeUploadRequest(String urlLocation, String contenttype, File file) throws APIError {
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
			connection.setRequestProperty("Content-Type", contenttype);
			 
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
		  
		    
			switch (connection.getResponseCode()) {

			case 400:
				throw new RequiredParameterMissingException();

			case 402:
				throw new PaymentRequiredException();

			case 404:
				throw new RecordNotFoundException();

			case 406:
				throw new MalformedRequestException();

			case 500:
				throw new ServerErrorException();

			}

			BufferedReader rd;

			rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			@SuppressWarnings("unchecked")
			JSONObject jsonResponse = (JSONObject) JSONValue.parse(rd);
			return jsonResponse;		    
		    
		    
		} catch (IOException e) {
			throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.IOException(e);
		}
	}	
	

}
