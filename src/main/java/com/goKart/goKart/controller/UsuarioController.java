package com.goKart.goKart.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;
import com.goKart.goKart.service.EnviaEmailService;

@Controller
@Component
public class UsuarioController{
	
	private UsuarioRepository usuarioRepository;
	private EnviaEmailService enviaEmailService;

	public UsuarioController(UsuarioRepository usuarioRepository, EnviaEmailService enviaEmailService) {
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
	@GetMapping("/trocarSenha")
	public String resetSenha(String username) {
		return "/trocarSenha";
	}

	@PostMapping("/trocarSenha")
	public ModelAndView enviaEmailRedefinirSenha(HttpServletRequest request, RedirectAttributes redirectAttributes){

		String email = request.getParameter("username");

		Usuario usuario = usuarioRepository.findByEmail(email);

		if(verificaCadastro(email)){
			usuario.setEmail(email);
			enviaEmailService.enviarUsuarioRedefinirSenha(usuario);
		}else{
			redirectAttributes.addFlashAttribute("errormessage", "E-mail não cadastrado no sistema.");
			return new ModelAndView("redirect:/trocarSenha");
		}
		return new ModelAndView("redirect:/");
	}

	@GetMapping("/novaSenha")
	public String novaSenha(String username, String password, String passwordRepeat) {
		return "/novaSenha";
	}

	@PostMapping("/novaSenha")
	public ModelAndView novaSenhaPiloto(String email, HttpServletRequest request, String password, String passwordRepeat, RedirectAttributes redirectAttributes) {

		email = request.getParameter("username");
		password = request.getParameter("password");
		passwordRepeat = request.getParameter("passwordRepeat");

		Usuario usuario = usuarioRepository.findByEmail(email);

		if(password.equals(passwordRepeat)){

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(password);
			usuario.setSenha(encodedPassword);
			usuarioRepository.save(usuario);
			enviaEmailService.enviarUsuarioSenhaTrocada(usuario);

		}else{
			redirectAttributes.addFlashAttribute("errormessage", "As senhas estão diferentes.");
			return new ModelAndView("redirect:/novaSenha");
		}

		return new ModelAndView("redirect:/login");
	}
}
