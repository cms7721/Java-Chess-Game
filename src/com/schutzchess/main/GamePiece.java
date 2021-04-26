package com.schutzchess.main;


public abstract class GamePiece {
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int NEUTRAL = 2;
	
	private int team;	//0 is white, 1 is black, 2 is neutral (for empty)
	protected ID id;
	private boolean inPlay;
	protected int x;
	protected int y;
	protected enum ID {
		Pawn,
		Rook,
		Knight,
		Bishop,
		Queen,
		King,
		NoPiece;
	}
	

	
	public GamePiece(int team, boolean inPlay, int x, int y) {
		this.team = team;
		this.inPlay = inPlay;
		this.x = x;
		this.y = y;
	}
	
	public GamePiece() {
		this.team = 2;
		this.inPlay = true;
	}
	
	
	public abstract boolean[][] moveable(GameBoard gameboard);
	
	public void setPlay(boolean play) {
		this.inPlay = play;
	}
	
	public boolean getPlay() {
		return this.inPlay;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public int getTeam() {
		return this.team;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public ID getID() {
		return this.id;
	}
	
	public static boolean moveIsSafe(GameBoard gameboard, int[] move, int team) {
		Tile[][] board = gameboard.getBoard();
		King king;
		boolean safe;
		GamePiece temp = board[move[2]][move[3]].getPiece();
		GamePiece original = board[move[0]][move[1]].getPiece();
		//Simulate move
		board[move[2]][move[3]].setPiece(original);
		board[move[0]][move[1]].setPiece(new NoPiece());
		board[move[2]][move[3]].getPiece().setX(move[3]);
		board[move[2]][move[3]].getPiece().setY(move[2]);
		int[] pos = {move[2],move[3]};
		if(original.getID() == ID.King) {
			gameboard.setKingPos(team, pos);
		}
		gameboard.inCheck();

		//Test if move creates check
		king = (King)board[gameboard.getKingPos(team)[0]][gameboard.getKingPos(team)[1]].getPiece();
		if(king.getCheck()) {
			safe = false;
		}
		else {
			safe = true;
		}
		
		//restore gameboard
		if(original.getID() == ID.King) {
			pos[0] = move[0];
			pos[1] = move[1];
			gameboard.setKingPos(team, pos);
		}
		board[move[0]][move[1]].setPiece(original);
		original.setX(move[1]);
		original.setY(move[0]);
		board[move[2]][move[3]].setPiece(temp);
		gameboard.inCheck();
		return safe;

	}
	
	
	
	
}
