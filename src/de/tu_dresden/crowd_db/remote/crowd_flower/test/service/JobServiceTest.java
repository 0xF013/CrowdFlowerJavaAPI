package de.tu_dresden.crowd_db.remote.crowd_flower.test.service;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Job;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Channels;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobGetChannelsResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobGoldResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobSetChannelsResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.response.JobStatusResponse;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;
import de.tu_dresden.crowd_db.remote.crowd_flower.service.JobService;
import de.tu_dresden.crowd_db.remote.crowd_flower.service.ServiceFactory;

public class JobServiceTest {

	
	private ServiceFactory serviceFactory = new ServiceFactory("3f7274940d0a4e3bab9b968a5da93c525ba166ca");
	private JobService jobService = serviceFactory.getJobService();
	
	private Job createJob() throws APIError {
		Job job = new Job();
		job.setTitle("Test job title");
		job.setInstructions("Some sample instructions");
		jobService.createOrUpdate(job);
		return job;
	}
	
	
	
	@Test
	public void testFindJob() {
		try {
			Job job = createJob();
			Job foundJob = jobService.find(job.getId());
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void testCreateJob() {
		try {
			Job job = createJob();
			assertNotSame(0, job.getId());
			assertNotNull(job.getCreatedAt());
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateJob() {
		try {
			Job job = createJob();
			Date updatedAt = job.getUpdatedAt();
			int oldId = job.getId();
			job.setTitle("Modified title for test");	
			
			jobService.createOrUpdate(job);
			assert(oldId == job.getId());
			
			assert(updatedAt.before(job.getUpdatedAt()));
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}	
	
	@Test
	public void testCopyJob() {
		try {
			Job job = createJob();
			Job copy = jobService.copy(job, true);
			assert(job.getId() != copy.getId());
			assert(job.getTitle().equals(job.getTitle()));
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPauseJob() {
		try {
			Job job = createJob();
			jobService.pause(job);
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}	
	
	@Test
	public void testResumeJob() {
		try {
			Job job = createJob();
			jobService.resume(job);
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}	
	
	@Test
	public void testCancelJob() {
		try {
			Job job = createJob();
			jobService.cancel(job);
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testGetJobStatus() {
		try {
			Job job = createJob();
			JobStatusResponse status = jobService.getStatus(job);
			System.out.println(status.getAllJudgments());
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testGetJobLegend() {
		try {
			Job job = createJob();
			Map<String, String> legend = jobService.getLegend(job);
			System.out.println(legend);
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testResetGold() {
		try {
			Job job = createJob();
			JobGoldResponse gold = jobService.resetGold(job);
			System.out.println(gold.getAffected());
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testSetGold() {
		//TODO: rewrite with cml set
//		try {
//			Job job = createJob();
//			JobGoldResponse gold = jobRepository.setGold(job, "");
//			System.out.println(gold.getAffected());
//		} catch (APIError e) {
//			System.out.println(e.getMessage());
//			fail(e.getMessage());
//		}
	}		
	
	@Test
	public void testSetChannels() {
		try {
			Job job = createJob();
			List<Channels> channels = new ArrayList<Channels>();
			channels.add(Channels.AMT);
			JobSetChannelsResponse response = jobService.setChannels(job, channels);
			System.out.println(response.getSuccessMessage());
			System.out.println(response.getSuccessMessage());
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testGetChannels() {
		try {
			Job job = createJob();
			JobGetChannelsResponse response = jobService.getChannels(job);
			System.out.println(response.getAvailableChannels());
			System.out.println(response.getEnabledChannels());
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testDeleteJob() {
		List<Job> jobs;
		try {
			jobs = jobService.getList();
			for (Job job:jobs) {
				jobService.remove(job);
			}			
		} catch (APIError e) {
			fail(e.getMessage());
		}

	}	
	

	
	
	
	
}
