package com.sevak_avet.GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sevak_avet.ScheduleParser.DOMParser;
import com.sevak_avet.ScheduleParser.ScheduleMarkup;

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
	private Button btnSave;
	
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
	
	public static final String[] winterSession = new String[]{"Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
	public static final String[] summerSession = new String[]{"Февраль", "Март", "Апрель", "Май"};
	private static boolean isWinterSession = false;
	
	private static TextField[] months;
	private static TextField[] periodsNum;
	private static TextField[] periodsDenom;
	
	private static String schedule = "";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Controller initialized!..");
		
		months = new TextField[]{monthNum1, monthNum2, monthNum3, monthNum4, monthDenom1, monthDenom2, monthDenom3, monthDenom4};
		periodsNum = new TextField[]{periodsNum1, periodsNum2, periodsNum3, periodsNum4};
		periodsDenom = new TextField[]{periodsDenom1, periodsDenom2, periodsDenom3, periodsDenom4};
		
		EventHandler<ActionEvent> actionURL = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				if(!periodsIsEmpty() && !tfURL.getText().isEmpty()) {
					try {
						DOMParser dp = DOMParser.getInstance();
						URL url = new URL(tfURL.getText() + "/lesson");
						String path = new File(".").getAbsolutePath() + "schedule.xls";
						String[][] table = dp.parse(url, path);
						
						String[] curSession = isWinterSession ? winterSession : summerSession;
						ScheduleMarkup markup = new ScheduleMarkup(table, curSession, getPeriodData(periodsNum), getPeriodData(periodsDenom));
						schedule = markup.getSchedule();
						
						btnSave.setDisable(false);
					} catch (SAXException e) {
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (OperationNotSupportedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Some periods or URL is empty!..");
				}
			}
		};
		
		EventHandler<ActionEvent> actionOpenFile = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				if(!periodsIsEmpty()) {
					FileChooser chooser = new FileChooser();
					chooser.getExtensionFilters().add(new ExtensionFilter("Расписание", Arrays.asList("*.xml", "*.xls")));
					
					File desktop = new File(System.getProperty("user.home") + "\\Рабочий стол");
					if(!desktop.exists()) {
						desktop = new File(System.getProperty("user.home") + "\\Desktop");
					}
					
					if(desktop.exists()) {
						chooser.setInitialDirectory(desktop);
					}
					
					File file = chooser.showSaveDialog(new Stage(StageStyle.UTILITY));
					
					if(file != null) {
						System.out.println(file);
						
						try {
							DOMParser dp = DOMParser.getInstance();
							String[][] table = dp.parse(file);
							
							String[] curSession = isWinterSession ? winterSession : summerSession;
							ScheduleMarkup markup = new ScheduleMarkup(table, curSession, getPeriodData(periodsNum), getPeriodData(periodsDenom));							
							schedule = markup.getSchedule();
							
							btnSave.setDisable(false);
						} catch (SAXException e) {
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (OperationNotSupportedException e) {
							e.printStackTrace();
						}
						
						btnSave.setDisable(false);	
					} else {
						System.out.println("File not choosed!");
					}
					
				} else {
					System.out.println("Some periods is empty!..");
				}
			}
		};
		
		EventHandler<ActionEvent> actionSaveToFile = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser chooser = new FileChooser();
				chooser.getExtensionFilters().add(new ExtensionFilter("Текстовый файл", Arrays.asList("*.txt")));
				
				File desktop = new File(System.getProperty("user.home") + "\\Рабочий стол");
				if(!desktop.exists()) {
					desktop = new File(System.getProperty("user.home") + "\\Desktop");
				}
				
				if(desktop.exists()) {
					chooser.setInitialDirectory(desktop);
				}
				
				File file = chooser.showSaveDialog(new Stage(StageStyle.UTILITY));
				
				if(file != null) {
					System.out.println(file.getAbsolutePath());
					try(PrintWriter pw = new PrintWriter(file)) {
						System.out.println("WRITING");
						System.out.println(schedule);
						pw.write(schedule);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("File save path not choosed!");
				}
			}
		};
		
		btnLoad.setOnAction(actionURL);
		btnSave.setOnAction(actionSaveToFile);
		btnSave.setDisable(true);
		
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
					isWinterSession = true;
				} else {
					chooseSession(summerSession);
					isWinterSession = false;
				}
			}
		});		
	}
	
	private String[] getPeriodData(TextField[] array) {
		String[] data = new String[4];
		
		int index = 0;
		for(TextField tf : array) {
			data[index++] = tf.getText();
		}
		
		return data;
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
