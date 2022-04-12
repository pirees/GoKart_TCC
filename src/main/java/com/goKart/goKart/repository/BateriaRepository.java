package com.goKart.goKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.goKart.goKart.model.Bateria;

@Repository
@EnableJpaRepositories
public interface BateriaRepository extends JpaRepository<Bateria, Long>  {

}
