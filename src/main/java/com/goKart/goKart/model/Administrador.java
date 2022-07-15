package com.goKart.goKart.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
public class Administrador extends Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Sobrenome não pode ficar em branco")
	private String sobrenome;

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
