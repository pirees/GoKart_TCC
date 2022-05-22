package com.goKart.goKart.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="tb_bateria")
public class Bateria {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	private LocalTime horaBateria;
	private Integer nrMaxPiloto;
	private String tracado;
	private BigDecimal valorBateria;
	private Integer vagasDisponiveis;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="kartodromo_id")
	private Kartodromo kartodromo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Kartodromo getKartodromo() {
		return kartodromo;
	}
	public void setKartodromo(Kartodromo kartodromo) {
		this.kartodromo = kartodromo;
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
	public Bateria() {
		super();
	}
	

}
