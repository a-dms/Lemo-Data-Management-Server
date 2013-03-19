/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/ExtractAndMap.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.Clock;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.AssignmentMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.ChatMining;
import de.lemo.dms.db.miningDBclass.ConfigMining;
import de.lemo.dms.db.miningDBclass.CourseAssignmentMining;
import de.lemo.dms.db.miningDBclass.CourseChatMining;
import de.lemo.dms.db.miningDBclass.CourseForumMining;
import de.lemo.dms.db.miningDBclass.CourseGroupMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseQuizMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.CourseScormMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.CourseWikiMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.ForumMining;
import de.lemo.dms.db.miningDBclass.GroupMining;
import de.lemo.dms.db.miningDBclass.GroupUserMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.PlatformMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuestionMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.QuizMining;
import de.lemo.dms.db.miningDBclass.QuizQuestionMining;
import de.lemo.dms.db.miningDBclass.QuizUserMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.ScormMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.WikiMining;

/**
 * The main class of the extraction process.
 * Inherit from this class to make an extract class for a specific LMS.
 * Contains bundle of fields as container for LMS objects,
 * which are used for linking the tables.
 * 
 * @author Benjamin Wolf
 * @author Sebastian Schwarzrock
 */
public abstract class ExtractAndMap {

	// lists of object tables which are new found in LMS DB
	/** A map containing all CourseMining entries found in the database */
	protected Map<Long, CourseMining> courseMining;

	/** A map containing all PlatformMining entries found in the database */
	protected Map<Long, PlatformMining> platformMining;

	/** A map containing all QuizQuestionMining entries found in the database */
	protected Map<Long, QuizQuestionMining> quizQuestionMining;

	/** A map containing all CourseQuizMining entries found in the database */
	protected Map<Long, CourseQuizMining> courseQuizMining;

	/** A map containing all QuizMining entries found in the database */
	protected Map<Long, QuizMining> quizMining;

	/** A map containing all AssignmentMining entries found in the database */
	protected Map<Long, AssignmentMining> assignmentMining;

	/** A map containing all ScormMining entries found in the database */
	protected Map<Long, ScormMining> scormMining;

	/** A map containing all ForumMining entries found in the database */
	protected Map<Long, ForumMining> forumMining;

	/** A map containing all ResourceMining entries found in the database */
	protected Map<Long, ResourceMining> resourceMining;

	/** A map containing all UserMining entries found in the database */
	protected Map<Long, UserMining> userMining;

	/** A map containing all WikiMining entries found in the database */
	protected Map<Long, WikiMining> wikiMining;

	/** A map containing all GroupMining entries found in the database */
	protected Map<Long, GroupMining> groupMining;

	/** A map containing all QuestionMining entries found in the database */
	protected Map<Long, QuestionMining> questionMining;

	/** A map containing all RoleMining entries found in the database */
	protected Map<Long, RoleMining> roleMining;

	/** A map containing all LevelMining entries found in the database */
	protected Map<Long, LevelMining> levelMining;

	/** A map containing all ChatMining entries found in the database */
	protected Map<Long, ChatMining> chatMining;

	/** A map containing all ChatLogMining entries found in the database */
	protected Map<Long, ChatLogMining> chatLogMining;

	/** A map containing all PlatformMining entries found in the mining-database */
	protected Map<Long, PlatformMining> oldPlatformMining;

	/** A map containing all CourseMining entries found in the mining-database */
	protected Map<Long, CourseMining> oldCourseMining;

	/** A map containing all QuizMining entries found in the mining-database */
	protected Map<Long, QuizMining> oldQuizMining;

	/** A map containing all AssignmentMining entries found in the mining-database */
	protected Map<Long, AssignmentMining> oldAssignmentMining;

	/** A map containing all ScormMining entries found in the mining-database */
	protected Map<Long, ScormMining> oldScormMining;

	/** A map containing all ForumMining entries found in the mining-database */
	protected Map<Long, ForumMining> oldForumMining;

	/** A map containing all ResourceMining entries found in the mining-database */
	protected Map<Long, ResourceMining> oldResourceMining;

	/** A map containing all UserMining entries found in the mining-database */
	protected Map<Long, UserMining> oldUserMining;

	/** A map containing all WikiMining entries found in the mining-database */
	protected Map<Long, WikiMining> oldWikiMining;

	/** A map containing all GroupMining entries found in the mining-database */
	protected Map<Long, GroupMining> oldGroupMining;

	/** A map containing all QuestionMining entries found in the mining-database */
	protected Map<Long, QuestionMining> oldQuestionMining;

	/** A map containing all RoleMining entries found in the mining-database */
	protected Map<Long, RoleMining> oldRoleMining;

