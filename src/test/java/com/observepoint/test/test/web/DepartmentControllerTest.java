package com.observepoint.test.test.web;

import com.observepoint.test.test.exception.DBException;
import com.observepoint.test.test.exception.ResourceNotFoundException;
import com.observepoint.test.test.model.Department;
import com.observepoint.test.test.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

	@Mock
	private DepartmentRepository repository;

	private DepartmentController departmentController;

	private final List<Department> expectedDepartments = new ArrayList<>();

	@BeforeEach
	void setUp() {
		departmentController = new DepartmentController(repository);

		Department department = new Department("testDept1");
		expectedDepartments.add(department);

		department = new Department();
		department.setName("testDept2");
		expectedDepartments.add(department);

	}

	@Test
	void getAllDepartmentsTest() {
		when(repository.findAll()).thenReturn(expectedDepartments);
		List<Department> departments = departmentController.getAllDepartments();
		assertEquals(expectedDepartments, departments);
	}

	@Test
	void getDepartmentByIdTest() {
		when(repository.findById(1L)).thenReturn(Optional.of(expectedDepartments.get(0)));
		Department department = departmentController.getDepartmentById(1L);
		assertEquals(expectedDepartments.get(0), department);
	}

	@Test
	void getDepartmentByIdNotFoundTest() {
		assertThrows(
				ResourceNotFoundException.class, () ->
						departmentController.getDepartmentById(0L));
	}

	@Test
	void createDepartmentTest() {
		Department department = new Department("testDept3");
		when(repository.save(department)).thenReturn(department);
		Department departmentActual = departmentController.createDepartment(department);
		assertEquals(department, departmentActual);
	}

	@Test
	void updateExistingDepartmentTest() {
		expectedDepartments.get(0).setName("newName");
		when(repository.findById(1L)).thenReturn(Optional.ofNullable(expectedDepartments.get(0)));

		when(repository.save(expectedDepartments.get(0))).thenReturn(expectedDepartments.get(0));

		Department departmentActual = departmentController.updateDepartment(expectedDepartments.get(0), 1L);
		assertEquals(expectedDepartments.get(0), departmentActual);

	}

	@Test
	void updateNewDepartmentTest() {

		when(repository.findById(0L)).thenReturn(Optional.empty());
		when(repository.save(expectedDepartments.get(0))).thenReturn(expectedDepartments.get(0));

		Department departmentActual = departmentController.updateDepartment(expectedDepartments.get(0), 0L);
		assertEquals(expectedDepartments.get(0), departmentActual);
	}

	@Test
	void deleteEmployeeTest() {
		departmentController.deleteDepartmentById(1L);
		verify(repository).deleteById(1L);
	}

	@ParameterizedTest
	@MethodSource("exceptionArguments")
	void deleteEmployeeExceptionTest(Class<Exception> exceptionClass) {
		doThrow(exceptionClass).when(repository).deleteById(0L);
		assertThrows(
				DBException.class, () ->
						departmentController.deleteDepartmentById(0L));
	}

	private static Stream<Arguments> exceptionArguments() {
		return Stream.of(Arguments.of(DataIntegrityViolationException.class, EmptyResultDataAccessException.class));
	}

}
