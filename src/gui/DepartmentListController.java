package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener {
	
	private DepartmentService service;						// VARI�VEL PARA RECEBER A INSTANCIA DO SERVI�O RESPONS�VEL POR RETORNAR A LISTA DE DEPARTAMENTOS
	
	/* ELEMENTOS DA TELA DE DEPARTAMENTO */
	@FXML
	private TableView<Department> tableViewDepartment;		// TABELA QUE LISTA OS DEPARTAMENTOS
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;	//	COLUNA REFERENTE AO ID DO DEPARTAMENTO
	
	@FXML
	private TableColumn<Department, String> tableColumnName;//	COLUNA REFERENTE AO NOME DO DEPARTAMENTO
	
	@FXML
	private Button btNew;									// BOT�O PARA INSERIR UM NOVO DEPARTAMENTO
	
	private ObservableList<Department> obsList;				// VARI�VEL DO JAVAX QUE IR� ARMAZENAR A LISTA DE DEPARTAMENTOS
	
	// M�TODO QUE FAZ UMA INJE��O DE DEPEND�NCIA INSTANCIANDO O SERVI�O QUE IR� NOS RETORNAR A LISTA DE DEPARTAMENTOS
	public void setDepartmentService(DepartmentService department) {
		this.service = department;
	}
	
	// M�TODO PARA O EVENTO DO BOT�O DE ADICIONAR UM NOVO DEPARTAMENTO
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);				// OBTENDO O PALCO (STAGE) PAI DE ONDE O EVENTO FOI ACIONADO
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);	// MOSTRANDO A JANELA DE INSER��O DE UM NOVO DEPARTAMENTO
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
	
	// M�TODO QUE ACESSA O SERVI�O DE DEPARTAMENTO, CARREGAR OS DEPARTAMENTOS E INSER�-LOS NA VARI�VEL DO TIPO 'ObservableList' QUE � UM TIPO COMPAT�VEL COM O JAVAFX 
	public void updateTableView() {
		if(service == null) {	// VERIFICANDO SE O SERVI�O N�O FOI INJETADO / INICIADO
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll();			// LISTA TEMPOR�RIA PARA ARMAZENAR A LISTA DE DEPARTAMENTOS QUE O SERVI�O NOS TROUXE
		obsList = FXCollections.observableArrayList(list);	// CARREGANDO A LISTA DE DEPARTAMENTOS NA LISTA COMPAT�VEL COM O JAVAFX
		tableViewDepartment.setItems(obsList);				// ASSOCIANDO A LISTA DE DEPARTAMENTOS � 'TableView' PARA QUE ELA POSSA SER MOSTRADA NA APLICA��O
	}
	
	//
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();	// CARREGANDO O CONTROLLER DA TELA DE CADASTRO DE DEPARTAMENTO
			controller.setDepartment(obj);									// INJETANDO O DEPARTAMENTO NO CONTROLADOR
			controller.setDepartmentService(new DepartmentService());		// INJETANDO O SERVI�O DE DEPARTAMENTO NO CONTROLADOR
			controller.updateFormData();									// CARREGANDO DADOS DO OBJETO DEPARTAMENTO NO FORMUL�RIO
			controller.subscribeDataChangeListener(this);					// SE INSCREVENDO PARA O EVENTO DE INSERIR/ATUALIZAR UM DEPARTAMENTO
			Stage dialogStage = new Stage();				// CRIANDO UM NOVO PALCO PARA EXIBIR A JANELA DE INSER��O DE UM NOVO DEPARTAMENTO
			dialogStage.setTitle("Enter Department data");	// CONFIGURANDO O T�TULO DA JANELA
			dialogStage.setScene(new Scene(pane));			// CARREGANDO A VIEW (TELA DE INSER��O DE DEPARTAMENTO)
			dialogStage.setResizable(false);				// IMPEDINDO QUE A TELA SEJA REDIMENSION�VEL
			dialogStage.initOwner(parentStage);				// INFORMANDO QUEM � O PAI DA JANELA QUE SER� EXIBIDA
			dialogStage.initModality(Modality.WINDOW_MODAL);// ESPECIFICNADO QUE A JANELA SER� DO TIPO MODAL, OU SEJA, OUTRA JANELA N�O SER� ACESS�VEL ENQUANTO ESSA N�O FOR FECHADA
			dialogStage.showAndWait();						// EXIBINDO A TELA DE CADASTRO
		} catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	// M�TODO QUE ESCUTA A CLASSE 'DEPARTMENTFORMCONTROLLER' ESPERANDO PELO ACIONAMENTO DO EVENTO
	@Override
	public void onDataChanged() {
		updateTableView();	// ATUALIZANDO A TABELA DE DEPARTAMENTOS
		
	}
}
