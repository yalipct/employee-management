package com.employees.crud.model.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Jobs {
	ENGINEER("engineer", 30000), 
	MANAGER("manager", 40000), 
	SALESMAN("salesman", 20000),
	DEVELOPER("developer", 50000);
	
	private  String jobTitle;
	private  double salary;	
	

	private Jobs(String jobTitle, double salary) {
		this.jobTitle = jobTitle;
		this.salary = salary;
	}
	
	
	public String getJobTitle() {
		return jobTitle;
	}


	public double getSalary() {
		return salary;
	}	
	
	@JsonCreator
	public static Jobs decode(String str) {
		
		return Arrays.stream(Jobs.values()).filter(targetEnum -> targetEnum.getJobTitle().equalsIgnoreCase(str)).findFirst().orElse(null);
	}
	
}
