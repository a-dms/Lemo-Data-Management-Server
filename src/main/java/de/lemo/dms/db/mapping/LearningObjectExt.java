package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "umed_learningObjectExt")
public class LearningObjectExt{

	private long id;
	private LearningObject learningObject;
	private String value;
	private String attr;
	

	
	
	/**
	 * @return the value
	 */
	@Lob
	@Column(name="value")
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the learningId
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningObject")
	public LearningObject getLearningObject() {
		return learningObject;
	}
	/**
	 * @param learningId the learningId to set
	 */
	public void setLearningObject(LearningObject learningObject) {
		this.learningObject = learningObject;
	}

	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean equals(LearningObjectExt o) {
		if ((o.getId() == this.getId()) && (o instanceof LearningObjectExt)) {
			return true;
		}
		return false;
	}
	/**
	 * @return the attr
	 */
	@Column(name="attr")
	public String getAttr() {
		return attr;
	}
	/**
	 * @param attr the attr to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	public void setLearningObject(final long learningObject, final Map<Long, LearningObject> learningObjects,
			final Map<Long, LearningObject> oldLearningObjects) {

		if (learningObjects.get(learningObject) != null)
		{
			this.learningObject = learningObjects.get(learningObject);
			learningObjects.get(learningObject).addLearningObjectExtension(this);
		}
		if ((this.learningObject == null) && (oldLearningObjects.get(learningObject) != null))
		{
			this.learningObject = oldLearningObjects.get(learningObject);
			oldLearningObjects.get(learningObject).addLearningObjectExtension(this);
			
		}
	}
	
	
	
}
