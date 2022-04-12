package com.goKart.goKart.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goKart.goKart.dto.PilotoDTO;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Usuario;
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
	
	@GetMapping("atualizarPiloto/{id}")
	public String atualizarFormulario(Model model, @PathVariable Long id) {
		
		Piloto pilotos = pilotoRepository.getById(id);
		model.addAttribute("pilotos", pilotos);
		return "piloto/atualizarPiloto";
	}
	
	@GetMapping("login")
	public String login(PilotoDTO pilotoDTO) {
		return "piloto/menuPiloto";
	}

	@PostMapping ("novo")
	public String salvarPiloto(@Valid PilotoDTO pilotoDTO,BindingResult resultado) throws Exception {
		
		if(resultado.hasErrors()) {
			return "piloto/cadastroPiloto";
		}
		
		UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		Piloto piloto = pilotoDTO.toPiloto();
		userControl.verificaCadastro(piloto.getEmail());
		pilotoRepository.save(piloto);
		
		return "piloto/cadastroPiloto";
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
