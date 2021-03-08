package com.observepoint.test.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn
	private Department department;

	public Employee(String name, Department department) {
		this.name = name;
		this.department = department;
	}

	public Employee() {

	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Department getDepartment() {
		return this.department;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
