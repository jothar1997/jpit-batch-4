package com.mmit.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Orders;
import com.mmit.entity.Users;
import com.mmit.service.OrderService;

@Named
@ViewScoped
public class PlaceOrderBean implements Serializable {
	@Inject
	private OrderService service;
	private static final long serialVersionUID = 1L;
	@Inject
	private LoginBean loginuser;
	@Inject
	private ShopBean shop;
	
	private String name;
	private String phone;
	private String address;
	@PostConstruct
	public void init() {
		Users user = loginuser.getLoginuser();
		name = user.getName();
		phone = user.getPhone();
		address = user.getAddress();
		
	}
	public LoginBean getLoginuser() {
		return loginuser;
	}
	public void setLoginuser(LoginBean loginuser) {
		this.loginuser = loginuser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String placeOrder() {
		service.createOrder(name,phone,address,shop.getOrder());
		shop.setOrder(new Orders());//after order
		return "/index?faces-redirect=true";
		
	}

}
