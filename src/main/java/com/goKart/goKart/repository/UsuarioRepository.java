package com.goKart.goKart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goKart.goKart.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	@Query("select p from Usuario p where email = :email")
	List<Usuario> findByCadastroEmail(String email);

}
