package com.mpn.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.mpn.model.Employee;
import com.mpn.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("SELECT t FROM User t where t.username = :username and t.password = :password ")
    User login(@Param("username") String username, @Param("password") String password);
    
    User findByUsername(String username);
//    List<User> findAll();
//        List<User> findByRole(String name);
//        List<User> findByNameStartingWithIgnoreCase(String name);
}
