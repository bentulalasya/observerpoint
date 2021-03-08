package com.observepoint.test.test.repository;

import com.observepoint.test.test.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
