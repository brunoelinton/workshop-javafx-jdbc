package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	// MÉTODO QUE RETORNA O STAGE, OU SEJA, O PALCO ATUAL A PARTIR DE ONDE O EVENTO FOI ACIONADO
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	// MÉTODO QUE TENTA REALIZAR A CONVERSÃO DO CONTEÚDO DE UM 'TextField' PARA INTEIRO
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
