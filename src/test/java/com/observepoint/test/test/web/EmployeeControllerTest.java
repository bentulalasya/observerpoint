package com.observepoint.test.test.web;

import com.observepoint.test.test.exception.DBException;
import com.observepoint.test.test.exception.ResourceNotFoundException;
import com.observepoint.test.test.model.Department;
import com.observepoint.test.test.model.Employee;
import com.observepoint.test.test.repository.EmployeeRepository;
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
public class EmployeeControllerTest {

	@Mock
	private EmployeeRepository repository;

	private EmployeeController employeeController;

	private final List<Employee> expectedEmployees = new ArrayList<>();

	@BeforeEach
	void setUp() {
		employeeController = new EmployeeController(repository);

		Department department = new Department("testDept1");
		Employee employee = new Employee();
		employee.setDepartment(department);
		employee.setName("testEmp1");
		expectedEmployees.add(employee);

		department = new Department();
		department.setName("testDept2");
		employee = new Employee();
		employee.setDepartment(department);
		employee.setName("testEmp2");
		expectedEmployees.add(employee);
	}

	@Test
	void getAllEmployeesTest() {
		when(repository.findAll()).thenReturn(expectedEmployees);
		List<Employee> employees = employeeController.getAllEmployees();
		assertEquals(expectedEmployees, employees);
	}

	@Test
	void getEmployeeByIdTest() {
		when(repository.findById(1L)).thenReturn(Optional.of(expectedEmployees.get(0)));
		Employee employee = employeeController.getEmployeeById(1L);
		assertEquals(expectedEmployees.get(0), employee);
	}

	@Test
	void getDEmployeeByIdNotFoundTest() {
		assertThrows(
				ResourceNotFoundException.class, () ->
						employeeController.getEmployeeById(0L));
	}

	@Test
	void createEmployeeTest() {
		Employee employee = new Employee("testEmp3", new Department("testDept3"));
		when(repository.save(employee)).thenReturn(employee);
		Employee employeeActual = employeeController.createEmployee(employee);
		assertEquals(employee, employeeActual);
	}

	@Test
	void updateExistingEmployeeTest() {
		expectedEmployees.get(0).setName("newName");
		when(repository.findById(1L)).thenReturn(Optional.ofNullable(expectedEmployees.get(0)));

		when(repository.save(expectedEmployees.get(0))).thenReturn(expectedEmployees.get(0));

		Employee employeeActual = employeeController.updateEmployee(expectedEmployees.get(0), 1L);
		assertEquals(expectedEmployees.get(0), employeeActual);

	}

	@Test
	void updateNewDepartmentTest() {

		when(repository.findById(0L)).thenReturn(Optional.empty());
		when(repository.save(expectedEmployees.get(0))).thenReturn(expectedEmployees.get(0));

		Employee employeeActual = employeeController.updateEmployee(expectedEmployees.get(0), 0L);
		assertEquals(expectedEmployees.get(0), employeeActual);
	}

	@Test
	void deleteEmployeeTest() {
		employeeController.deleteEmployeeById(1L);
		verify(repository).deleteById(1L);
	}

	@ParameterizedTest
	@MethodSource("exceptionArguments")
	void deleteEmployeeExceptionTest(Class<Exception> exceptionClass) {
		doThrow(exceptionClass).when(repository).deleteById(0L);
		assertThrows(
				DBException.class, () ->
						employeeController.deleteEmployeeById(0L));
	}

	private static Stream<Arguments> exceptionArguments() {
		return Stream.of(Arguments.of(DataIntegrityViolationException.class, EmptyResultDataAccessException.class));
	}

}
