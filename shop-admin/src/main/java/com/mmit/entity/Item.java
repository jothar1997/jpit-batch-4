package com.mmit.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Entity implementation class for Entity: Product
 *
 */
@Entity
@NamedQuery(name = "Item.findAll",query = "SELECT b FROM Item b")
public class Item implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false,unique = true)
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	private int price;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="category_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category category;
	
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name="brand_id")
	private Brand brand;
	private ClothType type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public ClothType getType() {
		return type;
	}
	public void setType(ClothType type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public enum ClothType{
		Male,Female
	}
	public Item() {
		super();
	}
	
	private String photo;
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
   
}
