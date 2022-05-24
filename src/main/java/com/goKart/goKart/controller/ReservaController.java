package com.goKart.goKart.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.ReservaRepository;

@Controller
public class ReservaController {

	private ReservaRepository reservaRepository;
	
	private PilotoRepository pilotoRepository;
	
	private BateriaRepository bateriaRepository;

	@Autowired
	public ReservaController(ReservaRepository reservaRepository, PilotoRepository pilotoRepository,
			BateriaRepository bateriaRepository) {
		super();
		this.reservaRepository = reservaRepository;
		this.pilotoRepository = pilotoRepository;
		this.bateriaRepository = bateriaRepository;
	}

	// PRECISA CRIAR REGRA NOVA
	/*public Integer pilotoReserva(Reserva reserva) {
		int newVaga = 0;
		Reserva reservaa = new Reserva();
		if (reserva.getNrReserva() != 0 && reserva.isConfirmado()) {
			newVaga = reserva.getBateria().getVagasDisponiveis() - reserva.getNrReserva();
			reservaa.getBateria().setVagasDisponiveis(newVaga);
		}

		return newVaga;

	}*/

	// LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/reservasPiloto")
	public String listaReservas(Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
				
		List<Reserva> reservas = reservaRepository.findByEmail(email);

		if (reserva.isConfirmado() == false) {
			model.addAttribute("reserva", reservas);
			
		} else {
		
		
		}
		
		return "piloto/reservasPiloto";
		
	}

	

	// LISTA TODAS AS BATERIAS DISPONÍVEIS
	/*
	 * @GetMapping("piloto/confirmarReserva/{id}") public String
	 * listaPilotosConfirmados(Model model, @PathVariable Long id) { Reserva reserva
	 * = reservaRepository.getById(id); if(reserva.isConfirmado() == true) {
	 * model.addAttribute("reserva", reserva);
	 * 
	 * }else {
	 * 
	 * }
	 *  
	 * return "piloto/confirmarReserva"; }
	 */

	@PostMapping("piloto/confirmarReserva/{id}")
	public String salvarReserva(@PathVariable Long id, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Piloto piloto = pilotoRepository.findByEmail(email);
		
		Bateria bateria = bateriaRepository.getById(id);
		
		reserva.setBateria(bateria);
		reserva.setKartodromo(reserva.getBateria().getKartodromo());
		reserva.setPiloto(piloto);
		reserva.setDataReserva(LocalDate.now());

		
		reservaRepository.save(reserva);

		return "redirect:/piloto/menuPiloto";
	}
	
	public Reserva checkAvailableDriver(Reserva reserva) {
					
		if(reserva.getBateria().getVagasDisponiveis() != 0) {
			reserva.getBateria().setVagasDisponiveis(reserva.getBateria().getVagasDisponiveis() - reserva.getNrReserva());
		}
		
		return reserva;
	}

}
