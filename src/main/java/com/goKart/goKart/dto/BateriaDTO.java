package com.goKart.goKart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Kartodromo;


public class BateriaDTO {

	
	//@DateTimeFormat (pattern="dd/MMM/YYYY")
	private LocalDate data;
	
	//@NotBlank(message = "Hora não pode ficar em branco")
	private LocalTime horaBateria;
	
	//@NotEmpty(message = "Número de pilotos não pode ficar em branco")
	private Integer nrMaxPiloto;
	
	//@NotBlank(message = "Traçado não pode ficar em branco")
	private String tracado;
	
	//@NotBlank(message = "Valor não pode ficar em branco")
	private BigDecimal valorBateria;
	
	private Integer vagasDisponiveis;
	
	@ManyToOne
	@JoinColumn(name="kartodromo_id")
	private Kartodromo kartodromo;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHoraBateria() {
		return horaBateria;
	}

	public void setHoraBateria(LocalTime horaBateria) {
		this.horaBateria = horaBateria;
	}

	public String getTracado() {
		return tracado;
	}

	public void setTracado(String tracado) {
		this.tracado = tracado;
	}

	public BigDecimal getValorBateria() {
		return valorBateria;
	}

	public void setValorBateria(BigDecimal valorBateria) {
		this.valorBateria = valorBateria;
	}

	public Integer getNrMaxPiloto() {
		return nrMaxPiloto;
	}

	public void setNrMaxPiloto(Integer nrMaxPiloto) {
		this.nrMaxPiloto = nrMaxPiloto;
	}

	public Integer getVagasDisponiveis() {
		return vagasDisponiveis;
	}

	public void setVagasDisponiveis(Integer vagasDisponiveis) {
		this.vagasDisponiveis = vagasDisponiveis;
	}

	public Kartodromo getKartodromo() {
		return kartodromo;
	}

	public void setKartodromo(Kartodromo kartodromo) {
		this.kartodromo = kartodromo;
	}

	public BateriaDTO(LocalDate data, LocalTime horaBateria, Integer nrMaxPiloto, String tracado, BigDecimal valorBateria,
			Integer vagasDisponiveis, Kartodromo kartodromo) {
		super();
		this.data = data;
		this.horaBateria = horaBateria;
		this.nrMaxPiloto = nrMaxPiloto;
		this.tracado = tracado;
		this.valorBateria = valorBateria;
		this.vagasDisponiveis = vagasDisponiveis;
		this.kartodromo = kartodromo;
	}
	
	public Bateria toBateria() {
		
		Bateria bateria = new Bateria();
		
		bateria.setData(data);
		bateria.setHoraBateria(horaBateria);
		bateria.setKartodromo(kartodromo);
		bateria.setNrMaxPiloto(nrMaxPiloto);
		bateria.setTracado(tracado);
		bateria.setVagasDisponiveis(nrMaxPiloto);
		bateria.setValorBateria(valorBateria);
		
		return bateria;
	}
}
