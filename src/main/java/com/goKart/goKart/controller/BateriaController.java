package com.goKart.goKart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.repository.BateriaRepository;

@Controller
public class BateriaController {
	
	@Autowired
	private BateriaRepository bateriaRepository;
	
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
	
	
}
