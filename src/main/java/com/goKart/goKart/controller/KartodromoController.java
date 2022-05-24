package com.goKart.goKart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.dto.KartodromoDTO;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PerfilRepository;
import com.goKart.goKart.repository.UsuarioRepository;

@Controller
public class KartodromoController{
	
	private KartodromoRepository kartodromoRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	private UsuarioRepository usuarioRepository;
	
	public KartodromoController(KartodromoRepository kartodromoRepository, UsuarioRepository usuarioRepository) {
		super();
		this.kartodromoRepository = kartodromoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	@GetMapping("admin/cadastroKartodromo")
	public String formulario(KartodromoDTO kartodromoDTO) {	
		return "admin/cadastroKartodromo";
	}

	@PostMapping ("admin/cadastroKartodromo")
	public String salvarKartodromo( KartodromoDTO kartodromoDTO,BindingResult resultado){
		
		/*if(resultado.hasErrors()) {
			return "admin/cadastroKartodromo";
		}*/
		//UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		//userControl.verificaCadastro(kartodromo.getEmail());
		
		Kartodromo kartodromo = kartodromoDTO.toKartodromo();
		
		Perfil perfil = perfilRepository.findByNome("KARTODROMO");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);	
		
		kartodromo.setPerfis(perfis);
		
		kartodromoRepository.save(kartodromo);
		
		return "redirect:/admin/cadastroKartodromo";
	}
	
	@GetMapping("admin/todosKartodromos")
	public String listarKartodromos(Model model) {
		List<Kartodromo> kartodromos = kartodromoRepository.findAll();
		
		model.addAttribute("kartodromos", kartodromos);
		
		return "admin/todosKartodromos";
	}
}
