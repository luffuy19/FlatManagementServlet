package com.chainsys.model;

public class Task {
	int id; 
	String description; 
	String status;
	int employeeId;
	public Task(int id, String description, String status, int employeeId) {
		super();
		this.id = id;
		this.description = description;
		this.status = status;
		this.employeeId = employeeId;
	}
	public int getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	public String getStatus() {
		return status;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	
	
}
