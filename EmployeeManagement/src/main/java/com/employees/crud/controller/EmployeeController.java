package com.employees.crud.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employees.crud.model.domain.Employee;
import com.employees.crud.model.service.EmployeeDAOImpl;


@RestController
@RequestMapping("employees")
@WebFilter("/employees/*") //para añadir los headers a todos los endpoints

public class EmployeeController implements Filter{

	@Autowired // inyección de dependencia
	private EmployeeDAOImpl employeeService;
	

	@GetMapping //localhost:8080/employees -> GET
	public ResponseEntity<?> getAllEmployees() {

		ResponseEntity<?> employees = null;
		try { //array vacío
			employees = ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
		} catch (Exception ex) {
			employees = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return employees;
	}
	
	@RequestMapping(value = "{id}") //localhost:8080/employees/{id} -> GET
	public ResponseEntity<?> getEmployeeDetails(@PathVariable("id") int id) {		
		
		ResponseEntity<?> result = null;
		
		try {			
			Optional<Employee> employee = employeeService.getEmployeeById(id);
			if(employee.isPresent()) {
				result = ResponseEntity.status(HttpStatus.OK).body(employee.get());
			}else {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");
			}
		} catch (Exception e) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		return result;
	}

	@PostMapping //localhost:8080/employees -> POST
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {

		ResponseEntity<?> result = null;
		try {				
			
			Employee newEmployee = employeeService.addEmployee(employee);
			
			result = ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
			
		} catch (Exception ex) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}

		return result;
	}
	
	@PutMapping //localhost:8080/employees -> PUT
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {

		ResponseEntity<?> result = null;
		try {
			Optional<Employee> optionalEmployee = employeeService.getEmployeeById(employee.getId());
			
			if(optionalEmployee.isPresent()) {
				Employee updateEmployee = optionalEmployee.get();
				updateEmployee.setName(employee.getName());
				updateEmployee.setSurname(employee.getSurname());
				updateEmployee.setJob(employee.getJob());	
				employeeService.updateEmployee(updateEmployee);
				
				result = ResponseEntity.status(HttpStatus.OK).body(updateEmployee);
			}else {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado que intenta modificar no existe");
			}

		} catch (Exception ex) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}

		return result;
	}

	@DeleteMapping(value = "{id}") //localhost:8080/employees/{id} -> DELETE
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) {

		ResponseEntity<?> result = null;
		try {
			Optional<Employee> employee = employeeService.getEmployeeById(id);			
			
			if (employee.isPresent()) {
				employeeService.deleteEmployee(id);
				result = ResponseEntity.status(HttpStatus.OK).body("Empleado borrado");
			} else {
				result = ResponseEntity.status(HttpStatus.NOT_FOUND).body("El empleado no existe");		
			}

		} catch (Exception ex) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return result;
	}

	// flitrar por empleo
	@RequestMapping(value = "Filter/{job}") //localhost:8080/products/Filter/{job} -> GET
	private ResponseEntity<?> getEmployeeByJob(@PathVariable("job") String job) {		
		
		ResponseEntity<?> result = null;
		try {
			
			List<Employee> employeesJob = employeeService.getAllEmployees().stream()
							.filter(e -> e.getJob().getJobTitle().equalsIgnoreCase(job))
							.collect(Collectors.toList());
			
			result = ResponseEntity.ok(employeesJob);
			
		} catch (Exception ex) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
		return result;
	}
	
	//NIVEL 2 
	
	@PostMapping(value = "/uploadPhoto")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

		ResponseEntity<?> result = null;

		try {						
			employeeService.uploadPhoto(file);
			result = ResponseEntity.status(HttpStatus.OK).body("Imagen subida correctamente");
			
		} catch (Exception e) {
			result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

		return result;

	}
	
	
	//download photo	
	@GetMapping("/downloadPhoto/{filename}") // localhost:8080/employees/downloadPhoto/{filename} -> GET
	public ResponseEntity<Resource> getEmployeePhoto(@PathVariable("filename") String filename, HttpServletResponse response) {
		
		Resource resource = employeeService.downloadPhoto(filename);
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + resource.getFilename())
				.body(resource);
	}
	
	
	//NIVEL3 Añadir header
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	    httpServletResponse.setHeader("IT-Academy-Exercise", "Simple Service");
	    chain.doFilter(request, response);		
	}
	@Override
	public void destroy() {
	}

}
