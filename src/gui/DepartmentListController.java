package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener {
	// DEPENDÊNCIA DO TIPO SERVIÇO
	private DepartmentService service;						// VARIÁVEL PARA RECEBER A INSTANCIA DO SERVIÇO RESPONSÁVEL POR RETORNAR A LISTA DE DEPARTAMENTOS
	
	/* ELEMENTOS DA TELA DE DEPARTAMENTO */
	@FXML
	private TableView<Department> tableViewDepartment;			// TABELA QUE LISTA OS DEPARTAMENTOS
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;		//	COLUNA REFERENTE AO ID DO DEPARTAMENTO
	
	@FXML
	private TableColumn<Department, String> tableColumnName;	//	COLUNA REFERENTE AO NOME DO DEPARTAMENTO
	
	@FXML
	private TableColumn<Department, Department> tableColumnEDIT;//	COLUNA REFERENTE AO NOME DO DEPARTAMENTO
	@FXML
	private Button btNew;										// BOTÃO PARA INSERIR UM NOVO DEPARTAMENTO
	
	private ObservableList<Department> obsList;					// VARIÁVEL DO JAVAX QUE IRÁ ARMAZENAR A LISTA DE DEPARTAMENTOS
	
	// MÉTODO QUE FAZ UMA INJEÇÃO DE DEPENDÊNCIA INSTANCIANDO O SERVIÇO QUE IRÁ NOS RETORNAR A LISTA DE DEPARTAMENTOS
	public void setDepartmentService(DepartmentService department) {
		this.service = department;
	}
	
	// MÉTODO PARA O EVENTO DO BOTÃO DE ADICIONAR UM NOVO DEPARTAMENTO
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);				// OBTENDO O PALCO (STAGE) PAI DE ONDE O EVENTO FOI ACIONADO
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);	// MOSTRANDO A JANELA DE INSERÇÃO DE UM NOVO DEPARTAMENTO
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
		initEditButtons();									// EXIBINDO O BOTÃO DE EDIÇÃO
	}
	
	//
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();	// CARREGANDO O CONTROLLER DA TELA DE CADASTRO DE DEPARTAMENTO
			controller.setDepartment(obj);									// INJETANDO O DEPARTAMENTO NO CONTROLADOR
			controller.setDepartmentService(new DepartmentService());		// INJETANDO O SERVIÇO DE DEPARTAMENTO NO CONTROLADOR
			controller.updateFormData();									// CARREGANDO DADOS DO OBJETO DEPARTAMENTO NO FORMULÁRIO
			controller.subscribeDataChangeListener(this);					// SE INSCREVENDO PARA O EVENTO DE INSERIR/ATUALIZAR UM DEPARTAMENTO
			Stage dialogStage = new Stage();				// CRIANDO UM NOVO PALCO PARA EXIBIR A JANELA DE INSERÇÃO DE UM NOVO DEPARTAMENTO
			dialogStage.setTitle("Enter Department data");	// CONFIGURANDO O TÍTULO DA JANELA
			dialogStage.setScene(new Scene(pane));			// CARREGANDO A VIEW (TELA DE INSERÇÃO DE DEPARTAMENTO)
			dialogStage.setResizable(false);				// IMPEDINDO QUE A TELA SEJA REDIMENSIONÁVEL
			dialogStage.initOwner(parentStage);				// INFORMANDO QUEM É O PAI DA JANELA QUE SERÁ EXIBIDA
			dialogStage.initModality(Modality.WINDOW_MODAL);// ESPECIFICNADO QUE A JANELA SERÁ DO TIPO MODAL, OU SEJA, OUTRA JANELA NÃO SERÁ ACESSÍVEL ENQUANTO ESSA NÃO FOR FECHADA
			dialogStage.showAndWait();						// EXIBINDO A TELA DE CADASTRO
		} catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	// MÉTODO QUE ESCUTA A CLASSE 'DEPARTMENTFORMCONTROLLER' ESPERANDO PELO ACIONAMENTO DO EVENTO
	@Override
	public void onDataChanged() {
		updateTableView();	// ATUALIZANDO A TABELA DE DEPARTAMENTOS
		
	}
	
	// MÉTODO QUE ADICIONA O BOTÃO DE ATUALIZAR O DEPARTAMENTO PARA CADA APARTAMENTO DO BANCO DE DADOS
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}
}
