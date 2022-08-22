package com.goKart.goKart.model;

import lombok.Data;

public enum StatusUsuario {

    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    PENDENTE("Pendente");

    private String StatusUsuario;

    public String getStatusUsuario() {
        return StatusUsuario;
    }

    public void setStatusUsuario(String statusUsuario) {
        StatusUsuario = statusUsuario;
    }

    StatusUsuario(String statusUsuario) {
        StatusUsuario = statusUsuario;
    }
}
