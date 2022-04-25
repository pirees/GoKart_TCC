package com.goKart.goKart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Reserva;


@Repository
@EnableJpaRepositories
public interface ReservaRepository extends JpaRepository<Reserva, Long>  {
	
	@Query(value = "select * from tb_reserva where piloto_Id = :id", nativeQuery = true)
	List<Reserva> findByIdPiloto(Long id);
}
