package com.goKart.goKart.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.dto.PaymentResponseDTO;
import com.goKart.goKart.excel.ReservaExcel;
import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.model.StatusPagamento;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.ReservaRepository;

@Controller
public class ReservaController {

	private ReservaRepository reservaRepository;
	
	private PilotoRepository pilotoRepository;
	
	private BateriaRepository bateriaRepository;
	
	private KartodromoRepository kartodromoRepository;

	public ReservaController(ReservaRepository reservaRepository, PilotoRepository pilotoRepository,
			BateriaRepository bateriaRepository, KartodromoRepository kartodromoRepository) {
		super();
		this.reservaRepository = reservaRepository;
		this.pilotoRepository = pilotoRepository;
		this.bateriaRepository = bateriaRepository;
		this.kartodromoRepository = kartodromoRepository;
	}

	// LISTA TODAS AS BATERIAS DISPON??VEIS
	@GetMapping("piloto/reservasPiloto")
	public String listaReservas(@PageableDefault Pageable paginacao,Sort sort, Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		sort = Sort.by("id").ascending();
		paginacao = PageRequest.of(0, 15, sort);
				
		Page<Reserva> reservas = reservaRepository.findByEmail(email, paginacao);

		model.addAttribute("reserva", reservas);
		
		return "piloto/reservasPiloto";
		
		//return this.reservaRepository.findByEmail(email, paginacao);
		
	}
	
	public Reserva atulizarVagasDisponiveis(Reserva reserva) {
					
		if(reserva.getBateria().getVagasConfirmadas() < reserva.getBateria().getNrMaxPiloto()) {
			reserva.getBateria().setVagasConfirmadas(reserva.getBateria().getVagasConfirmadas() + reserva.getNrReserva());
		}
		
		return reserva;
	}
	
	@GetMapping("kartodromo/relatorio")
	public String listarPilotosKartodromo(Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		reserva.setKartodromo(kartodromo);
		
		List<Reserva> reservas = reservaRepository.findDataReservaKartodromoById(reserva.getKartodromo().getId());
				
		model.addAttribute("reservas", reservas);
		
		return "kartodromo/relatorio";
	}
	
	@GetMapping("kartodromo/relatorio/exports/csv")
	public void listarPilotosKartodromoExport(Reserva reserva, HttpServletResponse response) throws IOException {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		reserva.setKartodromo(kartodromo);
		
		List<Reserva> reservas = reservaRepository.findDataReservaKartodromoById(reserva.getKartodromo().getId());
	
		ReservaExcel reservaExcel = new ReservaExcel(reservas);
		
		reservaExcel.export(response);
	
	}
	@DeleteMapping("menuPiloto/reservasPiloto")
	public String excluirReserva(Reserva reserva, LocalTime localTime){

		if(reserva.getBateria().getHoraBateria().isBefore(localTime.minus(1, ChronoUnit.HOURS))){
			reservaRepository.deleteById(reserva.getId());
		}

		return "menuPiloto/reservasPiloto";

	}
}
