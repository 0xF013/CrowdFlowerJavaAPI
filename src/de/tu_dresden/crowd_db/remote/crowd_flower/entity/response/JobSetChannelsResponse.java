package de.tu_dresden.crowd_db.remote.crowd_flower.entity.response;

import java.util.List;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;

public class JobSetChannelsResponse extends JobGetChannelsResponse {

	
	protected String successMessage;
	protected String errorString;
	
	public JobSetChannelsResponse(List<Channels> enabledChannels,
			List<Channels> availableChannels) {
		super(enabledChannels, availableChannels);
		// TODO Auto-generated constructor stub
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	public JobSetChannelsResponse(List<Channels> enabledChannels,
			List<Channels> availableChannels, String successMessage,
			String errorString) {
		super(enabledChannels, availableChannels);
		this.successMessage = successMessage;
		this.errorString = errorString;
	}
	
	
	
	
}
