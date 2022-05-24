package com.goKart.goKart.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goKart.goKart.repository.AdministradorRepository;
import com.goKart.goKart.repository.PerfilRepository;

@SpringBootTest
class AdministradorTest {

	@Autowired
	private AdministradorRepository  administradorRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	
	

	@Test
	void salvarUmAdministrador() {
		Administrador adm = new Administrador();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("123");
		
		Perfil perfil = perfilRepository.findByNome("ADMIN");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);		
		
		
		adm.setEmail("adm@gmail.com");
		adm.setNome("Administrador");
		adm.setSenha(encodedPassword);
		adm.setPerfis(perfis);
		
		
		administradorRepository.save(adm);
	}

}
