package br.com.totemti.totemmeeting.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemti.totemmeeting.models.User;
import br.com.totemti.totemmeeting.repositories.UserRepository;
import br.com.totemti.totemmeeting.util.TokenUtil;
import br.com.totemti.totemmeeting.util.dto.SignIn;

@Service
public class SessionService {
	
	@Autowired
	private UserRepository userRepository;
	
	public SignIn signIn(User user) {
		User signInUser = userRepository.findByEmail(user.getEmail());
		
		if (signInUser == null) {
			throw new IllegalArgumentException("Invalid e-mail.");
		}
		
		String encryptedPassword = DigestUtils.md5Hex(user.getPassword());
		
		if (!encryptedPassword.equals(signInUser.getPassword())) {
			throw new IllegalArgumentException("Invalid password.");
		}
		
		return new SignIn(TokenUtil.createToken(signInUser.getId()));
	}

}
