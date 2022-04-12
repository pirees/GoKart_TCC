package com.goKart.goKart.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;

@SpringBootTest
class BateriaTest {

	@Autowired
	private BateriaRepository bateriaRepository;
	
	@Autowired
	private KartodromoRepository kartodromoRepository;
	
	@Test
	void test() {
		
		Kartodromo kartodromo = new Kartodromo();
		
		kartodromo.setNome("Fast Lap");
		kartodromo.setCidade("Lapa");
		kartodromo.setCNPJ("1014564544");
		kartodromo.setEmail("faslap@contato.com.br");
		kartodromo.setEstado(Estado.PR);
		kartodromo.setSenha("789");

		
		Bateria bateria = new Bateria();
		
		bateria.setData(LocalDate.now());
		bateria.setHoraBateria(LocalTime.now());
		bateria.setNrMaxPiloto(9);
		bateria.setTracado("Invertido");
		bateria.setKartodromo(kartodromo);
		bateria.setValorBateria(89.99);

		bateriaRepository.save(bateria);
	}
	
}
