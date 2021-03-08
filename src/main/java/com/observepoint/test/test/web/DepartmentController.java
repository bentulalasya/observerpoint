package com.observepoint.test.test.web;

import com.observepoint.test.test.exception.DBException;
import com.observepoint.test.test.exception.ResourceNotFoundException;
import com.observepoint.test.test.model.Department;
import com.observepoint.test.test.repository.DepartmentRepository;
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
@RequestMapping("/department")
public class DepartmentController {

	private final DepartmentRepository departmentRepository;

	public DepartmentController(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@GetMapping
	List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	@GetMapping("/{id}")
	Department getDepartmentById(@PathVariable Long id) {
		return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", id));
	}

	@PostMapping
	Department createDepartment(@RequestBody Department department) {
		return departmentRepository.save(department);
	}

	@PutMapping("/{id}")
	Department updateDepartment(@RequestBody Department department, @PathVariable Long id) {

		return departmentRepository.findById(id)
				.map(dept -> {
					dept.setName(department.getName());
					return departmentRepository.save(dept);
				})
				.orElseGet(() ->
						departmentRepository.save(department)
				);
	}

	@DeleteMapping("/{id}")
	void deleteDepartmentById(@PathVariable Long id) {
		try {
			departmentRepository.deleteById(id);
		} catch (DataIntegrityViolationException | EmptyResultDataAccessException e) {
			throw new DBException(e.getMessage());
		}
	}
}
