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
@NamedQuery(name = "item.findphoto",query = "SELECT b.photo FROM Item b WHERE b.id = :id")
@NamedQuery(name = "item.findwithcategoryid",query = "SELECT b FROM Item b WHERE b.category.id = :cid")
@NamedQuery(name = "item.findwithbrandid",query = "SELECT b FROM Item b WHERE b.brand.id = :bid")
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
	
	@Enumerated(EnumType.STRING)
	private ClothType type;
	
	public enum ClothType{
		Men,Women
	}
	
	public ClothType getType() {
		return type;
	}
	public void setType(ClothType type) {
		this.type = type;
	}
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Item() {
		super();
	}
	
	private String photo;
	
	@ManyToOne
	@JoinColumn(name = "user_createdBy")
	private Users createdBy;
	
	@ManyToOne
	@JoinColumn(name = "user_updatedBy")
	private Users updatedBy;
	
	public Users getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}
	public Users getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
   
}
