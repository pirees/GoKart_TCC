package com.goKart.goKart.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Kartodromo extends Usuario {
	
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

}