	/** A map containing all QuizQuestionMining entries found in the mining-database */
	protected Map<Long, QuizQuestionMining> oldQuizQuestionMining;

	/** A map containing all CourseQuizMining entries found in the mining-database */
	protected Map<Long, CourseQuizMining> oldCourseQuizMining;

	/** A map containing all LevelMining entries found in the mining-database */
	protected Map<Long, LevelMining> oldLevelMining;

	/** A map containing all ChatMining entries found in the mining-database */
	protected Map<Long, ChatMining> oldChatMining;

	/** A map containing all ChatLogMining entries found in the mining-database */
	protected Map<Long, ChatLogMining> oldChatLogMining;

	/** A list of objects used for submitting them to the DB. */
	protected List<Collection<?>> updates;
	/** A list of time stamps of the previous runs of the extractor. */
	protected List<Timestamp> configMiningTimestamp;

	/** value of the highest user-id in the data set. Used for creating new numeric ids **/
	protected long largestId;

	/** Designates which entries should be read from the LMS Database during the process. */
	private long starttime;

	private IDBHandler dbHandler;

	private final Logger logger = Logger.getLogger(this.getClass());

	private Clock c;

	protected Long resourceLogMax;
	protected Long chatLogMax;
	protected Long assignmentLogMax;
	protected Long courseLogMax;
	protected Long forumLogMax;
	protected Long questionLogMax;
	protected Long quizLogMax;
	protected Long scormLogMax;
	protected Long wikiLogMax;
	protected Long courseChatMax;
	protected Long maxLog = 0L;

	private final IConnector connector;

	public ExtractAndMap(final IConnector connector) {
		this.connector = connector;
	}

	/**
	 * Starts the extraction process by calling getLMS_tables() and saveMining_tables().
	 * A time stamp can be given as optional argument.
	 * When the argument is used the extraction begins after that time stamp.
	 * When no argument is given the program starts with the time stamp of the last run.
	 * 
	 * @param args
	 *            Optional arguments for the process. Used for the selection of the ExtractAndMap Implementation and
	 *            time stamp when the extraction should start.
	 **/
	public void start(final String[] args, final DBConfigObject sourceDBConf, List<Long> courses) {

		this.dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		this.c = new Clock();
		this.starttime = System.currentTimeMillis() / 1000;

		final Session session = this.dbHandler.getMiningSession();

		// get the status of the mining DB; load timestamp of last update and objects needed for associations
		long readingtimestamp = this.getMiningInitial();

		this.dbHandler.saveToDB(session, new PlatformMining(this.connector.getPlatformId(), this.connector.getName(),
				this.connector.getPlattformType().toString(), this.connector.getPrefix()));

		logger.info("Initialized database in " + this.c.getAndReset());
		// default call without parameter
		if (args.length == 1)
		{
			// get the needed tables from LMS DB
			this.c.reset();
			this.getLMStables(sourceDBConf, readingtimestamp, courses);
			logger.info("Loaded data from source in " + this.c.getAndReset());

			// create and write the mining database tables
			this.saveMiningTables();
		}
		// call with parameter timestamp
		else {
			if (args[1].matches("[0-9]+"))
			{
				readingtimestamp = Long.parseLong(args[1]);
				long readingtimestamp2 = Long.parseLong(args[1]) + 172800;
				final long currenttimestamp = this.starttime;
				this.logger.info("starttime:" + currenttimestamp);
				this.logger.info("parameter:" + readingtimestamp);

				// first read & save LMS DB tables from 0 to starttime for timestamps which are not set(which are 0)
				if (this.configMiningTimestamp.get(0) == null) {
					this.c.reset();
					this.getLMStables(sourceDBConf, 0, readingtimestamp, courses);
					logger.info("Loaded data from source in " + this.c.getAndReset());
					// create and write the mining database tables
					this.saveMiningTables();
				}

				// read & save LMS DB in steps of 2 days
				for (long looptimestamp = readingtimestamp - 1; looptimestamp < currenttimestamp;)
				{
					this.logger.info("looptimestamp:" + looptimestamp);
					this.c.reset();
					this.getLMStables(sourceDBConf, looptimestamp + 1, readingtimestamp2, courses);
					logger.info("Loaded data from source in " + this.c.getAndReset());
					looptimestamp += 172800;
					readingtimestamp2 += 172800;
					this.logger.info("currenttimestamp:" + currenttimestamp);
					this.saveMiningTables();
					this.prepareMiningData();
					this.clearMiningTables();
				}
			}
			else {
				// Fehlermeldung Datenformat
				this.logger.info("wrong data format in parameter:" + args[1]);
			}
		}

		// calculate running time of extract process
		final long endtime = System.currentTimeMillis() / 1000;
		final ConfigMining config = new ConfigMining();
		config.setLastModifiedLong(System.currentTimeMillis());
		config.setElapsedTime((endtime) - (this.starttime));
		config.setDatabaseModel("1.3");
		config.setPlatform(this.connector.getPlatformId());
		config.setLatestTimestamp(maxLog);
		this.dbHandler.saveToDB(session, config);
		this.logger.info("Elapsed time: " + (endtime - this.starttime) + "s");
		this.dbHandler.closeSession(session);
	}

