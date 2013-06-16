package de.tu_dresden.crowd_db.remote.crowd_flower.exceptions;


public class IOException extends APIError {

	public IOException(String message) {
		super(message);
	}

	public IOException(Throwable e) {
		super(e);
	}

	public IOException() {
		super();
	}



}
