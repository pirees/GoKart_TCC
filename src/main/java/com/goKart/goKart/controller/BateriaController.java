package com.goKart.goKart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;

@Controller
public class BateriaController {
	
	private BateriaRepository bateriaRepository;
	
	@Autowired
	private KartodromoRepository kartodromoRepository;
	
	public BateriaController(BateriaRepository bateriaRepository) {
		super();
		this.bateriaRepository = bateriaRepository;
	}

	//LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/menuPiloto")
	public String listarBaterias(Model model) {
		List<Bateria> baterias = bateriaRepository.findAll();
		model.addAttribute("baterias", baterias);
		
		return "piloto/menuPiloto";
	}
	
	//FAZ O GET DA BATERIA SELECIONADA NA PAGINA DO MENU PILOTO
	@GetMapping("piloto/confirmarReserva/{id}")
	public String listarBaterias(@PathVariable("id") Long id, Model model) {
		
		Bateria bateria = bateriaRepository.getById(id);
			
		model.addAttribute("bateria", bateria);
			
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
		bateria.setVagasDisponiveis(bateria.getNrMaxPiloto());
		
		bateriaRepository.save(bateria);
		
		return "kartodromo/menuKartodromo";
	}
	
}
