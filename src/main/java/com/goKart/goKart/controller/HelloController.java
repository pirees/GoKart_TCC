package com.goKart.goKart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	
	/*HTTPSERVERREQUEST é para requisitar um valor que apareça na tela para o usuário*/

	@GetMapping("/hello")
	public String hello(Model model) {
		model.addAttribute("nome", "Leonardooo");
		return "hello";
	}
}
