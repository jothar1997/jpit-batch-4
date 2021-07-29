package com.mmit.security;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import com.mmit.entity.Users.Role;

import com.mmit.entity.Users;
import com.mmit.service.UserService;

@ApplicationScoped
@Singleton
@Startup
public class AdminUserCreation {
	@Inject
	private UserService service;
	
	@PostConstruct
	private void init() {
		long count= service.countUser();
		
		if(count == 0) {
			Users u = new Users();
			u.setName("saikyawmyotun");
			u.setEmail("saikyawmyotun.it@gmail.com");
			u.setPassword("123");
			u.setAccessLevel(Role.Admin);
			u.setPhone("09761051414");
			u.setAddress("Shan State, Muse");
			service.save(u);
		}
		
		
		
	}
}
