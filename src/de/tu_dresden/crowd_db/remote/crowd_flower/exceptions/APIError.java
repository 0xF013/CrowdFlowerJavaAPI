package de.tu_dresden.crowd_db.remote.crowd_flower.exceptions;

public abstract class APIError extends Exception {

	public APIError(String message) {
		super(message);
	}
	
	public APIError() {
		super();
	}

	public APIError(Throwable e) {
		super(e);
	}	

}
