package com.mmit.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Item;
import com.mmit.service.ItemService;

@ViewScoped
@Named
public class ItemBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Item item;
	@Inject
	private ItemService service;
	
	public List<Item> getItems(){
		return service.findAll();
	}
	@PostConstruct
	public void init() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(id == null) {
			item = new Item();
		}else {
			item = service.findById(Integer.parseInt(id));
		}
		
	}
	public String save() {
		service.save(item);
		return "items?faces-redirect=true";
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public ItemService getService() {
		return service;
	}
	public void setService(ItemService service) {
		this.service = service;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String remove(int id) {
		service.remove(id);
		return "items?faces-redirect=true";
	}
	
}
