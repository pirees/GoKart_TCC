package com.goKart.goKart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
public class Perfil implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;

	public Perfil(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Perfil() {
		super();
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}
	
	
}
