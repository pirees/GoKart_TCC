package com.goKart.goKart.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.model.Usuario;
import com.goKart.goKart.repository.UsuarioRepository;

@Service
@Transactional
public class AutentificacaoService implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	public AutentificacaoService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username);
		if(usuario != null) {
			Set<GrantedAuthority> perfis = new HashSet<GrantedAuthority>();
			for (Perfil perfil : usuario.getPerfis()) {
				GrantedAuthority pp = new SimpleGrantedAuthority(perfil.getNome());
				perfis.add(pp);
			}
			
			User user = new User(usuario.getEmail(), usuario.getSenha(), perfis);
			
			return user;

			//return (UserDetails) usuario.get();
		}
		
		throw new UsernameNotFoundException("Usuário com senha ou e-mail inválidos");
	}

}
