package de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.impl;

import java.util.List;

import org.json.simple.JSONObject;

import de.tu_dresden.crowd_db.remote.crowd_flower.data_mapper.DataMapperImpl;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.Job;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Countries;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Languages;

public class JobDataMapper extends DataMapperImpl<Job> {
	

	@SuppressWarnings("unchecked")
	@Override
	public Job jsonToEntity(JSONObject json, Job job) {
		job.setTitle(parseString(json.get("title")));
		job.setInstructions(parseString(json.get("instructions")));
		job.setCml(parseString(json.get("cml")));
		job.setProblem(parseString(json.get("problem")));
		System.out.println("````````````````````````````````````");
		System.out.println(json);
		System.out.println(json.get("auto_order"));
		job.setAutoOrder(Boolean.valueOf(json.get("auto_order").toString()));
		job.setLanguage(Languages.findByCode(json.get("language").toString()));
		
		job.setJudgmentsCount(parseInt(json.get("judgments_count")));
		job.setUnitsCount(parseInt(json.get("units_count")));
		job.setJudgementsPerUnit(parseInt(json.get("judgments_per_unit")));
		job.setMaxJudgmentsPerUnit(parseInt(json.get("max_judgments_per_unit")));
		job.setMaxJudgmentsPerWorker(parseInt(json.get("max_judgments_per_worker")));
		
		job.setId(Integer.valueOf(json.get("id").toString()));
		
		parseCountries(json, "included_countries", job.getIncludedCountries());
		parseCountries(json, "excluded_countries", job.getExcludedCountries());

		job.setCreatedAt(parseDate(json.get("created_at")));
		job.setUpdatedAt(parseDate(json.get("updated_at")));
		job.setCompletedAt(parseDate(json.get("completed_at")));
		job.setCompleted((Boolean) json.get("completed"));
		
		JSONObject fields = (JSONObject)json.get("fields");
		if (fields != null) {
			job.setFields(fields);
		}
		
		return job;
	}

	@Override
	public String entityToRequestString(Job job) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(propertyToRequestString("title", job.getTitle()));
		sb.append(propertyToRequestString("instructions", job.getInstructions()));
		sb.append(propertyToRequestString("cml", job.getCml()));
		sb.append(propertyToRequestString("problem", job.getProblem()));
		sb.append(propertyToRequestString("auto_order", String.valueOf(job.isAutoOrder())));
		
		sb.append(propertyToRequestString("included_countries", job.getIncludedCountries()));
		sb.append(propertyToRequestString("excluded_countries", job.getExcludedCountries())); 
		sb.append(propertyToRequestString("language", job.getLanguage() == null ? "en" : job.getLanguage().getCode()));
		
		sb.append(propertyToRequestString("judgments_per_unit", job.getJudgementsPerUnit()));
		sb.append(propertyToRequestString("max_judgments_per_unit", job.getMaxJudgmentsPerUnit()));
		sb.append(propertyToRequestString("max_judgments_per_worker", job.getMaxJudgmentsPerWorker()));
		sb.append(propertyToRequestString("units_per_assignment", job.getUnitsPerAssignment()));
		
		return sb.substring(1);
	}

	@Override
	protected String getModelAPIName() {
		return "job";
	}

}
