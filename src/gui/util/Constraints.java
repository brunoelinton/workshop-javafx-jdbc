package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	// RESTRIÇÃO PARA INSERIR SOMENTE NÚMEOS DO TIPO INTEIRO
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\d*")) {
	        	txt.setText(oldValue);
	        }
	    });
	}
	
	// RESTRIÇÃO PARA INSERIR SOMENTE NÚMEROS DO TIPO DOUBLE
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
		    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(oldValue);
                }
		    });
	}
	
	// RESTRIÇÃO PARA LIMITAR A QUANTIDADE MÁXIMA DE DÍGITOS INSERIDOS
	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && newValue.length() > max) {
	        	txt.setText(oldValue);
	        }
	    });
	}
	
	
}
