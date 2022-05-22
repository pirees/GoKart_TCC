package com.goKart.goKart.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.goKart.goKart.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	private AutentificacaoService autenticacaoService;
	
	private UsuarioRepository usuarioRepository;
	
	private LoginSucesso loginSucesso;

	public SecurityConfiguration(AutentificacaoService autenticacaoService, UsuarioRepository usuarioRepository,
			LoginSucesso loginSucesso) {
		super();
		this.autenticacaoService = autenticacaoService;
		this.usuarioRepository = usuarioRepository;
		this.loginSucesso = loginSucesso;
	}

	/*CONFIGUAR AÇÕES DE AUTENTICAÇÃO*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	/*CONFIGURACOES DE AUTORIZACAO*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {	
		
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.POST,"/piloto/cadastroPiloto").permitAll()
		.antMatchers(HttpMethod.GET,"/piloto/cadastroPiloto").permitAll()
		.antMatchers("/kartodromo/**").hasAnyAuthority("KARTODROMO")
		.antMatchers("/piloto/**").hasAnyAuthority("PILOTO")
		.and()
		//.exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado")
		//.and()
		
		.formLogin().successHandler(loginSucesso)
		.loginPage("/login").permitAll()
		//.and().csrf()
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/").permitAll();
		http.csrf().disable();
	}
	
	/*RECURSOS ESTÁTICOS*/
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		AutentificacaoService serivce = new AutentificacaoService(usuarioRepository);
		return serivce;
	}
	
}
