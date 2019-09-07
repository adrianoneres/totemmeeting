package br.com.totemti.totemmeeting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.totemti.totemmeeting.models.User;
import br.com.totemti.totemmeeting.services.SessionService;
import br.com.totemti.totemmeeting.util.dto.SignIn;

@RestController
@RequestMapping(value = "/sessions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {
	
	@Autowired
	private SessionService sessionService;
	
	@PostMapping
	public ResponseEntity create(@RequestBody User user) {
		try {
			SignIn signIn = sessionService.signIn(user);
			
			return ResponseEntity.status(200).body(signIn);
		} catch(IllegalArgumentException e) {
			return ResponseEntity.status(400).body(e);
		}
	}

}
