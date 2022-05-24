package com.goKart.goKart.dto;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.goKart.goKart.model.Administrador;

public class AdminDTO {
	

	private String nome;
	
	private String email;
	
	private String senha;

	private String sobrenome;
	
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public AdminDTO(String nome,String email, String senha, String sobrenome) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.sobrenome = sobrenome;
	}

	public Administrador toAdministrador() {
		
		Administrador administrador = new Administrador();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(senha);
		
		administrador.setNome(nome);
		administrador.setEmail(email);
		administrador.setSenha(encodedPassword);
		administrador.setSobrenome(sobrenome);
		
		return administrador;
	}

}