	/**
	 * Reads the Mining Database.
	 * Initial informations needed to start the process of updating are collected here.
	 * The time stamp of the last run of the extractor is read from the config table
	 * and the objects which might been needed to associate are read and saved here.
	 * 
	 * @return The time stamp of the last run of the extractor. If this is the first run it will be set 0.
	 **/
	@SuppressWarnings("unchecked")
	public long getMiningInitial() {

		// open a DB connection
		final Session session = this.dbHandler.getMiningSession();

		List<?> t;
		
		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from PlatformMining x order by x.id asc");
		this.oldPlatformMining = new HashMap<Long, PlatformMining>();
		if (t != null) {
			for (int i = 0; i < t.size(); i++) {
				this.oldPlatformMining.put(((PlatformMining) (t.get(i))).getId(), (PlatformMining) t.get(i));
			}
		}
		logger.info("Loaded " + this.oldPlatformMining.size()
				+ " PlatformMining objects from the mining database.");

		this.platformMining = new HashMap<Long, PlatformMining>();

		Long readingtimestamp;
		readingtimestamp = (Long) session.createQuery("Select max(latestTimestamp) from ConfigMining where platform=" + this.connector.getPlatformId()).uniqueResult();
		
		if(readingtimestamp == null)
		{
			readingtimestamp = 0L;
		}



		// load objects which are already in Mining DB for associations

		Query logCount = session.createQuery("select max(id) from ResourceLogMining");
		this.resourceLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.resourceLogMax == null) {
			this.resourceLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from chat_log");
		this.chatLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.chatLogMax == null) {
			this.chatLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from assignment_log");
		this.assignmentLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.assignmentLogMax == null) {
			this.assignmentLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from course_log");
		this.courseLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.courseLogMax == null) {
			this.courseLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from forum_log");
		this.forumLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.forumLogMax == null) {
			this.forumLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from question_log");
		this.questionLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.questionLogMax == null) {
			this.questionLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from quiz_log");
		this.quizLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.quizLogMax == null) {
			this.quizLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from scorm_log");
		this.scormLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.scormLogMax == null) {
			this.scormLogMax = 0L;
		}

		logCount = session.createSQLQuery("select max(id) from wiki_log");
		this.wikiLogMax = ((ArrayList<Long>) logCount.list()).get(0);
		if (this.wikiLogMax == null) {
			this.wikiLogMax = 0L;
		}
		
		Query couCaCount = session.createQuery("select max(cc.id) from CourseChatMining cc");
		this.courseChatMax = ((ArrayList<Long>) couCaCount.list()).get(0);
		if (this.courseChatMax == null) {
			this.courseChatMax = 0L;
		}

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from CourseMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldCourseMining = new HashMap<Long, CourseMining>();

