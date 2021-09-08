package com.foxminded.Task7_SQL.entity;

import java.util.Objects;

public class Student {
    private final int personalID;
    private int groupID;
    private String firstName;
    private String lastName;
    
    public Student(int personalID, int groupID, String firstName, String lastName) {
	this.personalID = personalID;
	this.groupID = groupID;
	this.firstName = firstName;
	this.lastName = lastName;
    }

    public int getPersonalID() {
        return personalID;
    }

    public int getGroupID() {
        return groupID;
    }
    
    public void setGroupId(int groupId) {
	this.groupID = groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public static class StudentBuild {
	private int personalID;
	private int groupID;
	private String firstName;
	private String lastName;
	
	public StudentBuild setPersonalID(int personalID) {
	    this.personalID = personalID;

	    return this;
	}
	
	public StudentBuild setGroupID(int groupID) {
	    this.groupID = groupID;
	    
	    return this;
	}
	
	public StudentBuild setFirstName(String firstName) {
	    this.firstName = firstName;
	    
	    return this;
	}
	
	public StudentBuild setLastName(String lastName) {
	    this.lastName = lastName;
	    
	    return this;
	}
	
	public Student build() {
	    return new Student(personalID, groupID, firstName, lastName);
	}
	
	
    }
    
    @Override
    public int hashCode() {
	return Objects.hash(firstName, groupID, lastName, personalID);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Student other = (Student) obj;
	return Objects.equals(firstName, other.firstName) && groupID == other.groupID
		&& Objects.equals(lastName, other.lastName) && personalID == other.personalID;
    }

    @Override
    public String toString() {
	return "Student [personalID=" + personalID + ", groupID=" + groupID + ", firstName=" + firstName + ", lastName="
		+ lastName + "]";
    }
    
    
    
    
     
}
