package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class handles all of the movement rules for the Knight piece.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Knight extends Piece{

	/**
	 * Names the Knight piece. It should be wN or bN.
	 * 
	 * @param pieceName	Name of Knight piece.
	 */
    public Knight(String pieceName) {
        super(pieceName);
    }

    /**
     * Verifies that the movement between the two positions is valid
     * 
     * @param board	The board class instance that holds various pieces of information
     * @param currentPosition	The position the Knight is moving from
     * @param nextPosition	The position the Knight is moving to
     * @return 	True or false depending on if the move is valid
     */
	public boolean isMoveValid(Board board, String currentPosition, String nextPosition) {
		
		// All possible coordinates
		
		List<String> possibleCoordinates = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		
		builder.append((char)(currentPosition.charAt(0) - 1));
		builder.append((char)(currentPosition.charAt(1) - 2));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) - 2));
		builder.append((char)(currentPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) - 2));
		builder.append((char)(currentPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) - 1));
		builder.append((char)(currentPosition.charAt(1) + 2));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 1));
		builder.append((char)(currentPosition.charAt(1) + 2));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 2));
		builder.append((char)(currentPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 2));
		builder.append((char)(currentPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 1));
		builder.append((char)(currentPosition.charAt(1) - 2));
		possibleCoordinates.add(builder.toString());
		
		for(String coordinate: possibleCoordinates)
		{
			if (coordinate.equals(nextPosition))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Verifies that the movement between the two positions is valid
	 * Accepts different arguments compared to the above method
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Knight is moving from
	 * @param nextPosition 	The position the Knight is moving to
	 * @return	True or false depending on if the move is valid
	 */
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		
		// All possible coordinates
		
		List<String> possibleCoordinates = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		
		builder.append((char)(currentPosition.charAt(0) - 1));
		builder.append((char)(currentPosition.charAt(1) - 2));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) - 2));
		builder.append((char)(currentPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) - 2));
		builder.append((char)(currentPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) - 1));
		builder.append((char)(currentPosition.charAt(1) + 2));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 1));
		builder.append((char)(currentPosition.charAt(1) + 2));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 2));
		builder.append((char)(currentPosition.charAt(1) + 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 2));
		builder.append((char)(currentPosition.charAt(1) - 1));
		possibleCoordinates.add(builder.toString());
		
		builder.setLength(0);
		builder.append((char)(currentPosition.charAt(0) + 1));
		builder.append((char)(currentPosition.charAt(1) - 2));
		possibleCoordinates.add(builder.toString());
		
		for(String coordinate: possibleCoordinates)
		{
			if (coordinate.equals(nextPosition))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Knights are allowed to jump over other pieces. Therefore, other pieces between the two positions cannot
	 * block the Knight.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Knight is moving from
	 * @param nextPosition	The position the Knight is moving to
	 * @return	Always returns true since a Knight can jump over other pieces
	 */
	@Override
	public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition)
	{
		// Knights can jump over other pieces.
		// Other methods will check to make sure there is not friendly piece at the next position.
		
		return false;
	}

}
