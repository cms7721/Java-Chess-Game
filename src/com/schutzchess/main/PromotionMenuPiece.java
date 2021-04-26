package com.schutzchess.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PromotionMenuPiece implements RenderObject {
	private GamePiece piece;
	public static final int OFFSET = 25;
	private int team;
	private int file;
	private int rank;
	private boolean isHovered;

	public PromotionMenuPiece(GamePiece piece, int file, int team) {
		this.piece = piece;
		this.team = team;
		this.isHovered = false;
		this.file = file;
		if(team == GamePiece.WHITE) {
			this.rank = GameBoard.HEIGHT - 1;
		}
		else {
			this.rank = 0;
		}
	}

	public void setHovered(boolean hover) {
		isHovered = hover;
	}

	public GamePiece getPiece() {
		return piece;
	}

	public void render(Graphics g) {
		SpriteSheet sheet = new SpriteSheet(Game.spriteSheet);
		BufferedImage background;
		int rowSet = 0;
		if(team==GamePiece.BLACK) {
			rowSet += 1;
		}

		if(team == GamePiece.BLACK) {
			background = sheet.getImage(1, 1, IMAGE_SIDE, IMAGE_SIDE);
		}
		else {
			background = sheet.getImage(2, 1, IMAGE_SIDE, IMAGE_SIDE);
		}
		g.drawImage(background,file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET,null);

		switch(piece.getID()) {
		case Queen:
			g.drawImage(sheet.getImage(11+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET,null);
			if(isHovered) {
				g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE,rank*IMAGE_SIDE, null);
			}
			break;
		case Rook:
			g.drawImage(sheet.getImage(5+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET,null);
			if(isHovered) {
				g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET, null);
			}
			break;
		case Bishop:
			g.drawImage(sheet.getImage(9+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET,null);
			if(isHovered) {
				g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET, null);
			}
			break;
		case Knight:
			g.drawImage(sheet.getImage(7+rowSet,1,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET,null);
			if(isHovered) {
				g.drawImage(sheet.getImage(7,2,IMAGE_SIDE,IMAGE_SIDE),file*IMAGE_SIDE+OFFSET,rank*IMAGE_SIDE+OFFSET, null);
			}
			break;

		default:
			break;
		}
	}

}
