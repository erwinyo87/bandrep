package com.mpn.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import com.mpn.model.User;
import com.mpn.repository.UserRepository;

@Component("userService")
@EnableJpaRepositories(basePackages = {"com.mpn.repository"})
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository rep;

    @Override
    public void saveUser(User user) {
        rep.save(user);
    }

//	@Override
//	public List<User> findByNameWithIgnoreCase(String name) {
//		return emp.findByNameStartingWithIgnoreCase(name);
//	}
////
//	@Override
//	public List<User> findByRole(String role) {
//		return emp.findByRole(role);
//	}
    @Override
    public List<User> findAll() {
        return (List<User>) rep.findAll();
    }
//        @Override
//	public User login(String username, String password) {
//		return (User) emp.login(username, password);
//	}

    @Override
    public User findByUsername(String username) {
        return (User)rep.findByUsername(username);
    }
     @Override
    public User login(String username,String password) {
        return (User)rep.login(username, password);
    }

}
