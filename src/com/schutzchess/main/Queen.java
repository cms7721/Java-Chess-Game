package com.schutzchess.main;

public class Queen extends GamePiece {

	public Queen(int team, boolean inPlay, int x, int y) {
		super(team, inPlay, x, y);
		this.id = ID.Queen;
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

		//Leftward horizontal
		for (i=x-1;i>=0;i--) {
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
		for (i=x+1;i<GameBoard.WIDTH;i++) {
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
		for (i=y-1;i>=0;i--) {
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
		for (i=y+1;i<GameBoard.HEIGHT;i++) {
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

}
