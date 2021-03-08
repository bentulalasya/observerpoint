package com.observepoint.test.test.web;

import com.observepoint.test.test.exception.DBException;
import com.observepoint.test.test.exception.ResourceNotFoundException;
import com.observepoint.test.test.model.Employee;
import com.observepoint.test.test.repository.EmployeeRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ComponentScan
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping
	List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@GetMapping("/{id}")
	Employee getEmployeeById(@PathVariable Long id) {

		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", id));
	}

	@PostMapping
	Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}

	@PutMapping("/{id}")
	Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {

		return employeeRepository.findById(id)
				.map(emp -> {
					emp.setName(employee.getName());
					emp.setDepartment(employee.getDepartment());
					return employeeRepository.save(emp);
				})
				.orElseGet(() ->
						employeeRepository.save(employee)
				);
	}

	@DeleteMapping("/{id}")
	void deleteEmployeeById(@PathVariable Long id) {
		try {
			employeeRepository.deleteById(id);
		} catch (DataIntegrityViolationException | EmptyResultDataAccessException e) {
			throw new DBException(e.getMessage());
		}
	}
}
