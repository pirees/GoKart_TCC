package com.goKart.goKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Piloto;

@Repository
@EnableJpaRepositories
public interface PilotoRepository extends JpaRepository<Piloto, Long>  {

	Piloto findByEmail(String email);
	
}
