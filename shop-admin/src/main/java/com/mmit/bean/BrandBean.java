package com.mmit.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.entity.Brand;
import com.mmit.service.BrandService;

@ViewScoped
@Named
public class BrandBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private BrandService service;
	
	private boolean isDisabled=true;
	private String msg;
	
	public String getMsg() {
		return msg;
	}
	public boolean isDisabled() {
		return isDisabled;
	}
	public void checkBrand() {
		String name=service.findByName(brand);
		if(name!=null) {
			isDisabled=true;
			msg="This Brand is Already Exist";
		}else {
			isDisabled=false;
			msg="";
		}
	}
	public List<Brand> getBrands(){
		return service.findAll();
	};
	
	public BrandService getService() {
		return service;
	}

	public void setService(BrandService service) {
		this.service = service;
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

	private Brand brand;
	@PostConstruct
	public void init() {
		brand = new Brand();
	}
	
	public void save() {
		service.save(brand);
	}
	
	public void retriCat(int id) {
		if(id == 0) {
			brand = new Brand();
		}else {
			brand = service.findById(id);
		}
	}
	public String delete(int id) {
		service.delete(id);
		return null;
	}

}
