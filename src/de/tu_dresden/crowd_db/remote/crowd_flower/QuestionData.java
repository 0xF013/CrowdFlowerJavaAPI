package de.tu_dresden.crowd_db.remote.crowd_flower;

public class QuestionData {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int jobId) {
		this.typeId = jobId;
	}

	public QuestionData(int id, int typeId) {
		this.id = id;
		this.typeId = typeId;
	}
	private int id;
	private int typeId;
	
}
