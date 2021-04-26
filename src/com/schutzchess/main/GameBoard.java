package com.schutzchess.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import com.schutzchess.main.GamePiece.ID;

public class GameBoard extends MouseAdapter {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2737700553299070859L;

	public static final int WIDTH = 8, HEIGHT = WIDTH;

	private int playerTurn;
	private int selectedRank;
	private int selectedFile;
	private int[] whiteKingPos = {7,4};
	private int[] blackKingPos = {0,4};
	private Tile lastMoved;
	private Pawn pawnDouble;
	private Game game;

	private Tile board[][]; // [0][0] is top left, [7][7] is bottom right,
	// relative to right. first rows(ranks), second columns(files)

	public GameBoard(Game game) {

		//this.handler = handler;
		this.game = game;
		this.board = new Tile[HEIGHT][WIDTH];
		this.playerTurn = GamePiece.WHITE;
		lastMoved = new Tile(false, new NoPiece(), -1, -1, false, false, false);
		selectedRank = -1;
		selectedFile = -1;

		boolean even;
		int team;
		GamePiece piece = null;

		for(int i=0;i<HEIGHT;i++) {			//Setting up initial board
			for(int j=0;j<WIDTH;j++) {

				if((i & 1) == 0) {
					if((j & 1) == 0) {
						even = false;
					}
					else {
						even = true;
					}	
				}
				else {
					if((j & 1) == 0) {
						even = true;
					}
					else {
						even = false;
					}	

				}

				if(i == 0 || i == 1) {
					team = GamePiece.BLACK;
				}
				else if (i == 6 || i == 7) {
					team = GamePiece.WHITE;
				}

				else {
					team = GamePiece.NEUTRAL;
				}


				if(i == 1 || i == 6) {
					piece = new Pawn(team, true, j, i, false);
				}

				else if(i == 0 || i == 7) {
					switch (j) {
					case 0:
						piece = new Rook(team, true, j, i, true);
						break;
					case 1:
						piece = new Knight(team, true, j, i);
						break;
					case 2:
						piece = new Bishop(team, true, j, i);
						break;
					case 3:
						piece = new Queen(team, true, j, i);
						break;
					case 4:
						piece = new King(team, true, j, i, true);
						break;
					case 5:
						piece = new Bishop(team, true, j, i);
						break;
					case 6:
						piece = new Knight(team, true, j, i);
						break;
					case 7:
						piece = new Rook(team, true, j, i, true);
						break;
					}
				}

				else {
					piece = new NoPiece();
				}

				Tile tile = new Tile(even, piece, i, j, false, false, false);
				this.board[i][j] = tile;			
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println("press");
		int mx = e.getX();
		int my = e.getY();
		//System.out.println("press at: " + mx + ", " + my);
		if(mx <= RenderObject.IMAGE_SIDE*GameBoard.WIDTH && my <= RenderObject.IMAGE_SIDE*GameBoard.HEIGHT) {
			int rank = my/64;
			int file = mx/64;

			if(selectedRank != -1) {
				board[selectedRank][selectedFile].setSelected(false);
			}

			//Deselect if already seleted or unmoveable location
			if(rank == selectedRank && file == selectedFile || 
					(!board[rank][file].getMoveable() && board[rank][file].getPiece().getTeam() != playerTurn)) {
				deselect(rank, file);
				game.render();
				return;
			}

			//Select piece
			if(playerTurn == board[rank][file].getPiece().getTeam()) {
				select(rank,file);
			}


			//Move piece if moveable
			else if(this.board[rank][file].getMoveable()) {
				move(rank, file);
			}

		}
		game.render();
		game.mousePressed();

	}

	private void move(int rank, int file) {
		board[rank][file].setPiece(board[selectedRank][selectedFile].getPiece());

		GamePiece piece = board[rank][file].getPiece();
		piece.setX(file);
		piece.setY(rank);

		//Pawn en passant checking and promotion checking
		//En Passant
		if(!Objects.isNull(pawnDouble) && pawnDouble.getTeam() == playerTurn) {
			pawnDouble.setDoubleMoved(false);
			pawnDouble = null;
		}
		if(piece.id == GamePiece.ID.Pawn) {
			Pawn p = (Pawn)piece;
			if(rank == selectedRank+2 || rank == selectedRank - 2) {
				p.setDoubleMoved(true);
				pawnDouble = (Pawn)board[rank][file].getPiece();
			}
			if(file == selectedFile+1 || file == selectedFile - 1) {

				if(p.getTeam() == GamePiece.WHITE) {
					if(board[rank+1][file].getPiece().getID() == ID.Pawn) {
						Pawn dm = (Pawn)board[rank+1][file].getPiece();
						if(dm.getDoubleMoved()) {
							board[rank+1][file].setPiece(new NoPiece());
						}
					}
				}
				else {
					if(board[rank-1][file].getPiece().getID() == ID.Pawn) {
						Pawn dm = (Pawn)board[rank-1][file].getPiece();
						if(dm.getDoubleMoved()) {
							board[rank-1][file].setPiece(new NoPiece());
						}
					}
				}
			}
			if(lastMoved.getPiece().getID() == GamePiece.ID.Pawn) {
				Pawn dm = (Pawn)lastMoved.getPiece();
				dm.setDoubleMoved(false);
			}
			//Pawn Promotion
			if(rank == HEIGHT-1 || rank == 0) {
				//PromotionMenu pmenu = new PromotionMenu(rank, file, board, handler);
				board[rank][file].setPiece(new Queen(p.getTeam(),true,file,rank));
			}

		}

		//Rook Castle Checking
		else if(piece.id == GamePiece.ID.Rook) {
			Rook r = (Rook)piece;
			r.setCastle(false);
		}

		//King checking
		else if(piece.id == GamePiece.ID.King) {
			//updating king pos
			if(piece.getTeam() == GamePiece.WHITE) {
				whiteKingPos[0] = rank;
				whiteKingPos[1] = file;
			}
			else {
				blackKingPos[0] = rank;
				blackKingPos[1] = file;
			}

			//King Castle checking
			King k = (King)piece;
			k.setCastle(false);

			//Castling
			if(file == selectedFile + 2 || file == selectedFile - 2) {
				castle(rank, file);
			}

		}


		//Cleanup
		lastMoved.setLastMoved(false);
		lastMoved = board[rank][file];
		for(int i=0;i<HEIGHT;i++) {
			for(int j=0;j<WIDTH;j++) {
				board[i][j].setMoveable(false);
			}
		}
		board[selectedRank][selectedFile].setLastMoved(true);
		board[selectedRank][selectedFile].setSelected(false);
		board[selectedRank][selectedFile].setPiece(new NoPiece());
		selectedRank = -1;
		selectedFile = -1;
		playerTurn = (playerTurn + 1) % 2;
		inCheck();
	}


	private void castle(int rank, int file) {

		if(rank == HEIGHT-1) {//White castle
			if(file < selectedFile) {//Castle left
				board[rank][0].setPiece(new NoPiece());
				board[rank][3].setPiece(new Rook(GamePiece.WHITE,true,3,rank,false));
			}
			else {//Castle right
				board[rank][WIDTH-1].setPiece(new NoPiece());
				board[rank][5].setPiece(new Rook(GamePiece.WHITE,true,5,rank,false));
			}
		}
		else {//Black Castle
			if(file < selectedFile) {//Castle left
				board[rank][0].setPiece(new NoPiece());
				board[rank][3].setPiece(new Rook(GamePiece.BLACK,true,3,rank,false));
			}
			else {//Castle right
				board[rank][WIDTH-1].setPiece(new NoPiece());
				board[rank][5].setPiece(new Rook(GamePiece.BLACK,true,5,rank,false));
			}
		}

	}


	private void select(int rank, int file) {
		board[rank][file].setSelected(true);
		selectedRank = rank;
		selectedFile = file;
		boolean moves[][] = board[rank][file].getPiece().moveable(this);
		for(int i=0;i<HEIGHT;i++) {
			for(int j=0;j<WIDTH;j++) {
				if(moves[i][j]) {
					board[i][j].setMoveable(true);
				}

				else {
					board[i][j].setMoveable(false);
				}
			}
		}	
	}
	
	private void deselect(int rank, int file) {
		board[rank][file].setSelected(false);
		selectedRank = -1;
		selectedFile = -1;
		for(int i=0;i<HEIGHT;i++) {
			for(int j=0;j<WIDTH;j++) {
				board[i][j].setMoveable(false);
			}
		}
	}

	public void inCheck() {

		//Test white check first
		Tile loc = board[whiteKingPos[0]][whiteKingPos[1]];
		King k = (King)loc.getPiece();
		//System.out.println("i: " + whiteKingPos[0] + ", j: " + whiteKingPos[1]);
		boolean setCheck = false;

		//Check Diagonals first
		//Top Left diagonal
		int i=loc.getRank()-1;
		int j=loc.getFile()-1;
		while(i>=0 && j>=0) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}			
				if((i==loc.getRank()-1 && j==loc.getFile()-1) && 
						(cur.getID() == ID.Pawn || cur.getID() == ID.King)) {
					setCheck = true;
				}
				break;
			}
			i--;
			j--;
		}

