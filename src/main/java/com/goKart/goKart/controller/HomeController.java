package com.goKart.goKart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	/*@Autowired
	private PilotoRepository pilotoReposiroty;
	
	@GetMapping("/home")
	public String home() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		model.addAttribute("pedidos", pedidos);
		
		return "home";
	}*/
	
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}	
	
	@RequestMapping("/piloto/kartodromos")
	public String todosKartodromos() {
		return "/piloto/kartodromos";
	}
	
	@RequestMapping("/kartodromos")
	public String todosKartodromosIndex() {
		return "/kartodromos";
	}
	
	@RequestMapping("/goKartNovidades")
	public String goKartNovidadesIndex() {
		return "/goKartNovidades";
	}
	@RequestMapping("/termosCondicoes")
	public String termosCondicoes() {
		return "/termosCondicoes";
	}
}
