package com.goKart.goKart.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "tb_reserva")
public class Reserva {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int nrReserva;
	
	private boolean confirmado = false;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="kartodromo_id")
	private Kartodromo kartodromo;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="bateria_id")
	private Bateria bateria;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="piloto_id")
	private Piloto piloto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNrReserva() {
		return nrReserva;
	}

	public void setNrReserva(int nrReserva) {
		this.nrReserva = nrReserva;
	}

	public Kartodromo getKartodromo() {
		return kartodromo;
	}

	public void setKartodromo(Kartodromo kartodromo) {
		this.kartodromo = kartodromo;
	}

	public Bateria getBateria() {
		return bateria;
	}

	public void setBateria(Bateria bateria) {
		this.bateria = bateria;
	}
	
	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public Reserva() {
		super();
	}

}
