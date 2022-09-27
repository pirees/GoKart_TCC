package com.goKart.goKart.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import com.goKart.goKart.model.*;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.service.EnviaEmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.repository.AdministradorRepository;
import com.goKart.goKart.repository.PerfilRepository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdministradorController {
	
	private AdministradorRepository administradorRepository;
	
	private PerfilRepository perfilRepository;
	
	private UsuarioController usuarioController;

	private KartodromoRepository kartodromoRepository;

	private EnviaEmailService enviaEmailService;

	public AdministradorController(AdministradorRepository administradorRepository, PerfilRepository perfilRepository,
			UsuarioController usuarioController, KartodromoRepository kartodromoRepository, EnviaEmailService enviaEmailService) {
		this.administradorRepository = administradorRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioController = usuarioController;
		this.kartodromoRepository = kartodromoRepository;
		this.enviaEmailService = enviaEmailService;
	}

	@GetMapping("admin/cadastroAdmin")
	public String formulario(Administrador administrador) {	
		return "admin/cadastroAdmin";
	}
	
	@PostMapping ("admin/cadastroAdmin")
	public String salvarAdm(@Valid Administrador administrador, BindingResult resultado, String email, RedirectAttributes redirectAttributes) throws Exception{
		
		if(resultado.hasErrors()) {
			return "admin/cadastroAdmin";
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(administrador.getSenha());
		
		Perfil perfil = perfilRepository.findByNome("ADMIN");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);				
		administrador.setPerfis(perfis);
		administrador.setSenha(encodedPassword);

		if(usuarioController.verificaCadastro(email)){
			redirectAttributes.addFlashAttribute("errormessage", "E-mail já cadastrado no sistema.");
			return "redirect:/cadastroAdmin";
		}else{
			administradorRepository.save(administrador);
		}

		return "admin/cadastroAdmin";
	}

	@GetMapping("admin/pendencias")
	public String visualiarPendencias(Model model){
		List<Kartodromo> kartodromo = kartodromoRepository.findByPendencia();

		model.addAttribute("kartodromo", kartodromo);

		return "admin/pendencias";

	}
	@GetMapping("admin/pendencias/aprovar/{id}")
	public ModelAndView aprovarCadastro(@PathVariable("id") Long id, Kartodromo kartodromo) {
		kartodromo = kartodromoRepository.getById(id);
		kartodromo.setStatusUsuario(StatusUsuario.APROVADO);
		kartodromoRepository.save(kartodromo);
		enviaEmailService.enviarcadastroKartodromoAprovado(kartodromo);

		return new ModelAndView("redirect:/admin/pendencias");
	}

	@GetMapping("admin/pendencias/reprovar/{id}")
	public ModelAndView reprovarCadastro(@PathVariable("id") Long id, Kartodromo kartodromo) {
		kartodromo = kartodromoRepository.getById(id);
		kartodromo.setStatusUsuario(StatusUsuario.REPROVADO);
		kartodromoRepository.save(kartodromo);
		return new ModelAndView("redirect:/admin/pendencias");
	}
	

}
