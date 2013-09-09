package com.prime.app.agvirtual.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.prime.app.agvirtual.service.MailService;
import com.prime.infra.validator.EmailValidator;
import com.prime.infra.validator.Validator;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    final private static String EMAIL_SUPORTE = "teste@prime.com.br";
    final private static String EMAIL_SUBJECT = "Solicita��o de Suporte";

    @Autowired(required = false)
    private JavaMailSender mailSender;

    private Validator emailValidator = new EmailValidator();

    public void sendSuporteEmail(String fromEmail, String telefone, String conteudo) {
        if (!emailValidator.isValid(fromEmail)) {
            LOGGER.error("Endere�o de email [{}] inv�lido.", fromEmail);
            return;
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(EMAIL_SUPORTE);
        msg.setSubject(EMAIL_SUBJECT);
        msg.setText(conteudo + "\nTelefone de contato do cliente.:" + telefone);

        LOGGER.debug("Enviando email de {} para {}", EMAIL_SUPORTE, fromEmail);
        mailSender.send(msg);
    }

}
