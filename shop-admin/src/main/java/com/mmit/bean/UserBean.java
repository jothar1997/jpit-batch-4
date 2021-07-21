package com.mmit.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Users;
import com.mmit.service.UserService;

@ViewScoped
@Named
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Users user;
	@Inject
	private UserService service;
	
	private String msg;
	public String getMsg() {
		return msg;
	}
	public List<Users> getUsers(){
		return service.findAll();
	}
	@PostConstruct
	public void init() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if ( id == null) {
			user = new Users();
		}else {
			user = service.findById(Integer.parseInt(id));
		}
	}
	FacesContext cxt = FacesContext.getCurrentInstance();
	public String save() {
		
		try {
			service.save(user);
		} catch (EJBException e) {
			FacesContext cxt = FacesContext.getCurrentInstance();
			cxt.addMessage("form:email",new FacesMessage("Email Error.Choose Another Email"));
			return null;
		}
		
		return "user?faces-redirect=true";
	}
	public String remove(int id) {
		service.remove(id);
		return null;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public UserService getService() {
		return service;
	}
	public void setService(UserService service) {
		this.service = service;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void checkEmail() {
		String mail = service.findByEmail(user);
		if(mail!=null) {
			msg="This Email is Already Exists";
		}else {
			msg="";
		}
	}

}
