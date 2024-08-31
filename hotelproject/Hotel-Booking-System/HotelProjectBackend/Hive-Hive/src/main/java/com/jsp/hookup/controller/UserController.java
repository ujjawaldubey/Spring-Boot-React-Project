package com.jsp.hookup.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.hookup.model.User;
import com.jsp.hookup.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getUsers(){
		return new ResponseEntity<>(userService.getUsers(),HttpStatus.FOUND);
	}
	
	@GetMapping("/{email}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
		try {
			User theUser = userService.getUser(email);
			return ResponseEntity.ok(theUser);
			
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error featching user");
		}
		
	}
	
	
	@DeleteMapping("/delete/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or ((hasRole('ROLE_USER') and #email == principal.username)) ")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String email){
		try {
			userService.deleteUser(email);
			return ResponseEntity.ok("User Deleted succesfully");
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
		}
	}
	
	
}
