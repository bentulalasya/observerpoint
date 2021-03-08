package com.observepoint.test.test.repository;

import com.observepoint.test.test.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class DepartmentRepositoryTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private DepartmentRepository repository;

	@Test
	public void testSaveNewProduct() {
		Department department = new Department();
		department.setName("testDept");
		entityManager.persist(department);

		Optional<Department> departmentFromDB = repository.findById(department.getId());

		assertEquals(department.getName(), departmentFromDB.get().getName());
		
	}
}
