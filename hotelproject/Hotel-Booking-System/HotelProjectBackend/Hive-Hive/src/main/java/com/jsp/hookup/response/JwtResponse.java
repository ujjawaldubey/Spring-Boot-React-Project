package com.jsp.hookup.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
	
	private long id;
	private String email;
	private String token;
	private String type = "Bearer";
	
	private List<String> roles;

	public JwtResponse(long id, String email, String token, List<String> roles) {
		super();
		this.id = id;
		this.email = email;
		this.token = token;
		this.roles = roles;
	}

	
	

}
