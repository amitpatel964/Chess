package chess;

/**
 * Many of the important castling methods are contained within this class.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Castling {
	public static boolean canWhiteKingCastle = true;
	public static boolean canBlackKingCastle = true;
	public static boolean whiteKingLeftCastle = true;
	public static boolean whiteKingRightCastle = true;
	public static boolean blackKingLeftCastle = true;
	public static boolean blackKingRightCastle = true;
	
	/**
	 * If a king moves, castling can no longer be done with that player.
	 * 
	 * @param coordinate Checks to see if white or black's king has moved
	 */
	public static void kingMoved(String coordinate) {
		if (coordinate.equals("e1")) {
			canWhiteKingCastle = false;
		} else if (coordinate.equals("e8")) {
			canBlackKingCastle = false;
		}
	}
	
	/**
	 * If a rook moves, castling can no longer be done on that side.
	 * If both rooks have moved, the king can no longer castle.
	 * 
	 * @param coordinate Checks to see if a white or black rook has moved
	 */
	public static void rookMoved(String coordinate) {
		if (coordinate.equals("a1")) {
			whiteKingLeftCastle = false;
		} 
		else if (coordinate.equals("h1")) {
			whiteKingRightCastle = false;
		} 
		else if (coordinate.equals("a8")) {
			blackKingRightCastle = false;
		}
		else if (coordinate.equals("h8")) {
			blackKingLeftCastle = false;
		}
		
		// If both rooks have moved, castling can no longer be done.
		if (!whiteKingLeftCastle && !whiteKingRightCastle) {
			canWhiteKingCastle = false;
		}
		if (!blackKingLeftCastle && !blackKingRightCastle) {
			canBlackKingCastle = false;
		}
	}
	
	/**
	 * This method is called if the white king tries to castle.
	 * It checks to make sure that the king did not move at all and that it is white's turn.
	 * Furthermore, it checks to make sure the path between the king and the selected rook is clear and
	 * the relevant tiles are not in check, including the king itself.
	 * 
	 * @param boardObject Contains the chess board info
	 * @param firstPosition Position of the king
	 * @param lastPosition Position where the king wants to do
	 * @return True if king can castle, false otherwise
	 */
	public static boolean whiteKingCastleAttempt(Board boardObject, String firstPosition, String lastPosition) {
		if (!canWhiteKingCastle || Board.is_black) {
			return false;
		}

		if (!firstPosition.equals("e1")) {
			return false;
		}
		if (!lastPosition.equals("c1") && !lastPosition.equals("g1")) {
			return false;
		}
		
		// White king attempting to castle to the left rook.
		// Check that path to the left white rook is clear and that tiles c1 and d1 are not in a check status
		if (!whiteKingLeftCastle) {
			return false;
		}
		if (boardObject.board.get("b1") instanceof EmptyTile && boardObject.board.get("c1") instanceof EmptyTile &&
				boardObject.board.get("d1") instanceof EmptyTile && !KingCheck.castlingKingCheck(boardObject, "c1", 'b') &&
				!KingCheck.castlingKingCheck(boardObject, "d1", 'b') && !KingCheck.castlingKingCheck(boardObject, "e1", 'b')) {
			return true;
		}
			
		// White king attempting to castle to the right rook.
		if (!whiteKingRightCastle) {
			return false;
		}
		if (boardObject.board.get("f1") instanceof EmptyTile && boardObject.board.get("g1") instanceof EmptyTile &&
				!KingCheck.castlingKingCheck(boardObject, "g1", 'b') && !KingCheck.castlingKingCheck(boardObject, "f1", 'b') &&
				!KingCheck.castlingKingCheck(boardObject, "e1", 'b')) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method is called if the black king tries to castle.
	 * It checks to make sure that the king did not move at all and that it is white's turn.
	 * Furthermore, it checks to make sure the path between the king and the selected rook is clear and
	 * the relevant tiles are not in check, including the king itself.
	 * 
	 * @param boardObject boardObject Contains the chess board info
	 * @param firstPosition firstPosition Position of the king
	 * @param lastPosition lastPosition Position where the king wants to do
	 * @return True if king can castle, false otherwise
	 */
	public static boolean blackKingCastleAttempt(Board boardObject, String firstPosition, String lastPosition) {
		if (!canBlackKingCastle || Board.is_white) {
			return false;
		}

		if (!firstPosition.equals("e8")) {
			return false;
		}
		if (!lastPosition.equals("c8") && !lastPosition.equals("g8")) {
			return false;
		}
		
		// Black king attempting to castle to the left rook.
		// Check that path to the left white rook is clear and that tiles f8 and g8 are not in a check status
		if (!blackKingLeftCastle) {
			return false;
		}
		if (boardObject.board.get("f8") instanceof EmptyTile && boardObject.board.get("g8") instanceof EmptyTile &&
				!KingCheck.castlingKingCheck(boardObject, "g8", 'w') && !KingCheck.castlingKingCheck(boardObject, "f8", 'w') &&
				!KingCheck.castlingKingCheck(boardObject, "e8", 'w')) {
			return true;
		}
			
		// Black king attempting to castle to the right rook.
		if (!blackKingRightCastle) {
			return false;
		}
		if (boardObject.board.get("b8") instanceof EmptyTile && boardObject.board.get("c8") instanceof EmptyTile &&
				boardObject.board.get("d8") instanceof EmptyTile && !KingCheck.castlingKingCheck(boardObject, "c8", 'w') &&
				!KingCheck.castlingKingCheck(boardObject, "d8", 'w') && !KingCheck.castlingKingCheck(boardObject, "e8", 'w')) {
			return true;
		}
		
		return false;
	}
}
