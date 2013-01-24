/**
 * File ./main/java/de/lemo/dms/processing/questions/QCourseUsers.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

@Path("activecourseusers")
public class QCourseUsers extends Question {

	@POST
	public ResultListLongObject compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courseIds,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) {

		// Check arguments
		if (startTime >= endTime) {
			return null;
		}

		// Set up db-connection
		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		final Criteria criteria = session.createCriteria(CourseLogMining.class, "log");

		criteria.add(Restrictions.in("log.course.id", courseIds))
				.add(Restrictions.between("log.timestamp", startTime, endTime));

		@SuppressWarnings("unchecked")
		final ArrayList<ILogMining> logs = (ArrayList<ILogMining>) criteria.list();

		final HashSet<Long> users = new HashSet<Long>();
		for (final ILogMining log : logs) {
			if (log.getUser() == null) {
				continue;
			}
			users.add(log.getUser().getId());
		}

		return new ResultListLongObject(new ArrayList<Long>(users));
	}

}
