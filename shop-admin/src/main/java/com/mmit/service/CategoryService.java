package com.mmit.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Category;

@Stateless
public class CategoryService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private LoginBean loginuser;
	public List<Category> findAll(){
		return em.createNamedQuery("Category.findAll", Category.class).getResultList();
	}
	public  Category findById(int id) {
		return em.find(Category.class, id);
	}
	public void save(Category category) {
		if(category.getId() == 0) {
			category.setCreatedBy(loginuser.getLoginuser());
			em.persist(category);
		}else {
			category.setUpdatedBy(loginuser.getLoginuser());
			em.merge(category);
		}
		
	}
	public void delete(int id) {
		em.remove(findById(id));
		
	}
	public String findByName(Category c) {
		try {
			return em.createNamedQuery("Category.findByName", String.class)
					.setParameter("name", c.getName()).setParameter("id", c.getId()).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public Category findCategoryByName(String name) {
		try {
			return em.createNamedQuery("category.findwithname", Category.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public String findWithIdToGetName(int id) {
		
		return em.createNamedQuery("category.findname", String.class)
				.setParameter("cid", id).getSingleResult();
	}
}
