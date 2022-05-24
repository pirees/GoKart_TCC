package com.goKart.goKart.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Piloto extends Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Column
	private Estado estado;
	
	@NotNull @NotBlank(message = "Cidade não pode ficar em branco")
	private String cidade;
	
	@Column
	private Nivel nivel;
	
	@Column
	@NotBlank(message = "Sobrenome não pode ficar em branco")
	private String sobrenome;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Piloto() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNivel().gettipoNivel();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getPerfis();
	}

	@Override
	public String getPassword() {
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		System.out.println(getId());
		return this.getEmail();
		
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
