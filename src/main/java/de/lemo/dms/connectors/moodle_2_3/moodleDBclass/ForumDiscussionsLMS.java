/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/ForumDiscussionsLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_discussions_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table ForumDiscussions.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ForumDiscussionsLMS {

	private long id;
	private long forum;
	private String userid;
	private String name;
	private long firstpost;
	private long timemodified;
	private String usermodified;
	private long timestart;
	private long timeend;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getForum() {
		return this.forum;
	}

	public void setForum(final long forum) {
		this.forum = forum;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getFirstpost() {
		return this.firstpost;
	}

	public void setFirstpost(final long firstpost) {
		this.firstpost = firstpost;
	}

	public long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(final long timemodified) {
		this.timemodified = timemodified;
	}

	public String getUsermodified() {
		return this.usermodified;
	}

	public void setUsermodified(final String usermodified) {
		this.usermodified = usermodified;
	}

	public long getTimestart() {
		return this.timestart;
	}

	public void setTimestart(final long timestart) {
		this.timestart = timestart;
	}

	public long getTimeend() {
		return this.timeend;
	}

	public void setTimeend(final long timeend) {
		this.timeend = timeend;
	}
}
