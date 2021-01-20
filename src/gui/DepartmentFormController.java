package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {
	/* COMPONENTES/CONTROLES DA TELA DE CADASTRO DE DEPARTAMENTO */
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label lableErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	/*-----------------------------------------------------------------*/
	
	/* EVENTOS DA TELA */
	
	// EVENTO QUE SALVA UM DEPARTAMENTO
	@FXML
	public void onBtSaveAction() {
		System.out.println("onBtSaveAction");
	}
	
	// EVENTO QUE CANCELA A OPERAÇÃO DE CADASTRO DE UM DEPARTAMENTO
	@FXML
	public void onBtCancelAction() {
		System.out.println("onBtCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	// INICIALIZANDO OS CONTROLES DA TELA E COLOCANDO RESTRIÇÕES DE PREENCHIMENTO
	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);				// SOMENTE INSERÇÃO DE NÚMEROS INTEIROS
		Constraints.setTextFieldMaxLength(txtName, 30);		// QUANTIDADE MÁXIMA DE CARACTERES NO CAMPO NOME = 30
	}
}
