package com.goKart.goKart.model;

public enum Nivel {

	Iniciante ("Iniciante"),
	Mediano ("Mediano"),
	Profissional ("Profissional");
	
	private String tipoNivel;
	
	public String gettipoNivel() {
		return tipoNivel;
	}

	public void settipoNivel(String tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	private Nivel(String tipoNivel) {
		this.tipoNivel = tipoNivel;
	}
}
