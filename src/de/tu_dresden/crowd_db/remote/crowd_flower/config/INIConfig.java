package de.tu_dresden.crowd_db.remote.crowd_flower.config;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_dresden.crowd_db.remote.crowd_flower.JobType;

public class INIConfig implements ApiConfig {

	private Map<String, Map<String, String>> iniData = new HashMap<String, Map<String, String>>();
	
	private Map<JobType, String> sections = new HashMap<JobType, String>();
	
	private void initSections() {
		sections.put(JobType.RELATIONSHIP, "relationship");
		sections.put(JobType.DISAMBIGUATION, "disambiguation");
		sections.put(JobType.TYPE_MATCHING, "type_matching");
	}
	
	private String typeToSection(JobType type) {		
		return sections.get(type);
	}
	
	private String getValue(JobType type, String key) {
		String ret = iniData.get(typeToSection(type)).get(key);
		if (ret == null) {
			ret = iniData.get("common").get("payment_cents");
		}
		
		return ret;
	}
	
	public INIConfig(String filename) throws IOException {
		initSections();
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String section = "common";
		while ((strLine = br.readLine()) != null) {
			String[] parts = strLine.split("=");
			if (parts[0].length() == 0) {
				continue;
			}
			if (parts[0].charAt(0) == '#') {
				continue;
			}
			if (parts[0].startsWith("[")) {
				section = parts[0].substring(1, parts[0].length() - 1);
				iniData.put(section, new HashMap<String, String>());
				continue;
			}
			iniData.get(section).put(parts[0].trim(), parts[1].trim());
			
		}
		in.close();
	}
	
	@Override
	public String getSecretKey() {
		return iniData.get("common").get("secret");
	}

	@Override
	public int getPaymentCents(JobType type) {
		return Integer.valueOf(getValue(type, "payment_cents"));
	}

	@Override
	public int getJobId(JobType type) {
		return Integer.valueOf(getValue(type, "id"));
	}

	@Override
	public int getUnitsCount(JobType type) {
		return Integer.valueOf(getValue(type, "units_count"));
	}

	@Override
	public List<String> getChannels(JobType type) {
		List<String> ret = new ArrayList<String>();
		for (String c: getValue(type, "channels").split(",")) {
			ret.add(c);
		}
		return ret;
	}


}
