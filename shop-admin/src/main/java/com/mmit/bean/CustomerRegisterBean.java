package com.mmit.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Users;
import com.mmit.entity.Users.Role;
import com.mmit.service.UserService;

@Named
@ViewScoped
public class CustomerRegisterBean implements Serializable {
	public Users getUsernew() {
		return usernew;
	}
	public void setUsernew(Users usernew) {
		this.usernew = usernew;
	}
	private static final long serialVersionUID = 1L;

	private Users usernew;
	
	private int check=0;

	@Inject
	private UserService service;
	
	@PostConstruct
	public void init() {
		usernew = new Users();
		String profileid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("profile");
		String orderhistory = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderhistory");
		if(profileid != null) {
			check = Integer.parseInt(profileid);
		}
		if(orderhistory !=null) {
			check = Integer.parseInt(orderhistory);
		}
	}
	public int getCheck() {
		return check;
	}
	public String save() {
		usernew.setAccessLevel(Role.Customer);
		service.save(usernew);
		return "index?faces-redirect=true";
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
