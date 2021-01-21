package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;	// NÚMERO DE VERSÃO PADRÃO
	
	private Map<String, String> errors = new HashMap<>();// COLEÇÃO PARA MAPEAR AS MENSAGENS DE ERROS DE CADA CAMPO DO FORMULÁRIO DE CADASTRO DE DEPARTAMENTO
	// CONSTRUTOR - FORÇA A INSTANCIAÇÃO DA EXCEÇÃO
	public ValidationException(String msg) {
		super(msg);
	}
	
	// MÉTODO PARA OBTER A COLEÇAO DE ERROS
	public Map<String, String> getErrors(){
		return errors;
	}
	
	// MÉTODO PARA ADICIONAR UM ERRO À COLEÇÃO
	public void addError(String fieldName, String erroMessage) {
		errors.put(fieldName, erroMessage);
	}
}
