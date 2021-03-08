package com.observepoint.test.test;

import com.observepoint.test.test.model.Department;
import com.observepoint.test.test.model.Employee;
import com.observepoint.test.test.repository.DepartmentRepository;
import com.observepoint.test.test.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}


	@Bean
	boolean initDatabase(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {

		Department departmentEng = new Department("engineering");
		Department departmentHR = new Department("hr");
		Department departmentSup = new Department("support");

		List<Department> departments = new ArrayList<>();
		departments.add(departmentEng);
		departments.add(departmentHR);
		departments.add(departmentSup);

		Employee employeeLasya = new Employee("Lasya Bentula", departmentEng);

		Employee employeeTest1 = new Employee("Test 1", departmentEng);

		Employee employeeTest2 = new Employee("Test 2", departmentHR);

		List<Employee> employees = new ArrayList<>();
		employees.add(employeeLasya);
		employees.add(employeeTest1);
		employees.add(employeeTest2);
		departmentRepository.saveAll(departments);
		employeeRepository.saveAll(employees);

		return true;
	}
}
