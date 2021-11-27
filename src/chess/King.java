package chess;

import java.util.HashMap;

/**
 * This class handles all of the movement rules for the King pieces.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class King extends Piece{
	
	/**
	 * Handles the naming of the King piece. Should be wK or bK.
	 * 
	 * @param pieceName	Name of the King piece
	 */
    public King(String pieceName) {
        super(pieceName);
    }

    /**
     * Determine if the movement between the two coordinates is valid for the King.
     * 
     * @param board	Board class instance that has various pieces of information, including a HashMap of the board.
     * @param currentPosition	The position the King piece is trying to move from
     * @param nextPosition	The position the King piece is moving to
     * @return	True of false depending on if the movement is legal
     */
    @Override
	public boolean isMoveValid(Board board, String currentPosition, String nextPosition) {
		int letterDifference = Math.abs(currentPosition.charAt(0) - nextPosition.charAt(0));
		int numberDifference = Math.abs(currentPosition.charAt(1) - nextPosition.charAt(1));
		
		if (letterDifference == 1 && numberDifference == 1 || 
				letterDifference == 0 && numberDifference == 0 ||
				letterDifference == 1 && numberDifference == 0 ||
				letterDifference == 0 && numberDifference == 1)
		{
			return true;
		}
		
		return false;
	}
	
    /**
     * Determine if the movement between the two coordinates is valid for the King.
     * Same as above method, but accepts different arguments.
     * 
     * @param boardgame	HashMap of the board and where the pieces are
     * @param currentPosition	The position the King piece is trying to move from
     * @param nextPosition	The position the King piece is moving to
     * @return	True of false depending on if the movement is legal
     */
    @Override
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		int letterDifference = Math.abs(currentPosition.charAt(0) - nextPosition.charAt(0));
		int numberDifference = Math.abs(currentPosition.charAt(1) - nextPosition.charAt(1));
	
		
		if (letterDifference == 1 && numberDifference == 1 || 
				letterDifference == 0 && numberDifference == 0 ||
				letterDifference == 1 && numberDifference == 0 ||
				letterDifference == 0 && numberDifference == 1)
		{
			return true;
		}
		
		return false;
	}
	
    /**
     * Determines if moving the King to this position will place it in check or not.
     * If it does, the movement is not allowed.
     * 
     * @param boardgame	HashMap of the board and where the pieces are
     * @param currentPosition	The position the King piece is trying to move from
     * @param nextPosition	The position the King piece is moving to
     * @return	True of false depending on if the movement will put the king into check
     */
    @Override
	public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition)
	{
		// King can only move one space. No need to worry about the king being blocked outside of the surrounding tiles.
		// Next checks should verify if king can move to a certain space.
		// King should not move in a way that will leave itself in a checked position.
		
		char opponentColor = 'w';
		if (Board.is_white)
		{
			opponentColor = 'b';
		}
		else if (Board.is_black)
		{
			opponentColor = 'w';
		}
		
		// Checks to see if any piece can attack the king if the king moves.
		// If there is such a piece, the method returns false since the king cannot move itself into a check.
		for(String key: boardgame.keySet())
		{
			Piece currentPiece = boardgame.get(key);

			if (currentPiece instanceof King)
			{
				if(currentPiece.pieceName.charAt(0) == opponentColor &&
						currentPiece.isMoveValid(boardgame, key, nextPosition))
				{
					return true;
				}
			}
			else
			{
				if(currentPiece.pieceName.charAt(0) == opponentColor && ErrorCheck.checkForErrors(boardgame, key, nextPosition, Board.is_white))
				{
					return true;
				}
			}
		}

		return false;
	}

}