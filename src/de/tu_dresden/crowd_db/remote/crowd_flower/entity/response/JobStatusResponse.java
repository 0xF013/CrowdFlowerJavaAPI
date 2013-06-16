package de.tu_dresden.crowd_db.remote.crowd_flower.entity.response;

public class JobStatusResponse {

	
	private int goldenUnits;
	private int completedGoldEstimate;
	private int allJudgments;
	private int taintedJudgments;
	private int completedNonGoldEstimate;
	private int orderedUnits;
	private int neededJudgments;
	private int completedUnitsEstimate;
	private int allUnits;
	public int getGoldenUnits() {
		return goldenUnits;
	}
	public void setGoldenUnits(int goldenUnits) {
		this.goldenUnits = goldenUnits;
	}
	public int getCompletedGoldEstimate() {
		return completedGoldEstimate;
	}
	public void setCompletedGoldEstimate(int completedGoldEstimate) {
		this.completedGoldEstimate = completedGoldEstimate;
	}
	public int getAllJudgments() {
		return allJudgments;
	}
	public void setAllJudgments(int allJudgments) {
		this.allJudgments = allJudgments;
	}
	public int getTaintedJudgments() {
		return taintedJudgments;
	}
	public void setTaintedJudgments(int taintedJudgments) {
		this.taintedJudgments = taintedJudgments;
	}
	public int getCompletedNonGoldEstimate() {
		return completedNonGoldEstimate;
	}
	public void setCompletedNonGoldEstimate(int completedNonGoldEstimate) {
		this.completedNonGoldEstimate = completedNonGoldEstimate;
	}
	public int getOrderedUnits() {
		return orderedUnits;
	}
	public void setOrderedUnits(int orderedUnits) {
		this.orderedUnits = orderedUnits;
	}
	public int getNeededJudgments() {
		return neededJudgments;
	}
	public void setNeededJudgments(int neededJudgments) {
		this.neededJudgments = neededJudgments;
	}
	public int getCompletedUnitsEstimate() {
		return completedUnitsEstimate;
	}
	public void setCompletedUnitsEstimate(int completedUnitsEstimate) {
		this.completedUnitsEstimate = completedUnitsEstimate;
	}
	public int getAllUnits() {
		return allUnits;
	}
	public void setAllUnits(int allUnits) {
		this.allUnits = allUnits;
	}
	public JobStatusResponse(int goldenUnits, int completedGoldEstimate,
			int allJudgments, int taintedJudgments,
			int completedNonGoldEstimate, int orderedUnits,
			int neededJudgments, int completedUnitsEstimate, int allUnits) {
		super();
		this.goldenUnits = goldenUnits;
		this.completedGoldEstimate = completedGoldEstimate;
		this.allJudgments = allJudgments;
		this.taintedJudgments = taintedJudgments;
		this.completedNonGoldEstimate = completedNonGoldEstimate;
		this.orderedUnits = orderedUnits;
		this.neededJudgments = neededJudgments;
		this.completedUnitsEstimate = completedUnitsEstimate;
		this.allUnits = allUnits;
	}
	
	
	
}
