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
import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.repository.PerfilRepository;
import com.goKart.goKart.repository.PilotoRepository;

@Controller
public class PilotoController{
	
	private PilotoRepository pilotoRepository;
		
	private PerfilRepository perfilRepository;
	
	private UsuarioController usuarioController;
	
	public PilotoController(PilotoRepository pilotoRepository, PerfilRepository perfilRepository, UsuarioController usuarioController) {
		this.pilotoRepository = pilotoRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioController = usuarioController;
	}

	@GetMapping("piloto/atualizarPiloto")
	public String listaNivel(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Piloto piloto = pilotoRepository.findByEmail(email);

		model.addAttribute("piloto", piloto);
		
		return "piloto/atualizarPiloto";
		
	}
	
	@PostMapping("piloto/atualizarPiloto")
	public ModelAndView atualizarNivel(String email, Piloto piloto) {
		
		email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		piloto.atualizarNivel(email, pilotoRepository);

		return new ModelAndView("redirect:/piloto/atualizarPiloto");
	}

	@GetMapping("piloto/cadastroPiloto")
	public String formulario(Piloto piloto) {
		return "piloto/cadastroPiloto";
	}
	
	@PostMapping ("piloto/cadastroPiloto")
	public String salvarPiloto(@Valid Piloto piloto, BindingResult resultado, String email) throws Exception{
		
		if(resultado.hasErrors()) {
			return "piloto/cadastroPiloto";
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(piloto.getSenha());
		
		Perfil perfil = perfilRepository.findByNome("PILOTO");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);				
		piloto.setPerfis(perfis);
		piloto.setSenha(encodedPassword);
		
		usuarioController.verificaCadastro(email);
		pilotoRepository.save(piloto);
		
		return "redirect:/login";
	}
	
	@GetMapping("admin/todosPilotos")
	public String listarPilotosAdmin(Model model) {
		List<Piloto> pilotos = pilotoRepository.findAll();
		
		model.addAttribute("pilotos", pilotos);
		
		return "admin/todosPilotos";
	}
	
	
}
