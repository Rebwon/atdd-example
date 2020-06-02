package com.rebwon.atddexample.employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	private final GreetingService service;

	public EmployeeController(GreetingService service) {
		this.service = service;
	}

	@GetMapping("/hello/{lastName}")
	public String greeting(@PathVariable String lastName) {
		return service.greet(lastName);
	}
}
