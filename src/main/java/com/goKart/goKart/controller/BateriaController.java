package com.goKart.goKart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.repository.BateriaRepository;

@Controller
public class BateriaController {
	
	@Autowired
	private BateriaRepository bateriaRepository;
	
	//LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("/piloto/menuPiloto")
	public String listarBaterias(Model model) {
		List<Bateria> baterias = bateriaRepository.findAll();
		model.addAttribute("baterias", baterias);
		
		return "/piloto/menuPiloto";
	}
	
	
}
