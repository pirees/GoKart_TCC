package com.goKart.goKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.goKart.goKart.model.Administrador;

@Repository
@EnableJpaRepositories
public interface AdministradorRepository extends JpaRepository<Administrador, Long>  {

	
}
