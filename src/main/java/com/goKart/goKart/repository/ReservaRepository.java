package com.goKart.goKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Reserva;

@Repository
@EnableJpaRepositories
public interface ReservaRepository extends JpaRepository<Reserva, Long>  {

}
