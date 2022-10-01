package com.goKart.goKart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/login")
	public String login() {
		return "/login";
	}

	@RequestMapping("/goKartNovidades")
	public String goKartNovidadesIndex() {
		return "/goKartNovidades";
	}
	@RequestMapping("/termosCondicoes")
	public String termosCondicoes() {
		return "/termosCondicoes";
	}

	@RequestMapping("/pendenciaCadastro")
	public String pendenciaCadastro() {
		return "/pendenciaCadastro";
	}
}
