package com.mmit.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Orders;
import com.mmit.service.OrderService;

@Named
@ViewScoped
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private OrderService service;
	
	private List<Orders> orders;
	
	private Orders order;
	@PostConstruct
	private void init() {
		String orderid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderid");
		orders = service.findAll(); 
		if(orderid != null) {
			order = service.findById(Long.parseLong(orderid));
		}else {
			order = new Orders();
		}
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public List<Orders> getOrders() {
		return orders;
	}
	
	public void changeOrderStatus(String status,long orderId) {
		service.changeStatus(status,orderId);
		orders =service.findAll();
	}

}
