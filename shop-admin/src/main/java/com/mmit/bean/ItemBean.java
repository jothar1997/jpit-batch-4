package com.mmit.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import com.mmit.entity.Item;

import com.mmit.service.ItemService;

@ViewScoped
@Named
public class ItemBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Item item;
	@Inject
	private ItemService service;
	
	
	public Part getImgPart() {
		return imgPart;
	}
	public void setImgPart(Part imgPart) {
		this.imgPart = imgPart;
	}
	
	private Part imgPart;
	
	

	private ServletContext s_context;
	private String imgFloder = null;
	
	
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
		
		s_context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		imgFloder  = s_context.getRealPath("/resources/uploads");
	}
	public String save() {
		try {
			
			if(imgPart!=null) {
				
				String photoname =getphotoName(imgPart.getSubmittedFileName());
				if(item.getId() != 0) {
					String oldPhoto = service.findPhoto(item.getId());
					if(oldPhoto != null ) {
						File oldphotoFile = new File(imgFloder+File.separator+oldPhoto);
						if(oldphotoFile.exists()) {
							oldphotoFile.delete();
						}
					}
				}
				imgPart.write(imgFloder+File.separator+photoname);//save to server
				
				item.setPhoto(photoname);// save in localhost
			}
			service.save(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "items?faces-redirect=true";
	}
	
	private String getphotoName(String uploadName) {
		String tmp = uploadName.substring(0,uploadName.lastIndexOf("."));
		String newName = item.getName()+"-"+System.currentTimeMillis();
		
		return uploadName.replace(tmp, newName);
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
