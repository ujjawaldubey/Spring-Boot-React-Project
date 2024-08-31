package com.jsp.hookup.service;

import java.util.List;

import com.jsp.hookup.model.Role;
import com.jsp.hookup.model.User;

public interface RoleService {
	
	List<Role> getRoles();
	
	Role createRole(Role theRole);
	
	void deleteRole(Long id);
	
	Role findByName(String name);
	
	User removeUserFromRole(Long userId, Long roleId);
	
	User assignRoleToUser(Long userId, Long roleId);
	
	Role removeAllUsersFromRole(Long roleId);
	

}
