package com.schutzchess.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile implements RenderObject {
	
	private boolean even;	//used to make alternating pattern
	private GamePiece piece;
	private boolean isSelected;
	private boolean isMoveable;
	private boolean inCheck;
	private int rank;
	private int file;
	private boolean lastMoved;
	
	public Tile(boolean even, GamePiece piece, int rank, int file, boolean select, boolean move, boolean check) {
		
		this.even = even;
		this.piece = piece;
		this.rank = rank;
		this.file = file;
		this.isSelected = select;
		this.isMoveable = move;
		this.inCheck = check;
		this.lastMoved = false;
	}
	
	
	public void render(Graphics g) {
		SpriteSheet sheet = new SpriteSheet(Game.spriteSheet);
		int rowSet = 0;
		BufferedImage tileImage;
		if(!even) {
			tileImage = sheet.getImage(1, 1, IMAGE_SIDE, IMAGE_SIDE);
		}
		else {
			tileImage = sheet.getImage(2, 1, IMAGE_SIDE, IMAGE_SIDE);
		}

		g.drawImage(tileImage,file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
		if(piece.getTeam() == GamePiece.BLACK) {
			rowSet += 1;
		}

		switch(piece.getID()) {
			case Pawn:
				g.drawImage(sheet.getImage(3+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
				if(isSelected) {
					g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(isMoveable) {
					g.drawImage(sheet.getImage(4,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				break;
			case Rook:
				g.drawImage(sheet.getImage(5+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
				if(isSelected) {
					g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(isMoveable) {
					g.drawImage(sheet.getImage(4,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				break;
			case Knight:
				g.drawImage(sheet.getImage(7+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
				if(isSelected) {
					g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(isMoveable) {
					g.drawImage(sheet.getImage(4,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				break;
			case Bishop:
				g.drawImage(sheet.getImage(9+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
				if(isSelected) {
					g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(isMoveable) {
					g.drawImage(sheet.getImage(4,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				break;
			case Queen:
				g.drawImage(sheet.getImage(11+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
				if(isSelected) {
					g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(isMoveable) {
					g.drawImage(sheet.getImage(4,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				break;
			case King:
				g.drawImage(sheet.getImage(1+rowSet,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE,null);
				if(isSelected) {
					g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(isMoveable) {
					g.drawImage(sheet.getImage(4,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				if(inCheck) {
					g.drawImage(sheet.getImage(6,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
				break;
			case NoPiece:
				if(isMoveable) {
					g.drawImage(sheet.getImage(3,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
				}
			default:
				break;
		}
		
		
			
	}

	

	
	public void setPiece(GamePiece piece) {
		this.piece = piece;
	}
	
	public GamePiece getPiece() {
		return this.piece;
	}
	
	public void setSelected(boolean select) {
		this.isSelected = select;
	}
	
	public boolean getSelected() {
		return this.isSelected;
	}
	
	public void setMoveable(boolean move) {
		this.isMoveable = move;
	}
	
	public boolean getMoveable() {
		return this.isMoveable;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public int getFile() {
		return this.file;
	}

	public boolean inCheck() {
		return inCheck;
	}

	public void setCheck(boolean inCheck) {
		this.inCheck = inCheck;
	}

	public boolean getLastMoved() {
		return lastMoved;
	}


	public void setLastMoved(boolean lastMoved) {
		this.lastMoved = lastMoved;
	}

}
