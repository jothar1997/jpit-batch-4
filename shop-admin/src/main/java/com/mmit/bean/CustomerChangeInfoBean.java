package com.mmit.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Users;
import com.mmit.service.UserService;

@Named
@ViewScoped
public class CustomerChangeInfoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Users user;
	@Inject
	private LoginBean loginuser;
	@Inject
	private UserService service;
	
	@PostConstruct
	public void init() {
		int id = loginuser.getLoginuser().getId();
		
		if(id != 0) {
			user = service.findById(id);
		}else {
			user = new Users();
		}
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String update() {
		
		service.save(user);
		loginuser.setLoginuser(user);
		return "customer-setting?faces-redirect=true";
	}

}
