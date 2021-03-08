package com.observepoint.test.test.repository;

import com.observepoint.test.test.model.Department;
import com.observepoint.test.test.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class EmployeeRepositoryTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository repository;

	@Test
	public void testSaveNewProduct() {
		Department department = new Department();
		department.setName("testDept");
		entityManager.persist(department);

		Employee employee = new Employee();
		employee.setName("testEmp");
		employee.setDepartment(department);

		entityManager.persist(employee);

		Optional<Employee> empFromDB = repository.findById(employee.getId());

		assertEquals(employee.getName(), empFromDB.get().getName());

		assertEquals(employee.getDepartment(), empFromDB.get().getDepartment());
	}
}
