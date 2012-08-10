package eu.phaenovum.physik.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the mainServerapplikation. 
 * @author marcel
 *
 */
public class Server implements Runnable{

	private int port;
	
	public Server(String _port){
		port = Integer.parseInt(_port);
		System.out.println("[SERVER] Succesfully started the Server, all classes will run throw this tag now");
	}
	
	/**
	 * Start the server and wait for connections
	 */
	public void run(){
		//TODO: Threading for multiple connections
		System.out.println("[INFO] Initing the Server and waiting for connections");
		while (true) {
            ServerSocket welcomeSocket = null;
            Socket connectionSocket = null;
            BufferedOutputStream outToClient = null;

            try {
                welcomeSocket = new ServerSocket(port);
                connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            	System.out.println("[ERROR] Can't create Server Socket, is there annother Service running ?");
                //System.exit(0);
            }

            if (outToClient != null) {
                File myFile = new File("test.jpg");
                byte[] mybytearray = new byte[(int) myFile.length()];

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(myFile);
                } catch (FileNotFoundException ex) {
                    // Do exception handling
                }
                BufferedInputStream bis = new BufferedInputStream(fis);

                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    connectionSocket.close();
                    System.out.println("[INFO] Stored File, exiting now");
                } catch (IOException ex) {
                    // Do exception handling
                	System.out.println("[ERROR] Wasn't able to store file. Do i have write privileges ?");
                	System.exit(0);
                }
            }
        }
    }
	
	/**
	 * This function will shutdown the servers functionallity 
	 */
	public void shutdownServer(){
		System.out.println("[SERVER] Server is going down for sleep");
	}
	
	/**
	 * This function will suspend the server 
	 */
	public void suspendServer(){
		System.out.println("[SERVER] Server is going to take a nap on the couch. Wake me if you need me");
	}

	
}
