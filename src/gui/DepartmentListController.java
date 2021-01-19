package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import model.entities.Department;

public class DepartmentListController implements Initializable {
	/* ELEMENTOS DA TELA DE DEPARTAMENTO */
	@FXML
	private TableView<Department> tableViewDepartment;			// TABELA QUE LISTA OS DEPARTAMENTOS
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;		//	COLUNA REFERENTE AO ID DO DEPARTAMENTO
	
	@FXML
	private TableColumn<Department, String> tableColumnName;	//	COLUNA REFERENTE AO NOME DO DEPARTAMENTO
	
	@FXML
	private Button btNew;										// BOTÃO PARA INSERIR UM NOVO DEPARTAMENTO
	
	// MÉTODO PARA O EVENTO DO BOTÃO DE ADICIONAR UM NOVO DEPARTAMENTO
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();	// INCILIZANDO AS COLUNAS DA TABELA
		
	}

	// MÉTODO QUE INICIALIZA AS COLUNAS DA TABELA
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		// AJUSTANDO A ALTURA DA TABELA À ALTURA DA JANELA
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

}
