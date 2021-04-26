package com.schutzchess.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PromotionMenu extends MouseAdapter {

	private int rank;
	private int file;
	private int team;
	private Tile[][] board;
	private PromotionMenuPiece[] pieces = new PromotionMenuPiece[4];
	private Handler handler;

	public PromotionMenu(int rank, int file, Tile[][] board, Handler handler) {
		this.rank = rank;
		this.file = file;
		this.board = board;
		this.handler = handler;
		if(rank == 0) {
			this.team = GamePiece.BLACK;
		}
		else {
			this.team = GamePiece.WHITE;
		}

		for(int i=1;i<=4;i++) {
			GamePiece piece = null;
			switch(i) {
			case 1:
				piece = new Queen(team, false, -1, -1);
				break;
			case 2:
				piece = new Rook(team, false, -1, -1, false);
				break;
			case 3:
				piece = new Bishop(team, false, -1, -1);
				break;
			case 4:
				piece = new Knight(team, false, -1, -1);
			
			default:
				break;
			}
			
			pieces[i-1] = new PromotionMenuPiece(piece, file, team); 
			for(PromotionMenuPiece p : pieces) {
				handler.addObject(p);
			}
			
		}
	}
	
	public PromotionMenuPiece[] getPieces() {
		return pieces;
	}
	
	public void MousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		int height = RenderObject.IMAGE_SIDE;
		int width = RenderObject.IMAGE_SIDE * 4;
		int[] origin = {(file+1)*RenderObject.IMAGE_SIDE+PromotionMenuPiece.OFFSET,
				(rank+1)*RenderObject.IMAGE_SIDE+PromotionMenuPiece.OFFSET};
		if((mx >= origin[0] && mx <= origin[0]+width) && (my>=origin[1] && my<= origin[1]+height)) {
			int loc = (mx-origin[0])/64;
			GamePiece piece = pieces[loc].getPiece();
			replacePiece(rank, file, board, piece);
			for(PromotionMenuPiece p : pieces) {
				handler.removeObject(p);
			}
		}
	}
	
	public void replacePiece(int rank, int file, Tile[][] board, GamePiece piece) {
		board[rank][file].setPiece(piece);
	}
}
