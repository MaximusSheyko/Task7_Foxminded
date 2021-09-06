package com.foxminded.Task7_SQL.entity;

import java.util.Objects;

public class Group {
    private final int id;
    private String name;
    
    public Group(int id, String name) {
	this.id = id;
	this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
    public static class GroupBuilder {
	private int id;
	private String name;
	
	public GroupBuilder setId(int id) {
	    this.id = id;
	    
	    return this;
	}
	public GroupBuilder setName(String name) {
	    this.name = name;
	    
	    return this;
	}
	
	public Group build() {
	    return new Group(id, name);
	}
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Group other = (Group) obj;
	return id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
	return "Group [id=" + id + ", name=" + name + "]";
    }
    
    
    
    

}
