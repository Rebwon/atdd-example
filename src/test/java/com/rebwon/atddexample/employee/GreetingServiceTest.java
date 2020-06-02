package com.rebwon.atddexample.employee;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GreetingServiceTest {
	private GreetingService service;
	@Mock private EmployeeRepository repository;
	private final String nonExistingLastName = "nonExistingLastName";
	private final String existingLastName = "existingLastName";
	private final String firstName = "firstName";
	private final String lastName = "lastName";

	@BeforeEach
	void setUp() {
		service = new GreetingService();
		given(repository.findByLastName(nonExistingLastName))
			.willReturn(Optional.empty());
		given(repository.findByLastName(existingLastName))
			.willReturn(Optional.of(new Employee(lastName, firstName)));
	}

	@Test
	void greet_with_nonExisting_last_name_should_return_default_message() {
		/*String nonExistingLastName = "nonExistingLastName";
		String msg = service.greet(nonExistingLastName);
		assertThat(msg).isEqualTo("Who is this " + nonExistingLastName + " you're talking about?");*/

		String lastName = this.nonExistingLastName;
		Optional<Employee> employee = repository.findByLastName(lastName);
		String msg = employee
			.map(e -> String.format("Hello %s %s!", e.getFirstName(), e.getLastName()))
			.orElse("Who is this " + lastName + " you're talking about?");
		assertThat(msg).isEqualTo("Who is this " + lastName + " you're talking about?");

		Optional<Employee> employee1 = repository.findByLastName(existingLastName);
		String msg1 = employee1
			.map(e -> String.format("Hello %s %s!", e.getFirstName(), e.getLastName()))
			.orElse("Who is this " + existingLastName + " you're talking about?");
		assertThat(msg1).isEqualTo(String.format("Hello %s %s!", firstName, this.lastName));
	}
}