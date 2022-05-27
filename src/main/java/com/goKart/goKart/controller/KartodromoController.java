package com.goKart.goKart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PerfilRepository;

@Controller
public class KartodromoController{
	
	private KartodromoRepository kartodromoRepository;
	
	private PerfilRepository perfilRepository;
	
	private UsuarioController usuarioController;
	
	public KartodromoController(KartodromoRepository kartodromoRepository, PerfilRepository perfilRepository,
			UsuarioController usuarioController) {
		this.kartodromoRepository = kartodromoRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioController = usuarioController;
	}

	@GetMapping("admin/cadastroKartodromo")
	public String formulario(Kartodromo kartodromo) {	
		return "admin/cadastroKartodromo";
	}
	
	@GetMapping("kartodromo/meusDados")
	public String listarDados(Model model) {	
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);

		
		model.addAttribute("kartodromo", kartodromo);

		return "kartodromo/meusDados";
	}
	
	@PostMapping("kartodromo/meusDados")
	public ModelAndView atualizarEmail(String email, Kartodromo kartodromo) {
		
		email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		kartodromo.atualizarEmail(email, kartodromoRepository);

		return new ModelAndView("redirect:/kartodromo/menuKartodromo");
	}

	@PostMapping ("admin/cadastroKartodromo")
	public String salvarKartodromo(@Valid Kartodromo kartodromo,BindingResult resultado, String email) throws Exception{
		
		if(resultado.hasErrors()) {			
			return "admin/cadastroKartodromo";
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(kartodromo.getSenha());
		
		Perfil perfil = perfilRepository.findByNome("KARTODROMO");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);	
		kartodromo.setPerfis(perfis);
		kartodromo.setSenha(encodedPassword);
		
		usuarioController.verificaCadastro(email);
		
		kartodromoRepository.save(kartodromo);
		
		return "redirect:/admin/menuAdmin";
	}
	
	@GetMapping("admin/todosKartodromos")
	public String listarKartodromos(Model model) {
		List<Kartodromo> kartodromos = kartodromoRepository.findAll();
		
		model.addAttribute("kartodromos", kartodromos);
		
		return "admin/todosKartodromos";
	}
}
