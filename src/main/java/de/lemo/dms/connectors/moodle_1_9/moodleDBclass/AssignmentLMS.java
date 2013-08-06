/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/AssignmentLMS.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/moodle_1_9/moodleDBclass/Assignment_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_1_9.moodleDBclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Assignment.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
@Entity
@Table(name = "mdl_assignment")
public class AssignmentLMS {

	private long id;
	private long course;
	private String name;
	private String assignmenttype;
	private long timemodified;
	private long timeavailable;
	private long timedue;
	private String description;

	@Column(name="description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="course")
	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	@Column(name="name")
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name="assignmenttype")
	public String getAssignmenttype() {
		return this.assignmenttype;
	}

	public void setAssignmenttype(final String assignmenttype) {
		this.assignmenttype = assignmenttype;
	}

	@Column(name="timemodified")
	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	@Column(name="timeavailable")
	public long getTimeavailable() {
		return this.timeavailable;
	}

	public void setTimeavailable(final long timeavailable) {
		this.timeavailable = timeavailable;
	}

	@Column(name="timedue")
	public long getTimedue() {
		return this.timedue;
	}

	public void setTimedue(final long timedue) {
		this.timedue = timedue;
	}
}