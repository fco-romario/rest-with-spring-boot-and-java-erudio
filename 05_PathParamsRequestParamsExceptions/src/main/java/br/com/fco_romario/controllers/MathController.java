package br.com.fco_romario.controllers;

import br.com.fco_romario.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    //@PathVariable : Path Parameters é usado quando o parametro é obrigatório. é necesário inserir o "value" -> http://localhost:8080/api/sum/1/2
    //@RequestParam : Query Parameters é usado quando o parametro não é obrigatório -> localhost:8080/api/greeting?name=romario

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable() String  numberOne,
            @PathVariable() String  numberTwo
    ) throws IllegalArgumentException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable() String  numberOne,
            @PathVariable() String  numberTwo
    ) throws IllegalArgumentException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable() String  numberOne,
            @PathVariable() String  numberTwo
    ) throws IllegalArgumentException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable() String  numberOne,
            @PathVariable() String  numberTwo
    ) throws IllegalArgumentException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable() String  numberOne,
            @PathVariable() String  numberTwo
    ) throws IllegalArgumentException {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    @RequestMapping("/square-root/{number}")
    public Double squareRoot(
            @PathVariable() String  number
    ) throws IllegalArgumentException {
        if (!isNumeric(number))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return Math.sqrt(convertToDouble(number));
    }

    private boolean isNumeric(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", "."); //R$ 4,00 USD 5.00
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    private Double convertToDouble(String strNumber) throws IllegalArgumentException {
        if(strNumber == null || strNumber.isEmpty())
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        String number = strNumber.replace(",", "."); //R$ 4,00 USD 5.00
        return Double.parseDouble(number);
    }

}
