package com.rebwon.atddexample.employee;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GreetingService {
	private final EmployeeRepository repository;

	public GreetingService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public String greet(String lastName) {
		Optional<Employee> employee =
			repository.findByLastName(lastName);
		return employee
			.map(e -> String.format("Hello %s %s!", e.getFirstName(), e.getLastName()))
			.orElse("Who is this " + lastName + " you're talking about?");
	}
}
