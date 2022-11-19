package com.goKart.goKart.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_reserva")
@Data
public class Reserva {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer nrReserva;
	
	private StatusPagamento status;
	
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private LocalDate dataReserva;
	
	@ManyToOne
	@JoinColumn(name="kartodromo_id")
	private Kartodromo kartodromo;
	
	@ManyToOne
	@JoinColumn(name="bateria_id")
	private Bateria bateria;
	
	@ManyToOne
	@JoinColumn(name="piloto_id")
	private Piloto piloto;

}
