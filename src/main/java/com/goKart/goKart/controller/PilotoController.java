package com.goKart.goKart.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goKart.goKart.dto.PilotoDTO;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.UsuarioRepository;

@Controller
@RequestMapping("piloto")
public class PilotoController{
	
	private PilotoRepository pilotoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public PilotoController(PilotoRepository pilotoRepository){
	 this.pilotoRepository = pilotoRepository;
	}

	@GetMapping("cadastroPiloto")
	public String formulario(PilotoDTO pilotoDTO) {
		return "piloto/cadastroPiloto";
	}
	
	/*Lista Nivel dos pilotos*/
	@GetMapping("atualizarPiloto")
	public String listaNivel(Model model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Piloto piloto = pilotoRepository.findByEmail(email);

		model.addAttribute("piloto", piloto);
		
		return "piloto/atualizarPiloto";
		
	}
	
	@PostMapping("atualizarPiloto")
	public ModelAndView atualizarNivel(String email, PilotoDTO pilotoDto) {
		
		email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		pilotoDto.atualizarNivel(email, pilotoRepository);

		return new ModelAndView("redirect:/piloto/atualizarPiloto");
	}

	@RequestMapping("login")
	public String login(Piloto piloto) {
		System.out.println("Acessando a página inicial");
		return "piloto/login";
	}	
	
	@GetMapping("menuPiloto/{id}")
	public String pegarId(@PathVariable Long id, PilotoDTO pilotoDTO) {
		Optional<Piloto> piloto = pilotoRepository.findById(id);
		return "piloto/menuPiloto";
		
	}

	@PostMapping ("cadastroPiloto")
	public String salvarPiloto(@Valid PilotoDTO pilotoDTO, BindingResult resultado) {
		
		if(resultado.hasErrors()) {
			return "piloto/cadastroPiloto";
		}
		
	
		
		//UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		Piloto piloto = pilotoDTO.toPiloto();
		//userControl.verificaCadastro(piloto.getEmail());
		pilotoRepository.save(piloto);
		
		return "piloto/login";
	}
	
	/* Foi transferido para a classe USUARIO
	public void verificaCadastro (String email) throws Exception{
		if(email != null) {
			List<Piloto> pilotos = pilotoRepository.findByEmail(email);
			for (Piloto piloto : pilotos) {
				if(piloto.getEmail() != email) {
					throw new Exception("Usuário já possui e-mail cadastrado");
				}else {
					pilotoRepository.save(piloto);
				}
			}
		} 
	}*/
}
