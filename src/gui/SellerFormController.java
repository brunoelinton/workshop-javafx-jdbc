package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

public class SellerFormController implements Initializable {
	// DEPENDÊNCIA DO TIPO 'VENDEDOR', SEU OBJETIVO É SERVIR DE OBJETO PARA POPULAR OS CAMPOS DE UM VENDEDOR
	private Seller entity;
	
	// DEPENDÊNCIA DO TIPO 'SERVIÇO DE VENDEDOR', SEU OBJETIVO É INSERIR OU ATUALIZAR UM VENDEDOR
	private SellerService service;
	
	// DEPENDÊNCIA DO TIPO 'SERVIÇO DE DEPARTAMENTO', SEU OBJETIVO É INSERIR CARREGAR OS DEPARTAMENTOS
	private DepartmentService departmentService;
	
	// LISTA DE OBJETOS INTERESSADOS EM RECEBER O EVENTO
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	/* COMPONENTES/CONTROLES DA TELA DE CADASTRO DE VENDEDOR */
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBithDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private ComboBox<Department> comboBoxDepartment;
	
	private ObservableList<Department> obsList;
	
	/*-----------------------------------------------------------------*/
	
	/* EVENTOS DA TELA */
	
	// EVENTO QUE SALVA UM VENDEDOR
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {			// VERIFICANDO SE O VENDEDOR NÃO FOI INJETADO			
			throw new IllegalStateException("Entity was null");
		}
		
		if(service == null) {			// VERIFICANDO SE O SERVIÇO DE VENDEDOR NÃO FOI INJETADO
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();				// OBTENDO OS DADOS DO FORMULÁRIO DE CADASTRO
			service.saveOrUpdate(entity);		// SALVANDO OU ATUALIZANDO O DEPARTMENTO NO BANCO DE DADOS
			Utils.currentStage(event).close();	// FECHANDO A JANELA DE CADASTRO APÓS A OPERAÇÃO
			notifyDataChangeListeners();		// NOTIFICANDO OS OBJETOS QUE O EVENTO FOI CONCLUÍDO
		} catch(DbException e) {
			Alerts.showAlert("Error saving objetc", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	// MÉTODO QUE NOTIFICA OS OBJETOS QUE O EVENTO OCORREU
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener: dataChangeListeners)
			listener.onDataChanged();
		
	}

	// MÉTODO QUE PEGA OS DADOS DO FORMULÁRIO DE CADASTRO DE VENDEDOR NO MOMENTO DE SAVÁ-LO NO BANCO DE DADOS
	private Seller getFormData() {
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		// VERIFICANDO SE O CAMPO NOME ESTÁ VAZIO
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		// SE HOUVER PELO MENOS UM ERRRO NA MINHA COLEÇÃO, ENTÃO SERÁ LANÇADA A EXCEÇÃO
		if(exception.getErrors().size() > 0)
			throw exception;
		return obj;
	}

	// EVENTO QUE CANCELA A OPERAÇÃO DE CADASTRO DE UM VENDEDOR
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();	// FECHANDO A JANELA DE CADASTRO APÓS A OPERAÇÃO
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	// INICIALIZANDO OS CONTROLES DA TELA E COLOCANDO RESTRIÇÕES DE PREENCHIMENTO
	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);				// SOMENTE INSERÇÃO DE NÚMEROS INTEIROS
		Constraints.setTextFieldMaxLength(txtName, 70);		// QUANTIDADE MÁXIMA DE CARACTERES NO CAMPO NOME = 70
		Constraints.setTextFieldDouble(txtBaseSalary);		// SEMENTE INSERÇÃOD DE NÚMERO DOUBLE
		Constraints.setTextFieldMaxLength(txtEmail, 60);	// QUANTIDADE MÁXIMA DE CARACTERES NO CAMPO EMAIL = 60
		Utils.formatDatePicker(dpBithDate, "dd/MM/yyyy");	// FORMATANDO A EXIBIÇÃO DO CAMPO DA DATA DE NASCIMENTO
		initializeComboBoxDepartment();						// INICIALIZANDO O COMBOBOX
	}
	
	// MÉTODO QUE REALIZA UMA INJEÇÃO DE DEPÊNDÊNCIA INSTANCIANDO UM OBJETO DO TIPO DEPARTMENT
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	// MÉTODO QUE REALIZA UMA INJEÇÃO DE DEPÊNCIA INSTANCIANDO UM OBJETO DO TIPO 'SERVIÇO DE VENDEDOR' E OUTRO OBJETO DO TIPO 'SERVIÇO DE DEPARTAMENTO'
	public void setServices(SellerService service, DepartmentService departmentService) {
		this.service = service;
		this.departmentService = departmentService;
	}
	
	// MÉTODO QUE ADICIONA OS OBJETOS INTERESSADOS NO EVENTO À LISTA
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	// MÉTODO QUE POPULA OS CAMPOS DO FORMULÁRIO COM OS DADOS DO OBJETO VENDEDOR
	public void updateFormData() {
		Locale.setDefault(Locale.US);
		if(entity == null)
			throw new IllegalStateException("Entity was null");
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if(entity.getBirthDate() != null)
			dpBithDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		
		// SE UM NOVO VENDEDOR ESTIVER SENDO CADASTRADO
		if(entity.getDepartment() == null)
			comboBoxDepartment.getSelectionModel().selectFirst();
		else
		comboBoxDepartment.setValue(entity.getDepartment());
	}
	
	// MÉTODO QUE CARREGA O DEPARTAMENTO ASSOCIADO AO VENDEDOR
	public void loadAssociatedObjects() {
		if(departmentService == null)
			throw new IllegalStateException("DepartmentService was null");
		List<Department> list = departmentService.findAll();	// CARREGANDO TODOS OS DEPARTAMENTOS DO BANCO E SALVANDO EM UMA LISTA
		obsList = FXCollections.observableArrayList(list);		// INSERINDO A LISTA DE DEPARTAMENTOS EM UMA COLEÇÃO COMPATÍVEL COM O JAVAFX
		comboBoxDepartment.setItems(obsList);					// INSERINDO OS DEPARTAMENTOS NO COMBOBOX DA JANELA
	}
	
	// MÉTODO QUE MOSTRA O ERRO NA TELA DA APLICAÇÃO
	public void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();	// DEFININDO UM CONJUNTO COM O CAMPO DE NOME DOS ERROS
		
		if(fields.contains("name"))				// VERIFICANDO SE O CONJUTO SET TEM ALGUM ERRO COM O CAMPO NOME
			labelErrorName.setText(errors.get("name"));
	}
	
	// MÉTODO QUE INICIALIZA O COMBOBOX COM A LISTA DE DEPARTAMENTOS
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}
}
