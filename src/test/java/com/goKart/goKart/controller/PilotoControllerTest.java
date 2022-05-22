package com.goKart.goKart.controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.repository.PilotoRepository;

@SpringBootTest
class PilotoControllerTest {
	
	@InjectMocks
	private PilotoController pilControl;
	private Piloto piloto;
	
	@Mock
	private PilotoRepository pilotoRepository;
	
	@BeforeEach
	public void iniciarClasse() {
		//this.pilControl = new PilotoController(pilotoRepository);
		this.piloto = new Piloto();
		
	}

	/*@Test
	public void VerificaSeJaExisteUmEmail() throws Exception {
		PilotoController pilControl = new PilotoController();
		
		Piloto piloto = new Piloto();
		piloto.setEmail("contato@gmail.com");
		
		//pilControl.findByEmail(piloto);
		
		pilControl.pilotoCadastrado(piloto);
		
		System.out.println("AQUUUUUUUUUUUUI" + piloto.getEmail());
		
		assertEquals("contato@gmail.com", piloto.toString());
	}*/

}	
