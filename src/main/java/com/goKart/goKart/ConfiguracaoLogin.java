package com.goKart.goKart;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ConfiguracaoLogin implements WebMvcConfigurer{

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/admin/menuAdmin").setViewName("/admin/menuAdmin");
		registry.addViewController("/piloto/menuPiloto").setViewName("/piloto/menuPiloto");
		registry.addViewController("/kartodromo/menuKartodromo").setViewName("/kartodromo/menuKartodromo");
		registry.addViewController("/semPermissao").setViewName("/semPermissao");
	}

}
