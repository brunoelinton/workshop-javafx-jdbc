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
	private MenuItem menuItemAbout;			// SUBITEM PARA VISUALIZAR INFORMA합ES DA APLICA플O
	
	/*----------------------------------------------------------------------------------------------*/
	
	/* EVENTOS PARA CADA UM DOS ITENS DO MENU */
	@FXML
	public void onMenuItemSellerAction() {					// EVENTO PARA SELE플O DO SUBITEM DE CADASTRO DE VENDEDORES
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {				// EVENTO PARA SELE플O DO SUBITEM DE CADASTRO DE DEPARTAMENTOS
		System.out.println("onMenuItemDepartmentAction");
	}
	
	@FXML
	public void onMenuItemAboutAction() {					// EVENTO PARA SELE플O DO SUBITEM 'SOBRE' 
		System.out.println("onMenuItemAboutAction");
	}
	
	/*----------------------------------------------------------------------------------------------*/
	
	/* SOBREPOSI플O / SOBRESCRITA DO M�TODO DA CLASSE INITIALIZBLE */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
