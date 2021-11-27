package chess;

import java.util.HashMap;

/**
 * This class is used to define the movement rules for the Rook piece.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Rook extends Piece{

	/**
	 * Names the Rook piece. It should be wR or bR.
	 * 
	 * @param pieceName The name for Rook
	 */
    public Rook(String pieceName) {
        super(pieceName);
    }

    /**
     * Verifies that the movement between the two positions is valid
     * 
     * @param board	The board class instance that holds various pieces of information, including board HashMap
     * @param currentPosition	The position the Rook is moving from
     * @param nextPosition	The position the Rook is moving to
     * @return 	True or false depending on if the move is valid
     */
    @Override
	public boolean isMoveValid(Board board, String currentPosition, String nextPosition) {
		
		if (currentPosition.charAt(0) == nextPosition.charAt(0) || 
				currentPosition.charAt(1) == nextPosition.charAt(1))
		{
			return true;
		}
		
		return false;
	}
    
	/**
	 * Verifies that the movement between the two positions is valid
	 * Accepts different arguments compared to the above method
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Rook is moving from
	 * @param nextPosition 	The position the Rook is moving to
	 * @return	True or false depending on if the move is valid
	 */
    @Override
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		
		if (currentPosition.charAt(0) == nextPosition.charAt(0) || 
				currentPosition.charAt(1) == nextPosition.charAt(1))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if there are are pieces between the Rook current position and the position the Rook is going to.
	 * The Rook is not allowed to jump over pieces.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Rook is moving from
	 * @param nextPosition	The position the Rook is moving to
	 * @return	True or false depending on if the move is valid
	 */
	@Override
	public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition)
	{
		char currentPositionLetter = currentPosition.charAt(0);
		char currentPositionNumber = currentPosition.charAt(1);
		char nextPositionLetter = nextPosition.charAt(0);
		char nextPositionNumber = nextPosition.charAt(1);
		StringBuilder builder = new StringBuilder();
		
		// Same letter, increment number accordingly.
		// Otherwise, increment the letter accordingly.
		if (currentPositionLetter == nextPositionLetter)
		{
			if (currentPositionNumber < nextPositionNumber)
			{
				currentPositionNumber++;
				while(currentPositionNumber < nextPositionNumber)
				{
					builder.setLength(0);
					builder.append(currentPositionLetter);
					builder.append(currentPositionNumber);
					
					if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
					{
						return true;
					}
					currentPositionNumber++;
				}
			}
			else
			{
				currentPositionNumber--;
				while(currentPositionNumber > nextPositionNumber)
				{
					builder.setLength(0);
					builder.append(currentPositionLetter);
					builder.append(currentPositionNumber);
					
					if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
					{
						return true;
					}
					currentPositionNumber--;
				}
			}
		}
		else if (currentPositionNumber == nextPositionNumber)
		{
			if (currentPositionLetter < nextPositionLetter)
			{
				currentPositionLetter++;
				while(currentPositionLetter < nextPositionLetter)
				{
					builder.setLength(0);
					builder.append(currentPositionLetter);
					builder.append(currentPositionNumber);
					
					if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
					{
						return true;
					}
					currentPositionLetter++;
				}
			}
			else
			{
				currentPositionLetter--;
				while(currentPositionLetter > nextPositionLetter)
				{
					builder.setLength(0);
					builder.append(currentPositionLetter);
					builder.append(currentPositionNumber);
					
					if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
					{
						return true;
					}
					currentPositionLetter--;
				}
			}
		}
		
		return false;
	}
}