package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import model.services.CityService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemCity;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemCityAction() {
		loadView("/gui/CityList.fxml", (CityListController controller) -> {
			controller.setCityServices(new CityService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x ->{});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String viewName, Consumer<T> initializingAction) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
		VBox newVBox = loader.load();
		
		Scene mainscene = Main.getMainScene();
		VBox mainVbox = (VBox) ((ScrollPane) mainscene.getRoot()).getContent();
		
		Node mainMenu = mainVbox.getChildren().get(0);
		mainVbox.getChildren().clear();
		mainVbox.getChildren().add(mainMenu);
		mainVbox.getChildren().addAll(newVBox.getChildren());
		
		//executa a função passado como argumento no caso auto carregar cidade
		T controller = loader.getController();
		initializingAction.accept(controller);
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Erro loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
