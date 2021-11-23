package com.employees.crud.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.employees.crud.model.domain.Employee;


public interface IEmployeeDAO {

	//READ
	List<Employee> getAllEmployees();
	
	//READ ONE EMPLOYEE BY ID
	Optional<Employee> getEmployeeById(int id);
	
	//CREATE
	Employee addEmployee(Employee employee);
	
	//UPDATE
	Employee updateEmployee(Employee employee);
	
	//FILTER BY JOB
	List<Employee> getEmployeeByJob(String jobTitle);

	//DELETE
	void deleteEmployee(int id);
	
	//upload employee photo
	void uploadPhoto(MultipartFile file) throws IOException;
	
	//download employee photo
	Resource downloadPhoto(String filename);
}
