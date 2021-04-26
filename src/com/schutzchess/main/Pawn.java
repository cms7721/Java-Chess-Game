package com.schutzchess.main;

public class Pawn extends GamePiece {

	private boolean doubleMoved;

	public Pawn(int team, boolean inPlay, int x, int y, boolean doubleMoved) {
		super(team, inPlay, x, y);
		this.doubleMoved = doubleMoved;
		this.id = ID.Pawn;
	}

	@Override
	public boolean[][] moveable(GameBoard gameboard) {
		Tile[][] board = gameboard.getBoard();

		boolean[][] moveTable = new boolean[GameBoard.HEIGHT][GameBoard.WIDTH];
		
		int[] move = {y,x,y+2,x};
		if (this.getTeam() == BLACK) {
			if (y == 1) {
				if (board[y + 2][x].getPiece().getTeam() == NEUTRAL && board[y+2][x].getPiece().getTeam() == NEUTRAL && 
						moveIsSafe(gameboard,move,BLACK)) {
					moveTable[y + 2][x] = true;
				}
			}
			move[2] = y+1;
			if (y+1 < GameBoard.HEIGHT && board[y + 1][x].getPiece().getTeam() == NEUTRAL && moveIsSafe(gameboard,move,BLACK)) {
				moveTable[y + 1][x] = true;
			}

			// Attack diagonally moves
			move[3] = x-1;
			if (x-1>=0 && y+1 < GameBoard.HEIGHT && board[y + 1][x - 1].getPiece().getTeam() == WHITE &&
					moveIsSafe(gameboard, move, BLACK)) {
				moveTable[y + 1][x - 1] = true;
			}
			move[3] = x+1;
			if (x+1 < GameBoard.WIDTH && y+1 < GameBoard.HEIGHT && board[y + 1][x + 1].getPiece().getTeam() == WHITE &&
					moveIsSafe(gameboard, move, BLACK)) {
				moveTable[y + 1][x + 1] = true;
			}

			// en passant
			move[3] = x-1;
			if (x-1>=0 && board[y][x - 1].getPiece().getID() == ID.Pawn) {
				if (((Pawn) board[y][x - 1].getPiece()).getDoubleMoved() && moveIsSafe(gameboard,move,BLACK)) {
					moveTable[y + 1][x - 1] = true;
				}
			}
			move[3] = x+1;
			if (x+1 < GameBoard.WIDTH && board[y][x + 1].getPiece().getID() == ID.Pawn && moveIsSafe(gameboard,move,BLACK)) {
				if (((Pawn) board[y][x + 1].getPiece()).getDoubleMoved()) {
					moveTable[y + 1][x + 1] = true;
				}
			}

		}

		else { // Same thing but reversed for white pawns
			move[2] = y-2;
			if (y == 6) {
				if (board[y - 2][x].getPiece().getTeam() == NEUTRAL && board[y-1][x].getPiece().getTeam() == NEUTRAL && 
						moveIsSafe(gameboard,move,WHITE)) {
					moveTable[y - 2][x] = true;
				}
			}
			move[2] = y-1;
			if (y-1 >= 0 && board[y - 1][x].getPiece().getTeam() == NEUTRAL && 
					moveIsSafe(gameboard,move,WHITE)) {
				moveTable[y - 1][x] = true;
			}
			move[3] = x-1;
			if (x-1 >= 0 && y-1 >= 0 && board[y - 1][x - 1].getPiece().getTeam() == BLACK && 
					moveIsSafe(gameboard,move,WHITE)) {
				moveTable[y - 1][x - 1] = true;
			}
			move[3] = x+1;
			if (x+1 < GameBoard.WIDTH && y-1 >= 0 && board[y - 1][x + 1].getPiece().getTeam() == BLACK && 
					moveIsSafe(gameboard,move,WHITE)) {
				moveTable[y - 1][x + 1] = true;
			}
			move[3] = x-1;
			if (x-1>=0 && board[y][x - 1].getPiece().getID() == ID.Pawn) {
				if (((Pawn) board[y][x - 1].getPiece()).getDoubleMoved() && moveIsSafe(gameboard,move,WHITE)) {
					moveTable[y - 1][x - 1] = true;
				}
			}
			move[3] = x+1;
			if (x+1 < GameBoard.WIDTH && board[y][x + 1].getPiece().getID() == ID.Pawn && moveIsSafe(gameboard,move,WHITE)) {
				if (((Pawn) board[y][x + 1].getPiece()).getDoubleMoved()) {
					moveTable[y - 1][x + 1] = true;
				}
			}

		}

		return moveTable;
	}

	public boolean getDoubleMoved() {
		return doubleMoved;
	}

	public void setDoubleMoved(boolean doubleMoved) {
		this.doubleMoved = doubleMoved;
	}

}
