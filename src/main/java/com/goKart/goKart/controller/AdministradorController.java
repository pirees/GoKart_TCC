package com.goKart.goKart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.dto.AdminDTO;
import com.goKart.goKart.model.Administrador;
import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.repository.AdministradorRepository;
import com.goKart.goKart.repository.PerfilRepository;

@Controller
public class AdministradorController {
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@GetMapping("admin/cadastroAdmin")
	public String formulario(AdminDTO administradorDTO) {	
		return "admin/cadastroAdmin";
	}
	
	@PostMapping ("admin/cadastroAdmin")
	public String salvarAdm(@Valid AdminDTO administradorDTO, BindingResult resultado){
		
		if(resultado.hasErrors()) {
			return "admin/cadastroAdmin";
		}
		
		
		Administrador administrador = administradorDTO.toAdministrador();
		
		Perfil perfil = perfilRepository.findByNome("ADMIN");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);				
		administrador.setPerfis(perfis);
		
		System.out.println("aqui");
		
		
		administradorRepository.save(administrador);
		
		return "admin/cadastroAdmin";
	}
	
	

}
