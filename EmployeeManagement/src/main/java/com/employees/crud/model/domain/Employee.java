package com.employees.crud.model.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="employees")
public class Employee implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	@Column(name = "surname", nullable = false, length = 30)
	private String surname;
	
	@Column(name = "job")
	private Jobs job;
	

	public Employee(int id, String name, String surname, Jobs job) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.job = job;
	}


	public Employee() {
		
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public Jobs getJob() {
		return job;
	}


	public void setJob(Jobs job) {
		this.job = job;
	}

	

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", surname=" + surname + ", job=" + job + "]";
	}

	
}
