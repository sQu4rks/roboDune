package eu.phaenovum.physik.mainProgram;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import eu.phaenovum.physik.MainController;


/**
 * This is an abstraction of the JFrame, representing all the MainGUI Components
 * @author Marcel Neidinger - phaenovum Fachbereich Physik
 *
 */
public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 2601272775593390792L;

	MainController mainController;
	
	//Button to open the select Dialogue
	JButton openButton;
	//Button that starts the Loaded Class File 
	JButton startButton;
	//Button to sent a global stop to all components
	JButton stopButton;
	
	//Panel to hold all buttons
	JPanel buttonPanel;
	
	//JLable to hold the path of the Frame
	JLabel pathLable;
	
	//Menu components
	JMenuBar mainMenuBar;
	
	JMenu dataMenu;
	JMenu settingsMenu;
	JMenu serverMenu;
	
	JMenuItem openMenuItem;
	JMenuItem closeMenuItem;
	
	JMenuItem showSettingsItem;
	JMenuItem resetSettingsItem;
	
	JMenuItem uploadMenuItem;
	JMenuItem syncMenuItem;
	JMenuItem startServerItem;
	
	JFileChooser fileChooser;
	
	//Excplicitly public in order to get the File from other classes
	public File selectedFile;
	//Frames
	SettingsFrame settingsFrame;
	
	//The Script File that will be openend using our GUI
	Script script;
	public MainFrame(MainController _mainController){
		mainController = _mainController;
	}
	public void init(){
		
		//Basic Frame initing
		this.setTitle("roboDune - GUI");
		
		//Init all the Buttons
		openButton = new JButton("Öffnen");
		openButton.addActionListener(new openButtonListener());
		
		startButton = new JButton("Starten");
		startButton.addActionListener(new startButtonListener());
		
		stopButton = new JButton("Stoppen");
		stopButton.addActionListener(new stopButtonListener());
		
 		
		//Add all Buttons to the Panel
		buttonPanel = new JPanel();
		
		buttonPanel.add(openButton);
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		
		JLabel titelLabel = new JLabel("Sanddünen Controller - GUI (c) phaenovum");
		titelLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//Init the pathLabel 
		pathLable = new JLabel("Datei: ");
		
		this.getContentPane().add(BorderLayout.NORTH,titelLabel);
		this.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		this.getContentPane().add(BorderLayout.CENTER,pathLable);
		this.setSize(350, 425);
		
		//Set menu 
		this.initMenu();
		this.setJMenuBar(mainMenuBar);
		this.setVisible(true);
	}
	
	/**
	 * Function to init all the Menu Components, set Listeners and register everything. 
	 */
	public void initMenu(){
		//Init the MainMenuBar
		mainMenuBar = new JMenuBar();
		
		//The Menus 
		dataMenu = new JMenu("Datei");
		settingsMenu = new JMenu("Einstellungen");
		serverMenu = new JMenu("Server");
		
		//Items and there Listener
		openMenuItem = new JMenuItem("Öffnen",new ImageIcon("folder_table.png"));
		openMenuItem.addActionListener(new openButtonListener());
		
		closeMenuItem = new JMenuItem("Schließen",new ImageIcon("door_in.png"));
		closeMenuItem.addActionListener(new closeMenuItemListener());
		
		dataMenu.add(openMenuItem);
		dataMenu.addSeparator();
		dataMenu.add(closeMenuItem);
		
		showSettingsItem = new JMenuItem("Einstellungen zeigen",new ImageIcon("wrench.png"));
		showSettingsItem.addActionListener(new showSettingsItemListener());
		
		resetSettingsItem = new JMenuItem("Einstellungen zurücksetzen",new ImageIcon("wrench_orange.png"));
		resetSettingsItem.addActionListener(new resetSettingsItemListener());
		
		settingsMenu.add(showSettingsItem);
		settingsMenu.addSeparator();
		settingsMenu.add(resetSettingsItem);
		
		uploadMenuItem = new JMenuItem("Bild hochladen",new ImageIcon("database_save.png"));
		uploadMenuItem.addActionListener(new uploadMenuItemListener());
		
		syncMenuItem = new JMenuItem("Bilder synchronisieren",new ImageIcon("database_refresh.png"));
		syncMenuItem.addActionListener(new syncMenuItemListener());
		
		startServerItem = new JMenuItem("Server starten",new ImageIcon("application_osx_terminal.png"));
		startServerItem.addActionListener(new startServerItemListener());
		
		serverMenu.add(uploadMenuItem);
		serverMenu.add(syncMenuItem);
		serverMenu.addSeparator();
		serverMenu.add(startServerItem);
		//Merge it all together
		mainMenuBar.add(dataMenu);
		mainMenuBar.add(settingsMenu);
		mainMenuBar.add(serverMenu);
		
	}
	//Listener
	public class openButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Init the FileChooser
			fileChooser = new JFileChooser();
			int returnvalue = fileChooser.showOpenDialog(null);
			//Set the File 
			selectedFile = fileChooser.getSelectedFile();
			//Log on console
			System.out.println("[INFO] Selected File: "+selectedFile.getName());
			//Trigger function from ioController
			script = mainController.ioController.reconstructScript(selectedFile);
			//script.execute();
			System.out.println("[INFO] Succesfully loaded File");
			
			//Now some candy, alter the lable under the Picture with the path
			pathLable.setText("Datei: "+selectedFile.getAbsolutePath());
		}
		
	}
	
	public class startButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Call the execut Function of our Script and terminated wether it worked or not
			if(script.execute()){
				System.out.println("[INFO] Succesfully executed Programm. Waiting for new Orders");
			}else{
				System.out.println("[ERROR] Failed to execute Programm. Please try fixin");
			}
			
		}
		
	}
	
	public class stopButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("[INFO] Shutting down the Engine");
			//Call the Programm
			if(script.stopTheEngine()){
				System.out.println("[INFO] Succesfully shuted down. What was the problem ?");
			}else{
				System.out.println("[ERROR] Failed to shut down the Engine. Search for an error");
			}
			
		}
		
	}
	
	/**
	 * Listener für das Schließen 
	 * @author marcel
	 *
	 */
	public class closeMenuItemListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			System.exit(NORMAL);
		}
	}
	
	public class showSettingsItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			//Init the settingsFrame by creating one
			settingsFrame = new SettingsFrame(mainController);
			settingsFrame.initGui();
		}
	}
	
	public class resetSettingsItemListener implements ActionListener{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent e){
			//Execute void from IOcontroller to delete the custome files
			mainController.ioController.deleteProperties();
		}
	}
	
	public class uploadMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("[INFO] Prepare to upload a file");
			JFileChooser uploadFileChooser = new JFileChooser();
			int returnvalue = uploadFileChooser.showOpenDialog(null);
			//Set the File 
			File selectFile = uploadFileChooser.getSelectedFile();
			System.out.println("[INFO] Will Upload File: "+selectFile.getName());
			mainController.ioController.uploadFile(selectFile);
		}
		
	}
	
	public class syncMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Do something in here XD
			System.out.println("[INFO] Will connect to your specified Server");
			
		}
		
	}
	
	public class startServerItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Init the Server
			mainController.serverController.initServer();
		}
		
	}
}
