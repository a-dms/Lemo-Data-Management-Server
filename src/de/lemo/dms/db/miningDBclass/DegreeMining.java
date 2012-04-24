package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

public class DegreeMining {

	

	private long id;
	private String title;

	private Set<DepartmentDegreeMining> department_degree = new HashSet<DepartmentDegreeMining>();
	private Set<DegreeCourseMining> degree_course = new HashSet<DegreeCourseMining>();
	
	public boolean equals(Object obj) {
		if(!(obj instanceof DegreeMining)){
			return false;			
		}
		DegreeMining r = (DegreeMining)obj;
		if(r.id == this.id){
			return true;
		}		
		return false;
	}	
	
	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the resource
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard setter for the attribut title
	 * @param title the title of the resource
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut title
	 * @return the title of the resource
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribut department_degreee
	 * @param department_degree a set of entrys in the department_degreee table which relate the degrees to the departments
	 */	
	public void setDepartment_degree(Set<DepartmentDegreeMining> department_degree) {
		this.department_degree = department_degree;
	}
	/** standard getter for the attribut 
	 * @return a set of entrys in the department_degreee table which relate the degree to the departments
	 */	
	public Set<DepartmentDegreeMining> getDepartment_degree() {
		return this.department_degree;
	}
	/** standard add method for the attribut department_degreee
	 * @param department_degree_add this entry will be added to the list of department_degree in this department
	 * */
	public void addDepartment_degree(DepartmentDegreeMining department_degree_add){	
		department_degree.add(department_degree_add);	
	}
	
	/** standard setter for the attribute department_degreee
	 * @param degree_course a set of entrys in the degree_course table which relate the degrees to the courses
	 */	
	public void setDegree_course(Set<DegreeCourseMining> degree_course) {
		this.degree_course = degree_course;
	}
	/** standard getter for the attribute 
	 * @return a set of entrys in the degree_course table which relate the degree to the courses
	 */	
	public Set<DegreeCourseMining> getDegree_course() {
		return this.degree_course;
	}
	/** standard add method for the attribute degree_course
	 * @param degree_course_add this entry will be added to the list of degree_course in this department
	 * */
	public void addDegree_course(DegreeCourseMining degree_course_add){	
		degree_course.add(degree_course_add);	
	}
	
}