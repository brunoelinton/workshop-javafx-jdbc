package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	// M�TODO QUE RETORNA O STAGE, OU SEJA, O PALCO ATUAL A PARTIR DE ONDE O EVENTO FOI ACIONADO
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	// M�TODO QUE TENTA REALIZAR A CONVERS�O DO CONTE�DO DE UM 'TextField' PARA INTEIRO
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
