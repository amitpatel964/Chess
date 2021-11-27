package chess;

import java.util.HashMap;

/**
 * This class helps us determine if the movement between two tiles has any problems.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class ErrorCheck {

	/**
	 * Performs various checks to make sure the movement between two tiles is valid.
	 * 
	 * @param board Board class instance that holds various pieces of information
	 * @param currentPosition	Position of current piece
	 * @param nextPosition	Position of where a player is trying to move to
	 * @return	True or false depending on if the move is valid.
	 */
	public static boolean checkForErrors(Board board, String currentPosition, String nextPosition)
	{
		// Check to see if the coordinates are different
		if (currentPosition.equals(nextPosition))
		{
			return false;
		}
		
		// Check to see if the chosen coordinates are valid and not out of bounds.
		if (currentPosition.charAt(0) < 'a' || currentPosition.charAt(0) > 'h'
				|| currentPosition.charAt(1) < '1' || currentPosition.charAt(1) > '8'
				|| nextPosition.charAt(0) < 'a' || nextPosition.charAt(0) > 'h'
				|| nextPosition.charAt(1) < '1' || nextPosition.charAt(1) > '8')
		{
			return false;
		}
		
		boolean isWhiteTurn = Board.is_white;
		
		Piece currentPiece = board.getPiece(currentPosition);
		Piece nextPiece = board.getPiece(nextPosition);
		
		// Check to see if there is a piece at the first coordinate.
		
		if (currentPiece instanceof EmptyTile)
		{
			return false;
		}
		
		// Check to see if it is the matching color of the player.
		if (isWhiteTurn && currentPiece.getPieceName().charAt(0) == 'b')
		{
			return false;
		}
		if (!isWhiteTurn && currentPiece.getPieceName().charAt(0) == 'w')
		{
			return false;
		}
		
		// Check to see if the distance of the chosen piece is legal.
		if (!currentPiece.isMoveValid(board, currentPosition, nextPosition))
		{
			return false;
		}
		
		// Check to see if the movement is blocked by any pieces before reaching the target spot.
		if (currentPiece.isPieceBlocked(board.board, currentPosition, nextPosition))
		{
			return false;
		}
		
		// Check to see what is at the second coordinate.
		// If it is empty, go ahead and move the piece.
		// If it is not, check to see if the piece is the opposite color.
		if(!(nextPiece instanceof EmptyTile))
		{
			if (isWhiteTurn && nextPiece.getPieceName().charAt(0) == 'w')
			{
				return false;
			}
			if (!isWhiteTurn && nextPiece.getPieceName().charAt(0) == 'b')
			{
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Performs various checks to make sure the move is valid. Same method as above, but the method aceeptd different arguments.
	 * 
	 * @param board	HashMap of where the pieces are currently on the board.
	 * @param currentPosition	The position a player is trying to move from.
	 * @param nextPosition	The position a player is trying to move to.
	 * @param isWhiteTurn	True or false depending on if the move is valid or not.
	 * @return	True or false depending on if the move is valid.
	 */
	public static boolean checkForErrors(HashMap<String,Piece> board, String currentPosition, String nextPosition, boolean isWhiteTurn)
	{
		// Check to see if the coordinates are different
		if (currentPosition.equals(nextPosition))
		{
			return false;
		}

		// Check to see if the chosen coordinates are valid and not out of bounds.

		if (currentPosition.charAt(0) < 'a' || currentPosition.charAt(0) > 'h'
				|| currentPosition.charAt(1) < '1' || currentPosition.charAt(1) > '8'
				|| nextPosition.charAt(0) < 'a' || nextPosition.charAt(0) > 'h'
				|| nextPosition.charAt(1) < '1' || nextPosition.charAt(1) > '8')
		{
			return false;
		}

		Piece currentPiece = board.get(currentPosition);

		// Check to see if there is a piece at the first coordinate.

		if (currentPiece instanceof EmptyTile)
		{
			return false;
		}

		// Check to see if the distance of the chosen piece is legal.

		if (!currentPiece.isMoveValid(board, currentPosition, nextPosition))
		{
			return false;
		}

		// Check to see if the movement is blocked by any pieces before reaching the target spot.
		if (currentPiece.isPieceBlocked(board, currentPosition, nextPosition))
		{
			return false;
		}

		return true;
	}

	/**
	 * Helper method that is used to determine if a king that is currently in check can go to a specified position.
	 * 
	 * @param boardgame	HashMap of where the pieces are on the board
	 * @param kingPosition	The position of where the king in check currently is
	 * @param coordinate	The position the king can possibly go to
	 * @return	True or false depending on if the king can go to the position
	 */
	public static boolean checkForErrorsCheckmate(HashMap<String, Piece> boardgame, String kingPosition,
			String coordinate) {
		
		// King would have to go out of bounds
		if (coordinate.charAt(0) < 'a' || coordinate.charAt(0) > 'h'
		|| coordinate.charAt(1) < '1' || coordinate.charAt(1) > '8')
		{
			return false;
		}
		
		Piece nextPiece = boardgame.get(coordinate);
		
		// If the surrounding tile is not empty, check if there is a friendly piece there.
		// If there is, the king cannot go there.
		if(!(nextPiece instanceof EmptyTile))
		{
			if (boardgame.get(kingPosition).pieceName.charAt(0) == nextPiece.getPieceName().charAt(0))
			{
				return false;
			}
		}
		
		return true;
	}
	
	
}
