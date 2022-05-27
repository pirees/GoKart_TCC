package com.goKart.goKart.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goKart.goKart.controller.ReservaController;
import com.goKart.goKart.repository.ReservaRepository;

@SpringBootTest
class ReservaTest {

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Test
	public void SalvarReserva() {

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
	
	Bateria bateria = new Bateria();
	bateria.setData(LocalDate.now());
	bateria.setHoraBateria(LocalTime.now());
	//bateria.setNrMaxPiloto(10);
	bateria.setTracado("Traçado Original");
	bateria.setKartodromo(kartodromo);
	//bateria.setValorBateria(50.00);
	
	Reserva reserva = new Reserva();
	reserva.setNrReserva(1);
	reserva.setBateria(bateria);
	reserva.setKartodromo(kartodromo);
	reserva.setPiloto(piloto);
	//reserva.setConfirmado(true);
	
	//ReservaController reservaControl = new ReservaController();
	
    //reservaControl.pilotoReserva(reserva);
	
	System.out.println("Número de reserva do piloto  " + reserva.getNrReserva());
	System.out.println("Número máximo de piloto " + reserva.getBateria().getNrMaxPiloto());
	
	//int newVagas = reserva.getBateria().getNrMaxPiloto() - reserva.getNrReserva();
	
	//newVagas = reserva.getBateria().getVagasDisponiveis();
	
	//System.out.println("Número atualizado de vagas " + reserva.getBateria().getNrMaxPiloto() + " || " + reserva.getBateria().getVagasDisponiveis());
	
	//reservaRepository.save(reserva);
	
	}
}
