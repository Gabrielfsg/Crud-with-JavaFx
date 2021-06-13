package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Main extends Application {
	
	private static Scene mainscene;
	
		@Override
		public void start(Stage primaryStage) {
			try{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
				Parent parent = loader.load();
				mainscene= new Scene(parent);
				primaryStage.setScene(mainscene);
				primaryStage.setTitle("Crud City");
				primaryStage.show();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		public static Scene getMainScene() {
			return mainscene;
		}

	public static void main(String[] args) {
		launch(args);
	}
}
