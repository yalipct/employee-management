package com.employees.crud.model.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employees.crud.model.domain.Employee;
import com.employees.crud.model.repository.EmployeeRepository;


@Service
public class EmployeeDAOImpl implements IEmployeeDAO {

	@Autowired
	private EmployeeRepository repository;

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> lista = repository.findAll();
		return lista;
	}

	@Override
	public Optional<Employee> getEmployeeById(int id) {
		return repository.findById(id);
	}

	@Override
	public List<Employee> getEmployeeByJob(String jobTitle) {

		return repository.findEmployeeByJob(jobTitle);
	}

	@Override
	public Employee addEmployee(Employee employee) {

		return repository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {

		return repository.save(employee);
	}

	@Override
	public void deleteEmployee(int id) {

		repository.deleteById(id);
	}
	

	@Override
	public void uploadPhoto(MultipartFile file) throws IOException {

		//System.out.println(file.getContentType());

		if (!file.isEmpty()) {
			
			if(!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
				throw new IOException("Error- Tipo de archivo aceptado: JPEG o PNG");
			}
			File upload_folder = new File("./uploads");

			String rutaArchivo = upload_folder.getAbsolutePath() + "//" + file.getOriginalFilename();
			FileOutputStream output = new FileOutputStream(rutaArchivo);
			output.write(file.getBytes());
			output.close();
		}else {
			throw new IOException("Debe seleccionar una imagen");
		}

	}

	@Override
	public Resource downloadPhoto(String filename) {
		String upload_folder = "./uploads/";
		Path path = Paths.get(upload_folder).toAbsolutePath().resolve(filename);
		
		Resource resource;
		try {
			resource = new UrlResource(path.toUri());
		
		} catch (MalformedURLException e) {
			throw new RuntimeException("Problema al leer el archivo", e);
		}
		if(resource.exists() || resource.isReadable()) {
			return resource;
		}else {
			throw new RuntimeException( "El archivo no existe o no es legible");
		}
	}

	
}
