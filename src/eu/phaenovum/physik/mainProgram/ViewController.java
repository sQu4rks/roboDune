package eu.phaenovum.physik.mainProgram;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import eu.phaenovum.physik.MainController;

/**
 * This is the ViewController of the roboDune Software. Handels the (small) part of view Components
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

public class ViewController {
	MainFrame mainFrame;
	MainController mainController;
	public ViewController(MainController _mainController){
		mainController = _mainController;
	}
	public void startGUI(){
		
		//Get a new MainFrame Objekt
		mainFrame = new MainFrame(mainController);
		//Init the GUI
		mainFrame.init();
	}

}
