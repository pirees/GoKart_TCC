package com.goKart.goKart.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Kartodromo extends Usuario implements UserDetails  {
	
	@NotNull (message = "Estado não pode ficar em branco")
	private Estado estado;
	
	@NotNull @NotBlank(message = "CNPJ não pode ficar em branco")
	private String CNPJ;
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public String getCNPJ() {
		return CNPJ; 
	}
	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}
	
	public Kartodromo() {
		super();
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
