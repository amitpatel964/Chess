package chess;

import java.util.HashMap;

/**
 * The empty tile class lets the program keep track of which tiles have no pieces.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class EmptyTile extends Piece{

	/**
	 * Handles naming of the tile. Should be ## or "  ".
	 * 
	 * @param pieceName Name of tile
	 */
    public EmptyTile(String pieceName){
        super(pieceName);
    }

    /**
     * Movements from an empty tile is always false since you cannot move an empty tile.
     * 
     * @param boardgame	HashMap of the board and where the pieces are
     * @param currentPosition	First position
     * @param nextPosition	Final position
     * @return	Should always return false, an empty tile cannot move.
     */
	@Override
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		return false;
	}

}
