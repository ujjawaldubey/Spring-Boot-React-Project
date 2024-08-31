package com.jsp.hookup.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsp.hookup.exception.UserAlreadyExistsException;
import com.jsp.hookup.model.Role;
import com.jsp.hookup.model.User;
import com.jsp.hookup.repository.RoleRepository;
import com.jsp.hookup.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
	
	@Override
	public User registerUser(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException(user.getEmail()+" already exists");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		
		user.setRoles(Collections.singletonList(userRole));
		return userRepository.save(user);
		
		
		
	}

	@Override
	public List<User> getUsers() {
		
		return userRepository.findAll();
	}
	
	@Transactional
	@Override
	public void deleteUser(String email) {
		User theUser = getUser(email);
		if(theUser != null) {
			userRepository.deleteByEmail(email);			
		}
		
	}

	@Override
	public User getUser(String email) {
		
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
