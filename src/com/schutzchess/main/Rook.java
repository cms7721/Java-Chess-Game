package com.schutzchess.main;

public class Rook extends GamePiece {
	private boolean canCastle;

	public Rook(int team, boolean inPlay, int x, int y, boolean castle) {
		super(team, inPlay, x, y);
		this.canCastle = castle;
		this.id = ID.Rook;
	}

	@Override
	public boolean[][] moveable(GameBoard gameboard) {
		Tile[][] board = gameboard.getBoard();
		boolean[][] moveTable = new boolean[GameBoard.HEIGHT][GameBoard.WIDTH];

		//Leftward horizontal
		for (int i=x-1;i>=0;i--) {
			int[] move = {y,x,y,i};
			if(board[y][i].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			if(!moveIsSafe(gameboard,move,this.getTeam())) {
				moveTable[y][i] = false;
			}
			else if(board[y][i].getPiece().getTeam() == NEUTRAL) {
				moveTable[y][i] = true;
			}

			else {
				moveTable[y][i] = true;
				break;
			}

		}	

		//Rightward horizontal
		for (int i=x+1;i<GameBoard.WIDTH;i++) {
			int[] move = {y,x,y,i};
			if(board[y][i].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard,move,this.getTeam())) {
				moveTable[y][i] = false;
			}
			else if(board[y][i].getPiece().getTeam() == NEUTRAL) {
				moveTable[y][i] = true;
			}

			else {
				moveTable[y][i] = true;
				break;
			}
		}

		//Upward vertical
		for (int i=y-1;i>=0;i--) {
			int[] move = {y,x,i,x};
			if(board[i][x].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard,move,this.getTeam())) {
				moveTable[i][x] = false;
			}
			else if(board[i][x].getPiece().getTeam() == NEUTRAL) {
				moveTable[i][x] = true;
			}

			else {
				moveTable[i][x] = true;
				break;
			}
		}	

		//Downward vertical
		for (int i=y+1;i<GameBoard.HEIGHT;i++) {
			int[] move = {y,x,i,x};
			if(board[i][x].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard,move,this.getTeam())) {
				moveTable[i][x] = false;
			}
			else if(board[i][x].getPiece().getTeam() == NEUTRAL) {
				moveTable[i][x] = true;
			}

			else {
				moveTable[i][x] = true;
				break;
			}
		}

		return moveTable;

	}

	public boolean canCastle() {
		return canCastle;
	}

	public void setCastle(boolean canCastle) {
		this.canCastle = canCastle;
	}

}
