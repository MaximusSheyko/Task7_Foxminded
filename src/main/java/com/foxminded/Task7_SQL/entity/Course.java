package com.foxminded.Task7_SQL.entity;

import java.util.Objects;

public class Course {
    final private int id;
    private String name;
    private String description;
    
    public Course(int id, String name, String description) {
	this.id = id;
	this.name = name;
	this.description = description;
    }
       
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static class CourseBuild{
	    private int id;
	    private String name;
	    private String description;
	       
	    public CourseBuild setId(int id) {
	        this.id = id;
	        
	        return this;
	    }
	    
	    public CourseBuild setName(String name) {
	        this.name = name;
	        
	        return this;
	    }
	    
	    public CourseBuild setDescription(String description) {
	        this.description = description;
	        
	        return this;
	    }
	    
	    public Course build() {
		return new Course(id, name, description);
	    }
    }

    @Override
    public int hashCode() {
	return Objects.hash(description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Course other = (Course) obj;
	return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
	return "Course [id=" + id + ", name=" + name + ", description=" + description + "]";
    }
    
    
    
    
}
