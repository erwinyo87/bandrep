package com.mpn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import com.mpn.model.Employee;
import com.mpn.repository.EmployeeRepository;

@Component("employeeService")
@EnableJpaRepositories(basePackages={"com.mpn.repository"})
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository emp;

	@Override
	public void saveEmployee(Employee employee) {
		emp.save(employee);
	}

	@Override
	public List<Employee> findByNameWithIgnoreCase(String name) {
		return emp.findByNameStartingWithIgnoreCase(name);
	}

	@Override
	public List<Employee> findByRole(String role) {
		return emp.findByRole(role);
	}

	@Override
	public List<Employee> findAll() {
		return (List<Employee>) emp.findAll();
	}
}
