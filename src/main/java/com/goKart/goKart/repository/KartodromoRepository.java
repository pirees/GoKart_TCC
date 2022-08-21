package com.goKart.goKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Kartodromo;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface KartodromoRepository extends JpaRepository<Kartodromo, Long>  {

	Kartodromo findByEmail(String email);

    @Query(value = "SELECT * FROM tb_usuario where tb_usuario.status_usuario = 2", nativeQuery = true)
    List<Kartodromo> findByPendencia();
}
