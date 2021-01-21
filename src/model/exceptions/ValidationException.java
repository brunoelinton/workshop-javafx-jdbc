package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;	// N�MERO DE VERS�O PADR�O
	
	private Map<String, String> errors = new HashMap<>();// COLE��O PARA MAPEAR AS MENSAGENS DE ERROS DE CADA CAMPO DO FORMUL�RIO DE CADASTRO DE DEPARTAMENTO
	// CONSTRUTOR - FOR�A A INSTANCIA��O DA EXCE��O
	public ValidationException(String msg) {
		super(msg);
	}
	
	// M�TODO PARA OBTER A COLE�AO DE ERROS
	public Map<String, String> getErrors(){
		return errors;
	}
	
	// M�TODO PARA ADICIONAR UM ERRO � COLE��O
	public void addError(String fieldName, String erroMessage) {
		errors.put(fieldName, erroMessage);
	}
}
