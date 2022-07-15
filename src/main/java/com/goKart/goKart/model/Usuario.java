package com.goKart.goKart.model;

import lombok.Data;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_usuario")
@Data
public abstract class Usuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@NotBlank(message = "E-mail não pode ficar em branco")
	private String email;

	@NotBlank(message = "Senha não pode ficar em branco")
	private String senha;
	
	@NotBlank(message = "Nome não pode ficar em branco")
	private String nome;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis;
}

