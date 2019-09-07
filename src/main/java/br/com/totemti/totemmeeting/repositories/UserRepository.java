package br.com.totemti.totemmeeting.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.totemti.totemmeeting.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByEmail(String email);
	
}
