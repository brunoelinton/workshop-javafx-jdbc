package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	/* ITENS DO MENU */
	@FXML
	private MenuItem menuItemSeller;		// SUBITEM PARA CADASTRO DE VENDEDORES
	@FXML
	private MenuItem menuItemDepartment;	// SUBITEM PARA CADASTRO DE DEPARTAMENTOS
	
	@FXML
	private MenuItem menuItemAbout;			// SUBITEM PARA VISUALIZAR INFORMA��ES DA APLICA��O
	
	/*----------------------------------------------------------------------------------------------*/
	
	/* EVENTOS PARA CADA UM DOS ITENS DO MENU */
	@FXML
	public void onMenuItemSellerAction() {					// EVENTO PARA SELE��O DO SUBITEM DE CADASTRO DE VENDEDORES
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {				// EVENTO PARA SELE��O DO SUBITEM DE CADASTRO DE DEPARTAMENTOS
		loadView("/gui/DepartmentList.fxml");
	}
	
	@FXML
	public void onMenuItemAboutAction() {					// EVENTO PARA SELE��O DO SUBITEM 'SOBRE' 
		loadView("/gui/About.fxml");
	}
	
	/*----------------------------------------------------------------------------------------------*/
	
	/* SOBREPOSI��O / SOBRESCRITA DO M�TODO DA CLASSE INITIALIZBLE */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	// EVENTO PARA CHAMADA DE UMA OUTRA JANELA QUALQUER DA APLICA��O
	private synchronized void loadView(String absoluteName) {
		try {
			// INSTANCIANDO UM OBJETO DO TIPO 'FXMLLoader' PARA CARREGAMENTO DA TELA
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// INSTANCIANDO UM CONTAINER DO TIPO 'VBox' QUE CONT�M A JANELA A SER EXIBIDA
			VBox newVBox = loader.load();
			// OBTENDO A REFER�NCIA DA CENA DA JANELA PRINCIPAL
			Scene mainScene = Main.getMainScene();
			// OBETENDO A REFR�NCIA DO CONTAINER 'VBox' DA CENA DA JANELA PRINCIPAL
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();
			
			/* PRESERVANDO O MENU DA JANELA PRINCIPAL */
			// 1� OBTENDO O PRIMEIRO ELEMENTO FILHO DO 'VBox' DA CENA DA JANELA PRINCIPAL, OU SEJA, O 'content'			
			Node mainMenu = mainVBox.getChildren().get(0);
			// 2� REMOVENDO TODOS OS FILHOS DO 'VBox' DA CENA DA JANELA PRINCILA
			mainVBox.getChildren().clear();
			// 3� ADICIONANDO DE VOLTA O MENU DA CENA DA JANELA PRINCIPAL
			mainVBox.getChildren().add(mainMenu);
			// 4� ADICIONANDO TODOS OS FILHOS DO 'VBox' DA JANELA A SER EXIBA, DENTRO DA SCENA DA JANELA PRINCIPAL
			mainVBox.getChildren().addAll(newVBox.getChildren());
		} catch(IOException e) {	// TRATANDO ERRO CASO HAJA ALGUM PROBLEMA NO CARRRGAMENTO DA TELA
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
