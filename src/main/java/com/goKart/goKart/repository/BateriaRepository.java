package com.goKart.goKart.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Bateria;

@Repository
@EnableJpaRepositories
public interface BateriaRepository extends JpaRepository<Bateria, Long>  {

	@Query("select p from Bateria p where p.kartodromo.id = :id")
	List<Bateria> findByKartodromoId(Long id);

	@Query("SELECT p FROM Bateria p WHERE p.data > CURDATE() and p.kartodromo.id = :id")
	List<Bateria> findByDateKartodromoId(Long id);

	@Query("SELECT p FROM Bateria p WHERE p.data > CURDATE() OR p.data = CURDATE() and p.horaBateria > CURTIME() ORDER BY p.data ASC")
	List<Bateria> findAllByOrderByDataAsc();

	@Query(value = "SELECT b FROM Bateria b INNER JOIN b.kartodromo k WHERE k.nome like %?1% and b.data = ?2")
	List<Bateria> findByKartodromoNomeData(@Param("nomepesquisa") String nomepesquisa,
										   @Param("datapesquisa") LocalDate datapesquisa);

	@Query(value = "SELECT b FROM Bateria b INNER JOIN b.kartodromo k WHERE k.nome like %?1% and b.data > CURDATE() OR b.data = CURDATE()")
	List<Bateria> findByKartodromoNome(@Param("nomepesquisa") String nomepesquisa);

	@Query(value = "SELECT b FROM Bateria b INNER JOIN b.kartodromo k where k.id = ?1 and b.data = ?2")
	List<Bateria> findByKartodromoNomeMenuKartodromo(Long id, LocalDate datapesquisa);
}
