package de.tu_dresden.crowd_db.remote.crowd_flower.entity.enumeration;

public enum Channels implements EnumWithCode {
	AMT, CBF, CROWDGURU, FUSIONCASH, GETPAID, MOB, SEDGROUP, ZOOMBUCKS;

	@Override
	public String getCode() {
		return toString().toLowerCase();
	}
	
	public static Channels findByCode(String code) {
		for(Channels c:values()) {		
			if (c.getCode().equals(code)) {
				return c;
			}
		}
		return null;
	}	
}
