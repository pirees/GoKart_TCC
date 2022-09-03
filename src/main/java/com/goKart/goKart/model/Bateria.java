package com.goKart.goKart.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PilotoRepository;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name="tb_bateria")
@Data
public class Bateria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Data não pode ficar em branco")
	@DateTimeFormat (pattern="dd/MM/yyyy")
	@CsvBindByName(column = "data")
	private LocalDate data;
	
	@NotNull(message = "Hora da bateria não pode ficar em branco")
	@DateTimeFormat (pattern="HH:mm")
	private LocalTime horaBateria;
	
	@NotNull(message = "Número de pilotos não pode ficar em branco")
	private Integer nrMaxPiloto;
	
	@NotBlank(message = "Traçado não pode ficar em branco")
	private String tracado;
	
	@NotNull(message = "Valor não pode ficar em branco")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorBateria;
	
	@NotBlank(message = "Duração da bateria não pode ficar em branco")
	private String duracaoBateria;

	private Integer vagasConfirmadas = 0;

	@Transient
	private MultipartFile file;
	
	@ManyToOne
	@JoinColumn(name="kartodromo_id")
	private Kartodromo kartodromo;
	public Bateria atualizarBateria(Long id, BateriaRepository bateriaRepository) {

		Bateria bateria = bateriaRepository.getById(id);
		//bateria.setData(this.getData());
		//bateria.setHoraBateria(this.horaBateria);
		bateria.setDuracaoBateria(this.getDuracaoBateria());
		bateria.setTracado(this.tracado);
		bateria.setNrMaxPiloto(this.nrMaxPiloto);
		bateria.setValorBateria(this.getValorBateria());
		bateriaRepository.save(bateria);

		return bateria;
	}

}
