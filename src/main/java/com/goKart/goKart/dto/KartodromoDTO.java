package com.goKart.goKart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.goKart.goKart.model.Estado;
import com.goKart.goKart.model.Kartodromo;
public class KartodromoDTO {
	
	@NotNull (message = "Nome não pode ficar em branco")
	private String nome;
	
	@NotBlank(message = "E-mail não pode ficar em branco")
	private String email;
	
	@NotBlank(message = "Senha não pode ficar em branco")
	private String senha;
	
	@NotBlank(message = "Cidade não pode ficar em branco")
	private String cidade;
	
	@NotBlank(message = "Sobrenome não pode ficar em branco")
	private String CNPJ;
	
	@NotNull (message = "Estado não pode ficar em branco")
	private Estado estado;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	public KartodromoDTO(String nome,String email, String senha, String cidade,String CNPJ,Estado estado) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cidade = cidade;
		this.CNPJ = CNPJ;
		this.estado = estado;
	}

	public Kartodromo toKartodromo() {
		
		Kartodromo kartodromo = new Kartodromo();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(senha);
		
		kartodromo.setNome(nome);
		kartodromo.setEmail(email);
		kartodromo.setSenha(encodedPassword);
		kartodromo.setCidade(cidade);
		kartodromo.setCNPJ(CNPJ);
		kartodromo.setEstado(getEstado());
		
		return kartodromo;
	}

}
