package com.goKart.goKart.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Estado;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Nivel;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.ReservaRepository;

@DataJpaTest
public class PilotoTest {

	@Autowired
	private PilotoRepository pilotoRepository;

	private static final String nomePiloto = "Leonardo";
	
	@Autowired
	private KartodromoRepository kartodromoRepository;
	
	@Autowired
	private BateriaRepository bateriaRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;

	@Mock
	Piloto piloto;
	
	@Test
	public void saveAll() {

	}
	
	@Test
	public void findByEmail(){
		//List<Piloto> piloto = pilotoRepository.findByEmail("contato@gmail.com");
		//assertEquals("contatoooo@gmail.com", piloto);
	}
	
	/*@Test
	public void testLerTudo() {
		Piloto piloto = pilotoRepository.findById(2l).get();
		assertEquals("Araucária", piloto.getNome());
		System.out.println("AQUI " + piloto.getNome());
	}*/
	
	
	
	
}
