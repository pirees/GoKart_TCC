package com.goKart.goKart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
public class Piloto extends Usuario {
	
	@Column
	private Estado estado;
	
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

	public Piloto() {
		super();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNivel().gettipoNivel();
	}

}
