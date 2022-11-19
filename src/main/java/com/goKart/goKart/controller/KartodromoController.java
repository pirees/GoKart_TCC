package com.goKart.goKart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.goKart.goKart.excel.TodosKartodromosExcel;
import com.goKart.goKart.service.EnviaEmailService;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PerfilRepository;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Perfil;
import com.goKart.goKart.model.StatusUsuario;

@Controller
public class KartodromoController{
	
	private KartodromoRepository kartodromoRepository;
	
	private PerfilRepository perfilRepository;
	
	private UsuarioController usuarioController;

	private EnviaEmailService enviaEmailService;
	
	public KartodromoController(KartodromoRepository kartodromoRepository, PerfilRepository perfilRepository,
			UsuarioController usuarioController, EnviaEmailService enviaEmailService) {
		this.kartodromoRepository = kartodromoRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioController = usuarioController;
		this.enviaEmailService = enviaEmailService;
	}

	@GetMapping("/cadastroKartodromo")
	public String formulario(Kartodromo kartodromo) {	
		return "/cadastroKartodromo";
	}
	
	@GetMapping("kartodromo/meusDados")
	public String listarDados(Model model, String email, Kartodromo kartodromo) {
		
		email = SecurityContextHolder.getContext().getAuthentication().getName();
		kartodromo = kartodromoRepository.findByEmail(email);
		
		model.addAttribute("kartodromo", kartodromo);

		return "kartodromo/meusDados";
	}

	@PostMapping ("/cadastroKartodromo")
	public String salvarKartodromo(@Valid Kartodromo kartodromo, BindingResult resultado, String email, @RequestParam("fileKartodromo") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception{

		if(resultado.hasErrors()){
			return "/cadastroKartodromo";
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(kartodromo.getSenha());
		
		Perfil perfil = perfilRepository.findByNome("KARTODROMO");
		List<Perfil> perfis = new ArrayList<Perfil>();
		perfis.add(perfil);	
		kartodromo.setPerfis(perfis);
		kartodromo.setSenha(encodedPassword);
		kartodromo.setStatusUsuario(StatusUsuario.PENDENTE);
		kartodromo.setImagem(file.getBytes());

		if(usuarioController.verificaCadastro(email)){
			redirectAttributes.addFlashAttribute("errormessage", "E-mail já cadastrado no sistema.");
			return "redirect:/cadastroKartodromo";
		} else{
			kartodromoRepository.save(kartodromo);
			//enviaEmailService.enviarcadastroKartodromoPendente(kartodromo);
		}
		return "redirect:/pendenciaCadastro";
	}
	
	@GetMapping("admin/todosKartodromos")
	public String listarKartodromos(Model model) {
		List<Kartodromo> kartodromos = kartodromoRepository.findAll();
		
		model.addAttribute("kartodromos", kartodromos);
		
		return "admin/todosKartodromos";
	}

	@GetMapping("admin/todosKartodromos/exports/csv")
	public void listarPilotosExport(HttpServletResponse response) throws IOException {

		List<Kartodromo> kartodromos = kartodromoRepository.findAll();

		TodosKartodromosExcel todosKartodromosExcel = new TodosKartodromosExcel(kartodromos);

		todosKartodromosExcel.export(response);

	}
	@GetMapping("imagem/{idkartodromo}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("idkartodromo") Long id){
		Kartodromo kartodromo = this.kartodromoRepository.getById(id);
		return kartodromo.getImagem();
	}

	@GetMapping("piloto/kartodromos")
	public String listarKartodromosPiloto(Model model) {
		List<Kartodromo> kartodromo = kartodromoRepository.findAll();

		model.addAttribute("kartodromo", kartodromo);

		return "piloto/kartodromos";
	}

	@GetMapping("/kartodromos")
	public String listarKartodromosInicial(Model model) {
		List<Kartodromo> kartodromo = kartodromoRepository.findAll();

		model.addAttribute("kartodromo", kartodromo);

		return "/kartodromos";
	}

}
