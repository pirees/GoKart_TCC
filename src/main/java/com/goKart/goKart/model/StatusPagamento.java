package com.goKart.goKart.model;

public enum StatusPagamento {

	CONFIRMADO("Confirmado"),
    NCONFIRMADO("Não confirmado"),
	DEVOLVIDO("Devolvido");
	
	private String StatusPagamento;

	public String getStatusPagamento() {
		return StatusPagamento;
	}

	public void setStatusPagamento(String StatusPagamento) {
		this.StatusPagamento = StatusPagamento;
	}

	private StatusPagamento(String StatusPagamento) {
		this.StatusPagamento = StatusPagamento;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.StatusPagamento;
	}
}
