package com.mmit.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Item;
import com.mmit.entity.OrderDetails;
import com.mmit.entity.Orders;
import com.mmit.service.ItemService;

@Named
@SessionScoped
public class ShopBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Orders order;
	@Inject
	private ItemService service;
	
	@PostConstruct
	private void init() {
		order = new Orders();
	}

	public Orders getOrder() {
		return order;
	}
	
	public int getItemCount() {
		return order.getDetails().size();
	}
	
	public void setOrder(Orders order) {
		this.order = order;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//	ajax
	public void addToCart(int itemId) {
//		increase qty
		for(OrderDetails od: order.getDetails()) {
			if(od.getItem().getId() == itemId) {
				od.setSubQty(od.getSubQty()+1);
				return;
			}
		}
		// add new item tode cart
		Item item = service.findById(itemId);
		OrderDetails newOrder = new OrderDetails();
		newOrder.setSubQty(1);
		newOrder.setItem(item);
		order.addOrderItem(newOrder);
		
	}
	
	public String removeFromCart(OrderDetails od) {
		order.getDetails().remove(od);
		
		return "checkin?faces-redirect=true";
	}
	
	public void updateCart(int itemId) {
		for(OrderDetails od: order.getDetails()) {
			if(od.getItem().getId() == itemId) {
				if(od.getSubQty()<1) {
					od.setSubQty(1);
				}
			}
		}
	}
	
}
