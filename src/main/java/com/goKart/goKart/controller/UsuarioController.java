package com.goKart.goKart.controller;

import java.util.List;

import com.goKart.goKart.service.EnviaEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.ErrorHandler;
import org.springframework.web.bind.annotation.RequestMapping;


import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("usuario")
public class UsuarioController{
	
	private UsuarioRepository usuarioRepository;
	private EnviaEmailService enviaEmailService;
	
	public UsuarioController(UsuarioRepository usuarioRepository, EnviaEmailService enviaEmailService)
	{
	 this.usuarioRepository = usuarioRepository;
	 this.enviaEmailService = enviaEmailService;
	}

	public Boolean verificaCadastro(String email){

		List<Usuario> listaUsuario = usuarioRepository.findByCadastroEmail(email);

		for (Usuario usuario : listaUsuario) {
			if (usuario.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	public String enviaEmailRedefinirSenha(Usuario usuario){

		enviaEmailService.enviarUsuarioRedefinirSenha(usuario);

		return "/";
	}
}
