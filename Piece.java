package chess;

import java.util.HashMap;

/**
 * Abstract class that is used to define the different pieces used in Chess.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public abstract class Piece {
    String pieceName;
    
    /**
     * Names the piece depending on what kind of piece is being defined
     * @param pieceName	Name of the piece
     */
    public Piece(String pieceName) {
        this.pieceName = pieceName;
    }

    /**
     * Used to get the name of a piece
     * 
     * @return	Returns name of a piece
     */
    public String getPieceName() {
        return this.pieceName;
    }

    /**
     * Is used to determine if the movement of a piece between two positions is valid.
     * 
     * @param board	The board class instance that holds various pieces of information
     * @param currentPosition	The position the piece is moving from
     * @param nextPosition	The position the piece is moving to
     * @return 	Return false if this version is called
     */
	public boolean isMoveValid(Board board, String currentPosition, String nextPosition) {
		return false;
	}
	
	/**
	 * Is used to determine if the movement of a piece between two positions is valid.
	 * Same method as above, but with different arguments.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the piece is moving from
	 * @param nextPosition 	The position the piece is moving to
	 * @return	Return false if this version is called
	 */
	public boolean isMoveValid(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition) {
		return false;
	}
    
	/**
	 * Used to determine if there are other piecs between the two positions a piece is travelling between
	 * and the piece cannot jump over other pieces.
	 * 
	 * @param boardgame	HashMap of the board and where the pieces are
	 * @param currentPosition	The position the piece is moving from
	 * @param nextPosition 	The position the piece is moving to
	 * @return	Return false if this version is called
	 */
    public boolean isPieceBlocked(HashMap<String,Piece> boardgame, String currentPosition, String nextPosition){
    	return true;
    }
}