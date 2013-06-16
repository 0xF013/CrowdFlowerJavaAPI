package de.tu_dresden.crowd_db.remote.crowd_flower.entity.response;

import java.util.ArrayList;
import java.util.List;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;

public class JobGetChannelsResponse {

	protected List<Channels> enabledChannels = new ArrayList<Channels>();
	protected List<Channels> availableChannels = new ArrayList<Channels>();
	public List<Channels> getEnabledChannels() {
		return enabledChannels;
	}
	public void setEnabledChannels(List<Channels> enabledChannels) {
		this.enabledChannels = enabledChannels;
	}
	public List<Channels> getAvailableChannels() {
		return availableChannels;
	}
	public void setAvailableChannels(List<Channels> availableChannels) {
		this.availableChannels = availableChannels;
	}
	public JobGetChannelsResponse(List<Channels> enabledChannels,
			List<Channels> availableChannels) {
		super();
		this.enabledChannels = enabledChannels;
		this.availableChannels = availableChannels;
	}
	
	
	
}
