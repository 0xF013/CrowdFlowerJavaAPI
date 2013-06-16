package de.tu_dresden.crowd_db.remote.crowd_flower.entity.response;

import java.util.ArrayList;
import java.util.List;

public class JobGoldResponse {
	private int affected;
	private List<String> populated = new ArrayList<String>();
	public int getAffected() {
		return affected;
	}
	public void setAffected(int affected) {
		this.affected = affected;
	}
	public List<String> getPopulated() {
		return populated;
	}
	public void setPopulated(List<String> populated) {
		this.populated = populated;
	}
	public JobGoldResponse(int affected, List<String> populated) {
		super();
		this.affected = affected;
		this.populated = populated;
	}
	
	
	
}
