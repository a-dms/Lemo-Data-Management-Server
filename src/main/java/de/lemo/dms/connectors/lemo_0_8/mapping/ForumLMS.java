/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ForumLMS.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
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
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/ForumLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents the forum table.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "forum")
public class ForumLMS{

	private long id;
	private String title;
	private String summary;
	private long timeCreated;
	private long timeModified;
	private Long platform;

	@Override
	public int hashCode() {
		return (int) id;
	}
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the forum
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the forum
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the forum
	 */
	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the forum
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute summary
	 * 
	 * @return a summary of the topic in the forum
	 */
	@Column(name="summary")
	public String getSummary() {
		return this.summary;
	}

	/**
	 * standard setter for the attribute summary
	 * 
	 * @param summary
	 *            a summary of the topic in the forum
	 */
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the forum was created
	 */
	
	@Column(name="timecreated")
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timecreated
	 *            the timestamp when the forum was created
	 */
	public void setTimeCreated(final long timecreated) {
		this.timeCreated = timecreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the forum was changed the last time
	 */
	@Column(name="timemodified")
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the forum was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
