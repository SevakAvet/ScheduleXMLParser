package com.sevak_avet.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(MainGUI.class.getResource("view.fxml"));
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
