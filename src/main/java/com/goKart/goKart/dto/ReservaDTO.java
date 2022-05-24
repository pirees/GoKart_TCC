package com.goKart.goKart.dto;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;

public class ReservaDTO {
	
	private Integer nrReserva;
	
	//private boolean confirmado = false;
	
	private Kartodromo kartodromo;

	private Bateria bateria;
	
	private Piloto piloto;
	
	public Integer getNrReserva() {
		return nrReserva;
	}

	public void setNrReserva(Integer nrReserva) {
		this.nrReserva = nrReserva;
	}

	/*public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}*/

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
	
	public ReservaDTO(Integer nrReserva, /*boolean confirmado,*/ Kartodromo kartodromo, Bateria bateria, Piloto piloto) {
		super();
		this.nrReserva = nrReserva;
		//this.confirmado = false;
		this.kartodromo = kartodromo;
		this.bateria = bateria;
		this.piloto = piloto;
	}

	public Reserva toReserva() {
		
		Reserva reserva = new Reserva();		
		
		System.out.println("aqui reserva" + nrReserva );
		
		reserva.setNrReserva(1);
		//reserva.setConfirmado(confirmado);
		
		

		return reserva;
	}
}
