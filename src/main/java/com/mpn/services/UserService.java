package com.mpn.services;

import java.util.List;


import com.mpn.model.User;

public interface UserService {
	public List<User> findAll();
	public void saveUser(User user);
        public User findByUsername(String username);
        public User login(String username,String password);
//	public List<User> findByNameWithIgnoreCase(String name);
//	public List<User> findByRole(String role);
}
