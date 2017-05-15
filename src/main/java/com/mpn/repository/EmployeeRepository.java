package com.mpn.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mpn.model.Employee;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
//        @Query("")
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	List<Employee> findByRole(String role);
}
