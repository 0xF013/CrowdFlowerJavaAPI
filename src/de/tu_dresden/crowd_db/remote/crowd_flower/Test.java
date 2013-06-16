package de.tu_dresden.crowd_db.remote.crowd_flower;

import java.io.IOException;
import java.util.List;

import de.tu_dresden.crowd_db.remote.crowd_flower.config.INIConfig;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Job;
import de.tu_dresden.crowd_db.remote.crowd_flower.service.JobService;

public class Test {

	
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Manager wr = new Manager(new INIConfig("config.ini"));
		try {
			//wr.createJobUnit(JobType.DISAMBIGUATION, "lambada", "song", "Resolve the entity");
			//wr.placeOrder(JobType.DISAMBIGUATION);
			//wr.cancelJob(JobType.DISAMBIGUATION);
			//wr.pauseJob(QuestionType.DISAMBIGUATION);
			//wr.resumeJob(QuestionType.DISAMBIGUATION);
			
			//System.out.println(wr.getAnswer(new QuestionData(157763696, 84257)));
			
			
			//JobService jr = new JobService();
			//List<Job> jobs = jr.getList();
			
			//for (Job job: jobs) {
			//	System.out.println(job.getTitle());
			//}
			/*
			Job job = jr.find(84261);
			System.out.println("Updated at: " + job.getUpdatedAt());
			System.out.println("Created at: " + job.getCreatedAt());
			
			
			job.setJudgementsPerUnit(3);
			job.setMaxJudgmentsPerUnit(15);
			job.setMaxJudgmentsPerWorker(25);
			job.setTitle("qqqqqqqqqqqqs2");
			job.setInstructions("qq  eq eq rf34 rf4 tggfh dfg 54 yrgfh fg");
			
			List<Countries> countries  = new ArrayList<Countries>();
			countries.add(Countries.GERMANY);
			countries.add(Countries.ITALY);
			//job.setIncludedCountries(countries);			
			
			jr.createOrUpdate(job);

			System.out.println("========================== ");
			System.out.println("Updated at: " + job.getUpdatedAt());
			System.out.println("Created at: " + job.getCreatedAt());
			
			
			Job job = new Job();
			job.setTitle("Other cool job name");
			job.setInstructions("Answer this question, I dare you answer this question");
			job.setLanguage(Language.FRENCH);
			List<Country> countries  = new ArrayList<Country>();
			countries.add(Country.GERMANY);
			countries.add(Country.ITALY);
			//job.setIncludedCountries(countries);
			
			List<Country> countries1  = new ArrayList<Country>();
			countries.add(Country.GREECE);
			countries.add(Country.MOLDOVA_REPUBLIC_OF);
			//job.setExcludedCountries(countries1);			
			job.setJudgmentsCount(14);
			job.setCmlFromFile("template/relationship.tpl");
			System.out.println("Request string: " + job.toRequestString());
			jr.createOrUpdate(job);
			System.out.println("Request string: " + job.toRequestString());
			System.out.println("Created at: " + job.getCreatedAt());
			
			
			Job job = jr.find(84261);
			System.out.println("Problem: " + job.getProblem());
			System.out.println("Request string: " + job.toRequestString());
			System.out.println("Created at: " + job.getCreatedAt());
			
			
			job.setJudgementsPerUnit(3);
			job.setMaxJudgmentsPerUnit(15);
			job.setMaxJudgmentsPerWorker(25);
			job.setTitle("qqqqqqqqqqqq");
			job.setInstructions("qq  eq eq rf34 rf4 tggfh dfg 54 yrgfh fg");
			jr.update(job);
			
			
			

			Job job = new Job();
			job.setTitle("Cool job name ss");
			jr.create(job);


			
			job.setTitle("113333333333333331");
			jr.update(job);
			
			
			JobRepository jr = new JobRepository();

			
			*/
			
			//wr.getAccountInformation();
			//wr.setUnitsPerAssignment(QuestionType.DISAMBIGUATION, 1);
			//wr.getJobStatus(QuestionType.DISAMBIGUATION);
			//wr.getJobChannels(QuestionType.DISAMBIGUATION);
			//wr.uploadData(QuestionType.DISAMBIGUATION, new File("sample.csv"));
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


