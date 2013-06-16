package de.tu_dresden.crowd_db.remote.crowd_flower.config;

import java.util.List;

import de.tu_dresden.crowd_db.remote.crowd_flower.JobType;

public interface ApiConfig {
	
	String getSecretKey();
	
	int getPaymentCents(JobType type);
	int getJobId(JobType type);
	int getUnitsCount(JobType type);
	List<String> getChannels(JobType type);
}
