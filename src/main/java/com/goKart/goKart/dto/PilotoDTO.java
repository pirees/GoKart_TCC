package com.goKart.goKart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.goKart.goKart.model.Estado;
import com.goKart.goKart.model.Nivel;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.repository.PilotoRepository;

public class PilotoDTO {

	@NotBlank(message = "Nome não pode ficar em branco")
	private String nome;
	
	@NotBlank(message = "E-mail não pode ficar em branco")
	private String email;
	
	@NotBlank(message = "Senha não pode ficar em branco")
	private String senha;
	
	@NotBlank(message = "Cidade não pode ficar em branco")
	private String cidade;
	
	@NotBlank(message = "Sobrenome não pode ficar em branco")
	private String sobrenome;
	
	private Estado estado;
	
	private Nivel nivel;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
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
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public PilotoDTO(String nome, String email, String senha, String cidade, String sobrenome, Estado estado,
			Nivel nivel) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cidade = cidade;
		this.sobrenome = sobrenome;
		this.estado = estado;
		this.nivel = nivel;
	}
	
	public Piloto toPiloto() {
		Piloto piloto = new Piloto();
		
		piloto.setNome(nome);
		piloto.setEmail(email);
		piloto.setSenha(senha);
		piloto.setCidade(cidade);
		piloto.setSobrenome(sobrenome);
		piloto.setEstado(getEstado());
		piloto.setNivel(getNivel());
		
		return piloto;
	}
	
	public Piloto atualizarNivel (Long id, PilotoRepository pilotoRepository) {
		Piloto piloto = pilotoRepository.getById(id);
		piloto.setNivel(this.nivel);
		pilotoRepository.save(piloto);
		return piloto;
	}

}
