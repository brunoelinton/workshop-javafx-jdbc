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
	// DEPEND�NCIA DO TIPO 'DEPARTAMENTO', SEU OBJETIVO � SERVIR DE OBJETO PARA POPULAR OS CAMPOS DE UM DEPARTAMENTO
	private Department entity;
	
	// DEPEND�NCIA DO TIPO 'SERVI�O DE DEPARTAMENTO', SEU OBJETIVO � INSERIR OU ATUALIZAR UM DEPARTAMENTO
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
		if(entity == null) {			// VERIFICANDO SE O DEPARTAMENTO N�O FOI INJETADO			
			throw new IllegalStateException("Entity was null");
		}
		
		if(service == null) {			// VERIFICANDO SE O SERVI�O DE DEPARTAMENTO N�O FOI INJETADO
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();				// OBTENDO OS DADOS DO FORMUL�RIO DE CADASTRO
			service.saveOrUpdate(entity);		// SALVANDO OU ATUALIZANDO O DEPARTMENTO NO BANCO DE DADOS
			Utils.currentStage(event).close();	// FECHANDO A JANELA DE CADASTRO AP�S A OPERA��O
			notifyDataChangeListeners();		// NOTIFICANDO OS OBJETOS QUE O EVENTO FOI CONCLU�DO
		} catch(DbException e) {
			Alerts.showAlert("Error saving objetc", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	// M�TODO QUE NOTIFICA OS OBJETOS QUE O EVENTO OCORREU
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener: dataChangeListeners)
			listener.onDataChanged();
		
	}

	// M�TODO QUE PEGA OS DADOS DO FORMUL�RIO DE CADASTRO DE DEPARTAMENTO NO MOMENTO DE SAV�-LO NO BANCO DE DADOS
	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	// EVENTO QUE CANCELA A OPERA��O DE CADASTRO DE UM DEPARTAMENTO
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();	// FECHANDO A JANELA DE CADASTRO AP�S A OPERA��O
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	// INICIALIZANDO OS CONTROLES DA TELA E COLOCANDO RESTRI��ES DE PREENCHIMENTO
	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);				// SOMENTE INSER��O DE N�MEROS INTEIROS
		Constraints.setTextFieldMaxLength(txtName, 30);		// QUANTIDADE M�XIMA DE CARACTERES NO CAMPO NOME = 30
	}
	
	// M�TODO QUE REALIZA UMA INJE��O DE DEP�ND�NCIA INSTANCIANDO UM OBJETO DO TIPO DEPARTMENT
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	// M�TODO QUE REALIZA UMA INJE��O DE DEP�NCIA INSTANCIANDO UM OBJETO DO TIPO 'SERVI�O DE DEPARTAMENTO'
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	// M�TODO QUE ADICIONA OS OBJETOS INTERESSADOS NO EVENTO � LISTA
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	// M�TODO QUE POPULA OS CAMPOS DO FORMUL�RIO COM OS DADOS DO OBJETO DEPARTAMENTO
	public void updateFormData() {
		if(entity == null)
			throw new IllegalStateException("Entity was null");
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
}
