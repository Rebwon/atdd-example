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
		service = new GreetingService(repository);
	}

	@Test
	void greet_with_nonExisting_last_name_should_return_default_message() {
		given(repository.findByLastName(nonExistingLastName))
			.willReturn(Optional.empty());

		String msg = service.greet(nonExistingLastName);
		assertThat(msg).isEqualTo("Who is this " + nonExistingLastName + " you're talking about?");
	}

	@Test
	void greet_with_existing_last_name_should_return_hello_message_with_appropriate_names() {
		given(repository.findByLastName(existingLastName))
			.willReturn(Optional.of(new Employee(lastName, firstName)));

		String msg1 = service.greet(existingLastName);
		assertThat(msg1).isEqualTo(String.format("Hello %s %s!", firstName, lastName));
	}
}