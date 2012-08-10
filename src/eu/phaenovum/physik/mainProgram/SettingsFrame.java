package eu.phaenovum.physik.mainProgram;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;

import eu.phaenovum.physik.MainController;

public class SettingsFrame extends JFrame{

	private MainController mainController;
	
	public JLabel titleLabel;
	
	//Properties File for use with Settings
	Properties settingsProperties;
	
	//Buttons
	JButton editButton;
	JButton saveButton;
	
	//Labels and Boxes for the Settings
	private JLabel savePathLabel;
	private JTextField savePathField;
	
	private JLabel fileExtensionLabel;
	private JTextField fileExtensionField;
	
	private JLabel fileOptimizeLabel;
	private JCheckBox fileOptimizeCheckbox;
	
	private JLabel videoDeviceLabel;
	private JTextField videoDeviceField;
	
	private JLabel serverIPLabel;
	private JTextField serverIPField;
	
	private JLabel serverPortLabel;
	private JTextField serverPortField;
	
	
	public SettingsFrame(MainController _mainController){
		//Get the Controller
		mainController = _mainController;
		//Frame behavior and titeling
		this.setTitle("roboDune - GUI");
		this.setFocusable(true);
		this.setSize(500, 150);
		
		//Create title Label for the frame 
		titleLabel = new JLabel("SanddünenController GUI - Einstellungen");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		this.getContentPane().add(BorderLayout.NORTH,titleLabel);
	}
	
	/*
	 * Void to init all gui Elements 
	 */
	public void initGui(){
		this.setVisible(true);
		//Load default values and write theme into the field
		loadData();
		
		//Now init all the buttons and fields and set them to unediteble
		editButton = new JButton("Einstellungen ändern");
		editButton.addActionListener(new editButtonListener());
		
		saveButton = new JButton("Einstellungen speichern");
		saveButton.addActionListener(new saveButtonListener());
		saveButton.setEnabled(false);
		
		//Add all the Buttons on the Frame
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(editButton);
		buttonPanel.add(saveButton);
		
		this.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		
		//Now init all the stuff 
		savePathLabel = new JLabel("Speicherort");
		savePathField = new JTextField(20);
		savePathField.setEditable(false);
		
		fileExtensionLabel = new JLabel("Dateiendung");
		fileExtensionField = new JTextField(10);
		fileExtensionField.setEditable(false);
		
		fileOptimizeLabel = new JLabel("Dateioptimierung");
		fileOptimizeCheckbox = new JCheckBox();
		fileOptimizeCheckbox.setSelected(false);
		fileOptimizeCheckbox.setEnabled(false);
		
		videoDeviceLabel = new JLabel("Video Gerät");
		videoDeviceField = new JTextField(1);
		videoDeviceField.setEditable(false);
		
		serverIPLabel = new JLabel("Server IP");
		serverIPField = new JTextField(10);
		serverIPField.setEditable(false);
		
		serverPortLabel = new JLabel("Server Port");
		serverPortField = new JTextField(4);
		serverPortField.setEditable(false);
		
		//Now lets get a JPanel and put GridLayout on it
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(0,2));
		
		fieldPanel.add(savePathLabel);
		fieldPanel.add(savePathField);
		
		fieldPanel.add(fileExtensionLabel);
		fieldPanel.add(fileExtensionField);
		
		fieldPanel.add(fileOptimizeLabel);
		fieldPanel.add(fileOptimizeCheckbox);
		
		fieldPanel.add(videoDeviceLabel);
		fieldPanel.add(videoDeviceField);
		
		fieldPanel.add(serverIPLabel);
		fieldPanel.add(serverIPField);
		
		fieldPanel.add(serverPortLabel);
		fieldPanel.add(serverPortField);
	
		
		//Now set the values from the loaded properties file
		//Savepath
		savePathField.setText(settingsProperties.getProperty("save_path"));
		//File Extensions
		fileExtensionField.setText(settingsProperties.getProperty("file_extension"));
		
		//Check if optimizing is enabled
		if(settingsProperties.getProperty("file_optimize")=="true"){
			fileOptimizeCheckbox.setSelected(true);
		}else{
			fileOptimizeCheckbox.setSelected(false);
		}
		
