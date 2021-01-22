package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {
	// DEPENDÊNCIA DO TIPO SERVIÇO
	private SellerService service;						// VARIÁVEL PARA RECEBER A INSTANCIA DO SERVIÇO RESPONSÁVEL POR RETORNAR A LISTA DE VENDEDORS
	
	/* ELEMENTOS DA TELA DE VENDEDOR */
	@FXML
	private TableView<Seller> tableViewSeller;					// TABELA QUE LISTA OS VENDEDORS
	
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;			//	COLUNA REFERENTE AO ID DO VENDEDOR
	
	@FXML
	private TableColumn<Seller, String> tableColumnName;		//	COLUNA REFERENTE AO NOME DO VENDEDOR
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;		//	COLUNA REFERENTE AO NOME DO VENDEDOR
	
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;	//	COLUNA REFERENTE AO NOME DO VENDEDOR
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;	//	COLUNA REFERENTE AO NOME DO VENDEDOR
	
	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;//	COLUNA REFERENTE PARA EXIBIR OS BOTÕES DE ATUALIZAÇÃO

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;// COLUNA REFERENTE PARA EXIBIR OS BOTÕES DE EXCLUSÃO
	
	@FXML
	private Button btNew;										// BOTÃO PARA INSERIR UM NOVO VENDEDOR
	
	private ObservableList<Seller> obsList;					// VARIÁVEL DO JAVAX QUE IRÁ ARMAZENAR A LISTA DE VENDEDORS
	
	// MÉTODO QUE FAZ UMA INJEÇÃO DE DEPENDÊNCIA INSTANCIANDO O SERVIÇO QUE IRÁ NOS RETORNAR A LISTA DE VENDEDORS
	public void setSellerService(SellerService department) {
		this.service = department;
	}
	
	// MÉTODO PARA O EVENTO DO BOTÃO DE ADICIONAR UM NOVO VENDEDOR
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);				// OBTENDO O PALCO (STAGE) PAI DE ONDE O EVENTO FOI ACIONADO
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);	// MOSTRANDO A JANELA DE INSERÇÃO DE UM NOVO VENDEDOR
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();	// INCILIZANDO AS COLUNAS DA TABELA
		
	}

	// MÉTODO QUE INICIALIZA AS COLUNAS DA TABELA
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		
		// FORMATANDO A DATA E O SALÁRIO
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		
		// AJUSTANDO A ALTURA DA TABELA À ALTURA DA JANELA
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}
	
	// MÉTODO QUE ACESSA O SERVIÇO DE VENDEDOR, CARREGAR OS VENDEDORS E INSERÍ-LOS NA VARIÁVEL DO TIPO 'ObservableList' QUE É UM TIPO COMPATÍVEL COM O JAVAFX 
	public void updateTableView() {
		if(service == null) {	// VERIFICANDO SE O SERVIÇO NÃO FOI INJETADO / INICIADO
			throw new IllegalStateException("Service was null");
		}
		List<Seller> list = service.findAll();			// LISTA TEMPORÁRIA PARA ARMAZENAR A LISTA DE VENDEDORS QUE O SERVIÇO NOS TROUXE
		obsList = FXCollections.observableArrayList(list);	// CARREGANDO A LISTA DE VENDEDORS NA LISTA COMPATÍVEL COM O JAVAFX
		tableViewSeller.setItems(obsList);				// ASSOCIANDO A LISTA DE VENDEDORS À 'TableView' PARA QUE ELA POSSA SER MOSTRADA NA APLICAÇÃO
		initEditButtons();									// EXIBINDO O BOTÃO DE EDIÇÃO
		initRemoveButtons();								// EXIBINDO O BOTÃO DE REMOÇÃO
	}
	
	//
	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			SellerFormController controller = loader.getController();				// CARREGANDO O CONTROLLER DA TELA DE CADASTRO DE VENDEDOR
			
			controller.setSeller(obj);												// INJETANDO O VENDEDOR NO CONTROLADOR
			controller.setServices(new SellerService(), new DepartmentService());	// INJETANDO OS SERVIÇSO DE VENDEDOR E DEPARTAMENTO NO CONTROLADOR
			controller.loadAssociatedObjects();										// CARREGANDO OS DEPARTAMENTOS
			controller.updateFormData();											// CARREGANDO DADOS DO OBJETO VENDEDOR NO FORMULÁRIO
			controller.subscribeDataChangeListener(this);							// SE INSCREVENDO PARA O EVENTO DE INSERIR/ATUALIZAR UM VENDEDOR
			Stage dialogStage = new Stage();										// CRIANDO UM NOVO PALCO PARA EXIBIR A JANELA DE INSERÇÃO DE UM NOVO VENDEDOR
			dialogStage.setTitle("Enter Seller data");								// CONFIGURANDO O TÍTULO DA JANELA
			dialogStage.setScene(new Scene(pane));									// CARREGANDO A VIEW (TELA DE INSERÇÃO DE VENDEDOR)
			dialogStage.setResizable(false);										// IMPEDINDO QUE A TELA SEJA REDIMENSIONÁVEL
			dialogStage.initOwner(parentStage);										// INFORMANDO QUEM É O PAI DA JANELA QUE SERÁ EXIBIDA
			dialogStage.initModality(Modality.WINDOW_MODAL);						// ESPECIFICNADO QUE A JANELA SERÁ DO TIPO MODAL, OU SEJA, OUTRA JANELA NÃO SERÁ ACESSÍVEL ENQUANTO ESSA NÃO FOR FECHADA
			dialogStage.showAndWait();												// EXIBINDO A TELA DE CADASTRO
		} catch(IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	// MÉTODO QUE ESCUTA A CLASSE 'DEPARTMENTFORMCONTROLLER' ESPERANDO PELO ACIONAMENTO DO EVENTO
	@Override
	public void onDataChanged() {
		updateTableView();	// ATUALIZANDO A TABELA DE VENDEDORS
		
	}
	
	// MÉTODO QUE ADICIONA O BOTÃO DE ATUALIZAR O VENDEDOR PARA CADA APARTAMENTO DO BANCO DE DADOS
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
			}
		});
	}
	
	// MÉTODO QUE ADICIONA O BOTÃO DE REMOVER VENDEDOR
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	// MÉTODO PARA REMOÇÃO DO VENDEDOR
	private void removeEntity(Seller obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure delete?");
		
		if(result.get() == ButtonType.OK) {	// VERIFICANDO A CONFIRMAÇÃO DE REMOÇÃO
			if(service == null) {			// VERIFICANDO SE O SERVIÇO FOI INJETADO
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);		// REMOVENDO O DEPARTAMEMTO
				updateTableView();			// ATUALIZANDO A TELA DE EXIBIÇÃO
			} catch(DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
			
		}
	}
}
