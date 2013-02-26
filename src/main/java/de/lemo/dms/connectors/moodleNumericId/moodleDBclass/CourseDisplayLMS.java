/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Course_display_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table CourseDisplay.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class CourseDisplayLMS {

	private long id;
	private long course;
	private long userid;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}
}