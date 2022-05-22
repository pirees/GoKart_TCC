package com.goKart.goKart.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Estado;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Nivel;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.ReservaRepository;

@SpringBootTest
class ReservaControllerTest {

	@InjectMocks
	private ReservaController reControl;
	private Reserva reserva;
	private Bateria bateria;
	
	@Mock
	private ReservaRepository reservaRepository;
	private BateriaRepository bateriaRepository;
	
	@BeforeEach
	public void iniciarClasse() {
		this.reControl = new ReservaController(reservaRepository);
		this.reserva = new Reserva();
		this.bateria = new Bateria();
		
	}
	
	
	@Test
	public void numeroDeVagasDeveDiminuir() {
		
		Piloto piloto = new Piloto();
		piloto.setNome("Joaqui");
		piloto.setSobrenome("Madeira");
		piloto.setCidade("Araucária");
		piloto.setEmail("contatooo@gmail.com");
		piloto.setEstado(Estado.AC);
		piloto.setNivel(Nivel.Mediano);
		piloto.setSenha("123");
		
		Kartodromo kartodromo = new Kartodromo();
		kartodromo.setNome("RA Kart Indoor");
		kartodromo.setCidade("Curitiba");
		kartodromo.setCNPJ("1014564564");
		kartodromo.setEmail("ra@contato.com.br");
		kartodromo.setEstado(Estado.SP);
		kartodromo.setSenha("123");
		
		bateria.setData(LocalDate.now());
		bateria.setHoraBateria(LocalTime.now());
		//bateria.setNrMaxPiloto(10);
		bateria.setTracado("Traçado Original");
		bateria.setKartodromo(kartodromo);
		//bateria.setValorBateria(50.00);
		//bateria.setVagasDisponiveis(10);
		
		
		
		reserva.setBateria(bateria);
		reserva.setKartodromo(kartodromo);
		reserva.setPiloto(piloto);
		reserva.setConfirmado(true);
		
		
		
		//bateriaRepository.save(bateria);
		//reControl.pilotoReserva(reserva);
		
		//System.out.println(reserva.getBateria().getVagasDisponiveis());
	}

}
