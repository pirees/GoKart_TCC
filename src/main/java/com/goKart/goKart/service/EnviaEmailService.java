package com.goKart.goKart.service;

import com.goKart.goKart.model.*;
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

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Cadastro realizado com sucesso | GoKart");
        mensagem.setText("Seja bem-vindo(a) ao nosso mundo da velocidade."
                        + "\n"
                        + "\n"
                        + "Qualquer dúvida basta enviar um e-mail para contato@gokart.com.br"
                        + "\n"
                        + "\n"
                        + "Atenciosamente,"
                        + "\n"
                        + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarPagamentoNaoConf(Piloto piloto, Reserva reserva){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        piloto = pilotoRepository.findByEmail(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Pagamento não autorizado | GoKart");
        mensagem.setText("Olá, sua reserva do número " + reserva.getId() + "não foi autorizada pelo seu cartão de crédito."
                        + "\n"
                        + "\n"
                        + "Favor tentar novamente ou entrar em contato conosco via e-mail contato@gokart.com.br"
                        + "\n"
                        + "\n"
                        + "Atenciosamente,"
                        + "\n"
                        + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarPagamentoConfiPiloto(Piloto piloto, Reserva reserva){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        piloto = pilotoRepository.findByEmail(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Reserva confirmada com sucesso | GoKart");
        mensagem.setText("Olá, sua reserva do número " + reserva.getId() + " foi autorizada com sucesso."
                        + "\n"
                        + "\n"
                        + "Atenciosamente,"
                        + "\n"
                        + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarReservaCanceladaPiloto(Piloto piloto, Reserva reserva) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        piloto = pilotoRepository.findByEmail(email);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(piloto.getEmail());
        mensagem.setSubject("Reserva cancelada com sucesso | GoKart");
        mensagem.setText("Olá, sua reserva do número " + reserva.getId() + " foi cancelada com sucesso."
                + "\n"
                + "\n"
                + "Atenciosamente,"
                + "\n"
                + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarReservaConfiKartodromo(Kartodromo kartodromo, Reserva reserva, Bateria bateria) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(bateria.getKartodromo().getEmail());
        mensagem.setSubject("Nova reserva realizada | GoKart");
        mensagem.setText("Olá, uma nova reserva foi confirmada em nosso sistema."
                + "\n"
                + "\n"
                + "Bateria: " + bateria.getId()
                + "\n"
                + "Horário: " + bateria.getHoraBateria()
                + "\n"
                + "Nome Piloto: " + reserva.getPiloto().getNome() + " " + reserva.getPiloto().getSobrenome()
                + "\n"
                + "Valor: " + bateria.getValorBateria()
                + "\n"
                + "Número de vaga(s): " + reserva.getNrReserva()
                + "\n"
                + "Método de pagamento: " + "Cartão de crédito."
                + "\n"
                + "\n"
                + "Atenciosamente,"
                + "\n"
                + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarcadastroKartodromoPendente(Kartodromo kartodromo) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(kartodromo.getEmail());
        mensagem.setSubject("Cadastro pendente | GoKart");
        mensagem.setText("Olá, seu cadastro está pendente de autorização do nosso time."
                + "\n"
                + "\n"
                + "Qualquer dúvida, pode nos enviar e-mail pelo contato@gokart.com"
                + "\n"
                + "\n"
                + "Atenciosamente,"
                + "\n"
                + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarcadastroKartodromoAprovado(Kartodromo kartodromo) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(kartodromo.getEmail());
        mensagem.setSubject("Redefinição de senha | GoKart");
        mensagem.setText("Olá, seu cadastro foi aprovado pelo nosso time."
                + "\n"
                + "\n"
                + "Qualquer dúvida, pode nos enviar e-mail pelo contato@gokart.com"
                + "\n"
                + "\n"
                + "Atenciosamente,"
                + "\n"
                + "GoKart");

        javaMailSender.send(mensagem);
    }

    public void enviarUsuarioRedefinirSenha(Usuario usuario) {

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(usuario.getEmail());
        mensagem.setSubject("Cadastro aprovado | GoKart");
        mensagem.setText("Olá, segue o link para a sua nova senha."
                + "\n"
                + "\n"
                + "https://localhost:8443/novaSenha"
                + "\n"
                + "\n"
                + "Atenciosamente,"
                + "\n"
                + "GoKart");

        javaMailSender.send(mensagem);
    }



}

