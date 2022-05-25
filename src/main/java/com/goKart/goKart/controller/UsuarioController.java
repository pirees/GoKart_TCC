package com.goKart.goKart.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController{
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioController(UsuarioRepository usuarioRepository){
	 this.usuarioRepository = usuarioRepository;
	}

	public String verificaCadastro(String email) throws Exception {

		List<Usuario> listaUsuario = usuarioRepository.findByCadastroEmail(email);

		for (Usuario usuario : listaUsuario) {

			if (usuario.getEmail().equals(email)) {
				throw new Exception("Usuário já possui e-mail cadastrado");
			}
		}

		return email;
	}
}
