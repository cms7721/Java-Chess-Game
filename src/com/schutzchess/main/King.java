package com.schutzchess.main;

public class King extends GamePiece {
	//TODO check/checkmate checking
	private boolean canCastle;
	private boolean inCheck;

	public King(int team, boolean inPlay, int x, int y, boolean castle) {
		super(team, inPlay, x, y);
		this.canCastle = castle;
		this.inCheck = false;
		this.id = ID.King;
	}

	@Override
	public boolean[][] moveable(GameBoard gameboard) {
		Tile[][] board = gameboard.getBoard();

		boolean[][] moveTable = new boolean[GameBoard.HEIGHT][GameBoard.WIDTH];

		//Loop through each row from top to bottom, left to right
		for(int i=y-1;i<=y+1;i++) {
			int[] move = {y,x,i,x-1};

			//Top row
			if(x-1>=0 && move[2] >= 0 && move[2] < GameBoard.HEIGHT && board[i][x-1].getPiece().getTeam() != this.getTeam() &&
					moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][x-1] = true;
			}

			//Middle row, skip current positon
			move[3]++;
			if(!(y==i) && move[2] >= 0 && move[2] < GameBoard.HEIGHT && board[i][x].getPiece().getTeam() != this.getTeam() &&
					moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][x] = true;
			}

			//Bottom Row
			move[3]++;
			if(x+1<GameBoard.WIDTH && move[2] >= 0 && move[2] < GameBoard.HEIGHT &&
					board[i][x+1].getPiece().getTeam() != this.getTeam() && moveIsSafe(gameboard, move, this.getTeam())) {
				moveTable[i][x+1] = true;
			}
		}

		//Castling checks
		if(this.canCastle && !this.inCheck) {
			boolean castle = true;

			//Check left castle
			if(board[y][0].getPiece().getID() == ID.Rook) {
				Rook r = (Rook)board[y][0].getPiece();
				if (r.getTeam() == this.getTeam() && r.canCastle()) {
					for(int i=3;i>=1;i--) {
						int[] move = {y,x,y,i};
						if(board[y][i].getPiece().getTeam() != NEUTRAL || !moveIsSafe(gameboard,move,this.getTeam())) {
							castle = false;
							break;
						}
					}
				}
				else {
					castle = false;
				}
			}
			else {
				castle = false;
			}

			if(castle) {
				moveTable[y][x-2] = true;
			}

			//Check right castle
			castle = true;
			if(board[y][GameBoard.WIDTH-1].getPiece().getID() == ID.Rook) {
				Rook r = (Rook)board[y][GameBoard.WIDTH-1].getPiece();
				if (r.getTeam() == this.getTeam() && r.canCastle()) {
					for(int i=5;i<=6;i++) {
						int[] move = {y,x,y,i};
						if(board[y][i].getPiece().getTeam() != NEUTRAL || !moveIsSafe(gameboard,move,this.getTeam())) {
							castle = false;
							break;
						}
					}
				}
				else {
					castle = false;
				}
			}
			else {
				castle = false;
			}

			if(castle) {
				moveTable[y][x+2] = true;
			}
		}

		return moveTable;
	}


	public boolean getCastle() {
		return canCastle;
	}

	public void setCastle(boolean canCastle) {
		this.canCastle = canCastle;
	}

	public boolean getCheck() {
		return inCheck;
	}

	public void setCheck(boolean check) {
		inCheck = check;
	}

}
