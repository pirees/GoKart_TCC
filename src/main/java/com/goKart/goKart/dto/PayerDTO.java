package com.goKart.goKart.dto;

import javax.validation.constraints.NotNull;

public class PayerDTO {

	@NotNull
    private String email;

    @NotNull
    private PayerIdentificationDTO identification;

    public PayerDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = "spring@gmail.com";
    }

    public PayerIdentificationDTO getIdentification() {
        return identification;
    }

    public void setIdentification(PayerIdentificationDTO identification) {
        this.identification = identification;
    }
}
