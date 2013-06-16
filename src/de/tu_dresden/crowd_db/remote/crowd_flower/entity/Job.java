package de.tu_dresden.crowd_db.remote.crowd_flower.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Countries;
import de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration.Languages;

public class Job extends Entity {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public boolean isAutoOrder() {
		return autoOrder;
	}

	public void setAutoOrder(boolean autoOrder) {
		this.autoOrder = autoOrder;
	}

	public List<Countries> getIncludedCountries() {
		return includedCountries;
	}

	public void setIncludedCountries(List<Countries> includedCountries) {
		this.includedCountries = includedCountries;
	}

	public List<Countries> getExcludedCountries() {
		return excludedCountries;
	}

	public void setExcludedCountries(List<Countries> excludedCountries) {
		this.excludedCountries = excludedCountries;
	}

	public Languages getLanguage() {
		return language;
	}

	public void setLanguage(Languages language) {
		this.language = language;
	}

	public int getJudgementsPerUnit() {
		return judgementsPerUnit;
	}

	public void setJudgementsPerUnit(int judgementsPerUnit) {
		this.judgementsPerUnit = judgementsPerUnit;
	}

	public int getMaxJudgmentsPerUnit() {
		return maxJudgmentsPerUnit;
	}

	public void setMaxJudgmentsPerUnit(int maxJudgmentsPerUnit) {
		this.maxJudgmentsPerUnit = maxJudgmentsPerUnit;
	}

	public int getMaxJudgmentsPerWorker() {
		return maxJudgmentsPerWorker;
	}

	public void setMaxJudgmentsPerWorker(int maxJudgmentsPerWorker) {
		this.maxJudgmentsPerWorker = maxJudgmentsPerWorker;
	}

	public int getUnitsPerAssignment() {
		return unitsPerAssignment;
	}

	public void setUnitsPerAssignment(int unitsPerAssignment) {
		this.unitsPerAssignment = unitsPerAssignment;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
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

	public int getJudgmentsCount() {
		return judgmentsCount;
	}

	public void setJudgmentsCount(int judgementsCount) {
		this.judgmentsCount = judgementsCount;
	}

	public int getUnitsCount() {
		return unitsCount;
	}

	public void setUnitsCount(int unitsCount) {
		this.unitsCount = unitsCount;
	}

	public String getCml() {
		return cml;
	}

	public void setCml(String cml) {
		this.cml = cml;
	}

	public void setCmlFromFile(String filePath) throws IOException {
		cml = readFileAsString(filePath);
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public Job(String title, String instructions, boolean autoOrder) {
		super();
		this.title = title;
		this.instructions = instructions;
		this.autoOrder = autoOrder;
	}

	public Job(String title, String instructions) {
		this(title, instructions, false);
	}

	public Job() {
		super();
	}

	private String readFileAsString(String filePath) throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	private String title;
	private String instructions;
	private String cml;
	private String problem;

	private boolean autoOrder = false;

	private List<Countries> includedCountries = new ArrayList<Countries>();
	private List<Countries> excludedCountries = new ArrayList<Countries>();
	private Languages language;

	private int judgementsPerUnit = 0;
	private int maxJudgmentsPerUnit = 0;
	private int maxJudgmentsPerWorker = 0;
	private int unitsPerAssignment = 0;

	// readonly api properties

	private boolean isCompleted = false;
	private Date completedAt;
	private Date createdAt;
	private Date updatedAt;
	private int judgmentsCount = 0;
	private int unitsCount = 0;
	private Map<String, String> fields = new HashMap<String, String>();

}
