package chess;

import java.util.HashMap;

/**
 * This classes handles the movement rules for Bishop
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Bishop extends Piece {

	/**
	 * Names the bishop piece. It should be wB or bB.
	 * 
	 * @param pieceName The name for bishop
	 */
    public Bishop(String pieceName) {
        super(pieceName);
    }

    /**
     * Verifies that the movement between the two positions is valid
     * 
     * @param boardObject	The board class instance that holds various pieces of information, including board HashMap
     * @param currentPosition	The position the Bishop is moving from
     * @param nextPosition	The position the Bishop is moving to
     * @return 	True or false depending on if the move is valid
     */
	public boolean isMoveValid(Board boardObject, String currentPosition, String nextPosition) {
		
		if (Math.abs(currentPosition.charAt(0) - nextPosition.charAt(0)) ==
				Math.abs(currentPosition.charAt(1) - nextPosition.charAt(1))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Verifies that the movement between the two positions is valid
	 * Accepts different arguments compared to the above method
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Bishop is moving from
	 * @param nextPosition 	The position the Bishop is moving to
	 * @return	True or false depending on if the move is valid
	 */
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		
		if (Math.abs(currentPosition.charAt(0) - nextPosition.charAt(0)) ==
				Math.abs(currentPosition.charAt(1) - nextPosition.charAt(1))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if there are are pieces between the bishop's current position and the position the bishop is going to.
	 * The Bishop is not allowed to jump over pieces.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the Bishop is moving from
	 * @param nextPosition	The position the Bishop is moving to
	 * @return	True or false depending on if the move is valid
	 */
	@Override
	public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		char currentPositionLetter = currentPosition.charAt(0);
		char currentPositionNumber = currentPosition.charAt(1);
		char nextPositionLetter = nextPosition.charAt(0);
		char nextPositionNumber = nextPosition.charAt(1);
		StringBuilder builder = new StringBuilder();
		
		// There are four possible scenarios for the two coordinates to differ.
		// Once a scenario is found, check if there are any pieces blocking the path.
		if (currentPositionLetter < nextPositionLetter && currentPositionNumber < nextPositionNumber) {
			currentPositionLetter++;
			currentPositionNumber++;
			while(currentPositionLetter < nextPositionLetter && currentPositionNumber < nextPositionNumber) {
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile)) {
					return true;
				}
				currentPositionLetter++;
				currentPositionNumber++;
			}
		}
		else if (currentPositionLetter > nextPositionLetter && currentPositionNumber > nextPositionNumber) {
			currentPositionLetter--;
			currentPositionNumber--;
			while(currentPositionLetter > nextPositionLetter && currentPositionNumber > nextPositionNumber) {
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile)) {
					return true;
				}
				currentPositionLetter--;
				currentPositionNumber--;
			}
		}
		else if (currentPositionLetter > nextPositionLetter && currentPositionNumber < nextPositionNumber) {
			currentPositionLetter--;
			currentPositionNumber++;
			while(currentPositionLetter > nextPositionLetter && currentPositionNumber < nextPositionNumber) {
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile)) {
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
			while(currentPositionLetter < nextPositionLetter && currentPositionNumber > nextPositionNumber) {
				builder.setLength(0);
				builder.append(currentPositionLetter);
				builder.append(currentPositionNumber);
				
				if (!(boardgame.get(builder.toString()) instanceof EmptyTile)) {
					return true;
				}
				currentPositionLetter++;
				currentPositionNumber--;
			}
		}
		
		return false;
	}
    
}