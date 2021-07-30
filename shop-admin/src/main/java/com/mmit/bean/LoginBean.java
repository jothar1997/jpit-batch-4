package com.mmit.bean;

import java.io.Serializable;
import java.net.http.HttpRequest;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mmit.entity.Users;
import com.mmit.entity.Users.Role;
import com.mmit.security.AppException;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	private Users loginuser;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Users getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(Users loginuser) {
		this.loginuser = loginuser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Inject
	SecurityContext secContext;
	public String login() {
		ExternalContext cxt = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getRequest();
		HttpServletResponse resp = (HttpServletResponse) cxt.getResponse();
		UsernamePasswordCredential cred = new UsernamePasswordCredential(email, password);
		try {
			AuthenticationStatus status = secContext.authenticate(req, resp, AuthenticationParameters.withParams().credential(cred));
			if(status == AuthenticationStatus.SUCCESS) {
				if(loginuser.getAccessLevel() == Role.Admin || loginuser.getAccessLevel() == Role.Staff) {
					return "/admin/dashboard?faces-redirect=true";
				}else {
					return "/index?faces-redirect=true";
				}
			}
			
		} catch (AppException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
		}
		return null;
	}
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index?faces-redirect=true";
	}

}
