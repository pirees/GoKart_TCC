package com.goKart.goKart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.UsuarioRepository;

@Controller
@RequestMapping("kartodromo")
public class KartodromoController{
	
	private KartodromoRepository kartodromoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public KartodromoController(KartodromoRepository kartodromoRepository){
	 this.kartodromoRepository = kartodromoRepository;
	}

	@GetMapping("cadastroKartodromo")
	public String formulario(Kartodromo kartodromo) {
		return "kartodromo/cadastroKartodromo";
	}
	
	@GetMapping("login")
	public String login(Kartodromo Kartodromo) {
		return "login";
	}

	@PostMapping ("novo")
	public String salvarKartodromo(@Valid Kartodromo kartodromo,BindingResult resultado) throws Exception {
		
		if(resultado.hasErrors()) {
			return "kartodromo/cadastroKartodromo";
		}
		
		UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		userControl.verificaCadastro(kartodromo.getEmail());
		kartodromoRepository.save(kartodromo);
		
		return "kartodromo/cadastroKartodromo";
	}

}
