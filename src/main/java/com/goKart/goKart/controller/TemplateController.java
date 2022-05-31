package com.goKart.goKart.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

	 /*@Value("${mercado_pago_sample_public_key}")
	    private String mercadoPagoSamplePublicKey;*/

	    @GetMapping
	    public String renderMainPage(Model model) {
	        model.addAttribute("publicKey", "TEST-3d40f433-f405-48ae-aa0d-93735b8a3955");
	        return "index";
	    }
}
