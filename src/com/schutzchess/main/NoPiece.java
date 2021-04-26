package com.schutzchess.main;

public class NoPiece extends GamePiece{

	public NoPiece() {
		super();
		this.id = ID.NoPiece;
	}

	@Override
	public boolean[][] moveable(GameBoard GameBoard) {
		return null;
	}

}
