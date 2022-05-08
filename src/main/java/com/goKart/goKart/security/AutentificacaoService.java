package com.goKart.goKart.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;

@Service
public class AutentificacaoService implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	public AutentificacaoService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
		if(usuario.isPresent()) {
			return (UserDetails) usuario.get();
		}
		throw new UsernameNotFoundException("Usuário com senha ou e-mail inválidos");
	}

}
