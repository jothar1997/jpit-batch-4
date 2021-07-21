package com.mmit.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mmit.entity.Users;

@Stateless
public class UserService {
	@PersistenceContext
	private EntityManager em;
	
	public List<Users> findAll(){
		return em.createNamedQuery("Users.findAll", Users.class).getResultList();
	}
	public  Users findById(int id) {
		return em.find(Users.class, id);
	}
	public void save(Users user) {
		if(user.getId() == 0) {
			em.persist(user);
		}else {
			em.merge(user);
		}
		
	}
	public void remove(int id) {
		em.remove(findById(id));
		
	}
	public String findByEmail(Users user) {
		try {
			return em.createNamedQuery("User.findEmail", String.class)
					.setParameter("email", user.getEmail()).setParameter("id", user.getId()).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
