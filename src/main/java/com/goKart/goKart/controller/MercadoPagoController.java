package com.goKart.goKart.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.goKart.goKart.dto.CardPaymentDTO;
import com.goKart.goKart.dto.PaymentResponseDTO;
import com.goKart.goKart.exception.MercadoPagoException;
import com.goKart.goKart.model.Bateria;
import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.model.StatusPagamento;
import com.goKart.goKart.repository.BateriaRepository;
import com.goKart.goKart.repository.PilotoRepository;
import com.goKart.goKart.repository.ReservaRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;


@Controller
public class MercadoPagoController {
	
	/*@Value("${TEST-1991860099924339-040218-18f25f380912ed9875437be04a2948a8-37091135}")
	 private String mercadoPagoAccessToken;*/
	
	@Autowired
	private BateriaRepository bateriaRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private PilotoRepository pilotoRepository;
	
	@Autowired
	private ReservaController reservaController;
	

	public PaymentResponseDTO processPayment(CardPaymentDTO cardPaymentDTO) {
		 try {
		      MercadoPagoConfig.setAccessToken("TEST-1991860099924339-040218-18f25f380912ed9875437be04a2948a8-37091135"); /*"TEST-1991860099924339-040218-18f25f380912ed9875437be04a2948a8-37091135"*/

		      PaymentClient paymentClient = new PaymentClient();
		     

		      PaymentCreateRequest paymentCreateRequest =
		          PaymentCreateRequest.builder()
		              .transactionAmount(cardPaymentDTO.getTransactionAmount())
		              .token(cardPaymentDTO.getToken())
		              .description(cardPaymentDTO.getProductDescription())
		              .installments(cardPaymentDTO.getInstallments())
		              .paymentMethodId(cardPaymentDTO.getPaymentMethodId())
		              .payer(
		                  PaymentPayerRequest.builder()
		                   	  .email(cardPaymentDTO.getPayer().getEmail())
		                      .identification(
		                          IdentificationRequest.builder()
		                              .type(cardPaymentDTO.getPayer().getIdentification().getType())
		                              .number(cardPaymentDTO.getPayer().getIdentification().getNumber())
		                              .build())
		                      .build())
		              .build();

		      Payment createdPayment = paymentClient.create(paymentCreateRequest);

		      return new PaymentResponseDTO(
		          createdPayment.getId(),
		          String.valueOf(createdPayment.getStatus()),
		          createdPayment.getStatusDetail()); 
		    } catch (MPApiException apiException) {
		      System.out.println(apiException.getApiResponse().getContent());
		      throw new MercadoPagoException(apiException.getApiResponse().getContent());
		    } catch (MPException exception) {
		      System.out.println(exception.getMessage());
		      throw new MercadoPagoException(exception.getMessage());
		    }
	}
	
	@PostMapping("piloto/process_payment/{id}")
    public ResponseEntity<PaymentResponseDTO>  processPaymentTest(@PathVariable Long id, @RequestBody @Valid CardPaymentDTO cardPaymentDTO, Reserva reserva){
        PaymentResponseDTO payment = processPayment(cardPaymentDTO);
        
        Bateria bateria = bateriaRepository.getById(id);
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Piloto piloto = pilotoRepository.findByEmail(email);
        
        if(payment.getStatus().contentEquals("rejected")) {
  
            reserva.setStatus(StatusPagamento.REJEITADO);
            reserva.setBateria(bateria);
    		reserva.setPiloto(piloto);
    		reserva.setKartodromo(reserva.getBateria().getKartodromo());
    		reserva.setDataReserva(LocalDate.now());
            
            reservaRepository.save(reserva);
            
        }else {
        	   
            reserva.setStatus(StatusPagamento.CONFIRMADO);
            reserva.setBateria(bateria);
    		reserva.setPiloto(piloto);
    		reserva.setKartodromo(reserva.getBateria().getKartodromo());
    		reserva.setDataReserva(LocalDate.now());
    		
    		reservaController.atulizarVagasDisponiveis(reserva);
            
            reservaRepository.save(reserva);	
        }
               
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
	
	@GetMapping("piloto/process_payment/{id}")
	public String formularioPagar(@PathVariable Long id, Model model) {
	
		Bateria bateria = bateriaRepository.getById(id);				
		model.addAttribute("bateria", bateria);
		
		return "piloto/process_payment";
	}
	
}
