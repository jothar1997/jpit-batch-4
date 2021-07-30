package com.mmit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Item;
import com.mmit.service.BrandService;
import com.mmit.service.CategoryService;
import com.mmit.service.ItemService;

@Named
@ViewScoped
public class SearchItemBean implements Serializable {

	public String getMessage() {
		return message;
	}

	public String getItemName() {
		return itemName;
	}

	private static final long serialVersionUID = 1L;
	@Inject
	private ItemService service;
	
	private String message;
	
	private String itemName;
	
	private List<Item> items;
	@Inject
	private CategoryService cser;
	
	@Inject
	private BrandService bser;
	
	public ItemService getService() {
		return service;
	}

	public void setService(ItemService service) {
		this.service = service;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void init() {
		ExternalContext cxt = FacesContext.getCurrentInstance().getExternalContext();
		String cId = cxt.getRequestParameterMap().get("categoryId");
		String bId = cxt.getRequestParameterMap().get("brandId");
			items = new ArrayList<Item>();
		if(cId != null) {
			items = service.findByCategoryId(Integer.parseInt(cId));
			message = "Category";
			itemName = cser.findWithIdToGetName(Integer.parseInt(cId));
		}
		if(bId != null) {
			items = service.findByBrandId(Integer.parseInt(bId));
			message="Brand";
			itemName = bser.findWithIdToGetName(Integer.parseInt(bId));
		}
		
	}

}
