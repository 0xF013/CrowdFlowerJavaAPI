package de.tu_dresden.crowd_db.remote.crowd_flower.test.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Job;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Order;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;
import de.tu_dresden.crowd_db.remote.crowd_flower.service.DataService;
import de.tu_dresden.crowd_db.remote.crowd_flower.service.JobService;
import de.tu_dresden.crowd_db.remote.crowd_flower.service.ServiceFactory;

public class DataServiceTest {

	
	private ServiceFactory serviceFactory = new ServiceFactory("3f7274940d0a4e3bab9b968a5da93c525ba166ca");
	
	private DataService dataService = serviceFactory.getDataService();
	private JobService jobService = serviceFactory.getJobService();
	
	private Job createJob() throws APIError {
		Job job = new Job();
		job.setTitle("Test job title");
		job.setInstructions("Some sample instructions");
		jobService.createOrUpdate(job);
		return job;
	}	
	
	@Test
	public void testCreateShouldRaisePaymentRequiredException() {
		try {
			Job job;
			job = createJob();
			List<Channels> channels = new ArrayList<Channels>();
			channels.add(Channels.AMT);
			Order order = dataService.createOrder(job.getId(), 3, channels);		
			fail();
		} catch (APIError e) {
			assert(true);
		}
	}
	
	
	@Test
	public void testUploadCreatesJob() {
		try {
			Job job	= dataService.uploadData(new File("sample.csv"), "text/csv");
		} catch (APIError e) {
			fail(e.getMessage());
		}	
	}
	
	@Test
	public void testUploadToExistingJob() {
		try {
			Job job	= createJob();
			Job newJob	= dataService.uploadData(job, new File("sample.csv"), "text/csv");
			assert(job.getId() == newJob.getId());
		} catch (APIError e) {
			fail(e.getMessage());
		}	
	}	
	
	@Test
	public void testDownload() {
		try {
			Job job	= createJob();
			Job newJob	= dataService.uploadData(job, new File("sample.csv"), "text/csv");
			dataService.download(job, "sampe_download.csv");
		} catch (APIError e) {
			fail(e.getMessage());
		}	
	}		
	
	
}