		for (int i = 0; i < t.size(); i++) {
			this.oldCourseMining.put(((CourseMining) (t.get(i))).getId(), (CourseMining) t.get(i));
		}
		logger.info("Loaded " + this.oldCourseMining.size() + " CourseMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL,
				"from QuizMining x where x.platform=" + this.connector.getPlatformId() + " order by x.id asc");
		this.oldQuizMining = new HashMap<Long, QuizMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldQuizMining.put(((QuizMining) (t.get(i))).getId(), (QuizMining) t.get(i));
		}
		logger.info("Loaded " + this.oldQuizMining.size() + " QuizMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from AssignmentMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldAssignmentMining = new HashMap<Long, AssignmentMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldAssignmentMining.put(((AssignmentMining) (t.get(i))).getId(), (AssignmentMining) t.get(i));
		}
		logger.info("Loaded " + this.oldAssignmentMining.size()
				+ " AssignmentMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from ScormMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldScormMining = new HashMap<Long, ScormMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldScormMining.put(((ScormMining) (t.get(i))).getId(), (ScormMining) t.get(i));
		}
		logger.info("Loaded " + this.oldScormMining.size() + " ScormMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from ForumMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldForumMining = new HashMap<Long, ForumMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldForumMining.put(((ForumMining) (t.get(i))).getId(), (ForumMining) t.get(i));
		}
		logger.info("Loaded " + this.oldForumMining.size() + " ForumMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from ResourceMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldResourceMining = new HashMap<Long, ResourceMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldResourceMining.put(((ResourceMining) (t.get(i))).getId(), (ResourceMining) t.get(i));
		}
		logger.info("Loaded " + this.oldResourceMining.size()
				+ " ResourceMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL,
				"from UserMining x where x.platform=" + this.connector.getPlatformId() + " order by x.id asc");
		this.oldUserMining = new HashMap<Long, UserMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldUserMining.put(((UserMining) (t.get(i))).getId(), (UserMining) t.get(i));
		}
		logger.info("Loaded " + this.oldUserMining.size() + " UserMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL,
				"from WikiMining x where x.platform=" + this.connector.getPlatformId() + " order by x.id asc");
		this.oldWikiMining = new HashMap<Long, WikiMining>();

		for (int i = 0; i < t.size(); i++) {
			this.oldWikiMining.put(((WikiMining) (t.get(i))).getId(), (WikiMining) t.get(i));
		}
		logger.info("Loaded " + this.oldWikiMining.size() + " WikiMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from GroupMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldGroupMining = new HashMap<Long, GroupMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldGroupMining.put(((GroupMining) (t.get(i))).getId(), (GroupMining) t.get(i));
		}
		logger.info("Loaded " + this.oldGroupMining.size() + " GroupMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from QuestionMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldQuestionMining = new HashMap<Long, QuestionMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldQuestionMining.put(((QuestionMining) (t.get(i))).getId(), (QuestionMining) t.get(i));
		}
		logger.info("Loaded " + this.oldQuizMining.size() + " QuestionMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL,
				"from RoleMining x where x.platform=" + this.connector.getPlatformId() + " order by x.id asc");
		this.oldRoleMining = new HashMap<Long, RoleMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldRoleMining.put(((RoleMining) (t.get(i))).getId(), (RoleMining) t.get(i));
		}
		logger.info("Loaded " + this.oldRoleMining.size() + " RoleMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from QuizQuestionMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldQuizQuestionMining = new HashMap<Long, QuizQuestionMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldQuizQuestionMining.put(((QuizQuestionMining) (t.get(i))).getId(), (QuizQuestionMining) t.get(i));
		}
		logger.info("Loaded " + this.oldQuizQuestionMining.size()
				+ " QuizQuestionMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL, "from LevelMining x where x.platform="
				+ this.connector.getPlatformId() + " order by x.id asc");
		this.oldLevelMining = new HashMap<Long, LevelMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldLevelMining.put(((LevelMining) (t.get(i))).getId(), (LevelMining) t.get(i));
		}
		logger.info("Loaded " + this.oldLevelMining.size() + " LevelMining objects from the mining database.");

		t = this.dbHandler.performQuery(session, EQueryType.HQL,
				"from ChatMining x where x.platform=" + this.connector.getPlatformId() + " order by x.id asc");
		this.oldChatMining = new HashMap<Long, ChatMining>();
		for (int i = 0; i < t.size(); i++) {
			this.oldChatMining.put(((ChatMining) (t.get(i))).getId(), (ChatMining) t.get(i));
		}
		logger.info("Loaded " + this.oldChatMining.size() + " ChatMining objects from the mining database.");

		this.dbHandler.closeSession(session);

		return readingtimestamp;
	}

	/**
	 * Has to read the LMS Database.
	 * It starts reading elements with time stamp readingtimestamp and higher.
	 * It is supposed to be used for frequently and small updates.
	 * For a greater Mass of Data it is suggested to use getLMS_tables(long, long);
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * Hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 * 
	 * @param readingfromtimestamp
	 *            Only elements with time stamp readingtimestamp and higher are read here.
	 *            *
	 * @return the lM stables
	 */
	public abstract void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, List<Long> courses);

	/**
	 * Has to read the LMS Database.
	 * It reads elements with time stamp between readingfromtimestamp and readingtotimestamp.
	 * This method is used to read great DB in a step by step procedure.
	 * That is necessary for a great mass of Data when handling an existing DB for example.
	 * If Hibernate is used to access the LMS DB too,
	 * it is suggested to write the found tables into lists of
	 * Hibernate object model classes, which have to
	 * be created as global variables in this class.
	 * So they can be used in the generate methods of this class.
	 * 
	 * @param readingfromtimestamp
	 *            Only elements with time stamp readingfromtimestamp and higher are read here.
	 * @param readingtotimestamp
	 *            Only elements with time stamp readingtotimestamp and lower are read here.
	 *            *
	 * @return the lM stables
	 */
	public abstract void getLMStables(DBConfigObject dbConf, long readingfromtimestamp, long readingtotimestamp, List<Long> courses);

	/**
	 * Has to clear the lists of LMS tables*.
	 */
	public abstract void clearLMStables();

	/**
	 * Clears the lists of mining tables.
	 **/
	public void clearMiningTables() {
		this.courseMining.clear();
		this.quizMining.clear();
		this.assignmentMining.clear();
		this.scormMining.clear();
		this.forumMining.clear();
		this.resourceMining.clear();
		this.userMining.clear();
		this.wikiMining.clear();
		this.groupMining.clear();
		this.questionMining.clear();
		this.roleMining.clear();
		this.levelMining.clear();
		this.chatMining.clear();
		this.chatMining.clear();
	}

	/**
	 * Only for successive readings. This is meant to be done, when the gathered mining data has already
	 * been saved and before the mining tables are cleared for the next iteration.
	 */

	public void prepareMiningData()
	{
		this.oldCourseMining.putAll(this.courseMining);
		this.oldQuizMining.putAll(this.quizMining);
		this.oldAssignmentMining.putAll(this.assignmentMining);
		this.oldScormMining.putAll(this.scormMining);
		this.oldForumMining.putAll(this.forumMining);
		this.oldResourceMining.putAll(this.resourceMining);
		this.oldUserMining.putAll(this.userMining);
		this.oldWikiMining.putAll(this.wikiMining);
		this.oldGroupMining.putAll(this.groupMining);
		this.oldQuestionMining.putAll(this.questionMining);
		this.oldRoleMining.putAll(this.roleMining);
		this.oldLevelMining.putAll(this.levelMining);
		this.oldChatMining.putAll(this.chatMining);
		this.oldQuizQuestionMining.putAll(this.quizQuestionMining);
		this.oldCourseQuizMining.putAll(this.courseQuizMining);
	}

	/**
	 * Generates and save the new tables for the mining DB.
	 * We call the generate-methods for each mining table to get the new entries.
	 * At last we create a Transaction and save the new entries to the DB.
	 **/
	public void saveMiningTables() {

		// generate & save new mining tables
		this.updates = new ArrayList<Collection<?>>();

		Long objects = 0L;

		// generate mining tables
		if (this.userMining == null) {

			this.c.reset();
			logger.info("\nObject tables:\n");

			this.assignmentMining = this.generateAssignmentMining();
			objects += this.assignmentMining.size();
			logger.info("Generated " + this.assignmentMining.size() + " AssignmentMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.assignmentMining.values());

			// Screwing up the alphabetical order, CourseMinings have to be calculated BEFORE ChatMinings due to the
			// temporal foreign key "course" in ChatMining
			this.courseMining = this.generateCourseMining();
			objects += this.courseMining.size();
			logger.info("Generated " + this.courseMining.size() + " CourseMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.courseMining.values());

			this.chatMining = this.generateChatMining();
			objects += this.chatMining.size();
			this.updates.add(this.chatMining.values());
			logger.info("Generated " + this.chatMining.size() + " ChatMining entries in " + this.c.getAndReset()
					+ " s. ");

			this.levelMining = this.generateLevelMining();
			objects += this.levelMining.size();
			logger.info("Generated " + this.levelMining.size() + " LevelMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.levelMining.values());

			this.forumMining = this.generateForumMining();
			objects += this.forumMining.size();
			logger.info("Generated " + this.forumMining.size() + " ForumMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.forumMining.values());

			this.groupMining = this.generateGroupMining();
			objects += this.groupMining.size();
			logger.info("Generated " + this.groupMining.size() + " GroupMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.groupMining.values());

			this.questionMining = this.generateQuestionMining();
			objects += this.questionMining.size();
			logger.info("Generated " + this.questionMining.size() + " QuestionMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.questionMining.values());

			this.quizMining = this.generateQuizMining();
			objects += this.quizMining.size();
			this.updates.add(this.quizMining.values());
			logger.info("Generated " + this.quizMining.size() + " QuizMining entries in " + this.c.getAndReset()
					+ " s. ");

			this.resourceMining = this.generateResourceMining();
			objects += this.resourceMining.size();
			logger.info("Generated " + this.resourceMining.size() + " ResourceMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.resourceMining.values());

			this.roleMining = this.generateRoleMining();
			objects += this.roleMining.size();
			logger.info("Generated " + this.roleMining.size() + " RoleMining entries in " + this.c.getAndReset()
					+ " s. ");
			this.updates.add(this.roleMining.values());

			this.scormMining = this.generateScormMining();
			objects += this.scormMining.size();
			logger.info("Generated " + this.scormMining.size() + " ScormMining entries in "
					+ this.c.getAndReset() + " s. ");
			this.updates.add(this.scormMining.values());

			this.userMining = this.generateUserMining();
			objects += this.userMining.size();
			logger.info("Generated " + this.userMining.size() + " UserMining entries in " + this.c.getAndReset()
					+ " s. ");
			this.updates.add(this.userMining.values());

			this.wikiMining = this.generateWikiMining();
			objects += this.wikiMining.size();
			logger.info("Generated " + this.wikiMining.size() + " WikiMining entries in " + this.c.getAndReset()
					+ " s. ");
			this.updates.add(this.wikiMining.values());

			this.updates.add(this.generateCourseAssignmentMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseAssignmentMining entries in " + this.c.getAndReset() + " s. ");
			
			this.updates.add(this.generateCourseChatMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseChatMining entries in " + this.c.getAndReset() + " s. ");

			this.updates.add(this.generateCourseScormMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseScormMining entries in " + this.c.getAndReset() + " s. ");

			this.updates.add(this.generateLevelAssociationMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " LevelAssociationMining entries in " + this.c.getAndReset() + " s. ");

			this.updates.add(this.generateLevelCourseMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " LevelCourseMining entries in " + this.c.getAndReset() + " s. ");

			this.quizQuestionMining = this.generateQuizQuestionMining();
			objects += this.quizQuestionMining.size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " QuizQuestionMining entries in " + this.c.getAndReset() + " s. ");

			this.updates.add(this.quizQuestionMining.values());

			this.updates.add(this.generateCourseGroupMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseGroupMining entries in " + this.c.getAndReset() + " s. ");

			this.courseQuizMining = this.generateCourseQuizMining();
			objects += this.courseQuizMining.size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseQuizMining entries in " + this.c.getAndReset() + " s. ");

			this.updates.add(this.courseQuizMining.values());
			this.updates.add(this.generateCourseResourceMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseResourceMining entries in " + this.c.getAndReset() + " s. ");

			this.updates.add(this.generateCourseWikiMining().values());
			objects += this.updates.get(this.updates.size() - 1).size();
			logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
					+ " CourseWikiMining entries in " + this.c.getAndReset() + " s. ");

		}

		this.updates.add(this.generateGroupUserMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " GroupUserMining entries in " + this.c.getAndReset() + " s. ");

		this.updates.add(this.generateQuizUserMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " QuizUserMining entries in " + this.c.getAndReset() + " s. ");

		this.updates.add(this.generateCourseUserMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " CourseUserMining entries in " + this.c.getAndReset() + " s. ");

		logger.info("\nLog tables:\n");

		this.updates.add(this.generateAssignmentLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " AssignmentLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateChatLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " ChatLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateCourseLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " CourseLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateForumLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " ForumLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateQuestionLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " QuestionLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateQuizLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " QuizLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateResourceLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " ResourceLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateScormLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " ScormLogMining entries in " + this.c.getAndReset() + " s. ");
		this.updates.add(this.generateWikiLogMining().values());
		objects += this.updates.get(this.updates.size() - 1).size();
		logger.info("Generated " + this.updates.get(this.updates.size() - 1).size()
				+ " WikiLogMining entries in " + this.c.getAndReset() + " s. ");

		if (objects > 0)
		{
			final Session session = this.dbHandler.getMiningSession();
			logger.info("Writing to DB");
			this.dbHandler.saveCollectionToDB(session, this.updates);
		}

		this.clearLMStables();
		this.updates.clear();

	}

	// methods for create and fill the mining-table instances
	/**
	 * Has to create and fill the course_user table.
	 * This table describes which user is enrolled in which course in which time span.
	 * The attributes are described in the documentation of the course_user_mining class.
	 * Please use the getter and setter predefined in the course_user_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_user table representing class.
	 **/
	abstract Map<Long, CourseUserMining> generateCourseUserMining();

	/**
	 * Has to create and fill the course_forum table.
	 * This table describes which forum is used in which course.
	 * The attributes are described in the documentation of the course_forum_mining class.
	 * Please use the getter and setter predefined in the course_forum_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_forum table representing class.
	 **/
	abstract Map<Long, CourseForumMining> generateCourseForumMining();

	/**
	 * Has to create and fill the course table.
	 * This table describes the courses in the LMS.
	 * The attributes are described in the documentation of the course_mining class.
	 * Please use the getter and setter predefined in the course_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the course table representing class.
	 **/
	abstract Map<Long, CourseMining> generateCourseMining();

	/**
	 * Has to create and fill the course_group table.
	 * This table describes which groups are used in which courses.
	 * The attributes are described in the documentation of the course_group_mining class.
	 * Please use the getter and setter predefined in the course_group_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_group table representing class.
	 **/
	abstract Map<Long, CourseGroupMining> generateCourseGroupMining();

	/**
	 * Has to create and fill the course_quiz table.
	 * This table describes which quiz are used in which courses.
	 * The attributes are described in the documentation of the course_quiz_mining class.
	 * Please use the getter and setter predefined in the course_quiz_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_quiz table representing class.
	 **/
	abstract Map<Long, CourseQuizMining> generateCourseQuizMining();

	/**
	 * Has to create and fill the course_assignment table.
	 * This table describes which assignment are used in which courses.
	 * The attributes are described in the documentation of the course_assignment_mining class.
	 * Please use the getter and setter predefined in the course_assignment_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_assignment table representing class.
	 **/
	abstract Map<Long, CourseAssignmentMining> generateCourseAssignmentMining();
	
	/**
	 * Has to create and fill the course_chat table.
	 * This table describes which chats are used in which courses.
	 * The attributes are described in the documentation of the course_chat_mining class.
	 * Please use the getter and setter predefined in the course_chat_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_wiki table representing class.
	 **/
	abstract Map<Long, CourseChatMining> generateCourseChatMining();

	/**
	 * Has to create and fill the course_scorm table.
	 * This table describes which scorm packages are used in which courses.
	 * The attributes are described in the documentation of the course_scorm_mining class.
	 * Please use the getter and setter predefined in the course_scorm_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_scorm table representing class.
	 **/
	abstract Map<Long, CourseScormMining> generateCourseScormMining();

	/**
	 * Has to create and fill the course_resource table.
	 * This table describes which resources are used in which courses.
	 * The attributes are described in the documentation of the course_resource_mining class.
	 * Please use the getter and setter predefined in the course_resource_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_resource table representing class.
	 **/
	abstract Map<Long, CourseResourceMining> generateCourseResourceMining();

	/**
	 * Has to create and fill the course_log table.
	 * This table contains the actions which are done on courses.
	 * The attributes are described in the documentation of the course_log_mining class.
	 * Please use the getter and setter predefined in the course_log_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the course_log table representing class.
	 **/
	abstract Map<Long, CourseLogMining> generateCourseLogMining();

	/**
	 * Has to create and fill the course_wiki table.
	 * This table describes which wikis are used in which courses.
	 * The attributes are described in the documentation of the course_wiki_mining class.
	 * Please use the getter and setter predefined in the course_wiki_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the course_wiki table representing class.
	 **/
	abstract Map<Long, CourseWikiMining> generateCourseWikiMining();

	/**
	 * Has to create and fill the forum_log table.
	 * This table contains the actions which are done on forums.
	 * The attributes are described in the documentation of the forum_log_mining class.
	 * Please use the getter and setter predefined in the forum_log_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the forum_log table representing class.
	 **/
	abstract Map<Long, ForumLogMining> generateForumLogMining();

	/**
	 * Has to create and fill the forum table.
	 * This table describes the forums in the LMS.
	 * The attributes are described in the documentation of the forum_mining class.
	 * Please use the getter and setter predefined in the forum_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the forum table representing class.
	 **/
	abstract Map<Long, ForumMining> generateForumMining();

	/**
	 * Has to create and fill the group_user table.
	 * This table describes which user are in which groups.
	 * The attributes are described in the documentation of the group_user_mining class.
	 * Please use the getter and setter predefined in the group_user_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the group_user table representing class.
	 **/
	abstract Map<Long, GroupUserMining> generateGroupUserMining();

	/**
	 * Has to create and fill the group table.
	 * This table describes the groups in the LMS.
	 * The attributes are described in the documentation of the group_mining class.
	 * Please use the getter and setter predefined in the group_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the group table representing class.
	 **/
	abstract Map<Long, GroupMining> generateGroupMining();

	/**
	 * Has to create and fill the question_log table.
	 * This table contains the actions which are done on questions.
	 * The attributes are described in the documentation of the question_log_mining class.
	 * Please use the getter and setter predefined in the question_log_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the question_log table representing class.
	 **/
	abstract Map<Long, QuestionLogMining> generateQuestionLogMining();

	/**
	 * Has to create and fill the quiz_log table.
	 * This table contains the actions which are done on quiz.
	 * The attributes are described in the documentation of the quiz_log_mining class.
	 * Please use the getter and setter predefined in the quiz_log_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the quiz_log table representing class.
	 **/
	abstract Map<Long, QuizLogMining> generateQuizLogMining();

	/**
	 * Has to create and fill the assignment_log table.
	 * This table contains the actions which are done on assignment.
	 * The attributes are described in the documentation of the assignment_log_mining class.
	 * Please use the getter and setter predefined in the assignment_log_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the assignment_log table representing class.
	 **/
	abstract Map<Long, AssignmentLogMining> generateAssignmentLogMining();

	/**
	 * Has to create and fill the scorm_log table.
	 * This table contains the actions which are done on scorm.
	 * The attributes are described in the documentation of the scorm_log_mining class.
	 * Please use the getter and setter predefined in the scorm_log_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the scorm_log table representing class.
	 **/
	abstract Map<Long, ScormLogMining> generateScormLogMining();

	/**
	 * Has to create and fill the quiz_user table.
	 * This table describes which user gets which grade in which quiz.
	 * The attributes are described in the documentation of the quiz_user_mining class.
	 * Please use the getter and setter predefined in the quiz_user_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the quiz_user table representing class.
	 **/
	abstract Map<Long, QuizUserMining> generateQuizUserMining();

	/**
	 * Has to create and fill the quiz table.
	 * This table describes the quiz in the LMS.
	 * The attributes are described in the documentation of the quiz_mining class.
	 * Please use the getter and setter predefined in the quiz_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the quiz table representing class.
	 **/
	abstract Map<Long, QuizMining> generateQuizMining();

	/**
	 * Has to create and fill the assignment table.
	 * This table describes the assignment in the LMS.
	 * The attributes are described in the documentation of the assignment_mining class.
	 * Please use the getter and setter predefined in the assignment_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the assignment table representing class.
	 **/
	abstract Map<Long, AssignmentMining> generateAssignmentMining();

	/**
	 * Has to create and fill the scorm table.
	 * This table describes the scorm packages in the LMS.
	 * The attributes are described in the documentation of the scorm_mining class.
	 * Please use the getter and setter predefined in the scorm_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the scorm table representing class.
	 **/
	abstract Map<Long, ScormMining> generateScormMining();

	/**
	 * Has to create and fill the quiz_question table.
	 * This table describes which question is used in which quiz.
	 * The attributes are described in the documentation of the quiz_question_mining class.
	 * Please use the getter and setter predefined in the quiz_question_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the quiz_question table representing class.
	 **/
	abstract Map<Long, QuizQuestionMining> generateQuizQuestionMining();

	/**
	 * Has to create and fill the question table.
	 * This table describes the question in the LMS.
	 * The attributes are described in the documentation of the question_mining class.
	 * Please use the getter and setter predefined in the question_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the question table representing class.
	 **/
	abstract Map<Long, QuestionMining> generateQuestionMining();

	/**
	 * Has to create and fill the resource table.
	 * This table describes the resource in the LMS.
	 * The attributes are described in the documentation of the resource_mining class.
	 * Please use the getter and setter predefined in the resource_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the resource table representing class.
	 **/
	abstract Map<Long, ResourceMining> generateResourceMining();

	/**
	 * Has to create and fill the resource_log table.
	 * This table contains the actions which are done on resource.
	 * The attributes are described in the documentation of the resource_log_mining class.
	 * Please use the getter and setter predefined in the resource_log_mining class to fill the tables within this
	 * method.
	 * 
	 * @return A list of instances of the resource_log table representing class.
	 **/
	abstract Map<Long, ResourceLogMining> generateResourceLogMining();

	/**
	 * Has to create and fill the user table.
	 * This table describes the user in the LMS.
	 * The attributes are described in the documentation of the user_mining class.
	 * Please use the getter and setter predefined in the user_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the user table representing class.
	 **/
	abstract Map<Long, UserMining> generateUserMining();

	/**
	 * Has to create and fill the wiki_log table.
	 * This table contains the actions which are done on wiki.
	 * The attributes are described in the documentation of the wiki_log_mining class.
	 * Please use the getter and setter predefined in the wiki_log_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the wiki_log table representing class.
	 **/
	abstract Map<Long, WikiLogMining> generateWikiLogMining();

	/**
	 * Has to create and fill the wiki table.
	 * This table describes the wiki in the LMS.
	 * The attributes are described in the documentation of the wiki_mining class.
	 * Please use the getter and setter predefined in the wiki_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the wiki table representing class.
	 **/
	abstract Map<Long, WikiMining> generateWikiMining();

	/**
	 * Has to create and fill the role table.
	 * This table describes the roles of users in the LMS.
	 * The attributes are described in the documentation of the role_mining class.
	 * Please use the getter and setter predefined in the role_mining class to fill the tables within this method.
	 * 
	 * @return A list of instances of the role table representing class.
	 **/
	abstract Map<Long, RoleMining> generateRoleMining();

	/**
	 * Generate level mining.
	 * 
	 * @return the list
	 */
	abstract Map<Long, LevelMining> generateLevelMining();

	/**
	 * Generate level association mining.
	 * 
	 * @return the list
	 */
	abstract Map<Long, LevelAssociationMining> generateLevelAssociationMining();

	/**
	 * Generate level course mining.
	 * 
	 * @return the list
	 */
	abstract Map<Long, LevelCourseMining> generateLevelCourseMining();

	/**
	 * Generate chat mining.
	 * 
	 * @return the list
	 */
	abstract Map<Long, ChatMining> generateChatMining();

	/**
	 * Generate chat log mining.
	 * 
	 * @return the list
	 */
	abstract Map<Long, ChatLogMining> generateChatLogMining();

}
