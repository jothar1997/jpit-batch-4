package com.mmit.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Category;
import com.mmit.service.CategoryService;

@ViewScoped
@Named
public class CategoryBean implements Serializable  {
	@Inject
	private CategoryService service;
	private static final long serialVersionUID = 1L;
	private boolean isDisabled=true;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public boolean isDisabled() {
		return isDisabled;
	}

	

	public CategoryService getService() {
		return service;
	}

	public void setService(CategoryService service) {
		this.service = service;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Category category;

	
	public List<Category> getCategories(){
		return service.findAll();
	}
	
	public void checkCategory() {
		String name = service.findByName(category);
		
		if(name!=null) {
			msg = "This category already already exists";
			isDisabled = true;
		}else {
			msg = "";
			isDisabled=false;
		}
	}
	
	@PostConstruct
	public void init() {
		category = new Category();
	}
	
	public void save() {
		service.save(category);
	}
	
	public void retriCat(int id) {
		if(id == 0) {
			category = new Category();
		}else {
			category = service.findById(id);
		}
	}
	public String delete(int id) {
		service.delete(id);
		return null;
	}
	
	

}
