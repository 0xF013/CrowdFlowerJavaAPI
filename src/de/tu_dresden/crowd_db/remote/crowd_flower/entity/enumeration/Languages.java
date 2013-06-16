package de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration;

public enum Languages implements EnumWithCode {
	ENGLISH("en"), GERMAN("de"), FRENCH("fr"), THAI("th");
	
	private String code;
	
	Languages(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static Languages findByCode(String code) {
		for(Languages l:Languages.values()) {		
			if (l.getCode().equals(code)) {
				return l;
			}
		}
		return null;
	}
}
