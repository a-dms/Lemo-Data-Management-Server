package de.lemo.dms.connectors.moodle.moodleDBclass;

public class Chat_LMS {

	private long id;
	private String title;
	private String description;
	private long chattime;
	private long timemodified;
	
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getChattime() {
		return chattime;
	}
	public void setChattime(long chattime) {
		this.chattime = chattime;
	}	
}