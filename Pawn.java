package chess;

import java.util.HashMap;

/**
 * This class handles various movement rules for the Pawn class.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Pawn extends Piece{

	/**
	 * Names the pawn piece. It should be wp or bp.
	 * 
	 * @param pieceName	Name of piece.
	 */
	public Pawn(String pieceName) {
		super(pieceName);
	}

	// Determines if a pawn piece can be taken by En Passant
	boolean canBeEnPassanted = false;

	/**
	 * Determines if the movement for the Pawn Piece is valid.
	 * Takes into account various movement rules of the Pawn class.
	 * 
	 * @param b	The board class instance that holds various pieces of information, including board HashMap
	 * @param currentPosition	The position the Pawn is moving from
	 * @param nextPosition	The position the Pawn is moving to
	 * @return	True or false depending on if the move is valid
	 */
	@Override
	public boolean isMoveValid(Board b, String currentPosition, String nextPosition) {
		char currentAlph = currentPosition.charAt(0);	// 'h' or 'b'
		int currentNum = currentPosition.charAt(1)-'0';		// 1 - 8
		char nextAlph = nextPosition.charAt(0);
		int nextNum = nextPosition.charAt(1)-'0';
		String pieceAtCurrentPosition = b.board.get(currentPosition).getPieceName();
		String PieceAtNextPosition = b.board.get(nextPosition).getPieceName();

		// Checks whether coordinates are valid
		if(!(b.board.containsKey(currentPosition)) || !(b.board.containsKey(nextPosition))) {
			return false;
		}

		if(pieceAtCurrentPosition.charAt(0)== 'w') { // white piece
			if(currentNum >= nextNum) {	// white pawn can't go backwards
				return false;
			}

			// Pawns can never move more than two spaces
			if (nextNum - currentNum > 2) {
				return false;
			}

			if(currentAlph != nextAlph) {	// pawn can only go straight up unless diagonal kill

				// Checking for en passant
				if (enPassantCheck(b.board, currentPosition, nextPosition) && isCoordinateEmpty(b.board, nextAlph, nextNum)) {
					StringBuilder builder = new StringBuilder();
					builder.append(nextAlph);
					builder.append(nextNum-1);
					String pawnTakenOut = builder.toString();

					if(Board.isBlackTile(nextAlph, nextNum-1)) {
						b.board.put(pawnTakenOut, new EmptyTile("##"));
					} else {
						b.board.put(pawnTakenOut, new EmptyTile("  "));
					}

					return true;
				}

				if((nextNum - currentNum) != 1 || (Math.abs(currentAlph- nextAlph) != 1 ) || isCoordinateEmpty(b.board, nextAlph, nextNum)) {
					return false;
				}
				if(PieceAtNextPosition.charAt(0) == 'w') {	// w can't kill w
					return false;
				}

				return true;	// attack
			}

			if((nextNum - currentNum) == 2) {	// two steps
				if(currentNum != 2) {	// 2 steps is allowed only in the first move
					return false;
				}

				// pawn can move only if the designated position is empty
				if(!(isPathEmpty(b.board, currentPosition, nextPosition))) {
					return false;
				}

				canBeEnPassanted = true;

				return true;	// 2 steps allowed
			}


			if((nextNum - currentNum) == 1) {	// 1 step forward
				if(isPathEmpty(b.board, currentPosition, nextPosition)) {
					return true;
				}
				return false;
			}
			return true;

		} else {	// black piece
			if(currentNum <= nextNum) { // black can't go backwards
				return false;
			}

			// Pawns can never move more than two spaces
			if (currentNum - nextNum > 2)
			{
				return false;
			}

			if(currentAlph != nextAlph) { // if not straight, it's a diagonal kill

				if (enPassantCheck(b.board, currentPosition, nextPosition) && isCoordinateEmpty(b.board, nextAlph, nextNum)) {
					StringBuilder builder = new StringBuilder();
					builder.append(nextAlph);
					builder.append(nextNum+1);
					String pawnTakenOut = builder.toString();

					if(Board.isBlackTile(nextAlph, nextNum+1)) {
						b.board.put(pawnTakenOut, new EmptyTile("##"));
					} else {
						b.board.put(pawnTakenOut, new EmptyTile("  "));
					}

					return true;
				}

				if((currentNum-nextNum)!=1 || Math.abs((currentAlph-nextAlph))!=1 || isCoordinateEmpty(b.board, nextAlph, nextNum)) {
					//pawn cannot move diagonal if not a kill
					return false;
				}

				if(PieceAtNextPosition.charAt(0) == 'b') {	// w can't kill w
					return false;
				}

				return true;

			}// diagonal done

			if((currentNum - nextNum) == 2) { //two step move, has to be done in row 7
				if(nextNum != 5) {
					// pawn can only move two steps in first move
					return false;
				}
				if(!(isPathEmpty(b.board, currentPosition, nextPosition))) {
					// pawn can only move two steps when path is clear
					return false;
				}

				canBeEnPassanted = true;

				// move two steps
				return true;
			}

			// one step
			if((currentNum - nextNum) == 1) {
				// path has to be empty
				if(isPathEmpty(b.board, currentPosition, nextPosition)) {
					// pawn can only move two steps when path is clear
					return true;
				}
				return false;
			}
		}
		return false;

	}

	/**
	 * Determines if the movement for the Pawn Piece is valid.
	 * Takes into account various movement rules of the Pawn class.
	 * Same method as above, but with different arguments.
	 * 
	 * @param board	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Pawn is moving from
	 * @param nextPosition	The position the Pawn is moving to
	 * @return	True or false depending on if the move is valid
	 */
	@Override
	public boolean isMoveValid(HashMap<String,Piece> board, String currentPosition, String nextPosition) {
		char currentAlph = currentPosition.charAt(0);	// 'h' or 'b'
		int currentNum = currentPosition.charAt(1)-'0';		// 1 - 8
		char nextAlph = nextPosition.charAt(0);
		int nextNum = nextPosition.charAt(1)-'0';
		String pieceAtCurrentPosition = board.get(currentPosition).getPieceName();
		String PieceAtNextPosition = board.get(nextPosition).getPieceName();

		// Checks whether coordinates are valid
		if(!(board.containsKey(currentPosition)) || !(board.containsKey(nextPosition))) {
			return false;
		}

		if(pieceAtCurrentPosition.charAt(0)== 'w') { // white piece
			if(currentNum >= nextNum) {	// white pawn can't go backwards
				return false;
			}

			// Pawns can never move more than two spaces
			if (nextNum - currentNum > 2)
			{
				return false;
			}

			// Checking for en passant
			if (enPassantCheck(board, currentPosition, nextPosition) && isCoordinateEmpty(board, nextAlph, nextNum)) {
				StringBuilder builder = new StringBuilder();
				builder.append(nextAlph);
				builder.append(nextNum-1);
				String pawnTakenOut = builder.toString();

				if(Board.isBlackTile(nextAlph, nextNum-1)) {
					board.put(pawnTakenOut, new EmptyTile("##"));
				} else {
					board.put(pawnTakenOut, new EmptyTile("  "));
				}

				return true;
			}

			if(currentAlph != nextAlph) {	// pawn can only go straight up unless diagonal kill
				if((nextNum - currentNum) != 1 || (Math.abs(currentAlph- nextAlph) != 1 ) || isCoordinateEmpty(board, nextAlph, nextNum)) {
					return false;
				}
				if(PieceAtNextPosition.charAt(0) == 'w') {	// w can't kill w
					return false;
				}

				return true;	// attack
			}

			if((nextNum - currentNum) == 2) {	// two steps
				if(currentNum != 2) {	// 2 steps is allowed only in the first move
					return false;
				}

				// pawn can move only if the designated position is empty
				if(!(isPathEmpty(board, currentPosition, nextPosition))) {
					return false;
				}

				canBeEnPassanted = true;

				return true;	// 2 steps allowed
			}


			if((nextNum - currentNum) == 1) {	// 1 step forward
				if(isPathEmpty(board, currentPosition, nextPosition)) {
					return true;
				}
				return false;
			}
			return true;

		} else {	// black piece
			if(currentNum <= nextNum) { // black can't go backwards
				return false;
			}

			// Pawns can never move more than two spaces
			if (currentNum - nextNum > 2) {
				return false;
			}

			if(currentAlph != nextAlph) { // if not straight, it's a diagonal kill

				if (enPassantCheck(board, currentPosition, nextPosition) && isCoordinateEmpty(board, nextAlph, nextNum)) {
					StringBuilder builder = new StringBuilder();
					builder.append(nextAlph);
					builder.append(nextNum+1);
					String pawnTakenOut = builder.toString();

					if(Board.isBlackTile(nextAlph, nextNum+1)) {
						board.put(pawnTakenOut, new EmptyTile("##"));
					} else {
						board.put(pawnTakenOut, new EmptyTile("  "));
					}

					return true;
				}

				if((currentNum-nextNum)!=1 || Math.abs((currentAlph-nextAlph))!=1 || isCoordinateEmpty(board, nextAlph, nextNum)) {
					//pawn cannot move diagonal if not a kill
					return false;
				}

				if(PieceAtNextPosition.charAt(0) == 'b') {	// w can't kill w
					return false;
				}

				return true;

			}// diagonal done

			if((currentNum - nextNum) == 2) { //two step move, has to be done in row 7
				if(nextNum != 5) {
					// pawn can only move two steps in first move
					return false;
				}
				if(!(isPathEmpty(board, currentPosition, nextPosition))) {
					// pawn can only move two steps when path is clear
					return false;
				}

				canBeEnPassanted = true;

				// move two steps
				return true;
			}

			// one step
			if((currentNum - nextNum) == 1) {
				// path has to be empty
				if(isPathEmpty(board, currentPosition, nextPosition)) {
					// pawn can only move two steps when path is clear
					return true;
				}
				return false;
			}
		}
		return false;
	}

	/**
	 * Helps us determine if an En Passant is being attempted.
	 * A piece is available to be En Passanted for only one turn.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Pawn is moving from
	 * @param nextPosition	The position the Pawn is moving to
	 * @return	True or false depending on if the En Passant attempt is valid
	 */
	public boolean enPassantCheck(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		char currentAlph = currentPosition.charAt(0);
		int currentNum = currentPosition.charAt(1)-'0';
		char nextAlph = nextPosition.charAt(0);
		int nextNum = nextPosition.charAt(1)-'0';
		String positionAboveOrBelowPawn = "";
		StringBuilder builder = new StringBuilder();

		if(Board.is_white) {
			if (currentPosition.equals("a5") || currentPosition.equals("b5") ||
					currentPosition.equals("c5") || currentPosition.equals("d5") || currentPosition.equals("e5") ||
					currentPosition.equals("f5") || currentPosition.equals("g5") || currentPosition.equals("h5") &&
					Math.abs((currentAlph-nextAlph)) == 1 && nextNum - currentNum == 1) {
				int rowBelowNextPosition = nextNum - 1;
				builder.append(nextAlph);
				builder.append(rowBelowNextPosition);
				positionAboveOrBelowPawn = builder.toString();

				if (boardgame.get(positionAboveOrBelowPawn) instanceof Pawn) {
					if (((Pawn) boardgame.get(positionAboveOrBelowPawn)).canBeEnPassanted) {
						return true;
					}
				}
			}
		} else {
			if (currentPosition.equals("a4") || currentPosition.equals("b4") ||
					currentPosition.equals("c4") || currentPosition.equals("d4") || currentPosition.equals("e4") ||
					currentPosition.equals("f4") || currentPosition.equals("g4") || currentPosition.equals("h4") &&
					Math.abs((currentAlph-nextAlph)) == 1 && currentNum - nextNum == 1) {
				int rowAboveNextPosition = nextNum + 1;
				builder.append(nextAlph);
				builder.append(rowAboveNextPosition);
				positionAboveOrBelowPawn = builder.toString();

				if (boardgame.get(positionAboveOrBelowPawn) instanceof Pawn) {
					if (((Pawn) boardgame.get(positionAboveOrBelowPawn)).canBeEnPassanted) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Determines if the path inbetween the positions the Pawn is travelling is empty.
	 * 
	 * @param board	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Pawn is moving from
	 * @param nextPosition	The position the Pawn is moving to
	 * @return	true or false depending on if there are pieces present
	 */
	public boolean isPathEmpty(HashMap<String,Piece> board, String currentPosition, String nextPosition) {
		char currentAlph = currentPosition.charAt(0);	// 'w' or 'b'
		int currentNum = Character.getNumericValue(currentPosition.charAt(1));		// 1 - 8
		int nextNum = Character.getNumericValue(nextPosition.charAt(1));

		char currentColor = board.get(currentPosition).pieceName.charAt(0);

		if(currentColor == 'w') { // for white pawn
			for(int i = currentNum+1; i<=nextNum; i++) {	// takes care of 2 steps too
				if(!isCoordinateEmpty(board, currentAlph, i)) {
					return false;
				}
			}

		}

		if(currentColor =='b') {	// for black pawn
			for(int i = currentNum-1; i>=nextNum; i--) {
				if(!isCoordinateEmpty(board, currentAlph, i)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks designated coordinate is empty for pawn to go
	 * @param board	Hashmap of board
	 * @param alph	alphabet part of coordinate
	 * @param num	number part of coordinate
	 * @return True or false, depending on if the coordinate is empty
	 */
	public boolean isCoordinateEmpty(HashMap<String,Piece> board, char alph, int num ) {
		StringBuilder builder = new StringBuilder();
		builder.append(alph);
		builder.append(num);
		String coordinate = builder.toString();
		if(board.get(coordinate).getPieceName().equals("##") || board.get(coordinate).getPieceName().equals("  ")) {
			return true;
		}

		return false;
	}

	/**
	 * Handles any eligible pawn promotions. If there is no letter given, the piece is automatically promoted to Queen.
	 *
	 * @param nextPosition Where the pawn is going
	 * @param promoPiece What the pawn is going to become
	 * @param boardgame HashMap of the board
	 */
	public void pawnPromotion(String nextPosition, String promoPiece, HashMap<String,Piece> boardgame) {
		char newPos = nextPosition.charAt(1);

		Piece promoted;
		if(newPos=='8') {	// for white
			if(promoPiece.equalsIgnoreCase("R")) {
				promoted = new Rook("wR");
			}
			else if(promoPiece.equalsIgnoreCase("N")) {
				promoted = new Knight("wN");
			}
			else if(promoPiece.equalsIgnoreCase("B")) {
				promoted = new Bishop("wB");
			}
			else if(promoPiece.equalsIgnoreCase("Q")) {
				promoted = new Queen("wQ");
			}else {
				promoted = new Queen("wQ");// default to set it as Queen
			}
			boardgame.put(nextPosition,promoted);
		} // end white

		if(newPos=='1') {	// for black
			if(promoPiece.equalsIgnoreCase("R")) {
				promoted = new Rook("bR");
			}
			else if(promoPiece.equalsIgnoreCase("N")) {
				promoted = new Knight("bN");
			}
			else if(promoPiece.equalsIgnoreCase("B")) {
				promoted = new Bishop("bB");
			}
			else if(promoPiece.equalsIgnoreCase("Q")) {
				promoted = new Queen("bQ");
			}else {
				promoted = new Queen("bQ");// default to set it as Queen
			}
			boardgame.put(nextPosition,promoted);
		} // end black

	}

	/**
	 * This function is basically handled in the isMoveValid function, verifying that nothing is blocking the pawn.
	 *
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Pawn is moving from
	 * @param nextPosition	The position the Pawn is moving to
	 * @return	Returns false since the above methods determine whether or not the Pawn is blocked.
	 */
	@Override
	public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition)
	{
		// This method is checked in the previous methods.
		// Therefore, no need to double check.

		return false;
	}


}