package com.schutzchess.main;

import java.awt.Canvas;
//import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends Canvas {

	/**
	 *  
	 */
	private static final long serialVersionUID = -6294394690737735524L;
	
	public Window(String title, Game game) {
		
		//Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame(title);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true); 
		frame.setLocationRelativeTo(null); 
		frame.add(game);
		frame.setVisible(true);
		game.render();
	}

}  
