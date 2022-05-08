package com.goKart.goKart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goKart.goKart.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//List<Usuario> findByEmail(String email);

	Optional<Usuario> findByEmail(String email);

}
