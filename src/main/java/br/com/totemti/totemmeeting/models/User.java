package br.com.totemti.totemmeeting.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "users_seq", allocationSize = 1)
public class User {
	
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq")
	private Long id;
	
	@Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Getter @Setter
	private String password;

}
