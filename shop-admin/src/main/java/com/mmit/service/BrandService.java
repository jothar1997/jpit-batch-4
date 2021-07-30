package com.mmit.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Brand;

@Stateless

public class BrandService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private LoginBean loginuser;
	public List<Brand> findAll(){
		return em.createNamedQuery("Brand.findAll", Brand.class).getResultList();
	}
	public  Brand findById(int id) {
		return em.find(Brand.class, id);
	}
	public void save(Brand brand) {
		if(brand.getId() == 0) {
			brand.setCreatedBy(loginuser.getLoginuser());
			em.persist(brand);
		}else {
			brand.setUpdatedBy(loginuser.getLoginuser());
			em.merge(brand);
		}
		
	}
	public void delete(int id) {
		em.remove(findById(id));
		
	}
	public String findByName(Brand brand) {
		try {
			return em.createNamedQuery("Brand.findName", String.class)
			.setParameter("name", brand.getName()).setParameter("id", brand.getId()).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public Brand findBrandByName(String name) {
		try {
			return em.createNamedQuery("brand.findwithname", Brand.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public String findWithIdToGetName(int id) {
		
		return em.createNamedQuery("brand.findname", String.class)
				.setParameter("bid", id).getSingleResult();
	}
}
