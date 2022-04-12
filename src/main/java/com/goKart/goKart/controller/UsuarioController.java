package com.goKart.goKart.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController{
	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public UsuarioController(UsuarioRepository usuarioRepository){
	 this.usuarioRepository = usuarioRepository;
	}

	public void verificaCadastro (String email) throws Exception{
		if(email != null) {
			List<Usuario> usuarios = usuarioRepository.findByEmail(email);
			for (Usuario usuario : usuarios) {
				if(usuario.getEmail() != email) {
					throw new Exception("Usuário já possui e-mail cadastrado");
				}else {
					usuarioRepository.save(usuario);
				}
			}
		} 
	}
}
