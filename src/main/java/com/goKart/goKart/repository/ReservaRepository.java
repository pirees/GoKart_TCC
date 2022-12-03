package com.goKart.goKart.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Reserva;


@Repository
@EnableJpaRepositories
public interface ReservaRepository extends JpaRepository<Reserva, Long>  {
	
	@Query(value = "select * from tb_reserva where piloto_Id = :id", nativeQuery = true)
	List<Reserva> findByIdPiloto(Long id);

	@Query("select p from Reserva p join p.piloto u where u.email= :email")
	Page<Reserva> findByEmail(@Param("email")String email, Pageable paginacao);

	@Query(value = "SELECT * FROM tb_reserva WHERE tb_reserva.kartodromo_id = kartodromo_id GROUP BY piloto_id ORDER BY tb_reserva.data_reserva ASC", nativeQuery = true)
	List<Reserva> findByDataReserva();
	
	@Query("select p from Reserva p where p.kartodromo.id = :id and p.status = 0 GROUP BY p.piloto.id ORDER BY p.dataReserva ASC")
	List<Reserva> findDataReservaKartodromoById(Long id);

	@Query(value = "SELECT * FROM tb_reserva where tb_reserva.bateria_id = :id and tb_reserva.status = 0 ORDER BY tb_reserva.id ASC", nativeQuery = true)
	List<Reserva> findPilotoByReserva(Long id);

	@Query(value = "select * from tb_reserva where tb_reserva.id = :id", nativeQuery = true)
	List<Reserva> findByIdReserva(Long id);
}
