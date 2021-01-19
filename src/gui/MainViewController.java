package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
	/* ITENS DO MENU */
	@FXML
	private MenuItem menuItemSeller;		// SUBITEM PARA CADASTRO DE VENDEDORES
	@FXML
	private MenuItem menuItemDepartment;	// SUBITEM PARA CADASTRO DE DEPARTAMENTOS
	
	@FXML
	private MenuItem menuItemAbout;			// SUBITEM PARA VISUALIZAR INFORMAÇÕES DA APLICAÇÃO
	
	/*----------------------------------------------------------------------------------------------*/
	
	/* EVENTOS PARA CADA UM DOS ITENS DO MENU */
	@FXML
	public void onMenuItemSellerAction() {					// EVENTO PARA SELEÇÃO DO SUBITEM DE CADASTRO DE VENDEDORES
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {				// EVENTO PARA SELEÇÃO DO SUBITEM DE CADASTRO DE DEPARTAMENTOS
		System.out.println("onMenuItemDepartmentAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() {					// EVENTO PARA SELEÇÃO DO SUBITEM 'SOBRE' 
		System.out.println("onMenuItemAboutAction");
	}
	
	/*----------------------------------------------------------------------------------------------*/
	
	/* SOBREPOSIÇÃO / SOBRESCRITA DO MÉTODO DA CLASSE INITIALIZBLE */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
