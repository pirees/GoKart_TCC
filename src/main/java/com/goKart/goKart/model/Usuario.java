package com.goKart.goKart.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_usuario")
public abstract class Usuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @NotBlank(message = "Cidade não pode ficar em branco")
	private String cidade;
	
	@Column(unique=true)
	@NotNull @NotBlank(message = "E-mail não pode ficar em branco")
	private String email;

	@NotBlank
	@NotNull @NotBlank(message = "Senha não pode ficar em branco")
	private String senha;
	
	@Column
	@NotNull @NotBlank(message = "Nome não pode ficar em branco")
	private String nome;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public Usuario() {
		super();
	}
	
	

}

