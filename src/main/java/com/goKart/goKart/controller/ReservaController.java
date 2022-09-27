package com.goKart.goKart.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.goKart.goKart.dto.FaturarDTO;
import com.goKart.goKart.model.*;
import com.goKart.goKart.repository.*;
import com.goKart.goKart.service.EnviaEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goKart.goKart.excel.ReservaExcel;

@Controller
public class ReservaController {

	private ReservaRepository reservaRepository;
	
	private PilotoRepository pilotoRepository;
	
	private BateriaRepository bateriaRepository;

	private CustomQueryRepository customQueryRepository;
	
	private KartodromoRepository kartodromoRepository;

	private EnviaEmailService enviaEmailService;

	public ReservaController(ReservaRepository reservaRepository, PilotoRepository pilotoRepository,
							 BateriaRepository bateriaRepository, KartodromoRepository kartodromoRepository, EnviaEmailService enviaEmailService, CustomQueryRepository customQueryRepository) {
		super();
		this.reservaRepository = reservaRepository;
		this.pilotoRepository = pilotoRepository;
		this.bateriaRepository = bateriaRepository;
		this.kartodromoRepository = kartodromoRepository;
		this.enviaEmailService = enviaEmailService;
		this.customQueryRepository = customQueryRepository;
	}

	// LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/reservasPiloto")
	public String listaReservas(@PageableDefault Pageable paginacao,Sort sort, Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		sort = Sort.by("id").ascending();
		paginacao = PageRequest.of(0, 15, sort);
				
		Page<Reserva> reservas = reservaRepository.findByEmail(email, paginacao);

		model.addAttribute("reserva", reservas);
		
		return "piloto/reservasPiloto";

	}
	
	public Reserva atulizarVagasDisponiveis(Reserva reserva) {

		if(reserva.getBateria().getVagasConfirmadas() < reserva.getBateria().getNrMaxPiloto()) {
			reserva.getBateria().setVagasConfirmadas(reserva.getBateria().getVagasConfirmadas() + 1);
		}
		return reserva;
	}
	
	@GetMapping("kartodromo/relatorio")
	public String listarPilotosKartodromo(Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		reserva.setKartodromo(kartodromo);
		
		List<Reserva> reservas = reservaRepository.findDataReservaKartodromoById(reserva.getKartodromo().getId());
				
		model.addAttribute("reserva", reservas);
		
		return "kartodromo/relatorio";
	}

	@GetMapping("admin/faturamento")
	public String visualizarFaturamento(Model model) {

		List<FaturarDTO> faturarDTOs = customQueryRepository.listarKartodromosFaturar();

		model.addAttribute("faturamentos", faturarDTOs);

		return "admin/faturamento";
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

	@GetMapping("piloto/cancelarReserva/{id}")
	public String listaReservasPiloto(@PathVariable("id") Long id, Model model){
		Reserva reserva = reservaRepository.getById(id);

		if(reserva.getBateria().getData().isAfter(LocalDate.now())) {
			if(reserva.getStatus().equals(StatusPagamento.CONFIRMADO)){

				model.addAttribute("reserva", reserva);

				return "piloto/cancelarReserva";
			}
		}

		model.addAttribute("reserva", reserva);
		return "piloto/listaBateriaId";
	}

	@GetMapping("piloto/cancelarReserva/apagar/{id}")
	public String apagarReserva(@PathVariable("id") Long id, Reserva reserva) {
		reserva = reservaRepository.getById(id);

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Piloto piloto = pilotoRepository.findByEmail(email);

		reserva.setPiloto(piloto);
		reserva.setKartodromo(reserva.getBateria().getKartodromo());
		reserva.getBateria().setVagasConfirmadas(reserva.getBateria().getVagasConfirmadas() - 1);
		reserva.setStatus(StatusPagamento.DEVOLVIDO);
		reservaRepository.save(reserva);

		enviaEmailService.enviarReservaCanceladaPiloto(piloto, reserva);

		return "redirect:/piloto/menuPiloto";
	}
}
