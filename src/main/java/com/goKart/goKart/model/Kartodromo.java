package com.goKart.goKart.model;

import java.math.BigInteger;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
public class Kartodromo extends Usuario implements UserDetails  {
	
	private static final long serialVersionUID = 1L;

	@NotNull (message = "Estado não pode ficar em branco")
	private Estado estado;

	@org.hibernate.validator.constraints.br.CNPJ
	@NotBlank(message = "CNPJ não pode ficar em branco")
	private String CNPJ;

	@NotBlank(message = "Cidade não pode ficar em branco")
	private String cidade;

	@NotBlank(message = "CEP não pode ficar em branco")
	private String CEP;

	@NotBlank(message = "Logradouro não pode ficar em branco")
	private String logradouro;

	private String complemento;

	@NotBlank(message = "Celular não pode ficar em branco")
	private String celular;

	private StatusUsuario statusUsuario;

	@Lob
	private byte[] imagem;

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

	public boolean getStatusUsuario(StatusUsuario pendente) {
		return false;
	}
}
