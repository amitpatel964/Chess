package chess;

import java.util.HashMap;

/**
 * This class handles drawing the board and keeping track of the changes on the board.
 * It also checks to see if there is a check or checkmate done and responds accordingly.
 * Furthermore, this class keeps tracks of whose turn it is.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */
public class Board {
    // String is cordinate, and Piece is a corresponding piece
    HashMap<String, Piece> board = new HashMap<String, Piece>(70);  // Will hold all of the changes
    HashMap<String, Piece> boardActual = new HashMap<>(70); // If changes are ok, changes copied over to this hashmap
                                                                        // This is done since if a change is reversed and a piece was removed,
                                                                        // the removed piece does not come back normally
    
    public static String whiteKingPosition = "e1";	// Keeps track of the positions of the white and black kings
    public static String blackKingPosition = "e8";
    
    public static boolean whiteKingInCheck = false;	// Tracks the check status for both kings
    public static boolean blackKingInCheck = false;
    
    boolean whiteKingCheckmated = false;	// Tracks the checkmate status for both kings
    boolean blackKingCheckmated = false;	// If a king is checkmated, the other player wins
    
    public static boolean is_white = true;		// check which player has move
    public static boolean is_black = false;

    /**
     * Default constructor to set up the board
     */
    public Board(){
        // row made of a - h
        // colounm made of 1 - 8
        for(char alph = 'a'; alph <= 'h'; alph++){
            for(int i=8; i>0; i--){
                String coordinate = Character.toString(alph) + Integer.toString(i);

                // black tiles filled with ## and white filled with empty
                if(isBlackTile(alph, i)){
                    board.put(coordinate, new EmptyTile("##"));
                    boardActual.put(coordinate, new EmptyTile("##"));
                }else{
                    board.put(coordinate, new EmptyTile("  "));
                    boardActual.put(coordinate, new EmptyTile("  "));
                }

                //Black pawns
                if(coordinate.equals("a7") || coordinate.equals("b7") || coordinate.equals("c7") || coordinate.equals("d7")
                        || coordinate.equals("e7") || coordinate.equals("f7") || coordinate.equals("g7") || coordinate.equals("h7")){
                    board.put(coordinate, new Pawn("bp"));
                    boardActual.put(coordinate, new Pawn("bp"));
                }

                if(coordinate.equals("a8") || coordinate.equals("h8")){   // Black Rook
                    board.put(coordinate, new Rook("bR"));
                    boardActual.put(coordinate, new Rook("bR"));
                }

                if(coordinate.equals("b8") || coordinate.equals("g8")){   // Black Knight
                    board.put(coordinate, new Knight("bN"));
                    boardActual.put(coordinate, new Knight("bN"));
                }

                if(coordinate.equals("c8") || coordinate.equals("f8")){   // Black Bishop
                    board.put(coordinate, new Bishop("bB"));
                    boardActual.put(coordinate, new Bishop("bB"));
                }

                if(coordinate.equals("d8")){ // Black Queen
                    board.put(coordinate, new Queen("bQ"));
                    boardActual.put(coordinate, new Queen("bQ"));
                }

                if(coordinate.equals("e8")){ // Black King
                    board.put(coordinate, new King("bK"));
                    boardActual.put(coordinate, new King("bK"));
                }

                //White pawns
                if(coordinate.equals("a2") || coordinate.equals("b2") || coordinate.equals("c2") || coordinate.equals("d2")
                || coordinate.equals("e2") || coordinate.equals("f2") || coordinate.equals("g2") || coordinate.equals("h2")){
                    board.put(coordinate, new Pawn("wp"));
                    boardActual.put(coordinate, new Pawn("wp"));
                }

                if(coordinate.equals("a1") || coordinate.equals("h1")){   // White Rook
                    board.put(coordinate, new Rook("wR"));
                    boardActual.put(coordinate, new Rook("wR"));
                }

                if(coordinate.equals("b1") || coordinate.equals("g1")){   // White Knight
                    board.put(coordinate, new Knight("wN"));
                    boardActual.put(coordinate, new Knight("wN"));
                }


                if(coordinate.equals("c1") || coordinate.equals("f1")){   // White Bishop
                    board.put(coordinate, new Bishop("wB"));
                    boardActual.put(coordinate, new Bishop("wB"));
                }

                if(coordinate.equals("d1")){ // White Queen
                    board.put(coordinate, new Queen("wQ"));
                    boardActual.put(coordinate, new Queen("wQ"));
                }

                if(coordinate.equals("e1")){ // White King
                    board.put(coordinate, new King("wK"));
                    boardActual.put(coordinate, new King("wK"));
                }
            }
        }
        
    } // end of board constructor

    
    /**
     * should be called every time prints out move
     * it switches move
     */
    public void changeMove() {
    	if(is_white) {
    		is_white = false;
    		is_black = true;
    	}else {
    		is_white = true;
    		is_black = false;
    	}
    }
    
