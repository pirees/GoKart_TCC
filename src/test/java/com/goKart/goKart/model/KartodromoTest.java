package com.goKart.goKart.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goKart.goKart.controller.UsuarioController;
import com.goKart.goKart.repository.UsuarioRepository;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class KartodromoTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	void test() throws Exception {
		Kartodromo kartodromo = new Kartodromo();
		UsuarioController userControl = new UsuarioController(usuarioRepository);
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("123");

		kartodromo.setNome("TCC Kart Indoor");
		kartodromo.setCidade("Curitiba");
		kartodromo.setCNPJ("1014564564");
		kartodromo.setEmail("tcc@contato.com.br");
		kartodromo.setEstado(Estado.SP);
		kartodromo.setSenha(encodedPassword);
		
	//	userControl.verificaCadastro(kartodromo.getEmail());
		
		
		
		usuarioRepository.save(kartodromo);
	}

}
