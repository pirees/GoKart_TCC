package com.goKart.goKart.service;

import com.goKart.goKart.model.Piloto;
import com.goKart.goKart.model.Reserva;
import com.goKart.goKart.repository.PilotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EnviaEmailService {


    private PilotoRepository pilotoRepository;

    private final JavaMailSender javaMailSender;

    public EnviaEmailService(PilotoRepository pilotoRepository, JavaMailSender javaMailSender) {
        this.pilotoRepository = pilotoRepository;
        this.javaMailSender = javaMailSender;
    }

    public void enviarCadastroSucesso(Piloto piloto){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        piloto = pilotoRepository.findByEmail(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Cadastro realizado com sucesso");
        mensagem.setText("Seja bem-vindo(a) ao nosso mundo da velocidade. Qualquer dúvida basta enviar um e-mail para contato@gokart.com.br");
        javaMailSender.send(mensagem);
    }

    public void enviarPagamentoNaoConf(Piloto piloto, Reserva reserva){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        piloto = pilotoRepository.findByEmail(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Pagamento não autorizado");
        mensagem.setText("Olá, sua reserva do número " + reserva.getNrReserva() + "não foi autorizada pelo seu cartão de crédito. Favor tentar novamente" +
                "ou entrar em contato conosco via e-mail contato@gokart.com.br");
        javaMailSender.send(mensagem);
    }

    public void enviarPagamentoConf(Piloto piloto, Reserva reserva){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        piloto = pilotoRepository.findByEmail(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Reserva confirmada com sucesso");
        mensagem.setText("Olá, sua reserva do número " + reserva.getNrReserva() + " foi autorizada com sucesso. " +
                "Atenciosamente" +
                "GoKart");
        javaMailSender.send(mensagem);
    }




}

