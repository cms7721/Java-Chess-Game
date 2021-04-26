package com.schutzchess.main;

public class Bishop extends GamePiece {

	public Bishop(int team, boolean inPlay, int x, int y) {
		super(team, inPlay, x, y);
		this.id = ID.Bishop;
	}

	@Override
	public boolean[][] moveable(GameBoard gameboard) {
		Tile[][] board = gameboard.getBoard();
		boolean[][] moveTable = new boolean[GameBoard.HEIGHT][GameBoard.WIDTH];

		int i;
		int j;

		//Top Left diagonal
		j = x - 1;
		i = y - 1;
		while (j >= 0 && i >= 0) {
			int[] move = {y,x,i,j};
			if(board[i][j].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][j] = false;
			}
			else if (board[i][j].getPiece().getTeam() == NEUTRAL) {
				moveTable[i][j] = true;
			}

			else {
				moveTable[i][j] = true;
				break;
			}

			i--;
			j--;
		}

		//Bottom Left diagonal
		j = x - 1;
		i = y + 1;
		while (i < GameBoard.HEIGHT && j >= 0) {
			int[] move = {y,x,i,j};
			if(board[i][j].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][j] = false;
			}
			else if (board[i][j].getPiece().getTeam() == NEUTRAL) {
				moveTable[i][j] = true;
			}

			else {
				moveTable[i][j] = true;
				break;
			}

			i++;
			j--;
		}

		//Bottom Right diagonal
		j = x + 1;
		i = y + 1;
		while (i < GameBoard.HEIGHT && j < GameBoard.WIDTH) {

			int[] move = {y,x,i,j};
			if(board[i][j].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][j] = false;
			}
			else if (board[i][j].getPiece().getTeam() == NEUTRAL) {
				moveTable[i][j] = true;
			}

			else {
				moveTable[i][j] = true;
				break;
			}

			i++;
			j++;
		}

		//Top Right diagonal
		j = x + 1;
		i = y - 1;
		while (i >= 0 && j < GameBoard.HEIGHT) {

			int[] move = {y,x,i,j};
			if(board[i][j].getPiece().getTeam() == this.getTeam()) {
				break;
			}
			else if(!moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][j] = false;
			}
			else if (board[i][j].getPiece().getTeam() == NEUTRAL) {
				moveTable[i][j] = true;
			}

			else {
				moveTable[i][j] = true;
				break;
			}

			i--;
			j++;
		}

		return moveTable;
	}

}
