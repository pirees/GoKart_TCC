package com.goKart.goKart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.excel.BateriaExcel;
import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.model.StatusPagamento;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.ReservaRepository;

@Controller
public class BateriaController {
	
	private BateriaRepository bateriaRepository;
	
	private KartodromoRepository kartodromoRepository;
	
	private ReservaRepository reservaRepository;

	public BateriaController(BateriaRepository bateriaRepository, KartodromoRepository kartodromoRepository,
			ReservaRepository reservaRepository) {
		super();
		this.bateriaRepository = bateriaRepository;
		this.kartodromoRepository = kartodromoRepository;
		this.reservaRepository = reservaRepository;
	}

	//LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/menuPiloto")
	public String listarBaterias(@PageableDefault(direction= Direction.ASC, size = 10) Model model, Pageable pageable) {
		Page<Bateria> baterias = bateriaRepository.findByData(pageable);
		model.addAttribute("baterias", baterias);
		
		return "piloto/menuPiloto";
	}
	
	//FAZ O GET DA BATERIA SELECIONADA NA PAGINA DO MENU PILOTO
	@GetMapping("piloto/confirmarReserva/{id}")
	public String listarBaterias(@PathVariable("id") Long id, Model model, StatusPagamento status) {
		
		Bateria bateria = bateriaRepository.getById(id);
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<Reserva> reserva = reservaRepository.findPilotoByReserva(id);
		
		for (Reserva reservas : reserva) {
			
			if(reservas.getPiloto().getEmail().equals(email) && reservas.getStatus().equals(status.CONFIRMADO)) {
				
				model.addAttribute("bateria", bateria);
				model.addAttribute("reserva", reserva);
				
				return "piloto/confirmarReservaPilotoPago";
			} 
		}
		
		model.addAttribute("bateria", bateria);
		model.addAttribute("reserva", reserva);

		return "piloto/confirmarReserva";
	}
	

	
	@GetMapping("kartodromo/cadastroBateria")
	public String formulario(Bateria bateria) {
		return "kartodromo/cadastroBateria";
	}
	
	@PostMapping ("kartodromo/cadastroBateria")
	public String salvarBateria(@Valid Bateria bateria, BindingResult resultado) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		if(resultado.hasErrors()) {
			return "kartodromo/cadastroBateria";
		}
				
		bateria.setKartodromo(kartodromo);
		
		bateriaRepository.save(bateria);
		
		return "redirect:/kartodromo/menuKartodromo";
	}
	
	//LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("kartodromo/listaBaterias")
	public String listarBateriasKartodromo(Model model, Bateria bateria) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		bateria.setKartodromo(kartodromo);
		
		List<Bateria> baterias = bateriaRepository.findByKartodromoId(bateria.getKartodromo().getId());
		
		System.out.println(bateria.getKartodromo().getId());
		
		model.addAttribute("baterias", baterias);
			
		return "kartodromo/listaBaterias";
	}
	
	@GetMapping("kartodromo/listaBaterias/exports/csv")
	public void listarBateriasKartodromoExport(Bateria bateria, HttpServletResponse response) throws IOException {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		bateria.setKartodromo(kartodromo);
		
		List<Bateria> baterias = bateriaRepository.findByKartodromoId(bateria.getKartodromo().getId());
		
		BateriaExcel bateriaExcel = new BateriaExcel(baterias);
		
		bateriaExcel.export(response);
			
		
	}
	
	//LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("kartodromo/menuKartodromo")
	public String listarMenuKartodromo(Model model, Bateria bateria) {
			
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
			
		bateria.setKartodromo(kartodromo);
			
		List<Bateria> baterias = bateriaRepository.findByDateKartodromoId(bateria.getKartodromo().getId());
						
		model.addAttribute("baterias", baterias);
				
		return "kartodromo/menuKartodromo";
		
	}
	
}