		//Top Right diagonal
		i=loc.getRank()-1;
		j=loc.getFile()+1;
		while(i>=0 && j<WIDTH) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}			
				if((i==loc.getRank()-1 && j==loc.getFile()+1) && 
						(cur.getID() == ID.Pawn || cur.getID() == ID.King)) {
					setCheck = true;
				}
				break;
			}
			i--;
			j++;
		}
		//Bottom Right Diagonal
		i=loc.getRank()+1;
		j=loc.getFile()+1;
		while(i<HEIGHT && j<WIDTH) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if((i==loc.getRank()+1 && j==loc.getFile()+1) && cur.getID() == ID.King) {
					setCheck = true;
				}
				break;
			}
			i++;
			j++;
		}

		//Bottom Left diagonal
		i=loc.getRank()+1;
		j=loc.getFile()-1;
		while(i<HEIGHT && j>=0) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if((i==loc.getRank()+1 && j==loc.getFile()-1) && cur.getID() == ID.King) {
					setCheck = true;
				}
				break;
			}
			i++;
			j--;
		}

		//Horizontal/Verticals

		//Bottom Vertical
		for(i=k.y+1;i<HEIGHT;i++) {
			GamePiece cur = board[i][loc.getFile()].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getRank()+1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Top vertical
		for(i=k.y-1;i>=0;i--) {
			GamePiece cur = board[i][loc.getFile()].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getRank()-1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Right Horizontal
		for(i=k.x+1;i<WIDTH;i++) {
			GamePiece cur = board[k.y][i].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getFile()+1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Left Horizontal
		for(i=k.x-1;i>=0;i--) {
			GamePiece cur = board[k.y][i].getPiece();
			if(cur.getTeam() == GamePiece.WHITE) {
				break;
			}
			else if(cur.getTeam() == GamePiece.BLACK) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getFile()-1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Knight checking
		if(k.y+1 < HEIGHT && k.x+2 < WIDTH && board[k.y+1][k.x+2].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y+1][k.x+2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y+2 < HEIGHT && k.x+1 < WIDTH && board[k.y+2][k.x+1].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y+2][k.x+1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y+1 < HEIGHT && k.x-2 >= 0 && board[k.y+1][k.x-2].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y+1][k.x-2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y+2 < HEIGHT && k.x-1 >= 0 && board[k.y+2][k.x-1].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y+2][k.x-1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-2 >= 0 && k.x+1 < WIDTH && board[k.y-2][k.x+1].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y-2][k.x+1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-1 >= 0 && k.x+2 < WIDTH && board[k.y-1][k.x+2].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y-1][k.x+2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-2 >= 0 && k.x-1 >= 0 && board[k.y-2][k.x-1].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y-2][k.x-1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-1 >= 0 && k.x-2 >= 0 && board[k.y-1][k.x-2].getPiece().getTeam() == GamePiece.BLACK &&
				board[k.y-1][k.x-2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}

		loc.setCheck(setCheck);
		k.setCheck(setCheck);

		//Black check
		loc = board[blackKingPos[0]][blackKingPos[1]];
		k = (King)loc.getPiece();
		setCheck = false;

		//Check Diagonals first
		//Top Left diagonal
		i=loc.getRank()-1;
		j=loc.getFile()-1;
		while(i>=0 && j>=0) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if((i==loc.getRank()-1 && j==loc.getFile()-1) && cur.getID() == ID.King) {
					setCheck = true;
				}
				break;
			}
			i--;
			j--;
		}

		//Top Right diagonal
		i=loc.getRank()-1;
		j=loc.getFile()+1;
		while(i>=0 && j<WIDTH) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if((i==loc.getRank()+1 && j==loc.getFile()-1) && cur.getID() == ID.King) {
					setCheck = true;
				}
				break;
			}
			i--;
			j++;
		}
		//Bottom Right Diagonal
		i=loc.getRank()+1;
		j=loc.getFile()+1;
		while(i<HEIGHT && j<WIDTH) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}			
				if((i==loc.getRank()+1 && j==loc.getFile()+1) && 
						(cur.getID() == ID.Pawn || cur.getID() == ID.King)) {
					setCheck = true;
				}
				break;
			}
			i++;
			j++;
		}

		//Bottom Left diagonal
		i=loc.getRank()+1;
		j=loc.getFile()-1;
		while(i<HEIGHT && j>=0) {
			GamePiece cur = board[i][j].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Bishop || cur.getID() == ID.Queen) {
					setCheck = true;
				}			
				if((i==loc.getRank()+1 && j==loc.getFile()-1) && 
						(cur.getID() == ID.Pawn || cur.getID() == ID.King)) {
					setCheck = true;
				}	
				break;
			}
			i++;
			j--;
		}

		//Horizontal/Verticals

		//Bottom Vertical
		for(i=k.y+1;i<HEIGHT;i++) {
			GamePiece cur = board[i][loc.getFile()].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getRank()+1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Top vertical
		for(i=k.y-1;i>=0;i--) {
			GamePiece cur = board[i][loc.getFile()].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}if(i==loc.getRank()-1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Right Horizontal
		for(i=k.x+1;i<WIDTH;i++) {
			GamePiece cur = board[k.y][i].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getFile()+1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Left Horizontal
		for(i=k.x-1;i>=0;i--) {
			GamePiece cur = board[k.y][i].getPiece();
			if(cur.getTeam() == GamePiece.BLACK) {
				break;
			}
			else if(cur.getTeam() == GamePiece.WHITE) {
				if(cur.getID() == ID.Rook || cur.getID() == ID.Queen) {
					setCheck = true;
				}
				if(i==loc.getFile()-1 && cur.getID() == ID.King) {
					setCheck = true;
				}

				break;
			}
		}

		//Knight checking
		if(k.y+1 < HEIGHT && k.x+2 < WIDTH && board[k.y+1][k.x+2].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y+1][k.x+2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y+2 < HEIGHT && k.x+1 < WIDTH && board[k.y+2][k.x+1].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y+2][k.x+1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y+1 < HEIGHT && k.x-2 >= 0 && board[k.y+1][k.x-2].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y+1][k.x-2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y+2 < HEIGHT && k.x-1 >= 0 && board[k.y+2][k.x-1].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y+2][k.x-1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-2 >= 0 && k.x+1 < WIDTH && board[k.y-2][k.x+1].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y-2][k.x+1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-1 >= 0 && k.x+2 < WIDTH && board[k.y-1][k.x+2].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y-1][k.x+2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-2 >= 0 && k.x-1 >= 0 && board[k.y-2][k.x-1].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y-2][k.x-1].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}
		if(k.y-1 >= 0 && k.x-2 >= 0 && board[k.y-1][k.x-2].getPiece().getTeam() == GamePiece.WHITE &&
				board[k.y-1][k.x-2].getPiece().getID() == ID.Knight) {
			setCheck = true;	
		}

		loc.setCheck(setCheck);
		k.setCheck(setCheck);


	}

	public Tile[][] getBoard() {
		return board;
	}

	public void setBoard(Tile[][] board) {
		this.board = board;
	}

	public int[] getKingPos(int team) {
		if (team == GamePiece.WHITE) {
			return whiteKingPos;
		}
		else {
			return blackKingPos;
		}
	}

	public void setKingPos(int team, int[] pos) {
		if (team == GamePiece.WHITE) {
			whiteKingPos = pos;
		}
		else {
			blackKingPos = pos;
		}
	}


}
