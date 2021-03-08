package com.observepoint.test.test.repository;

import com.observepoint.test.test.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
