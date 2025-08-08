package br.com.fco_romario.services;

import br.com.fco_romario.config.EmailConfig;
import br.com.fco_romario.data.dto.request.EmailRequestDTO;
import br.com.fco_romario.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfig;

    public void sendSimpleEmail(EmailRequestDTO emailRequest) {
        emailSender
            .to(emailRequest.getTo())
            .withSubject(emailRequest.getSubject())
            .withMessage(emailRequest.getBody())
            .send(emailConfig);
    }

    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment) {
        File tempFile = null;

        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);// Converte a string JSON recebida em um objeto EmailRequestDTO
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename()); // Cria um arquivo temporário para armazenar o anexo recebido
            attachment.transferTo(tempFile); // Transfere o conteúdo do anexo recebido para o arquivo temporário

            emailSender
                .to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getBody())
                .attach(tempFile.getAbsolutePath())
                .send(emailConfig);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email requet JSON!",e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment!",e);
        } finally {
            if(tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }

}
