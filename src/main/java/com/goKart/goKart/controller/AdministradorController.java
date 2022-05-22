package com.goKart.goKart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.model.Administrador;
import com.goKart.goKart.repository.AdministradorRepository;

@Controller
public class AdministradorController {
	
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@PostMapping ("admin/cadastroAdmin")
	public String salvarKartodromo(@Valid Administrador administrador,BindingResult resultado) throws Exception {
		
		if(resultado.hasErrors()) {
			return "admin/cadastroAdmin";
		}
		//UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		//userControl.verificaCadastro(kartodromo.getEmail());
		administradorRepository.save(administrador);
		
		return "admin/cadastroAdmin";
	}
	
	

}
