package com.goKart.goKart.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutentificacaoService autenticacaoService;
	
	/*CONFIGUAR AÇÕES DE AUTENTICAÇÃO*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	/*CONFIGURACOES DE AUTORIZACAO*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
			.antMatchers( "/").permitAll()
			.antMatchers( "/kartodromos").permitAll()
			.antMatchers( "/goKartNovidades").permitAll()
			.antMatchers("/piloto/cadastroPiloto").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/piloto/login").permitAll().defaultSuccessUrl("/piloto/menuPiloto", true)
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/piloto/menuPiloto");
		http.csrf().disable();
		
	}
	
	/*RECURSOS ESTÁTICOS*/
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
}
