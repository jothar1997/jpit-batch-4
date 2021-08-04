package com.mmit.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: OrderItems
 *
 */
@Entity
public class OrderDetails implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders order;
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	private int subQty;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getSubQty() {
		return subQty;
	}

	public void setSubQty(int subQty) {
		this.subQty = subQty;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OrderDetails() {
		super();
	}
	
	public int getSubPrice() {
		
		return this.subQty*this.getItem().getPrice();
	}
   
}
