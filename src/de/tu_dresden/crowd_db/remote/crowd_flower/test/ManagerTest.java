package de.tu_dresden.crowd_db.remote.crowd_flower.test;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.tu_dresden.crowd_db.remote.crowd_flower.JobType;
import de.tu_dresden.crowd_db.remote.crowd_flower.Manager;
import de.tu_dresden.crowd_db.remote.crowd_flower.QuestionData;
import de.tu_dresden.crowd_db.remote.crowd_flower.config.INIConfig;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.APIError;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.PaymentRequiredException;
import de.tu_dresden.crowd_db.remote.crowd_flower.exceptions.RecordNotFoundException;

public class ManagerTest {
 /*
	Manager wr;
	
	@Before
	public void setUp() throws Exception {
		wr = new Manager(new INIConfig("test_config.ini"));
	}


	@Test
	public void testCreateJobUnit() {
		try {
		QuestionData qd = wr.createJobUnit(JobType.TYPE_MATCHING, "lambada", "song", "Resolve the entity");
		assertNotNull(qd);
		assertEquals(qd.getTypeId(), 84257);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPlaceOrderFailBecauseOfPayment() {
		try {
			wr.placeOrder(JobType.DISAMBIGUATION);
			fail();
		} catch (PaymentRequiredException e) {
			assert(true);
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPauseJob() {
		try {
			wr.pauseJob(JobType.DISAMBIGUATION);
			assert(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}	
	
	@Test
	public void testResumeJob() {
		try {
			wr.resumeJob(JobType.DISAMBIGUATION);
			assert(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}		
	
	@Test
	public void testCancelJob() {
		try {
			wr.cancelJob(JobType.DISAMBIGUATION);
			assert(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}			
	
	@Test
	public void testGetAnswerRaisesNonFoundWhenWrongdId() {
		try {
			wr.getAnswer(new QuestionData(0, 84257));
			fail();
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			assert(true);
		} catch (APIError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetAnswerReturnsFalseForFreshlyCreatedUnit() {
		QuestionData qd;
		try {
			qd = wr.createJobUnit(JobType.TYPE_MATCHING, "lambada", "song", "Resolve the entity");
			Boolean answer = wr.getAnswer(qd);
			assertEquals(false, answer);
		} catch (Exception e) {
			fail();
		}
		
		
	}
	
	*/

}
