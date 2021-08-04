package com.mmit.service;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Delivery;
import com.mmit.entity.Orders;
import com.mmit.entity.Orders.Status;

@Stateless
public class OrderService {
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private LoginBean loginuser;

	public void createOrder(String name, String phone, String address, Orders order) {
	
		Delivery deli = new Delivery();
		deli.setReceiverName(name);
		deli.setReceiverPhone(phone);
		deli.setReceiverAddress(address);
		order.setCustomer(loginuser.getLoginuser());
		order.addDelivery(deli);
		order.setTotal(order.getTotalAmount());
		order.setStatus(Status.Pending);
		
		em.persist(order);
		
	}

	public List<Orders> findByUserId(int id) {
		
		return em.createNamedQuery("order.findwithuserid", Orders.class)
				.setParameter("userId", id).getResultList();
	}

	public Orders findById(Long id) {
		
		return em.createNamedQuery("order.findbyid", Orders.class)
				.setParameter("id", id).getSingleResult();
	}

	public List<Orders> findAll() {
		
		return em.createNamedQuery("order.findAll", Orders.class).getResultList();
	}

	public void changeStatus(String status, long orderId) {
		Orders order = findById(orderId);
		if(status.equals(Status.Receive.name())) {
			order.setStatus(Status.Receive);
			order.setReceivedDate(LocalDate.now());
		}else if(status.equals(Status.Deliver.name())) {
			order.setStatus(Status.Deliver);
			order.getDelivery().setDeliveryDate(LocalDate.now());
		}
		em.flush();
		
	}

}
