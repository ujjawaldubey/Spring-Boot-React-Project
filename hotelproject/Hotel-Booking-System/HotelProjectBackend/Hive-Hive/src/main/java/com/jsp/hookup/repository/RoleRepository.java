package com.jsp.hookup.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.hookup.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String role);

//	boolean existsByName(Role role);
	boolean existsByName(String rolename);

	

}
