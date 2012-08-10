package eu.phaenovum.physik;

import java.util.Scanner;

import eu.phaenovum.physik.mainProgram.IOController;
import eu.phaenovum.physik.mainProgram.Video;
import eu.phaenovum.physik.mainProgram.ViewController;
import eu.phaenovum.physik.server.ServerController;

/**
 * This programm is a data Logging software called roboDune for use in phaenovum Schülerforschungszentrum 
 * Lörrach in an experiment
 * 
 * Copyright (C) 2012  Marcel Neidinger
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
public class MainController {
	static Scanner inscanner;
	static String command;
	
	//My own Controller
	public static ViewController viewController;
	public static IOController ioController;
	public static MainController mainController;
	public static  ServerController serverController;
	/**
	 * Main methode of the programm. Init all the variables etc
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
		//Show some release Notes 
		System.out.println("      SanddünenController v0.1 (c) Marcel Neidinger - phaenovum Fachbereich Physik");
		System.out.println(" ");
		System.out.println("[INFO] Starting the Engine. Wait a moment...");
		try {
			Thread.currentThread();
			Thread.sleep(500);
			System.out.println("[INFO] Succesfully inited all core functions. Will load everything else dynamically");
		} catch (InterruptedException e) {
			System.out.println("[ERROR] An error occured while starting the main Thread, exiting");
			System.exit(0);
		}
		mainController = new MainController();
		ioController = new IOController();
		serverController = new ServerController(mainController);
		
		//Init the GUI
		mainController.initGui();
		//In Developers Release there will be a possibility to change between
		//Console and Server mode but for now we will stay with the Menu Settings
		Video video = new Video();
	}
	
	/**
	 * Returns our dstatusefault ioController
	 * @return
	 */
	public IOController getIOController(){
		return ioController;
	}
	
	public void initGui(){
		viewController = new ViewController(mainController);
		viewController.startGUI();
	}
}