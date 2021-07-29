package com.mmit.security;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.mmit.bean.LoginBean;
import com.mmit.entity.Users;
import com.mmit.service.UserService;

@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserService service;
	
	@Inject
	private Pbkdf2PasswordHash encoder;
	
	@Inject
	private LoginBean loginbean;
	
	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential c = (UsernamePasswordCredential) credential;
		
		Users user = service.findWithEmail(c.getCaller());
		if(user == null) {
			throw new AppException("this email does not exists");
		}
		if(!encoder.verify(c.getPasswordAsString().toCharArray(), user.getPassword())) {
			throw new AppException("this password is incorrect");
		}
		loginbean.setLoginuser(user);
		return new CredentialValidationResult(user.getEmail(),Set.of(user.getAccessLevel().name()));
	}
	
	

}
