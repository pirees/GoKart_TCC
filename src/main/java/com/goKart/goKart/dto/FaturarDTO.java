package com.goKart.goKart.dto;

import com.goKart.goKart.model.Reserva;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FaturarDTO {

    private String nome;
    private String email;
    private BigDecimal valorTotal;
    private BigDecimal valorAPagar;

    public FaturarDTO(Reserva reserva){
        this.nome = reserva.getKartodromo().getNome();
        this.email = reserva.getKartodromo().getEmail();
        this.valorTotal = reserva.getBateria().getValorBateria();
        this.valorAPagar = reserva.getBateria().getValorBateria();
    }

    public FaturarDTO(String nome, String email, BigDecimal valorTotal, BigDecimal valorApagar) {
        this.nome = nome;
        this.email = email;
        this.valorTotal = valorTotal;
        this.valorAPagar = valorApagar.divide(BigDecimal.valueOf(100).divide(BigDecimal.valueOf(100)));
    }
}
