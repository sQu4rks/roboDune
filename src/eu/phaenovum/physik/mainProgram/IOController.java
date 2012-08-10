package eu.phaenovum.physik.mainProgram;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import javax.swing.JFrame;

import eu.phaenovum.physik.MainController;


public class IOController {

	Socket clientSocket;
	InputStream clientInputStream;
	/**
	 * Load and Return the propertiesfile
	 * @return
	 */
	public Properties loadProperties(){
		//Init Properties and InputStream
		Properties properties = new Properties();
		
		//Check wether custom properties Exists
		File customPropertiesFile = new File("custome.properties");
		if(customPropertiesFile.exists()){
			try {
				BufferedInputStream stream = new BufferedInputStream(new FileInputStream("custome.properties"));
				properties.load(stream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			BufferedInputStream stream;
			try {
				stream = new BufferedInputStream(new FileInputStream("settings.properties"));
				properties.load(stream);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return properties;
	}
	
	/**
	 * Void to delete the custome Properties File 
	 */
	public void deleteProperties(){
		//First check if custome.properties exsists
		File checkFile = new File("custome.properties");
		
		if(checkFile.exists()){
			
			checkFile.delete();
			System.out.println("[INFO] Deleted custome.properties ! We will roll with settings.properties now");
		}else{
			System.out.println("[INFO] custom.properties doesn't exsist");
		}
	}	
	
	/**
	 * Void to reconstruct the Script class from File
	 */
	public Script reconstructScript(File _selectFile){
		System.out.println("[INFO] Will open "+_selectFile.getName()+" from "+_selectFile.getAbsolutePath());
		//Get the File Object from MainFrame
		try{
			@SuppressWarnings("deprecation")
			URL uri = MainController.viewController.mainFrame.selectedFile.toURL();
			URL[] urls = new URL[]{uri};
			
			
			//Classloader for the directory
			ClassLoader cl = new URLClassLoader(urls);
			Class cls = cl.loadClass("eu.phaenovum.physik.Script");
		}catch(Exception ex){
			System.out.println("[ERROR] Failed to load Class");
		}
		return null;
	}
	
	/**
	 * Function to upload a file to the server, specified in custom.properties
	 * @param _file
	 */
	public void uploadFile(File _file){
		//Bytearray to store our File
		byte[] aByte = new byte[1];
        int bytesRead;

		Properties properties = this.loadProperties();
		
		//Define Server IP and Port from properties
		String serverIP = properties.getProperty("server_ip");
		int serverPort = Integer.parseInt(properties.getProperty("server_port"));
		
		System.out.println("[INFO] Connecting to Server "+serverIP+" on Port "+serverPort);
		
		//Init the socket
		try{
			clientSocket = new Socket(serverIP,serverPort);
			clientInputStream = clientSocket.getInputStream();
			System.out.println("[INFO] Connected to Server");
		}catch (IOException e){
			System.out.println("[ERROR] Can't connect to Server, will exit on emergency");
			
			System.exit(0);
		}
		//Get File in Bytes
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//Check wether we have connection 
		if(clientInputStream !=null){
			//Init them with null in order to have them in this function
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			
			try{
				//All the streams and stuff
				fos = new FileOutputStream(_file);
				bos = new BufferedOutputStream(fos);
				
				bytesRead = clientInputStream.read(aByte, 0, aByte.length);
				
				do{
					baos.write(aByte);
					bytesRead = clientInputStream.read(aByte);
				}while(bytesRead != -1);
				
				//Write and then Clean up
				bos.write(baos.toByteArray());
				System.out.println("[INFO] Copied File: "+_file.getName());
				bos.flush();
				bos.close();
				clientSocket.close();
				System.out.println("[INFO] Disconnected the Server");
			}catch (IOException ex){
				System.out.println("[ERROR] Cannot write to the Server");
			}
		}
		
	}

}
