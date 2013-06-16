package de.tu_dresden.crowd_db.remote.crowd_flower.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONObject;

import de.tu_dresden.crowd_db.remote.crowd_flower.JobType;
import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.DataMapper;
import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.impl.JobDataMapper;
import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.impl.OrderDataMapper;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Job;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Order;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;

public class DataService extends ServiceImpl<Order> {


	private String url = "https://api.crowdflower.com/v1/jobs";	
	private DataMapper<Order> dataMapper = new OrderDataMapper();
	private DataMapper<Job> jobDataMapper = new JobDataMapper();
	
	public DataService(String secretKey) {
		super(secretKey);
	}

	private String getUrl(int jobId) {
		return url + "/" + String.valueOf(jobId) + "/orders.json?key=" + secretKey;
	}
	
	private String getUrl(int jobId, int orderId) {
		return url + "/" + String.valueOf(jobId) + "/orders/" + String.valueOf(orderId) + ".json?key=" + secretKey;
	}	
	
	public Order createOrder(Job job, int unitsCount, List<Channels> channels) throws APIError {
		return createOrder(job.getId(), unitsCount, channels);
	}
	
	
	public Order createOrder(int jobId, int unitsCount, List<Channels> channels) throws APIError {
		JSONObject response = makeRequest(getUrl(jobId) + "&debit[units_count]=" + String.valueOf(unitsCount) + channelsToString(channels), "", "POST");
		return dataMapper.jsonToEntity(response, new Order());
	}	
	
	public Order getOrder(int jobId, int orderId) throws APIError {
		JSONObject response = makeRequest(getUrl(jobId, orderId));
		return dataMapper.jsonToEntity(response, new Order());
	}
	
	public Job uploadData(int jobId, File file, String contentType) throws APIError {
		String urlLocation = "https://api.crowdflower.com/v1/jobs/"
				+ String.valueOf(jobId) + "/"
				+ "upload?key=" + secretKey;
		JSONObject json = makeUploadRequest(urlLocation, contentType, file);
		return jobDataMapper.jsonToEntity(json, new Job());
	}
	
	public Job uploadData(Job job, File file, String contentType) throws APIError {
		return uploadData(job.getId(), file, contentType);		
	}	
	
	public Job uploadData(File file, String contentType) throws APIError {
		String urlLocation = "https://api.crowdflower.com/v1/jobs/upload?key=" + secretKey;
		JSONObject response = makeUploadRequest(urlLocation, contentType, file);	
		return jobDataMapper.jsonToEntity(response, new Job());
	}
	
	public void download(int jobId, String filename) throws APIError {
		String urlLocation = "https://api.crowdflower.com/v1/jobs/" + String.valueOf(jobId) + ".csv?key=" + secretKey;
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try
        {
                in = new BufferedInputStream(new URL(urlLocation).openStream());
                fout = new FileOutputStream(filename);

                byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1)
                {
                        fout.write(data, 0, count);
                }
        } catch (Exception e) {
        	throw new de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.IOException(e);
        }

	}
	
	public void download(Job job, String filename) throws APIError {
		download(job.getId(), filename);
	}

	
	
	
}
