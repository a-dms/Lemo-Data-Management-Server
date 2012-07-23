package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class TAnswerPosition  implements IClixMappingClass{

	private TAnswerPositionPK id;
	private Long person;
	private Long question;
	private Long task;
	private Long test;
	
	public TAnswerPositionPK getId() {
		return id;
	}
	
	public String getString()
	{
		return "TAnswerPosition�$"
				+this.getEvaluated()+"�$"
				+this.getPerson()+"�$"
				+this.getQuestion()+"�$"
				+this.getTask()+"�$"
				+this.getTest();
	}


	public void setId(TAnswerPositionPK id) {
		this.id = id;
	}


	private String evaluated;
	
	
	public Long getPerson() {
		return person;
	}


	public void setPerson(Long person) {
		this.person = person;
	}


	public Long getQuestion() {
		return question;
	}


	public void setQuestion(Long question) {
		this.question = question;
	}


	public Long getTask() {
		return task;
	}


	public void setTask(Long task) {
		this.task = task;
	}


	public Long getTest() {
		return test;
	}


	public void setTest(Long test) {
		this.test = test;
	}


	public String getEvaluated() {
		return evaluated;
	}


	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}


	public TAnswerPosition()
	{
	}
}
