package com.schutzchess.main;

public class Knight extends GamePiece{

	public Knight(int team, boolean inPlay, int x, int y) {
		super(team, inPlay, x, y);
		this.id = ID.Knight;
	}


	@Override
	public boolean[][] moveable(GameBoard gameboard) {
		Tile[][] board = gameboard.getBoard();
		boolean[][] moveTable = new boolean[GameBoard.HEIGHT][GameBoard.WIDTH];
		
		int[] pos = {y,x,y+1,x-2};
		if((y+1 < GameBoard.HEIGHT && x-2 >= 0) && board[y+1][x-2].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y+1][x-2] = true;
		}
		pos[2] = y+2;
		pos[3] = x-1;
		if((y+2 < GameBoard.HEIGHT && x-1 >= 0) && board[y+2][x-1].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y+2][x-1] = true;
		}
		pos[3] = x+1;
		if((y+2 < GameBoard.HEIGHT && x+1 < GameBoard.WIDTH) && board[y+2][x+1].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y+2][x+1] = true;
		}
		pos[2] = y+1;
		pos[3] = x+2;
		if((y+1 < GameBoard.HEIGHT && x+2 < GameBoard.WIDTH) && board[y+1][x+2].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y+1][x+2] = true;
		}
		pos[2] = y-1;
		if((y-1 >= 0 && x+2 < GameBoard.WIDTH) && board[y-1][x+2].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y-1][x+2] = true;
		}
		pos[2] = y-2;
		pos[3] = x+1;
		if((y-2 >= 0 && x+1 < GameBoard.WIDTH) && board[y-2][x+1].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y-2][x+1] = true;
		}
		pos[3] = x-1;
		if((y-2 >= 0 && x-1 >= 0) && board[y-2][x-1].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y-2][x-1] = true;
		}
		pos[2] = y-1;
		pos[3] = x-2;
		if((y-1 >= 0 && x-2 >= 0) && board[y-1][x-2].getPiece().getTeam() != this.getTeam() &&
				moveIsSafe(gameboard, pos, this.getTeam())) {
			moveTable[y-1][x-2] = true;
		}

		
		return moveTable;
	}

}
