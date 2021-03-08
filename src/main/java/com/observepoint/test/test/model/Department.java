package com.observepoint.test.test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Department {

	@Id
	@GeneratedValue
	private Long id;

	private String name;


	public Department() {
	}

	public Department(String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
