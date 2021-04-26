package com.schutzchess.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas{// implements MouseListener{// Runnable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -6763831697481556775L;
	private Handler handler;
	private int numClicks;
	//private Menu menu;

	public enum STATE {
		Menu, Game, Tutorial
	};

	public STATE gameState = STATE.Game;

	public static BufferedImage spriteSheet;

	public Game() {

		BufferedImageLoader loader = new BufferedImageLoader();

		spriteSheet = loader.loadImage("res/tiles.png");
		
		handler = new Handler();
		numClicks = 0;
		new Window("SchutzChess", this);

		//menu = new Menu();

		GameBoard board = new GameBoard(this);
		for (int i = 0; i < GameBoard.HEIGHT; i++) {
			for (int j = 0; j < GameBoard.WIDTH; j++) {
				handler.addObject(board.getBoard()[i][j]);
			}
		}
		this.addMouseListener(board);
		render();
		//this.addMouseListener(this);
	}

	public void render() {
		BufferStrategy bufStrat = this.getBufferStrategy();
		if (bufStrat == null) {
			this.createBufferStrategy(1);
			return;
		}

		Graphics g = bufStrat.getDrawGraphics();

		handler.render(g);

		g.dispose();
		bufStrat.show();
	}

	public static void main(String args[]) {
		Game game = new Game();
		//game.addMouseListener(game);
	}
	
	/*@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}*/

	//@Override
	public void mousePressed() {
		//render();		
		numClicks++;
		System.out.println(numClicks);
		if (numClicks == 40) {
			numClicks = 0;
			System.out.println("gc");
			System.gc();
		}
	}
	

	/*@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}*/

}
