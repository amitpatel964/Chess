package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains various methods to help us determine if a King piece is currently in check or if there
 * is a checkmate
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class KingCheck {

	/**
	 * Determines if the movement of a piece between two positions has caused a check to take place.
	 * 
	 * @param boardgame	HashMap of the board, which contains locations of the pieces if they are still in play.
	 * @param opponentPieceLastMoved	The chess piece the player used that will be used to determine if the other player's king is in check.
	 * @param positionOfOpponent	The final position of the player's last played piece
	 * @param isWhiteTurn	Determines whose turn it currently is
	 * @return	True or false depending on if a check has occurred
	 */
	public static boolean checkCheck(HashMap<String,Piece> boardgame, Piece opponentPieceLastMoved, 
			String positionOfOpponent, boolean isWhiteTurn) {
		String kingPosition = null;
		
		// Check what color the opponent's last moved piece and find the king of the other color
		// Get the position of the king
		if (opponentPieceLastMoved.pieceName.charAt(0) == 'b') {
			kingPosition = Board.whiteKingPosition;
		} else {
			kingPosition = Board.blackKingPosition;
		}
		
		if (ErrorCheck.checkForErrors(boardgame, positionOfOpponent, kingPosition, isWhiteTurn)) {
			return true;
		}
		
		return false;
	}

	/**
	 * Helper method to determine if a check happens if a King piece moves here.
	 * Helps us determine if the King can get out of a checkmate.
	 * 
	 * @param boardgame	HashMap of the board, which contains locations of the pieces if they are still in play.
	 * @param positionOfOpponent	The final position of the player's last played piece
	 * @param kingPossiblePosition	The possible location the king can move to
	 * @return	True or false depending on if the King can move to get out of the check
	 */
	public static boolean checkCheckForCheckmate(HashMap<String,Piece> boardgame,
												 String positionOfOpponent, String kingPossiblePosition) {
		if (ErrorCheck.checkForErrors(boardgame, positionOfOpponent, kingPossiblePosition, Board.is_white)) {
			return true;
		}

		return false;
	}
	
	/**
	 * This method helps us determine if a king is left in check.
	 * If a king is in check and can move out of a check, it must be moved out of a check
	 * 
	 * @param boardgame	HashMap of the board, which contains locations of the pieces if they are still in play.
	 * @param kingPosition	Position of the King that is currently in check.
	 * @param whiteTurn	Determines whose turn it is
	 * @param boardObject	Board class instance that holds various pieces of information
	 * @return	True or false depending on if a King piece was left in check
	 */
	public static boolean kingLeftInCheck(HashMap<String,Piece> boardgame, String kingPosition, boolean whiteTurn, Board boardObject) {
		char opponentColor = 'w';

		if(boardgame.get(kingPosition).pieceName.charAt(0) == 'w') {
			opponentColor = 'b';
		} else {
			opponentColor = 'w';
		}
		
		for (String key: boardgame.keySet()) {
			Piece currentPiece = boardgame.get(key);

			if (currentPiece.pieceName.charAt(0) == opponentColor && 
					ErrorCheck.checkForErrors(boardgame, key, kingPosition, Board.is_white)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Helper method that helps us determinf if a King has to move into or through a check in order to castle.
	 * A King must not be in check, go through a check or go into a check in order to castle.
	 * 
	 * @param boardObject	Board class instance that holds various pieces of information
	 * @param position	The position the King has to move through or into
	 * @param color	Determines color of the opposing player
	 * @return	True or false depending on if the King has to go through a check
	 */
	public static boolean castlingKingCheck(Board boardObject, String position, char color) {
		for (String key: boardObject.board.keySet()) {
			Piece currentPiece = boardObject.board.get(key);
			if(!(currentPiece instanceof EmptyTile) && currentPiece.pieceName.charAt(0) == color &&
			ErrorCheck.checkForErrors(boardObject.board, key, position, Board.is_white)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Helper method and helps us determine if a King in check has a friendly piece that can block the opposing piece
	 * or attack the opposing piece.
	 * 
	 * @param boardgame	HashMap of the board, which contains locations of the pieces that are still in play.
	 * @param attackingPiece	The type of the piece that is attacking
	 * @param attackerPosition	Position of the attacking piece
	 * @return	True or false depending on if the opposing piece can be blocked or attacked
	 */
	public static boolean canCheckBeBlocked(HashMap<String,Piece> boardgame, Piece attackingPiece, String attackerPosition) {
		String kingPosition = null;
		char defendingKingColor = 'a';
		
		// Check what color the opponent's last moved piece and find the king of the other color
		// Get the position of the king
		if (attackingPiece.pieceName.charAt(0) == 'b') {
			kingPosition = Board.whiteKingPosition;
		} else {
			kingPosition = Board.blackKingPosition;
		}
		
		// Check if a piece can attack the piece that is putting the defending king into check.
		for (String key: boardgame.keySet()) {
			if (boardgame.get(key).pieceName.charAt(0) == defendingKingColor &&
					ErrorCheck.checkForErrors(boardgame, key, kingPosition, Board.is_white)) {
				return true;
			}
		}
		
		// If no friendly pieces can attack the attacking piece, check to see if the attacker is a rook, queen or bishop.
		// These pieces can be blocked since they move along a line.
		
		ArrayList<String> tilesToBlock = new ArrayList<String>();
		ArrayList<String> tilesToBlock2 = new ArrayList<String>();

		// First check if the attacking piece is a rook.
		if (attackingPiece instanceof Rook) {
			tilesToBlock = rookBlock(kingPosition, attackerPosition);
		} else if (attackingPiece instanceof Bishop) {
			tilesToBlock = bishopBlock(kingPosition, attackerPosition);
		} else if (attackingPiece instanceof Queen) {
			tilesToBlock = rookBlock(kingPosition, attackerPosition);
			tilesToBlock2 = bishopBlock(kingPosition, attackerPosition);

			tilesToBlock.addAll(tilesToBlock2);
		}
		
		// Check if any friendly pieces can block the attacker
		// If not, return false and check if there is a checkmate
		for (String key: boardgame.keySet()) {
			if (boardgame.get(key).pieceName.charAt(0) == defendingKingColor) {
				for (String position: tilesToBlock) {
					if (ErrorCheck.checkForErrors(boardgame, key, position, Board.is_white)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * This method helps us determine if there is a checkmate. A checkmate occurs if a King is in check and
	 * cannot get out of check.
	 * 
	 * @param boardgame	HashMap of the board, which contains locations of the pieces that are still in play.
	 * @param attackingPiece	The type of the piece that is attacking
	 * @param attackerPosition	Position of the attacking piece
	 * @return	True or false depending on if a checkmate has occurred or not
	 */
	public static boolean checkmateCheck(HashMap<String,Piece> boardgame, Piece attackingPiece, String attackerPosition) {
		// kingToCheck is a placeholder. Need to figure out a better way to get the king variable rather than searching for it again.
		
		// This method is used when a check occurs. Once a check occurs, the tiles surrounding the king are checked.
		// If the king's move to a surrounding tile is valid, the opponent pieces in play are checked to see if they can attack that tile.
		
		String kingPosition = "";
		
		if (attackingPiece.pieceName.charAt(0) == 'b') {
			kingPosition = Board.whiteKingPosition;
		} else {
			kingPosition = Board.blackKingPosition;
		}
		
		// Check what color the opponent's last moved piece and find the king of the other color
		// Get the position of the king
		if (attackingPiece.pieceName.charAt(0) == 'b') {
			kingPosition = Board.whiteKingPosition;
		} else {
			kingPosition = Board.blackKingPosition;
		}
		
		int tilesBlocked = 0;
		
		// All possible coordinates
		
		List<String> possibleCoordinates = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		
		builder.append((char)(kingPosition.charAt(0) - 1));
		builder.append(kingPosition.charAt(1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(kingPosition.charAt(0) - 1));
		builder.append((char)(kingPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append(kingPosition.charAt(0));
		builder.append((char)(kingPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(kingPosition.charAt(0) + 1));
		builder.append((char)(kingPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(kingPosition.charAt(0) + 1));
		builder.append(kingPosition.charAt(1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(kingPosition.charAt(0) + 1));
		builder.append((char)(kingPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append(kingPosition.charAt(0));
		builder.append((char)(kingPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(kingPosition.charAt(0) - 1));
		builder.append((char)(kingPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		for (String coordinate: possibleCoordinates) {
			if (!ErrorCheck.checkForErrorsCheckmate(boardgame, kingPosition, coordinate)) {
				// If the king is in check and there is no friendly piece that can either block or attack the opposing piece,
				// check if the surrounding tiles can be moved to. If there is a friendly piece or if the coordinate is 
				// nonexistant, the king cannot go there.
				tilesBlocked++;
			} else {
				for(String key: boardgame.keySet()) {
					if (checkCheckForCheckmate(boardgame, key, coordinate)) {
						// If a king moves to this tile, it will be in check.
						// Therefore, the king cannot move to this tile.
						tilesBlocked++;
					}
				}
			}
		}
		
		if (tilesBlocked == 8) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Helper method that finds out what tiles are between the defending king and the attacking rook.
	 * Can also be used with queen since a queen has the movement of a rook and a bishop.
	 * 
	 * @param kingPosition Position of the king
	 * @param attackerPosition Position of the rook or queen
	 * @return An ArrayList of all of the positions that the rook or queen has to go through to attack the king
	 */
	public static ArrayList<String> rookBlock(String kingPosition, String attackerPosition) {
		ArrayList<String> positionsToBlock = new ArrayList<String>();
		
		char kingLetter = kingPosition.charAt(0);
		char kingNumber = kingPosition.charAt(1);
		char attackerLetter = attackerPosition.charAt(0);
		char attackerNumber = attackerPosition.charAt(1);
		StringBuilder builder = new StringBuilder();
		
		if (kingLetter == attackerLetter) {
			if (kingNumber < attackerNumber) {
				kingNumber++;
				while(kingNumber < attackerNumber) {
					builder.setLength(0);
					builder.append(kingLetter);
					builder.append(kingNumber);
					
					positionsToBlock.add(builder.toString());
					kingNumber++;
				}
			} else {
				kingNumber--;
				while(kingNumber > attackerNumber) {
					builder.setLength(0);
					builder.append(kingLetter);
					builder.append(kingNumber);
					
					positionsToBlock.add(builder.toString());
					kingNumber--;
				}
			}
		}
		else if (kingNumber == attackerNumber) {
			if (kingLetter < attackerNumber) {
				kingLetter++;
				while(kingLetter < attackerLetter) {
					builder.setLength(0);
					builder.append(kingLetter);
					builder.append(kingNumber);
					
					positionsToBlock.add(builder.toString());
					kingLetter++;
				}
			}
			else {
				kingLetter--;
				while(kingLetter > attackerLetter) {
					builder.setLength(0);
					builder.append(kingLetter);
					builder.append(kingNumber);
					
					positionsToBlock.add(builder.toString());
					kingLetter--;
				}
			}
		}
		
		return positionsToBlock;
	}
	
	/**
	 * Helper method that finds out what tiles are between the defending king and the attacking bishop.
	 * Can also be used with queen since a queen has the movement of a rook and a bishop.
	 * 
	 * @param kingPosition Position of the king
	 * @param attackerPosition Position of the bishop or queen
	 * @return An arraylist of all of the positions that the bishop or queen has to go through to attack the king
	 */
	public static ArrayList<String> bishopBlock(String kingPosition, String attackerPosition) {
		ArrayList<String> positionsToBlock = new ArrayList<String>();
		
		char kingLetter = kingPosition.charAt(0);
		char kingNumber = kingPosition.charAt(1);
		char attackerLetter = attackerPosition.charAt(0);
		char attackerNumber = attackerPosition.charAt(1);
		StringBuilder builder = new StringBuilder();
		
		if (kingLetter < attackerLetter && kingNumber < attackerNumber) {
			kingLetter++;
			kingNumber++;
			while(kingLetter < attackerLetter && kingNumber < attackerNumber) {
				builder.setLength(0);
				builder.append(kingLetter);
				builder.append(kingNumber);
				
				positionsToBlock.add(builder.toString());
				kingLetter++;
				kingNumber++;
			}
		}
		else if (kingLetter > attackerLetter && kingNumber > attackerNumber) {
			kingLetter--;
			kingNumber--;
			while(kingLetter > attackerLetter && kingNumber > attackerNumber) {
				builder.setLength(0);
				builder.append(kingLetter);
				builder.append(kingNumber);
				
				positionsToBlock.add(builder.toString());
				kingLetter--;
				kingNumber--;
			}
		}
		else if (kingLetter > attackerLetter && kingNumber < attackerNumber) {
			kingLetter--;
			kingNumber++;
			while(kingLetter > attackerLetter && kingNumber < attackerNumber) {
				builder.setLength(0);
				builder.append(kingLetter);
				builder.append(kingNumber);
				
				positionsToBlock.add(builder.toString());
				kingLetter--;
				kingNumber++;
			}
		}
		else if (kingLetter < attackerLetter && kingNumber > attackerNumber) {
			kingLetter++;
			kingNumber--;
			while(kingLetter < attackerLetter && kingNumber > attackerNumber) {
				builder.setLength(0);
				builder.append(kingLetter);
				builder.append(kingNumber);
				
				positionsToBlock.add(builder.toString());
				kingLetter++;
				kingNumber--;
			}
		}
		
		return positionsToBlock;
	}
}