		//Videodevice
		//TODO: Add checking here
		videoDeviceField.setText(settingsProperties.getProperty("video_device"));
		
		serverIPField.setText(settingsProperties.getProperty("server_ip"));
		serverPortField.setText(settingsProperties.getProperty("server_port"));
		//Add the stuff to the frame
		this.getContentPane().add(BorderLayout.CENTER,fieldPanel);
	}
	
	/**
	 * Void to load all the needed data in to de the fram Properties
	 */
	public void loadData(){
		//Get Property from maincontroller or the iocontroller
		settingsProperties = mainController.ioController.loadProperties();
		
		//TODO For Testing only, show all the settings on the commandline 
		System.out.println("[INFO] Loading Properties from File");
		System.out.println("[INFO] Loaded Propertie ''SavePath'' "+settingsProperties.getProperty("save_path"));
		System.out.println("[INFO] Loaded Propertie ''File Extension'' "+settingsProperties.getProperty("file_extension"));
		System.out.println("[INFO] Loaded Propertie ''File Optimize'' which is set to "+settingsProperties.getProperty("file_optimize"));
		System.out.println("[INFO] Loaded Propertie ''Video Device'' "+settingsProperties.getProperty("video_device"));
		System.out.println("[ERROR] No video Device found, will continue with 0");
		System.out.println("[INFO] Loaded Propertie ''Server IP'' "+settingsProperties.getProperty("server_ip"));
		System.out.println("[INFO] Loaded Propertie ''Server Port'' "+settingsProperties.getProperty("server_port"));
		System.out.println("[INFO] Succesfully loaded all the Properties");
		
	}
	
	//Listener
	public class editButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("[INFO] Editing the Settings");
			//Set all the Fields editable
			savePathField.setEditable(true);
			fileExtensionField.setEditable(true);
			videoDeviceField.setEditable(true);
			fileOptimizeCheckbox.setEnabled(true);
			serverIPField.setEditable(true);
			serverPortField.setEditable(true);
			saveButton.setEnabled(true);
			editButton.setEnabled(false);
			
		}
		
	}
	
	public class saveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			//Local File Variables
			File propertiesFile;
			FileOutputStream fileStream;
			
			editButton.setEnabled(true);
			saveButton.setEnabled(false);
			//Get all Values and write them to the IOController
			Properties properties = new Properties();

			try {
				propertiesFile = new File("custome.properties");
				propertiesFile.createNewFile();
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("custome.properties"));
			} catch (FileNotFoundException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
				System.out.println("[ERROR] Can't find file custome.properties");
			} catch (IOException ev) {
				// TODO Auto-generated catch block
				ev.printStackTrace();
				System.out.println("[ERROR] Problem with the file handling");
			}
			//Set the Properties
			properties.setProperty("save_path", savePathField.getText());
			properties.setProperty("file_extension", fileExtensionField.getText());
			//Check the Checkbox status
			if(fileOptimizeCheckbox.isSelected()){
				properties.setProperty("file_optimize", "true");
			}else{
				properties.setProperty("file_optimize", "false");
			}
			properties.setProperty("video_device", videoDeviceField.getText());
			properties.setProperty("server_ip", serverIPField.getText());
			properties.setProperty("server_port", serverPortField.getText());
			
			//Create Output Stream to save
			try {
				fileStream = new FileOutputStream("custome.properties");
				properties.store(fileStream, "custome.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch blockFile 
				System.out.println("[ERROR] Can't find the coresponding File");
				e1.printStackTrace();
			} catch (IOException ev) {
				// TODO Auto-generated catch block
				System.out.println("[ERROR] Unable to handle IO ");
				
				ev.printStackTrace();
				
			}
			
			//Now deaktiväte all the other components
			savePathField.setEditable(false);
			fileExtensionField.setEditable(false);
			videoDeviceField.setEditable(false);
			fileOptimizeCheckbox.setEnabled(false);
			serverIPField.setEditable(false);
			serverPortField.setEditable(false);
			System.out.println("[INFO] Saved new Settings in custome.properties");
		}
	}
}
