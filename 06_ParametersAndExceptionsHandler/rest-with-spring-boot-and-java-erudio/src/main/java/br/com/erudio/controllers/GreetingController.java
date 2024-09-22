package br.com.erudio.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.converters.numberConverter;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.math.SimpleMath;

@RestController
public class GreetingController {
		
	private SimpleMath math = new SimpleMath();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
		) throws Exception {
		
		if(!numberConverter.isNumeric(numberOne) ||!numberConverter.isNumeric(numberTwo)) {
			throw  new UnsupportedMathOperationException("Please set an umeric value!");
		}
		return math.sum(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double subtraction(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!numberConverter.isNumeric(numberOne) ||!numberConverter.isNumeric(numberTwo)) {
			throw  new UnsupportedMathOperationException("Please set an umeric value!");
		}
		return  math.subtraction(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double multiplication(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!numberConverter.isNumeric(numberOne) ||!numberConverter.isNumeric(numberTwo)) {
			throw  new UnsupportedMathOperationException("Please set an umeric value!");
		}
		return math.multiplication(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/division/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double division(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!numberConverter.isNumeric(numberOne) ||!numberConverter.isNumeric(numberTwo)) {
			throw  new UnsupportedMathOperationException("Please set an umeric value!");
		}
		return math.division(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/mean/{numberOne}/{numberTwo}",method = RequestMethod.GET)
	public Double mean(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!numberConverter.isNumeric(numberOne) ||!numberConverter.isNumeric(numberTwo)) {
			throw  new UnsupportedMathOperationException("Please set an umeric value!");
		}
		return math.mean(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/square-root/{number}",method = RequestMethod.GET)
	public Double squareRoot(
			@PathVariable(value = "number") String number
			) throws Exception {
		
		if(!numberConverter.isNumeric(number)) {
			throw  new UnsupportedMathOperationException("Please set an umeric value!");
		} 
		return math.squareRoot(numberConverter.convertToDouble(number));
	}

}
