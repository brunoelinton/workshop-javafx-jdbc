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
	private DepartmentService service = new DepartmentService();
	
	/* ELEMENTOS DA TELA DE DEPARTAMENTO */
	@FXML
	private TableView<Department> tableViewDepartment;			// TABELA QUE LISTA OS DEPARTAMENTOS
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;		//	COLUNA REFERENTE AO ID DO DEPARTAMENTO
	
	@FXML
	private TableColumn<Department, String> tableColumnName;	//	COLUNA REFERENTE AO NOME DO DEPARTAMENTO
	
	@FXML
	private Button btNew;										// BOT�O PARA INSERIR UM NOVO DEPARTAMENTO
	
	private ObservableList<Department> obsList;
	
	public void setDepartmentService(DepartmentService department) {
		this.service = department;
	}
	
	// M�TODO PARA O EVENTO DO BOT�O DE ADICIONAR UM NOVO DEPARTAMENTO
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();	// INCILIZANDO AS COLUNAS DA TABELA
		
	}

	// M�TODO QUE INICIALIZA AS COLUNAS DA TABELA
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		// AJUSTANDO A ALTURA DA TABELA � ALTURA DA JANELA
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}

}
