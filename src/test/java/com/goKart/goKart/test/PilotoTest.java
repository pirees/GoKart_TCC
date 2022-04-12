package com.goKart.goKart.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
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
	
	@Autowired
	private KartodromoRepository kartodromoRepository;
	
	@Autowired
	private BateriaRepository bateriaRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Test
	public void saveAll() {
		
		Piloto piloto = new Piloto();
		Piloto piloto2 = new Piloto();
		Kartodromo kartodromo = new Kartodromo();
		Bateria bateria = new Bateria();
		Reserva reserva = new Reserva();
		
		piloto.setNome("Leonardo");
		piloto.setSobrenome("Pires");
		piloto.setCidade("Araucária");
		piloto.setEmail("contato@gmail.com");
		piloto.setEstado(Estado.AC);
		piloto.setNivel(Nivel.Mediano);
		piloto.setSenha("123");
		
		piloto2.setNome("Alcione");
		piloto2.setSobrenome("Pires");
		piloto2.setCidade("Araucária");
		piloto2.setEmail("contato@gmail.com");
		piloto2.setEstado(Estado.AC);
		piloto2.setNivel(Nivel.Mediano);
		piloto2.setSenha("123");
		
		kartodromo.setNome("RA Kart Indoor");
		kartodromo.setCidade("Curitiba");
		kartodromo.setCNPJ("1014564564");
		kartodromo.setEmail("ra@contato.com.br");
		kartodromo.setEstado(Estado.PR);
		kartodromo.setSenha("123");
		
		bateria.setData(LocalDate.now());
		bateria.setHoraBateria(LocalTime.now());
		bateria.setKartodromo(kartodromo);
		bateria.setNrMaxPiloto(10);
		bateria.setTracado("Traçado Original");
		
		reserva.setNrReserva(2);
		reserva.setBateria(bateria);
		reserva.setKartodromo(kartodromo);
		reserva.setPiloto(piloto);
		
		
		pilotoRepository.save(piloto);
		pilotoRepository.save(piloto2);
		kartodromoRepository.save(kartodromo);
		bateriaRepository.save(bateria);
		reservaRepository.save(reserva);
		
		List<Piloto> pilotos = new ArrayList<>();
		
		List<Reserva> reservas = new ArrayList<>();
		
		pilotos.add(piloto);
		pilotos.add(piloto2);
		reservas.add(reserva);
		
		System.out.println("Reserva ID: " + reserva.getId());
		System.out.println("Reserva vaga: " + reserva.getNrReserva());
		System.out.println("Reserva bateria: " + reserva.getBateria().getId());
		System.out.println("Reserva kartodromo: " + reserva.getKartodromo().getNome());
		System.out.println("Reserva piloto: " + reserva.getPiloto().getNome());
		
		System.out.println(piloto.getEmail() + "+" + piloto2.getEmail());
		
		//Exemplo 1
		for (Piloto pilotoos : pilotos) {
			//System.out.println("Pilotos " + pilotoos);
		}
		
		//Exemplo 2
		for(int i = 0; i < pilotos.size(); i++) {
			//System.out.println(pilotos.get(i).getNome());
		}
		
		for (Reserva listareserva : reservas) {
			System.out.println(listareserva);
		}
		
		//Exemplo 3
		pilotos.forEach(pilotoo -> {
			System.out.println("Pilotos cadastrados " + pilotoo.getEmail());
		});		
		
		
	}
	
	@Test
	public void findByEmail(){
		List<Piloto> piloto = pilotoRepository.findByEmail("contato@gmail.com");
		assertEquals("contatoooo@gmail.com", piloto);
	}
	
	/*@Test
	public void testLerTudo() {
		Piloto piloto = pilotoRepository.findById(2l).get();
		assertEquals("Araucária", piloto.getNome());
		System.out.println("AQUI " + piloto.getNome());
	}*/
	
	
	
	
}
