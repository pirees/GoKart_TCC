package com.goKart.goKart.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;

@Component
public class LoginSucesso extends SavedRequestAwareAuthenticationSuccessHandler {

	private UsuarioRepository  usuarioRepository;

	public LoginSucesso(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		// pega o login do usuário logado
		String login = authentication.getName();
		// busca o usuário no banco pelo login
		Usuario usuario = usuarioRepository.findByEmail(login);
		
		String redirectURL = "";
		if (temAutorizacao(usuario, "ADMIN")) {
			redirectURL = "/admin/menuAdmin";
		} else if (temAutorizacao(usuario, "PILOTO")) {
			redirectURL = "/piloto/menuPiloto";
		} else if (temAutorizacao(usuario, "KARTODROMO")) {
			redirectURL = "/kartodromo/menuKartodromo";
		}
		response.sendRedirect(redirectURL);
	}
	
	private boolean temAutorizacao(Usuario usuario, String nome) {
		for (Perfil pp : usuario.getPerfis()) {
			if (pp.getNome().equals(nome)) {
				return true;
			}
	    }
		return false;
	}


}
