package com.mpn.services;

import java.util.List;


import com.mpn.model.Employee;

public interface EmployeeService {
	public List<Employee> findAll();
	public void saveEmployee(Employee employee);
	public List<Employee> findByNameWithIgnoreCase(String name);
	public List<Employee> findByRole(String role);
}
