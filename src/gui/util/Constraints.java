package gui.util;

import javafx.scene.control.TextField;

public class Constraints {
	// RESTRI��O PARA INSERIR SOMENTE N�MEOS DO TIPO INTEIRO
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\d*")) {
	        	txt.setText(oldValue);
	        }
	    });
	}
	
	// RESTRI��O PARA INSERIR SOMENTE N�MEROS DO TIPO DOUBLE
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
		    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(oldValue);
                }
		    });
	}
	
	// RESTRI��O PARA LIMITAR A QUANTIDADE M�XIMA DE D�GITOS INSERIDOS
	public static void setTextFieldMaxLength(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && newValue.length() > max) {
	        	txt.setText(oldValue);
	        }
	    });
	}
	
	
}