    /**
     * Once all checks are passed and there is no illegal move being made, this method is then called, allowing a piece
     * to move from their current position to the next position. If a king moves, castling can no longer be done. If a rook moves,
     * castling can no longer be done with that rook. An empty tile class is made to replace the tile the current piece was at.
     * 
     * @param currentPosition The piece that is trying to the move.
     * @param nextPosition The location that the piece is moving to.
     * @param promoPiece The piece that pawn will be promoted to if applicable.
     */
    public void move(String currentPosition, String nextPosition, String promoPiece)
    {
		Piece currentPiece = board.get(currentPosition);
		
		// If the current position is the same as one of the king's, that mean one of the kings is moving.
		if (currentPosition.equals(whiteKingPosition))
		{
			whiteKingPosition = nextPosition;
		}
		else if (currentPosition.equals(blackKingPosition))
		{
			blackKingPosition = nextPosition;
		}
		
		// If a king moves, castling can no longer be done for that king, no matter what.
		if(currentPiece instanceof King)
		{
			Castling.kingMoved(currentPosition);
		}
		
		// If a rook moves, castling can no longer be done with that rook.
		if(currentPiece instanceof Rook)
		{
			Castling.rookMoved(currentPosition);
		}

        board.put(nextPosition, currentPiece);

        if(currentPiece instanceof Pawn) {	// for Pawn promotion
            ((Pawn) currentPiece).pawnPromotion(nextPosition,promoPiece,board);
        }

        if(isBlackTile(currentPosition.charAt(0), Character.getNumericValue(currentPosition.charAt(1)))){
            board.put(currentPosition, new EmptyTile("##"));
        }else{
            board.put(currentPosition, new EmptyTile("  "));
        }
        
        // Now check if the king of the opposing color is in check
        if(KingCheck.checkCheck(board, currentPiece, nextPosition, is_white))
        {
        	if(is_white)
        	{
        		blackKingInCheck = true;
        	}
        	else if (is_black)
        	{
        		whiteKingInCheck = true;
        	}
        	
        	// Check to see if the check can be blocked or attacked
        	if(!KingCheck.canCheckBeBlocked(board, currentPiece, nextPosition))
        	{
        		// If check cannot be blocked, check if there is a checkmate
        		if (KingCheck.checkmateCheck(board, currentPiece, nextPosition))
        		{
                	if(is_white)
                	{
                		blackKingCheckmated = true;
                	}
                	else if (is_black)
                	{
                		whiteKingCheckmated = true;
                	}
        		}
        	}
        }

        // Checks if the king of the same color is left in check.
        if(KingCheck.checkCheck(board, currentPiece, nextPosition, is_black)) {
            if (is_black) {
                blackKingInCheck = true;
            } else if (is_white) {
                whiteKingInCheck = true;
            }
        }

        // Check if king of the same color is in check if you move a piece
        // If so, this is not allowed

    }
    
