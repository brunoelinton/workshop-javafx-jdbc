package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	// DEPENDÊNCIA DO TIPO 'DEPARTAMENTO', SEU OBJETIVO É SERVIR DE OBJETO PARA POPULAR OS CAMPOS DE UM DEPARTAMENTO
	private Department entity;
	
	// DEPENDÊNCIA DO TIPO 'SERVIÇO DE DEPARTAMENTO', SEU OBJETIVO É INSERIR OU ATUALIZAR UM DEPARTAMENTO
	private DepartmentService service;
	
	// LISTA DE OBJETOS INTERESSADOS EM RECEBER O EVENTO
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {			// VERIFICANDO SE O DEPARTAMENTO NÃO FOI INJETADO			
			throw new IllegalStateException("Entity was null");
		}
		
		if(service == null) {			// VERIFICANDO SE O SERVIÇO DE DEPARTAMENTO NÃO FOI INJETADO
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();				// OBTENDO OS DADOS DO FORMULÁRIO DE CADASTRO
			service.saveOrUpdate(entity);		// SALVANDO OU ATUALIZANDO O DEPARTMENTO NO BANCO DE DADOS
			Utils.currentStage(event).close();	// FECHANDO A JANELA DE CADASTRO APÓS A OPERAÇÃO
			notifyDataChangeListeners();		// NOTIFICANDO OS OBJETOS QUE O EVENTO FOI CONCLUÍDO
		} catch(DbException e) {
			Alerts.showAlert("Error saving objetc", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	// MÉTODO QUE NOTIFICA OS OBJETOS QUE O EVENTO OCORREU
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener: dataChangeListeners)
			listener.onDataChanged();
		
	}

	// MÉTODO QUE PEGA OS DADOS DO FORMULÁRIO DE CADASTRO DE DEPARTAMENTO NO MOMENTO DE SAVÁ-LO NO BANCO DE DADOS
	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	// EVENTO QUE CANCELA A OPERAÇÃO DE CADASTRO DE UM DEPARTAMENTO
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
		Constraints.setTextFieldMaxLength(txtName, 30);		// QUANTIDADE MÁXIMA DE CARACTERES NO CAMPO NOME = 30
	}
	
	// MÉTODO QUE REALIZA UMA INJEÇÃO DE DEPÊNDÊNCIA INSTANCIANDO UM OBJETO DO TIPO DEPARTMENT
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	// MÉTODO QUE REALIZA UMA INJEÇÃO DE DEPÊNCIA INSTANCIANDO UM OBJETO DO TIPO 'SERVIÇO DE DEPARTAMENTO'
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	// MÉTODO QUE ADICIONA OS OBJETOS INTERESSADOS NO EVENTO À LISTA
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	// MÉTODO QUE POPULA OS CAMPOS DO FORMULÁRIO COM OS DADOS DO OBJETO DEPARTAMENTO
	public void updateFormData() {
		if(entity == null)
			throw new IllegalStateException("Entity was null");
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
