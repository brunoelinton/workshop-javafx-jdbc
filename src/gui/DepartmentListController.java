package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService service;						// VARIÁVEL PARA RECEBER A INSTANCIA DO SERVIÇO RESPONSÁVEL POR RETORNAR A LISTA DE DEPARTAMENTOS
	
	/* ELEMENTOS DA TELA DE DEPARTAMENTO */
	@FXML
	private TableView<Department> tableViewDepartment;		// TABELA QUE LISTA OS DEPARTAMENTOS
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;	//	COLUNA REFERENTE AO ID DO DEPARTAMENTO
	
	@FXML
	private TableColumn<Department, String> tableColumnName;//	COLUNA REFERENTE AO NOME DO DEPARTAMENTO
	
	@FXML
	private Button btNew;									// BOTÃO PARA INSERIR UM NOVO DEPARTAMENTO
	
	private ObservableList<Department> obsList;				// VARIÁVEL DO JAVAX QUE IRÁ ARMAZENAR A LISTA DE DEPARTAMENTOS
	
	// MÉTODO QUE FAZ UMA INJEÇÃO DE DEPENDÊNCIA INSTANCIANDO O SERVIÇO QUE IRÁ NOS RETORNAR A LISTA DE DEPARTAMENTOS
	public void setDepartmentService(DepartmentService department) {
		this.service = department;
	}
	
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
	
	// MÉTODO QUE ACESSA O SERVIÇO DE DEPARTAMENTO, CARREGAR OS DEPARTAMENTOS E INSERÍ-LOS NA VARIÁVEL DO TIPO 'ObservableList' QUE É UM TIPO COMPATÍVEL COM O JAVAFX 
	public void updateTableView() {
		if(service == null) {	// VERIFICANDO SE O SERVIÇO NÃO FOI INJETADO / INICIADO
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();			// LISTA TEMPORÁRIA PARA ARMAZENAR A LISTA DE DEPARTAMENTOS QUE O SERVIÇO NOS TROUXE
		obsList = FXCollections.observableArrayList(list);	// CARREGANDO A LISTA DE DEPARTAMENTOS NA LISTA COMPATÍVEL COM O JAVAFX
		tableViewDepartment.setItems(obsList);				// ASSOCIANDO A LISTA DE DEPARTAMENTOS À 'TableView' PARA QUE ELA POSSA SER MOSTRADA NA APLICAÇÃO
	}

}
