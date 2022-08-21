package com.goKart.goKart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.goKart.goKart.excel.TodosKartodromosExcel;
import com.goKart.goKart.excel.TodosPilotosExcel;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.StatusUsuario;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

	@GetMapping("/cadastroKartodromo")
	public String formulario(Kartodromo kartodromo) {	
		return "/cadastroKartodromo";
	}
	
	@GetMapping("kartodromo/meusDados")
	public String listarDados(Model model, String email, Kartodromo kartodromo) {
		
		email = SecurityContextHolder.getContext().getAuthentication().getName();
		kartodromo = kartodromoRepository.findByEmail(email);
		
		model.addAttribute("kartodromo", kartodromo);

		return "kartodromo/meusDados";
	}

	@PostMapping ("/cadastroKartodromo")
	public String salvarKartodromo(@Valid Kartodromo kartodromo,BindingResult resultado, String email) throws Exception{
		
		if(resultado.hasErrors()) {			
			return "/cadastroKartodromo";
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(kartodromo.getSenha());
		
		Perfil perfil = perfilRepository.findByNome("KARTODROMO");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);	
		kartodromo.setPerfis(perfis);
		kartodromo.setSenha(encodedPassword);
		kartodromo.setStatusUsuario(StatusUsuario.PENDENTE);
		
		usuarioController.verificaCadastro(email);
		
		kartodromoRepository.save(kartodromo);
		
		return "redirect:/pendenciaCadastro";
	}
	
	@GetMapping("admin/todosKartodromos")
	public String listarKartodromos(Model model) {
		List<Kartodromo> kartodromos = kartodromoRepository.findAll();
		
		model.addAttribute("kartodromos", kartodromos);
		
		return "admin/todosKartodromos";
	}

	@GetMapping("admin/todosKartodromos/exports/csv")
	public void listarPilotosExport(HttpServletResponse response) throws IOException {

		List<Kartodromo> kartodromos = kartodromoRepository.findAll();

		TodosKartodromosExcel todosKartodromosExcel = new TodosKartodromosExcel(kartodromos);

		todosKartodromosExcel.export(response);

	}
}
