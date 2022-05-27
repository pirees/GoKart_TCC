package com.goKart.goKart.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goKart.goKart.excel.ReservaExcel;
import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Kartodromo;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.model.StatusPagamento;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.KartodromoRepository;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.ReservaRepository;

@Controller
public class ReservaController {

	private ReservaRepository reservaRepository;
	
	private PilotoRepository pilotoRepository;
	
	private BateriaRepository bateriaRepository;
	
	private KartodromoRepository kartodromoRepository;

	@Autowired
	public ReservaController(ReservaRepository reservaRepository, PilotoRepository pilotoRepository,
			BateriaRepository bateriaRepository, KartodromoRepository kartodromoRepository) {
		super();
		this.reservaRepository = reservaRepository;
		this.pilotoRepository = pilotoRepository;
		this.bateriaRepository = bateriaRepository;
		this.kartodromoRepository = kartodromoRepository;
	}

	// PRECISA CRIAR REGRA NOVA
	/*public Integer pilotoReserva(Reserva reserva) {
		int newVaga = 0;
		Reserva reservaa = new Reserva();
		if (reserva.getNrReserva() != 0 && reserva.isConfirmado()) {
			newVaga = reserva.getBateria().getVagasDisponiveis() - reserva.getNrReserva();
			reservaa.getBateria().setVagasDisponiveis(newVaga);
		}

		return newVaga;

	}*/

	// LISTA TODAS AS BATERIAS DISPONÍVEIS
	@GetMapping("piloto/reservasPiloto")
	public String listaReservas(Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
				
		List<Reserva> reservas = reservaRepository.findByEmail(email);


		model.addAttribute("reserva", reservas);
		return "piloto/reservasPiloto";
		
	}


	@PostMapping("piloto/confirmarReserva/{id}")
	public String salvarReserva(@PathVariable Long id, Reserva reserva) throws Exception {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Piloto piloto = pilotoRepository.findByEmail(email);
		
		Bateria bateria = bateriaRepository.getById(id);
		
		reserva.setStatus(StatusPagamento.NCONFIRMADO);
		reserva.setBateria(bateria);
		reserva.setPiloto(piloto);
		reserva.setKartodromo(reserva.getBateria().getKartodromo());
		reserva.setDataReserva(LocalDate.now());
		
		atulizarVagasDisponiveis(reserva);
		//verificaPilotoCadastrado(reserva,piloto);
		
		reservaRepository.save(reserva);

		return "redirect:/piloto/menuPiloto";
	}
	
	public Reserva atulizarVagasDisponiveis(Reserva reserva) {
					
		if(reserva.getBateria().getVagasConfirmadas() < reserva.getBateria().getNrMaxPiloto()) {
			reserva.getBateria().setVagasConfirmadas(reserva.getBateria().getVagasConfirmadas() + reserva.getNrReserva());
		}
		
		return reserva;
	}
	
	/*public Reserva verificaPilotoCadastrado(Reserva reserva, Piloto piloto) throws Exception {
		
		List<Reserva> listaReservas = reservaRepository.findAll();
		
		for (Reserva reservas : listaReservas) {

			if(reservas.getPiloto().equals(piloto) && reservas.getId() != null) {
				throw new Exception("Você já está cadastrado nessa bateria!");
			}
		}

		return reserva;
	}*/
	
	@GetMapping("kartodromo/relatorio")
	public String listarPilotosKartodromo(Model model, Reserva reserva) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		reserva.setKartodromo(kartodromo);
		
		List<Reserva> reservas = reservaRepository.findDataReservaKartodromoById(reserva.getKartodromo().getId());
				
		model.addAttribute("reservas", reservas);
		
		return "kartodromo/relatorio";
	}
	
	@GetMapping("kartodromo/relatorio/exports/csv")
	public void listarPilotosKartodromoExport(Reserva reserva, HttpServletResponse response) throws IOException {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Kartodromo kartodromo = kartodromoRepository.findByEmail(email);
		
		reserva.setKartodromo(kartodromo);
		
		List<Reserva> reservas = reservaRepository.findDataReservaKartodromoById(reserva.getKartodromo().getId());
	
		ReservaExcel reservaExcel = new ReservaExcel(reservas);
		
		reservaExcel.export(response);
	
	}

}
