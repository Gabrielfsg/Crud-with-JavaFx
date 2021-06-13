package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.dataChange.DataChangerList;
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
import javafx.stage.Stage;
import model.entities.City;
import model.services.CityService;

public class CityInsUpDelController implements Initializable {
	
	private City city;
	
	private CityService cs;
	
	private List<DataChangerList> dataChangerList = new ArrayList<>();
	
	@FXML
	private TextField cityId;
	
	@FXML
	private TextField cityName;
	
	@FXML
	private Label errorName;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Button btnCancel;
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public void setCityService(CityService cs) {
		this.cs = cs;
	}
	
	public void subsDataChangeList(DataChangerList dcl) {
		dataChangerList.add(dcl);
	}
	
	@FXML
	public void onBtnDeleteAction() {
		int ver;
		if(city == null) {
			throw new IllegalStateException("City was null");
		}
		if(cs == null) {
			throw new IllegalStateException("City was null");
		}
		city = getFormCity();
		ver = cs.deleteCity(city.getId());
		if(ver == 1) {
			notifyDataChangerList();
			Alerts.showAlert("Delete", null, "Sucess", AlertType.CONFIRMATION);
			cancel();
		}else {
			Alerts.showAlert("Error Delete", null, "Error", AlertType.ERROR);
		}
	}
	
	@FXML
	public void onBtnSaveAction() {
		int val;
		if(city == null) {
			throw new IllegalStateException("City was null");
		}
		if(cs == null) {
			throw new IllegalStateException("City was null");
		}
		city = getFormCity();
		val = cs.saveOrUpdate(city);
		if(val == 1) {
			notifyDataChangerList();
			Alerts.showAlert("Update", null, "Sucess", AlertType.CONFIRMATION);
			cancel();
		}else if(val == 2) {
			notifyDataChangerList();
			Alerts.showAlert("Save", null, "Sucess", AlertType.CONFIRMATION);
			cancel();
		}
		
		}
	
	
	private void notifyDataChangerList() {
		for(DataChangerList dcl: dataChangerList) {
			dcl.onDataChange();
		}		
	}

	private City getFormCity() {
		City city = new City();
		
		
		city.setId(Utils.strToInt(cityId.getText()));
		city.setName(cityName.getText());
		return city;
	}

	@FXML
	public void onBtnCancelAction() {
		cancel();
	}
	@FXML
	public void cancel() {
		Stage pStage = (Stage) btnCancel.getScene().getWindow();
		pStage.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(cityId);
		Constraints.setTextFieldMaxLength(cityName, 20);
	}
	
	public void updateFormCity() {
		if(city == null) {
			throw new IllegalStateException("City is not injected");
		}
		cityId.setText(String.valueOf(city.getId()));
		cityName.setText(city.getName());
	}

}
