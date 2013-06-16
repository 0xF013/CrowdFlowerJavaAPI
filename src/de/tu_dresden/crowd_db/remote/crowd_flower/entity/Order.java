package de.tu_dresden.crowd_db.remote.crowd_flower.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;

public class Order extends Entity {
	
	private int id = 0;
	private String type;
	private String meta;
	private Date createdAt;
	private Date updatedAt;
	private int userId = 0;
	private int jobId = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	
	
	
}
