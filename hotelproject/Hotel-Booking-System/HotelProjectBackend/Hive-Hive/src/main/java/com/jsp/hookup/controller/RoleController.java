package com.jsp.hookup.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.hookup.exception.RoleAlreadyExistException;
import com.jsp.hookup.model.Role;
import com.jsp.hookup.model.User;
import com.jsp.hookup.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
	
	private final RoleService roleService;
	
	
	@GetMapping("/all-roles")
	public ResponseEntity<List<Role>> getAllRoles(){
		return new ResponseEntity<>(roleService.getRoles(),HttpStatus.FOUND);
	}
	
	@PostMapping("/create-new-role")
	public ResponseEntity<String> createRole(@RequestBody Role theRole){
		try {
			roleService.createRole(theRole);
			return ResponseEntity.ok("New role created successfully!");
		} catch (RoleAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/delete/{roleId}")
	public void deleteRole(@PathVariable Long roleId) {
		roleService.deleteRole(roleId);
	}
	
	@PostMapping("/remove-all-users-from-role/{rolrId}")
	public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
		return roleService.removeAllUsersFromRole(roleId);
	}
	
	@PostMapping("/remove-user-from-role")
	public User removeUserFromRole(@RequestParam("userId") long userId,@RequestParam("roleId") long roleId) {
		return roleService.removeUserFromRole(userId, roleId);
	}
	
	
	@PostMapping("/assign-user-to-role")
	public User assignUserToRole(@RequestParam("userId") long userId,@RequestParam("roleId") long roleId) {
		return roleService.assignRoleToUser(userId, roleId);
	}

}
