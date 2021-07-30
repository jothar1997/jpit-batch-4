package com.mmit.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Item;

@Stateless
public class ItemService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private LoginBean loginuser;
	public List<Item> findAll(){
		return em.createNamedQuery("Item.findAll", Item.class).getResultList();
	}
	public  Item findById(int id) {
		return em.find(Item.class, id);
	}
	public void save(Item item) {
		if(item.getId() == 0 ) {
			item.setCreatedBy(loginuser.getLoginuser());
			em.persist(item);
		}else {
			item.setUpdatedBy(loginuser.getLoginuser());
			em.merge(item);
		}
		
	}
	public void remove(int id) {
		em.remove(findById(id));
		
	}
	public String findPhoto(int id) {
		
		return em.createNamedQuery("item.findphoto", String.class)
				.setParameter("id", id).getSingleResult();
				
	}
	public List<Item> findByCategoryId(int id) {
		
		return em.createNamedQuery("item.findwithcategoryid", Item.class)
				.setParameter("cid", id).getResultList();
	}
	public List<Item> findByBrandId(int id) {
		// TODO Auto-generated method stub
		return em.createNamedQuery("item.findwithbrandid", Item.class)
				.setParameter("bid", id).getResultList();
	}
}	
