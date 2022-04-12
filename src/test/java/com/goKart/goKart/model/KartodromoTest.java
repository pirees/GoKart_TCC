package com.goKart.goKart.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.goKart.goKart.controller.UsuarioController;
import com.goKart.goKart.repository.UsuarioRepository;

@SpringBootTest
class KartodromoTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	void test() throws Exception {
		Kartodromo kartodromo = new Kartodromo();
		UsuarioController userControl = new UsuarioController(usuarioRepository);

		kartodromo.setNome("RA Kart Indoor");
		kartodromo.setCidade("Curitiba");
		kartodromo.setCNPJ("1014564564");
		kartodromo.setEmail("ra@contato.com.br");
		kartodromo.setEstado(Estado.SP);
		kartodromo.setSenha("123");
		
		userControl.verificaCadastro(kartodromo.getEmail());
		
		usuarioRepository.save(kartodromo);
	}

}
