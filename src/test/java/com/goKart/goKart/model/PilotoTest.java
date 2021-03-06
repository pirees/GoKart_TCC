package com.goKart.goKart.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goKart.goKart.controller.UsuarioController;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.UsuarioRepository;

@SpringBootTest
class PilotoTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	public void salvarTodosOsDadosDoPiloto() throws Exception {
		
		Piloto piloto = new Piloto();
		UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("123");
		
		piloto.setNome("Joaqui");
		piloto.setSobrenome("Madeira");
		piloto.setCidade("Araucária");
		piloto.setEmail("contato@gmail.com");
		piloto.setEstado(Estado.AC);
		piloto.setNivel(Nivel.Mediano);
		piloto.setSenha(encodedPassword);

		//userControl.verificaCadastro(piloto.getEmail());
		
		usuarioRepository.save(piloto);
		
	}
	
	
	

}
