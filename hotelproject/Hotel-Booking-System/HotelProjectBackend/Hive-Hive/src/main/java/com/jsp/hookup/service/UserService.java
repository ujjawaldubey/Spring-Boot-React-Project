package com.jsp.hookup.service;

import java.util.List;

import com.jsp.hookup.model.User;

public interface UserService {
	
	User registerUser(User user);
	
	List<User> getUsers();
	
	void deleteUser(String email);
	
	User getUser(String email);

}
