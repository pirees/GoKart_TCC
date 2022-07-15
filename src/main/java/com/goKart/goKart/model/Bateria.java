package com.goKart.goKart.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="tb_bateria")
@Data
public class Bateria {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Data não pode ficar em branco")
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private LocalDate data;
	
	@NotNull(message = "Hora da bateria não pode ficar em branco")
	@DateTimeFormat (pattern="HH:mm")
	private LocalTime horaBateria;
	
	@NotNull(message = "Número de pilotos não pode ficar em branco")
	private Integer nrMaxPiloto;
	
	@NotBlank(message = "Traçado não pode ficar em branco")
	private String tracado;
	
	@NotNull(message = "Valor não pode ficar em branco")
	private BigDecimal valorBateria;
	
	@NotBlank(message = "Duração da bateria não pode ficar em branco")
	private String duracaoBateria;

	private Integer vagasConfirmadas = 0;
	
	@ManyToOne
	@JoinColumn(name="kartodromo_id")
	private Kartodromo kartodromo;


}
