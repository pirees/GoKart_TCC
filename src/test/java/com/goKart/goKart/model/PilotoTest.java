package com.goKart.goKart.model;

import com.goKart.goKart.controller.UsuarioController;
import com.goKart.goKart.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goKart.goKart.controller.PilotoController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PilotoTest {

	private static final String nomePiloto = "João";
	private static final String sobrenomePiloto = "Souza";
	private static final String senhaPiloto = "123";
	private static final String emailPiloto = "testes@gmail.com";
	private static final String cidadePiloto = "Curitiba";
	private static final Enum<Estado> estadoPiloto = Estado.PR;
	private static final Enum<Nivel> nivelPiloto = Nivel.Profissional;
	private static final List<Perfil> perfilPiloto = new ArrayList<>();

	@Mock
	PilotoController pilotoController;
	BindingResult resultado;
	RedirectAttributes redirectAttributes;
	@Mock
	Piloto piloto;
	@Mock
	UsuarioController userControl;

	@Before
	public void setup(){
		piloto = new Piloto();
		piloto.setNome(nomePiloto);
		piloto.setSobrenome(sobrenomePiloto);
		piloto.setSenha(senhaPiloto);
		piloto.setEmail(emailPiloto);
		piloto.setCidade(cidadePiloto);
		piloto.setEstado((Estado) estadoPiloto);
		piloto.setNivel((Nivel) nivelPiloto);
		piloto.setPerfis(perfilPiloto);
	}

	@Test
	public void salvarTodosOsDadosDoPiloto() throws Exception {

		assertThat(piloto.getNome(), equalTo(nomePiloto));
		assertThat(piloto.getSobrenome(), equalTo(sobrenomePiloto));
		assertThat(piloto.getSenha(), equalTo(senhaPiloto));
		assertThat(piloto.getEmail(), equalTo(emailPiloto));
		assertThat(piloto.getCidade(), equalTo(cidadePiloto));
		assertThat(piloto.getEstado(), equalTo(estadoPiloto));
		assertThat(piloto.getNivel(), equalTo(nivelPiloto));
		assertThat(piloto.getPerfis(), equalTo(perfilPiloto));

		userControl.verificaCadastro(piloto.getEmail());

		pilotoController.salvarPiloto(piloto, resultado, emailPiloto, redirectAttributes);
	}
}
