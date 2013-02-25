/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseComposingExt.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TeamExerciseComposingExt.
 * 
 * @author S.Schwarzrock
 *
 */
public class TeamExerciseComposingExt implements IClixMappingClass {

	private Long id;
	private Long eComposingId;
	private String submissionDeadline;

	public TeamExerciseComposingExt()
	{

	}

	public String getString()
	{
		return "TeamExerciseComposingExt$$$"
				+ this.id + "$$$"
				+ this.getSubmissionDeadline() + "$$$"
				+ this.geteComposingId();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long geteComposingId() {
		return this.eComposingId;
	}

	public void seteComposingId(final Long eComposingId) {
		this.eComposingId = eComposingId;
	}

	public String getSubmissionDeadline() {
		return this.submissionDeadline;
	}

	public void setSubmissionDeadline(final String submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}

}
