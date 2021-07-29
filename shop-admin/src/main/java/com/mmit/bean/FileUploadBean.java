package com.mmit.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.mmit.entity.Brand;
import com.mmit.entity.Category;
import com.mmit.entity.Item;
import com.mmit.entity.Item.ClothType;
import com.mmit.service.BrandService;
import com.mmit.service.CategoryService;
import com.mmit.service.ItemService;

@Named
@ViewScoped
public class FileUploadBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ItemService service;
	@Inject
	private CategoryService cser;
	@Inject
	private BrandService bser;
	private Part file;
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String upload() {
		if(file!=null) {
			try(BufferedReader b = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
				String line = null;
				while((line=b.readLine())!= null) {
					System.out.println("line1"+line);
					Item i = getItem(line);
					service.save(i);
					
				}
			} catch (Exception e) {
				
			}
		}
		return null;
	}

	private Item getItem(String line) {
		System.out.println("line from function"+line);
		String arr1[]=line.split("\t");
		System.out.println("itemname"+arr1[0]);
		Item i = new Item();
		i.setName(arr1[0]);
//		for category
		Category c = cser.findCategoryByName(arr1[1]);
		if(c==null) {
			c = new Category();
			c.setName(arr1[1]);
			cser.save(c);
		}
		System.out.println("this is c name"+c.getName());
		i.setCategory(c);
//		for brand
		Brand b = bser.findBrandByName(arr1[2]);
		if(b == null) {
			b = new Brand();
			b.setName(arr1[2]);
			bser.save(b);
		}
		i.setBrand(b);
//		price
		i.setPrice(Integer.parseInt(arr1[3]));
//		type
		i.setDescription(arr1[4]);
		return i;
	}
	

}
