package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



import application.Main;
import gui.dataChange.DataChangerList;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.City;
import model.services.CityService;

public class CityListController implements Initializable, DataChangerList {
	
	private CityService cityService;
	
	@FXML
	private TableView<City> tableViewCity;
	
	@FXML
	private TableColumn<City, Integer> tableColumnId;
	
	@FXML
	private TableColumn<City, String> tableColumnName;
	
	@FXML
	private Button btnNew;
	
	private ObservableList<City> obsList;
	
	@FXML
	public void onBtnNewAction(ActionEvent event) {
		City ob = new City();
		
		Stage pStage = Utils.currentStage(event);
		createInsertCityView(ob, null, "/gui/CityInsUpDel.fxml");
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void setCityServices(CityService cityService) {
		this.cityService = cityService;
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCity.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if(cityService == null) {
			throw new IllegalStateException("Service was null");
		}else {
			List<City> list = cityService.findAll();
			obsList = FXCollections.observableArrayList(list);
			tableViewCity.setItems(obsList);
		}
	}
	
	private void createInsertCityView(City city, Stage pStage, String viewName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viewName));
			Pane pane = loader.load();
			
			CityInsUpDelController c = loader.getController();
			c.setCity(city);
			c.setCityService(new CityService());
			c.subsDataChangeList(this);
			c.updateFormCity();
			
			Stage dStage = new Stage();
			dStage.setTitle("Insert City");
			dStage.setScene(new Scene(pane));
			dStage.setResizable(false);
			dStage.initOwner(pStage);
			dStage.initModality(Modality.WINDOW_MODAL);
			dStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}

}
