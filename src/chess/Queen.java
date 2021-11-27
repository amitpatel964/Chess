package chess;

import java.util.HashMap;

/**
 * This class defines movement rules for the Queen class. The Queen is basically a Rook and Bishop combined.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Queen extends Piece{

	/**
	 * Names the Queen piece. It should be wQ or bQ.
	 * 
	 * @param pieceName The name for Queen
	 */
    public Queen(String pieceName) {
        super(pieceName);
    }

    /**
     * Verifies that the movement between the two positions is valid
     * 
     * @param boardObject	The board class instance that holds various pieces of information, including board HashMap
     * @param currentPosition	The position the Queen is moving from
     * @param nextPosition	The position the Queen is moving to
     * @return 	True or false depending on if the move is valid
     */
	public boolean isMoveValid(Board boardObject, String currentPosition, String nextPosition) {
		// The queen's movement is basically a bishop and rook combined.
		// Therefore, we can combine the checks used for rook and bishop.
		
		// Rook movement check
		if (currentPosition.charAt(0) == nextPosition.charAt(0) || 
				currentPosition.charAt(1) == nextPosition.charAt(1))
		{
			return true;
		}
		
		// Bishop movement check
		if (Math.abs(currentPosition.charAt(0) - nextPosition.charAt(0)) ==
				Math.abs(currentPosition.charAt(1) - nextPosition.charAt(1)))
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
	 * @param currentPosition	The position the Queen is moving from
	 * @param nextPosition 	The position the Queen is moving to
	 * @return	True or false depending on if the move is valid
	 */
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		// The queen's movement is basically a bishop and rook combined.
		// Therefore, we can combine the checks used for rook and bishop.
		
		// Rook movement check
		if (currentPosition.charAt(0) == nextPosition.charAt(0) || 
				currentPosition.charAt(1) == nextPosition.charAt(1))
		{
			return true;
		}
		
		// Bishop movement check
		if (Math.abs(currentPosition.charAt(0) - nextPosition.charAt(0)) ==
				Math.abs(currentPosition.charAt(1) - nextPosition.charAt(1)))
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Determines if there are are pieces between the Queen's current position and the position the Queen is going to.
	 * The Queen is not allowed to jump over pieces.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Queen is moving from
	 * @param nextPosition	The position the Queen is moving to
	 * @return	True or false depending on if the move is valid
	 */
	@Override
	public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition)
	{
		// The queen's movement is basically a bishop and rook combined.
		// Therefore, we can combine the checks used for rook and bishop.
		
		char currentPositionLetter = currentPosition.charAt(0);
		char currentPositionNumber = currentPosition.charAt(1);
		char nextPositionLetter = nextPosition.charAt(0);
		char nextPositionNumber = nextPosition.charAt(1);
		StringBuilder builder = new StringBuilder();
		
		// First two if else statements are for rook like movements.
		// Last four if else statements are for bishop like movements.
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
		else if (currentPositionLetter < nextPositionLetter && currentPositionNumber < nextPositionNumber)
		{
			currentPositionLetter++;
			currentPositionNumber++;
			while(currentPositionLetter < nextPositionLetter && currentPositionNumber < nextPositionNumber)
			{
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
				{
					return true;
				}
				currentPositionLetter++;
				currentPositionNumber++;
			}
		}
		else if (currentPositionLetter > nextPositionLetter && currentPositionNumber > nextPositionNumber)
		{
			currentPositionLetter--;
			currentPositionNumber--;
			while(currentPositionLetter > nextPositionLetter && currentPositionNumber > nextPositionNumber)
			{
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
				{
					return true;
				}
				currentPositionLetter--;
				currentPositionNumber--;
			}
		}
		else if (currentPositionLetter > nextPositionLetter && currentPositionNumber < nextPositionNumber)
		{
			currentPositionLetter--;
			currentPositionNumber++;
			while(currentPositionLetter > nextPositionLetter && currentPositionNumber < nextPositionNumber)
			{
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
				{
					return true;
				}
				currentPositionLetter--;
				currentPositionNumber++;
			}
		}
		else if (currentPositionLetter < nextPositionLetter && currentPositionNumber > nextPositionNumber)
		{
			currentPositionLetter++;
			currentPositionNumber--;
			while(currentPositionLetter < nextPositionLetter && currentPositionNumber > nextPositionNumber)
			{
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile))
				{
					return true;
				}
				currentPositionLetter++;
				currentPositionNumber--;
			}
		}
		
		return false;
	}

}