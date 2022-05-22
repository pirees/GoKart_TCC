package com.goKart.goKart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.UsuarioRepository;

@Controller
public class KartodromoController{
	
	private KartodromoRepository kartodromoRepository;
	
	private UsuarioRepository usuarioRepository;
	
	public KartodromoController(KartodromoRepository kartodromoRepository, UsuarioRepository usuarioRepository) {
		super();
		this.kartodromoRepository = kartodromoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@PostMapping ("admin/cadastroKartodromo")
	public String salvarKartodromo(@Valid Kartodromo kartodromo,BindingResult resultado) throws Exception {
		
		if(resultado.hasErrors()) {
			return "kartodromo/cadastroKartodromo";
		}
		//UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		//userControl.verificaCadastro(kartodromo.getEmail());
		kartodromoRepository.save(kartodromo);
		
		return "admin/cadastroKartodromo";
	}
	
	@GetMapping("admin/todosKartodromos")
	public String listarKartodromos(Model model) {
		List<Kartodromo> kartodromos = kartodromoRepository.findAll();
		
		model.addAttribute("kartodromos", kartodromos);
		
		return "admin/todosKartodromos";
	}
}
