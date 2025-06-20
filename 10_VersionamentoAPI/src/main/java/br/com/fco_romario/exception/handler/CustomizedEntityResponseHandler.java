package br.com.fco_romario.exception.handler;

import br.com.fco_romario.exception.ExceptionResponse;
import br.com.fco_romario.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice //-> usamos sempre que precisamos usar um tratamento que será espalhado para todos os controllers
@RestController
public class CustomizedEntityResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}


/*
	Use @RestControllerAdvice quando você deseja uma maneira simples e prática de tratar exceções em APIs REST, com respostas automaticamente serializadas em JSON.
	Use @ControllerAdvice + @RestController se você quiser mais controle sobre como tratar exceções globalmente e como definir os controladores, embora na prática seja menos comum, já que @RestControllerAdvice já simplifica esse fluxo.
 */