    /**
     * This method will actually handle the castling movement. That is, if all checks to castle are successful,
     * this method is then used to move the king and rook pieces into their respective places.
     * 
     * Once the castling is finished, the king can no longer castle. Please note that the left and right directions
     * for the black king is from the perspective of the player playing as black as if they were on the other side
     * of the board looking at the board.
     * 
     * @param currentPosition This is the position of the king that is trying to castle
     * @param nextPosition The location that the king is trying to castle to
     */
    public void castleMovement(String currentPosition, String nextPosition)
    {
    	Piece kingPiece = board.get(currentPosition);
    	Piece rookPiece = null;
    	
    	if (currentPosition.equals("e1"))
    	{
    		if (nextPosition.equals("c1"))
    		{
    			rookPiece = board.get("a1");
    			
    			board.put("c1", kingPiece);
    			board.put("d1", rookPiece);
    			board.put("e1", new EmptyTile("##"));
    			board.put("a1", new EmptyTile("##"));
    			Castling.whiteKingLeftCastle = false;
    		}
    		else if (nextPosition.equals("g1"))
    		{
    			rookPiece = board.get("h1");
    			
    			board.put("g1", kingPiece);
    			board.put("f1", rookPiece);
    			board.put("e1", new EmptyTile("##"));
    			board.put("h1", new EmptyTile("  "));
    			Castling.whiteKingRightCastle = false;
    		}
    		Castling.canWhiteKingCastle = false;
    	}
    	else if (currentPosition.equals("e8"))
    	{
    		if (nextPosition.equals("c8"))
    		{
    			rookPiece = board.get("a8");
    			
    			board.put("c8", kingPiece);
    			board.put("d8", rookPiece);
    			board.put("e8", new EmptyTile("  "));
    			board.put("a8", new EmptyTile("  "));
    			Castling.blackKingRightCastle = false;
    		}
    		else if (nextPosition.equals("g8"))
    		{
    			rookPiece = board.get("h8");
    			
    			board.put("g8", kingPiece);
    			board.put("f8", rookPiece);
    			board.put("e8", new EmptyTile("  "));
    			board.put("h8", new EmptyTile("##"));
    			Castling.blackKingLeftCastle = false;
    		}
    		Castling.canBlackKingCastle = false;
    	}
    }
    
    /**
     * Prints board in the terminal
     * Uses boardActual to print the board
     */
    public void printBoard(){
        for(int i=8; i>0; i--){
            for(char ch = 'a'; ch<='h'; ch++){
                String cordinate = Character.toString(ch) + Integer.toString(i);
                String piece = boardActual.get(cordinate).getPieceName();
                System.out.print(piece + " ");
                if(ch == 'h'){
                    System.out.println(i);
                }
            }
            if(i==1){
                System.out.println("a  b  c  d  e  f  g  h  ");
            }
        }
    }

    /**
     * Checks cordinate and whether corresponding tile is black or not
     * @param alph  row
     * @param num   column
     * @return boolean Whether ot not the tile is black or not
     */
    public static boolean isBlackTile(char alph, int num){
        if( ( (num == 1) || (num == 3) || (num ==5) || ((num == 7))) && 
        ((alph == 'a') || (alph == 'c') || (alph == 'e') || (alph == 'g'))){
             return true;
        }

        if( ( (num == 2) || (num == 4) || (num ==6) || ((num == 8))) && 
        ((alph == 'b') || (alph == 'd') || (alph == 'f') || (alph == 'h'))){
             return true;
        }
        
        return false;
    }
    
    /**
     * This function will get the piece from the hashmap if the hashmap is not being passed directly
     * 
     * @param position The key
     * @return The piece for the key, or the position
     */
    public Piece getPiece(String position) {
    	Piece piece = board.get(position);
    	return piece;
    }

    /**
     * If all of the changes are verified, the hashmap that is used to print the board is copied from the modified one.
     * This will allow the players to see the changes.
     *
     */
    public void copyToActual()
    {
        boardActual.clear();
        boardActual.putAll(board);
    }

    /**
     * If there is a problem with a move, such as a king still being in check, the modified board is reset to what it was
     * at the beginning of the turn.
     *
     */
    public void copyFromActual()
    {
        board.clear();
        board.putAll(boardActual);
    }
}

