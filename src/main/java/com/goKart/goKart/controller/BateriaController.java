package com.goKart.goKart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.dto.BateriaDTO;
import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.repository.BateriaRepository;

@Controller
public class BateriaController {
	
	private BateriaRepository bateriaRepository;
	
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
	
	@GetMapping("kartodromo/newBateria")
	public String formulario(BateriaDTO bateriaDTO) {		
		return "kartodromo/newBateria";
	}
	
	@PostMapping ("kartodromo/newBateria")
	public String salvarBateria(@Valid BateriaDTO bateriaDTO, BindingResult resultado) {
		
		if(resultado.hasErrors()) {
			return "kartodromo/newBateria";
		}
		
		Bateria bateria = bateriaDTO.toBateria();
		
		bateriaRepository.save(bateria);
		 
		 
		return "kartodromo/newBateria";
	}
	
}
