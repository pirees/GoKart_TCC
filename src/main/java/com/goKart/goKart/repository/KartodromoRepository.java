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

    @Query(value = "SELECT * FROM tb_usuario where status_usuario = 2 or status_usuario = 1", nativeQuery = true)
    List<Kartodromo> findByPendencia();

    @Query("select k from Kartodromo k where k.statusUsuario = 0")
    List<Kartodromo> findKartodromosAprovados();
}
