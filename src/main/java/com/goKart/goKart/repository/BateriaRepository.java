package com.goKart.goKart.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Bateria;

@Repository
@EnableJpaRepositories
public interface BateriaRepository extends JpaRepository<Bateria, Long>  {

	//@Query(value = "SELECT * FROM tb_bateria WHERE tb_bateria.kartodromo_id = piloto_id", nativeQuery = true)
	@Query("select p from Bateria p where p.kartodromo.id = :id")
	List<Bateria> findByKartodromoId(Long id);
	
	//@Query(value = "SELECT * FROM tb_bateria WHERE tb_bateria.data BETWEEN CURRENT_DATE() AND CURRENT_DATE()+1 GROUP BY kartodromo_id ORDER BY tb_bateria.data ASC", nativeQuery = true)
	@Query("SELECT p FROM Bateria p WHERE p.data BETWEEN CURRENT_DATE() AND CURRENT_DATE() +1 and p.kartodromo.id = :id ORDER BY p.data ASC")
	List<Bateria> findByDateKartodromoId(Long id);

	@Query("SELECT p FROM Bateria p WHERE p.data BETWEEN CURRENT_DATE() AND CURRENT_DATE() +1")
	Page<Bateria> findByData(Pageable pageable);

}
