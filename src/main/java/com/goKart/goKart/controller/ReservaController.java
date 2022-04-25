package com.goKart.goKart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.repository.ReservaRepository;

@Controller
public class ReservaController {
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@GetMapping ("novo")
	public void pilotoReserva(Reserva reserva, Piloto piloto) {
		if(reserva.getNrReserva() > reserva.getBateria().getNrMaxPiloto()) {
		}else {
			reservaRepository.save(reserva);		
		}
		 
	}
	
	//LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/bateriasPiloto/{id}")
	public String listaReservas(Model model, @PathVariable Long id, Reserva reserva) {
		//Optional<Reserva> reservas = reservaRepository.findById(id);
		List<Reserva> reservas = reservaRepository.findByIdPiloto(id);
		//List<Piloto> reservas = pilotoRepository.findAll();
		/*if(reservas.isPresent()) {
			model.addAttribute("reserva", reservas.get());
		}*/
		
		if(reserva.isConfirmado() == false) {
			model.addAttribute("reserva", reservas);
		}else {
			///NECESSÁRIO IMPLEMENTAR UMA REGRA AQUI
		}

		return "piloto/bateriasPiloto";
		}
	
	// LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/confirmarReserva/{id}")
	public String listaPilotosConfirmados(Model model, @PathVariable Long id, Reserva reserva) {
		 Optional<Reserva> reservas = reservaRepository.findById(id);
			
		if (reserva.isConfirmado() == true) {
			model.addAttribute("reserva", reservas);
		} else {
			/// NECESSÁRIO IMPLEMENTAR UMA REGRA AQUI
		}
			return "piloto/confirmarReserva";
		}
		

	}

	
