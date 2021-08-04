package com.mmit.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;


/**
 * Entity implementation class for Entity: Orders
 *
 */
@Entity
@NamedQuery(name = "order.findwithuserid",query = "SELECT c FROM Orders c WHERE c.customer.id = :userId")
@NamedQuery(name = "order.findbyid",query = "SELECT c FROM Orders c WHERE c.id = :id")
@NamedQuery(name="order.findAll",query = "SELECT c FROM Orders c")
public class Orders implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Users customer;
	@CreationTimestamp
	private LocalDate orderDate;
	private LocalDate receivedDate;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	private List<OrderDetails> details= new ArrayList<OrderDetails>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Users getCustomer() {
		return customer;
	}

	public void setCustomer(Users customer) {
		this.customer = customer;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDate receivedDate) {
		this.receivedDate = receivedDate;
	}

	public List<OrderDetails> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetails> details) {
		this.details = details;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Orders() {
		super();
	}
	
	public void addOrderItem(OrderDetails od) {
		od.setOrder(this);
		details.add(od);
	}
	
	public int getTotalQty() {
		return details.stream().mapToInt(od->od.getSubQty()).sum();
	}
	
	public int getTotalAmount() {
		return details.stream().mapToInt(od->od.getSubPrice()).sum();
	}
	@OneToOne(mappedBy = "order", cascade = { PERSIST, MERGE,CascadeType.REMOVE})
	private Delivery delivery;
	
	public void addDelivery(Delivery deli) {
		deli.setOrder(this);
		this.setDelivery(deli);
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
	@Enumerated(EnumType.STRING)
	private Status status;
	private int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	public enum Status{
		Pending,Receive,Deliver,Cance
	}
   
}
