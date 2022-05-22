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
	
	@RequestMapping("/kartodromos")
	public String todosKartodromos() {
		System.out.println("Acessando a página inicial 2");
		return "/kartodromos";
	}
}
