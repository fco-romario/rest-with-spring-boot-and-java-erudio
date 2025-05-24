package br.com.fco_romario.exception;

import java.util.Date;

public record ExceptionResponse(
        Date timestamp,
        String message,
        String details
    ) {
}

/*
O que é um Record?
Um Record é uma maneira simplificada de criar classes que são basicamente "portadoras de dados" (data carriers). Ele automaticamente gera:
   * Campos final privados
   * Um construtor público
   * Métodos equals(), hashCode() e toString()
   * Métodos getters (mas com nome igual ao campo, sem o prefixo "get")

Características Principais
   *Imutabilidade: Todos os campos são final por padrão
   *Concisão: Muito menos código boilerplate
   *Transparência: Foco nos dados que a classe carrega
   *Não pode ser estendido: Records são implicitamente final
   *Não pode herdar de outras classes: Só podem implementar interfaces

*/

