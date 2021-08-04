package com.mmit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Orders;
import com.mmit.entity.Users;
import com.mmit.service.OrderService;
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
	
	@Inject 
	private OrderService oservice;
	
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	private Orders order;
	
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	private List<Orders> orders;
	@PostConstruct
	public void init() {
		int id = loginuser.getLoginuser().getId();
		String orderid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("order");
		if(id != 0) {
			user = service.findById(id);
			orders = oservice.findByUserId(id);
		}else {
			user = new Users();
			orders = new ArrayList<Orders>();
		}
		if(orderid !=null) {
			order = oservice.findById(Long.parseLong(orderid));
		}else {
			order = new Orders();
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
