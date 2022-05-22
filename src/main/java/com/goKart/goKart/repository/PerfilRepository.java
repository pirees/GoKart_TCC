package com.goKart.goKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

	Perfil findByNome(String nome);
	
	
}
