package com.goKart.goKart.model;

import com.goKart.goKart.controller.KartodromoController;
import com.goKart.goKart.controller.PilotoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goKart.goKart.controller.UsuarioController;
import com.goKart.goKart.repository.UsuarioRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class KartodromoTest {

	private static final String nomeKartodromo = "João";
	private static final String senhaKartodromo = "123";
	private static final String emailKartodromo = "testes@gmail.com";
	private static final String cidadeKartodromo = "Curitiba";
	private static final String cnpjKartodromo = "29674212000136";
	private static final String logradouroKartodromo = "Rua de teste";
	private static final String cepKartodromo = "78581045";
	private static final String complementoKartodromo = "próximo ao teste";
	private static final Enum<Estado> estadoKartodromo = Estado.PR;
	private static final List<Perfil> perfilKartodromo = new ArrayList<>();
	private static final Enum<StatusUsuario> statusKartodromo = StatusUsuario.APROVADO;


	@Mock
	KartodromoController kartodromoController;
	BindingResult resultado;
	RedirectAttributes redirectAttributes;
	@Mock
	Kartodromo kartodromo;
	@Mock
	UsuarioController userControl;

	@Before
	public void setup(){
		kartodromo = new Kartodromo();
		kartodromo.setNome(nomeKartodromo);
		kartodromo.setSenha(senhaKartodromo);
		kartodromo.setEmail(emailKartodromo);
		kartodromo.setCidade(cidadeKartodromo);
		kartodromo.setCNPJ(cnpjKartodromo);
		kartodromo.setLogradouro(logradouroKartodromo);
		kartodromo.setCEP(cepKartodromo);
		kartodromo.setComplemento(complementoKartodromo);
		kartodromo.setEstado((Estado) estadoKartodromo);
		kartodromo.setPerfis(perfilKartodromo);
		kartodromo.setStatusUsuario((StatusUsuario) statusKartodromo);
	}

	@Test
	public void salvarTodosOsDadosDoPiloto() throws Exception {

		assertThat(kartodromo.getNome(), equalTo(nomeKartodromo));
		assertThat(kartodromo.getSenha(), equalTo(senhaKartodromo));
		assertThat(kartodromo.getEmail(), equalTo(emailKartodromo));
		assertThat(kartodromo.getCidade(), equalTo(cidadeKartodromo));
		assertThat(kartodromo.getCNPJ(), equalTo(cnpjKartodromo));
		assertThat(kartodromo.getLogradouro(), equalTo(logradouroKartodromo));
		assertThat(kartodromo.getCEP(), equalTo(cepKartodromo));
		assertThat(kartodromo.getComplemento(), equalTo(complementoKartodromo));
		assertThat(kartodromo.getEstado(), equalTo(estadoKartodromo));
		assertThat(kartodromo.getPerfis(), equalTo(perfilKartodromo));
		assertThat(kartodromo.getStatusUsuario(), equalTo(statusKartodromo));

		userControl.verificaCadastro(kartodromo.getEmail());

		//kartodromoController.salvarKartodromo(kartodromo, resultado, emailKartodromo, redirectAttributes);
	}

}
