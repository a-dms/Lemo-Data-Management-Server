package de.lemo.dms.processing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.resulttype.UserInstance;

public class FeatureProcessorTest {
	FeatureProcessor featureProcesor;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		featureProcesor = new FeatureProcessor(0L,0L,Long.MAX_VALUE);
	}
	
	/* Does not change the database */
	@Test 
	public void insertUserAssessmentLogs(){
		List<UserInstance> userInstances = featureProcesor.generateFeaturesForCourseUsers();
		assertNotNull(userInstances);
		int max = 0;
		for(UserInstance userInstance : userInstances){
			if(userInstance.getUnitProgress()>max){
				max = userInstance.getUnitProgress();
			}
		}
		assertTrue(max>0);
	}
}
