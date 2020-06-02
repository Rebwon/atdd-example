package com.rebwon.atddexample.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByLastName(String lastName);
}
