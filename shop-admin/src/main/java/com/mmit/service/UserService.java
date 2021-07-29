package com.mmit.service;

import java.security.Principal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Users;
import com.mmit.security.AppException;

@Stateless
public class UserService {
	@PersistenceContext
	private EntityManager em;
	@Inject
	private Pbkdf2PasswordHash encoder;
	
	@Inject
	private Principal principal;
	@Inject
	private LoginBean loginuser;
	
	public List<Users> findAll(){
		return em.createNamedQuery("Users.findAll", Users.class)
				
				.getResultList();
	}
	public List<Users> findWithoutMe(){
		return em.createNamedQuery("Users.findWithoutMe", Users.class)
				.setParameter("email",loginuser.getEmail())
				.getResultList();
	}
	public  Users findById(int id) {
		return em.find(Users.class, id);
	}
	public void save(Users user) {
		user.setPassword(encoder.generate(user.getPassword().toCharArray()));
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
	public long countUser() {
		
		return em.createNamedQuery("user.count", Long.class).getSingleResult();
	}
	public Users findWithEmail(String caller) {
		try {
			return em.createNamedQuery("user.findWithEmail", Users.class)
					.setParameter("email", caller)
					.getSingleResult();
		} catch (NoResultException e) {
			
		}
		return null;
	}
	public void changePsw(String currentPsw, String newPsw, String confirmPsw) {
		Users loginuser = findWithEmail(principal.getName());
		if(!encoder.verify(currentPsw.toCharArray(), loginuser.getPassword())) {
			throw new AppException("Incorrect Current Password");
		}
		if(!newPsw.equals(confirmPsw)) {
			throw new AppException("Incorrect Confirm Password");
		}
		loginuser.setPassword(encoder.generate(newPsw.toCharArray()));
		
	}
	
}
