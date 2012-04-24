package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

/**This class represents the log table for the wiki object.*/
public class WikiLogMining {

	private long id;
	private WikiMining wiki;
	private	UserMining user;
	private CourseMining course;
	private String action;
	private long timestamp;

	/** standard getter for the attribut id
	 * @return the identifier of the log entry
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the log entry
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut action
	 * @return the action which occur
	 */	
	public String getAction() {
		return action;
	}
	/** standard setter for the attribut action
	 * @param action the action which occur
	 */	
	public void setAction(String action) {
		this.action = action;
	}
	
	/** standard getter for the attribut 
	 * @return the timestamp the action did occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribut 
	 * @param timestamp the timestamp the logged action did occur
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/** standard getter for the attribut course
	 * @return the the course in which the action on the wiki occur
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of the course in which the action on the wiki occur as long value
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of courses in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {	
		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addWiki_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addWiki_log(this);
		}
	}
	/** standard setter for the attribut course
	 * @param course the the course in which the action on the wiki occur
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	
	/** standard getter for the attribut user
	 * @return the user who interact with the resource
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who interact with the resource
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribut 
	 * @param user the id of the user who interact with the resource
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addWiki_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addWiki_log(this);
		}
	}
	
	/** standard getter for the attribut wiki
	 * @return the wiki with which was interacted
	 */	
	public WikiMining getWiki() {
		return wiki;
	}
	/** standard setter for the attribut wiki
	 * @param wiki the wiki with which was interacted
	 */	
	public void setWiki(WikiMining wiki) {
		this.wiki = wiki;
	}
	/** parameterized setter for the attribut wiki
	 * @param wiki the id of the wiki with which was interacted
	 * @param wikiMining a list of new added wiki, which is searched for the wiki with the id submitted in the wiki parameter
	 * @param oldWikiMining a list of wiki in the miningdatabase, which is searched for the wiki with the id submitted in the wiki parameter
	 */	
	public void setWiki(long wiki, HashMap<Long, WikiMining> wikiMining, HashMap<Long, WikiMining> oldWikiMining) {		
		
		if(wikiMining.get(wiki) != null)
		{
			this.wiki = wikiMining.get(wiki);
			wikiMining.get(wiki).addWiki_log(this);
		}
		if(this.wiki == null && oldWikiMining.get(wiki) != null)
		{
			this.wiki = oldWikiMining.get(wiki);
			oldWikiMining.get(wiki).addWiki_log(this);
		}
	}
}