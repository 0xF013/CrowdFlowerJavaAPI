package de.tu_dresden.crowd_db.remote.crowd_flower;

import java.util.HashMap;
import java.util.Map;

public class TypeManager {
	
	private Map<JobType, Integer> types = new HashMap<JobType, Integer>();

	public TypeManager(int relationshipJobId,
			int disambiguationJobId, int typeMatchingJobId) {
		types.put(JobType.DISAMBIGUATION, disambiguationJobId);
		types.put(JobType.RELATIONSHIP, relationshipJobId);
		types.put(JobType.TYPE_MATCHING, typeMatchingJobId);		
	}
	
	public int getTypeId(JobType type) {
		return types.get(type);
	}
	
	
}
