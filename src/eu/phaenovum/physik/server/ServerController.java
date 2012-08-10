package eu.phaenovum.physik.server;

import java.net.Socket;
import java.util.Properties;

import eu.phaenovum.physik.MainController;

/**
 * This is the Controller for all Serverside Qualifikations. 
 * @author marcel
 *
 */
public class ServerController {

	public Server server;
	private MainController mainController;
	private Properties properties;
	
	public ServerController(MainController _mainController){
		mainController = _mainController;
	}
	public void initServer(){
		System.out.println("[INFO] Will init the Server Module now.");
		System.out.println("[WARNING] Be aware that the server module is currently under development and verry beta");
		//Get Properties
		properties = mainController.ioController.loadProperties();
		//Parse them into server
		//server = new Server(properties.getProperty("server_port"));
		
		//Starting the Server in a thread
		Thread serverThread = new Thread(new Server(properties.getProperty("server_port")));
		serverThread.start();
	}
	
	public Socket connectServer(String ip,String port){
		return null;
	}
}
