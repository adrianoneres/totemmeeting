package br.com.totemti.totemmeeting.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemti.totemmeeting.models.User;
import br.com.totemti.totemmeeting.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User create(User newUser) {
		newUser.setPassword(DigestUtils.md5Hex(newUser.getPassword()));
		return userRepository.save(newUser);
	}

}
