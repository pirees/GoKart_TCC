package com.goKart.goKart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goKart.goKart.dto.RequisicaoNovoPedido;
import com.goKart.goKart.model.Pedido;
import com.goKart.goKart.repository.PedidoRepository;


@Controller
@RequestMapping("pedido")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoPedido requisicao) {
		return "pedido/formulario";
	}
	
	@PostMapping ("novo")
	public String novo(@Valid RequisicaoNovoPedido requisicao, BindingResult resultado) {
		if(resultado.hasErrors()) {
			return "pedido/formulario";
		}
		Pedido pedido = requisicao.toPedido();
		pedidoRepository.save(pedido);
		return "pedido/formulario";
	}	
	
}
