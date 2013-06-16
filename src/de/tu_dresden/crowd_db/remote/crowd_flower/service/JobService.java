package de.tu_dresden.crowd_db.remote.crowd_flower.service;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.DataMapper;
import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.impl.JobDataMapper;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Job;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobGetChannelsResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobGoldResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobSetChannelsResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobStatusResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.ValidationException;

public class JobService extends ServiceImpl<Job> {

	protected DataMapper<Job> dataMapper = new JobDataMapper();


	private String url = "https://api.crowdflower.com/v1/jobs";

	public JobService(String secretKey) {
		super(secretKey);
	}

	protected String getUrl() {
		return url + ".json?key=" + secretKey;
	}

	protected String getUrl(int id) {
		return getUrl(id, null);
	}
	
	protected String getUrl(int id, String action) {
		return getUrl(id, action, true);
	}	
	
	protected String getUrl(int id, String action, boolean json) {
		return url + "/" + String.valueOf(id) + (action == null ? "" : "/" + action) + (json ? ".json" : "") +  "?key=" + secretKey;
	}		

	public Job find(int id) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(getUrl(id));
		return dataMapper.jsonToEntity(response, new Job());
	}

	public void createOrUpdate(Job job) throws APIError {
		JSONObject response;
		try {
			if (job.getId() == 0) {
				response = makeRequest(getUrl(),
						dataMapper.entityToRequestString(job), "POST");
				dataMapper.jsonToEntity(response, job);
			} else {
				response = makeRequest(getUrl(job.getId()),
						dataMapper.entityToRequestString(job), "PUT");
				dataMapper.jsonToEntity(response, job);
	
			}
		} catch(ValidationException e) {
			System.out.println("&&&&&&&&&&&&&&& " + e.getMessage() );
			JSONObject response1 = this.<JSONObject> makeRequest(getUrl(job.getId()));
			dataMapper.jsonToEntity(response1, job);
		}			
		
	}

	public List<Job> getList() throws APIError {
		// the api seems not have a method to get all the jobs, that's why
		// a request for each page is needed
		List<Job> ret = new ArrayList<Job>();
		int page = 1;
		while (true) {
			List<Job> jobs = getList(page);
			if (jobs.size() > 0) {
				ret.addAll(jobs);
				page++;
			} else {
				break;
			}
		}

		return ret;
	}

	public List<Job> getList(int page) throws APIError {
		JSONArray response = makeRequest(getUrl() + "&page="
				+ String.valueOf(page));
		List<Job> ret = new ArrayList<Job>();
		for (Object jsonObject : response) {
			JSONObject json = (JSONObject) jsonObject;
			ret.add(dataMapper.jsonToEntity(json, new Job()));
		}
		return ret;
	}
	
	
	public Job copy(Job job, boolean allUnits, boolean gold) throws APIError { 
		return copy(job.getId(), allUnits, gold); 
	}
	
	public Job copy(int id, boolean allUnits, boolean gold) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(
				getUrl(id, "copy") 
				+ "&all_units=" + String.valueOf(allUnits)
				+ "&gold=" + String.valueOf(gold), 
				"", "POST");
		return dataMapper.jsonToEntity(response, new Job());
	}
	
	public Job copy(Job job, boolean allUnits) throws APIError { 
		return copy(job.getId(), allUnits, false); 
	}	
	
	public Job copy(int id, boolean allUnits) throws APIError { 
		return copy(id, allUnits, false); 
	}		

	
	public void remove(int id) throws APIError {
		makeRequest(getUrl(id), "", "DELETE");
	}
	
	public void remove(Job job) throws APIError{
		remove(job.getId());
	}	
	
	public void pause(int id) throws APIError{
		 makeRequest(getUrl(id, "pause"));
	}
	
	public void pause(Job job) throws APIError{
		pause(job.getId());
	}		
	
	public void resume(int id) throws APIError{
		 makeRequest(getUrl(id, "resume"));
	}
	
	public void resume(Job job) throws APIError{
		resume(job.getId());
	}	
	
	public void cancel(int id) throws APIError{
		 makeRequest(getUrl(id, "cancel"));
	}	
	
	public void cancel(Job job) throws APIError{
		cancel(job.getId());
	}		
	
	public JobStatusResponse getStatus(Job job) throws APIError {
		return getStatus(job.getId());
	}
	
	public JobStatusResponse getStatus(int id) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(getUrl(id, "ping"));
		return new JobStatusResponse(
				Integer.valueOf(response.get("golden_units").toString()),
				Integer.valueOf(response.get("completed_gold_estimate").toString()),
				Integer.valueOf(response.get("all_judgments").toString()),
				Integer.valueOf(response.get("tainted_judgments").toString()),
				Integer.valueOf(response.get("completed_non_gold_estimate").toString()),
				Integer.valueOf(response.get("ordered_units").toString()),
				Integer.valueOf(response.get("needed_judgments").toString()),
				Integer.valueOf(response.get("completed_units_estimate").toString()),
				Integer.valueOf(response.get("all_units").toString())
		);
	}
	
	public Map<String, String> getLegend(Job job) throws APIError {
		return getLegend(job.getId());
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getLegend(int id) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(getUrl(id, "legend"));
		return (Map<String, String>)response;
	}	
	
	
	@SuppressWarnings("unchecked")
	private JobGoldResponse mapGoldResponse(JSONObject json) {
		return new JobGoldResponse(
				Integer.valueOf(json.get("affected").toString()),
				new ArrayList<String>(((JSONArray)json.get("populated")))
		);	
	}
	
	public JobGoldResponse resetGold(int id) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(getUrl(id, "gold") + "&reset=true", "", "PUT");
		return mapGoldResponse(response);
	}
	
	public JobGoldResponse resetGold(Job job) throws APIError {
		return resetGold(job.getId());
	}
	
	public JobGoldResponse setGold(int id, String check, String with) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(
				getUrl(id, "gold")
				+ "&check=" + encodeString(check)
				+ (with == null ? "" : "&with=" + encodeString(check)), 
				"", "PUT");
		return mapGoldResponse(response);
	}
	
	public JobGoldResponse setGold(int id, String check) throws APIError {
		return setGold(id, check, null);
	}	

	public JobGoldResponse setGold(Job job, String check, String with) throws APIError {
		return setGold(job.getId(), check, with);
	}	

	public JobGoldResponse setGold(Job job, String check) throws APIError {
		return setGold(job.getId(), check, null);
	}		
	
	public JobGetChannelsResponse getChannels(int id) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(getUrl(id, "channels", false));
		return new JobGetChannelsResponse(
				parseChannels((JSONArray) response.get("enabled_channels")),
				parseChannels((JSONArray) response.get("available_channels"))
		);
	}
	
	
	public JobGetChannelsResponse getChannels(Job job) throws APIError {
		return getChannels(job.getId());
	}		
	
	public JobSetChannelsResponse setChannels(int id, List<Channels> channels) throws APIError {
		JSONObject response = this.<JSONObject> makeRequest(getUrl(id, "channels", false), channelsToString(channels), "PUT");
		return new JobSetChannelsResponse(
				parseChannels((JSONArray) response.get("enabled_channels")),
				parseChannels((JSONArray) response.get("available_channels")),
				response.get("success") == null ? null : response.get("success").toString(),
				response.get("error") == null ? null : response.get("error").toString()
		);
	}
	
	public JobSetChannelsResponse setChannels(Job job, List<Channels> channels) throws APIError {
		return setChannels(job.getId(), channels);
	}




}
