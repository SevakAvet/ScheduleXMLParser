package com.sevak_avet.GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Controller implements Initializable {
	@FXML
	private ChoiceBox<String> choiceBoxType;
	@FXML
	private ChoiceBox<String> choiceBoxSession;
	
	@FXML
	private TextField tfURL;
	@FXML
	private Button btnLoad;
	
	@FXML
	private TextField monthNum1;
	@FXML
	private TextField monthNum2;
	@FXML
	private TextField monthNum3;
	@FXML
	private TextField monthNum4;
	@FXML
	private TextField monthDenom1;
	@FXML
	private TextField monthDenom2;
	@FXML
	private TextField monthDenom3;
	@FXML
	private TextField monthDenom4;
	
	@FXML
	private TextField periodsNum1;
	@FXML
	private TextField periodsNum2;
	@FXML
	private TextField periodsNum3;
	@FXML
	private TextField periodsNum4;
	@FXML
	private TextField periodsDenom1;
	@FXML
	private TextField periodsDenom2;
	@FXML
	private TextField periodsDenom3;
	@FXML
	private TextField periodsDenom4;
	
	private static final String[] winterSession = new String[]{"Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
	private static final String[] summerSession = new String[]{"Февраль", "Март", "Апрель", "Май"};
	
	private static TextField[] months;
	private static TextField[] periodsNum;
	private static TextField[] periodsDenom;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Controller initialized!..");
		
		months = new TextField[]{monthNum1, monthNum2, monthNum3, monthNum4, monthDenom1, monthDenom2, monthDenom3, monthDenom4};
		periodsNum = new TextField[]{periodsNum1, periodsNum2, periodsNum3, periodsNum4};
		periodsDenom = new TextField[]{periodsDenom1, periodsDenom2, periodsDenom3, periodsDenom4};
		
		EventHandler<ActionEvent> actionURL = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				if(!periodsIsEmpty()) {
					System.out.println("URL DOWNLOADING...");
				} else {
					System.out.println("ERROR");
				}
			}
		};
		
		EventHandler<ActionEvent> actionOpenFile = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				if(!periodsIsEmpty()) {
					System.out.println("OPENING FILE");
				} else {
					System.out.println("ERROR");
				}
			}
		};
		btnLoad.setOnAction(actionURL);
		
		choiceBoxType.getItems().addAll("URL", "Файл");
		choiceBoxType.getSelectionModel().select(0);
		choiceBoxType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("URL")) {
					btnLoad.setText("Загрузить");
					setEnabled(tfURL, true);
					
					btnLoad.setOnAction(actionURL);
				} else {
					btnLoad.setText("Открыть");
					setEnabled(tfURL, false);
					
					btnLoad.setOnAction(actionOpenFile);
				}
			}
		});
		
		choiceBoxSession.getItems().addAll("Летняя", "Зимняя");
		choiceBoxSession.getSelectionModel().select(0);
		chooseSession(summerSession);
		choiceBoxSession.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("Зимняя")) {
					chooseSession(winterSession);
				} else {
					chooseSession(summerSession);
				}
			}
		});		
	}
	
	private void setEnabled(TextField tf, boolean enabled) {
		if(enabled) {
			tf.setEditable(true);
			tf.setOpacity(1.0);
		} else {
			tf.setEditable(false);
			tf.setOpacity(0.5);
		}
	}
	
	private void chooseSession(String[] session) {
		int index = 0;
		for(TextField tf : months) {
			tf.setText(session[index]);
			index = (index + 1 < 4) ? index + 1 : 0;
		}
	}
	
	private boolean periodsIsEmpty() {
		for(TextField tf : periodsNum) {
			if(tf.getText().equals("")) {
				return true;
			}
		}
		
		for(TextField tf : periodsDenom) {
			if(tf.getText().equals("")) {
				return true;
			}
		}
		
		return false;
	}
 
}
