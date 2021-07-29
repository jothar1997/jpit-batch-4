package com.mmit.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.security.AppException;
import com.mmit.service.UserService;

@Named
@RequestScoped
public class ChangePasswordBean {
	@Inject
	private UserService service;
	
	private String currentPsw;
	private String newPsw;
	private String confirmPsw;
	public String getCurrentPsw() {
		return currentPsw;
	}
	public void setCurrentPsw(String currentPsw) {
		this.currentPsw = currentPsw;
	}
	public String getNewPsw() {
		return newPsw;
	}
	public void setNewPsw(String newPsw) {
		this.newPsw = newPsw;
	}
	public String getConfirmPsw() {
		return confirmPsw;
	}
	public void setConfirmPsw(String confirmPsw) {
		this.confirmPsw = confirmPsw;
	}
	
	public String changePsw() {
		try {
			service.changePsw(currentPsw,newPsw,confirmPsw);
		} catch (AppException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
			return null;
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index?faces-redirect=true";
	}
}
