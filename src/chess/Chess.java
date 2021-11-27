
package chess;

import java.util.Scanner;

/**
 * The class that has the main method. This class handles user input and calls upon different methods to make sure
 * the move is valid and determines if there is a check or checkmate.
 * 
 * @author Amit Patel, Hideyo Sakamoto
 *
 */

public class Chess {
	/**
	 * Handles all of the user input and calls upon the method from the Board class to print the board.
	 * 
	 * @param args Not used
	 */
    public static void main(String[] args) {
        Board boardObject = new Board();
        
        Scanner scan = new Scanner(System.in);
        String str = "";
        String[] separatedInput = new String[3];
        String currentPosition ="", nextPosition="";	
        boolean drawAvailable = false;
        String promoPiece = "Q";
        // game start
        while(true) {

        	// At the beginning of a player's turn, any pawns that can be en passanted can no longer be attacked
			// by an en passant

			if(Board.is_white)
			{
				for(String key: boardObject.board.keySet())
				{
					Piece pawnPieceCheck = boardObject.board.get(key);

					if (pawnPieceCheck instanceof Pawn && pawnPieceCheck.pieceName.charAt(0) == 'w')
					{
						((Pawn) pawnPieceCheck).canBeEnPassanted = false;
					}
				}
			}
			else
			{
				for(String key: boardObject.board.keySet())
				{
					Piece pawnPieceCheck = boardObject.board.get(key);

					if (pawnPieceCheck instanceof Pawn && pawnPieceCheck.pieceName.charAt(0) == 'b')
					{
						((Pawn) pawnPieceCheck).canBeEnPassanted = false;
					}
				}
			}
        	
        	boardObject.printBoard();

			System.out.println("");

			if (boardObject.blackKingCheckmated)
			{
				System.out.println("Checkmate");
				System.out.println("White wins");
				break;
			}
			if (boardObject.whiteKingCheckmated)
			{
				System.out.println("Checkmate");
				System.out.println("Black wins");
				break;
			}
			if (Board.whiteKingInCheck || Board.blackKingInCheck)
			{
				System.out.println("Check");
			}
        	
        	if(Board.is_white) {
            	System.out.print("White's move: ");
            }else {
            	System.out.print("Black's move: ");
            }

        	str = scan.nextLine().toLowerCase();

        	// resign
        	if(str.equals("resign")) {
        		if(Board.is_white) {
        			System.out.println("Black wins");
            		break;
                }else {
                	System.out.println("White wins");
            		break;
                }
        	}

			// draw
			if(str.equals("draw") && drawAvailable) {
				System.out.print("draw");
				break;
			}


			separatedInput = str.split(" ");

        	// String should be something like "e2 e4" or "resign"
        	while(str.length() < 3 || str.length() > 11 || separatedInput.length < 2 || separatedInput.length > 4) {
        		System.out.println("Illegal move, try again");
        		
        		if(Board.is_white) {
                	System.out.print("White's move: ");
                }else {
                	System.out.print("Black's move: ");
                }
        		str = scan.nextLine().toLowerCase();

				// draw
				if(str.equals("draw") && drawAvailable) {
					System.out.print("draw");
					break;
				}

				separatedInput = str.split(" ");
        	}
        	
    		currentPosition = separatedInput[0];
    		nextPosition = separatedInput[1];
    		String thirdEntry = "";

			if(separatedInput.length == 3)
			{
				thirdEntry = separatedInput[2];

				if (thirdEntry.equals("draw?"))
				{
					drawAvailable = true;
				}
				else if (thirdEntry.equalsIgnoreCase("R") || thirdEntry.equalsIgnoreCase("N") || 
						thirdEntry.equalsIgnoreCase("B") || thirdEntry.equalsIgnoreCase("Q"))
				{
					promoPiece = thirdEntry;
				}
			}
    		
    		// Separate check is done if a player tries to castle
    		// If the check is successful, perform the castling movement and proceed to the next turn
        	if (Board.is_white)
        	{
        		if (Castling.whiteKingCastleAttempt(boardObject, currentPosition, nextPosition))
        		{
        			boardObject.castleMovement(currentPosition, nextPosition);
        			boardObject.changeMove();
        			System.out.println("");
        			continue;
        		}
        	}
        	else
        	{
        		if (Castling.blackKingCastleAttempt(boardObject, currentPosition, nextPosition))
        		{
        			boardObject.castleMovement(currentPosition, nextPosition);
        			boardObject.changeMove();
        			System.out.println("");
        			continue;
        		}
        	}
        	
        	// Regular check is done
        	while (!ErrorCheck.checkForErrors(boardObject, currentPosition, nextPosition))
        	{
        		System.out.println("Illegal move, try again");

        		if(Board.is_white) {
                	System.out.print("White's move: ");
                }else {
                	System.out.print("Black's move: ");
                }
        		str = scan.nextLine().toLowerCase();
        		
            	if(str.equals("resign")) {
            		if(Board.is_white) {
            			System.out.println("Black wins");
                		break;
                    }else {
                    	System.out.println("White wins");
                		break;
                    }
            	}

				// draw
				if(str.equals("draw") && drawAvailable) {
					System.out.print("draw");
					break;
				}
        		
        		separatedInput = str.split(" ");

				while(str.charAt(2) != ' ' || str.length() < 4 || str.length() > 11 || separatedInput.length < 2 ||
						separatedInput.length > 4) {
					System.out.println("Illegal move, try again");

					if(Board.is_white) {
						System.out.print("White's move: ");
					}else {
						System.out.print("Black's move: ");
					}
					str = scan.nextLine().toLowerCase();

					// draw
					if(str.equals("draw") && drawAvailable) {
						System.out.print("draw");
						break;
					}
					else
					{
						drawAvailable = false;
					}

					separatedInput = str.split(" ");
				}

				currentPosition = separatedInput[0];
				nextPosition = separatedInput[1];

				if(separatedInput.length == 3)
				{
					thirdEntry = separatedInput[2];

					if (thirdEntry.equals("draw?"))
					{
						drawAvailable = true;
					}
					else if (thirdEntry.equalsIgnoreCase("R") || thirdEntry.equalsIgnoreCase("N") || 
							thirdEntry.equalsIgnoreCase("B") || thirdEntry.equalsIgnoreCase("Q"))
					{
						promoPiece = thirdEntry;
					}
				}
            	
        		// If the check is successful, perform the castling movement and proceed to the next turn
            	if (Board.is_white)
            	{
            		if (Castling.whiteKingCastleAttempt(boardObject, currentPosition, nextPosition))
            		{
            			boardObject.castleMovement(currentPosition, nextPosition);
            			boardObject.changeMove();
            			System.out.println("");
            			break;
            		}
            	}
            	else
            	{
            		if (Castling.blackKingCastleAttempt(boardObject, currentPosition, nextPosition))
            		{
            			boardObject.castleMovement(currentPosition, nextPosition);
            			boardObject.changeMove();
            			System.out.println("");
            			break;
            		}
            	}
        	}

        	boardObject.move(currentPosition, nextPosition, promoPiece);
        	
        	// If king is left in check, move cannot be done
        	while (Board.is_white && Board.whiteKingInCheck &&
        		KingCheck.kingLeftInCheck(boardObject.board, Board.whiteKingPosition, Board.is_white, boardObject) ||
        		Board.is_black && Board.blackKingInCheck &&
        		KingCheck.kingLeftInCheck(boardObject.board, Board.blackKingPosition, Board.is_black, boardObject))
        	{
        		
        		boardObject.copyFromActual();
        		
        		System.out.println("Illegal move, try again");
        		
        		currentPosition = "";
        		nextPosition = "";

				if(Board.is_white) {
					System.out.print("White's move: ");
				}else {
					System.out.print("Black's move: ");
				}
				str = scan.nextLine().toLowerCase();

				if(str.equals("resign")) {
					if(Board.is_white) {
						System.out.println("Black wins");
					}else {
						System.out.println("White wins");
					}
					break;
				}

				separatedInput = str.split(" ");

				// draw
				if(str.equals("draw") && drawAvailable) {
					System.out.print("draw");
					break;
				}

				separatedInput = str.split(" ");

				while(str.charAt(2) != ' ' || str.length() < 4 || str.length() > 11 || separatedInput.length < 2 ||
						separatedInput.length > 4) {
					System.out.println("Illegal move, try again");

					if(Board.is_white) {
						System.out.print("White's move: ");
					}else {
						System.out.print("Black's move: ");
					}
					str = scan.nextLine().toLowerCase();

					// draw
					if(str.equals("draw") && drawAvailable) {
						System.out.print("draw");
						break;
					}
					else
					{
						drawAvailable = false;
					}

					separatedInput = str.split(" ");
				}

				currentPosition = separatedInput[0];
				nextPosition = separatedInput[1];
				thirdEntry = "";

				if(separatedInput.length == 3)
				{
					thirdEntry = separatedInput[2];

					if (thirdEntry.equals("draw?"))
					{
						drawAvailable = true;
					}
					else if (thirdEntry.equalsIgnoreCase("R") || thirdEntry.equalsIgnoreCase("N") || 
							thirdEntry.equalsIgnoreCase("B") || thirdEntry.equalsIgnoreCase("Q"))
					{
						promoPiece = thirdEntry;
					}
				}
        		
            	// Regular check is done
            	while (!ErrorCheck.checkForErrors(boardObject, currentPosition, nextPosition))
            	{
					System.out.println("Illegal move, try again");

            		if(Board.is_white) {
                    	System.out.print("White's move: ");
                    }else {
                    	System.out.print("Black's move: ");
                    }
            		str = scan.nextLine().toLowerCase();

                	if(str.equals("resign")) {
                		if(Board.is_white) {
                			System.out.println("Black wins");
                    		break;
                        }else {
                        	System.out.println("White wins");
                    		break;
                        }
                	}
            		
            		separatedInput = str.split(" ");

					// draw
					if(str.equals("draw") && drawAvailable) {
						System.out.print("draw");
						break;
					}

					separatedInput = str.split(" ");

					while(str.charAt(2) != ' ' || str.length() < 4 || str.length() > 11 || separatedInput.length < 2 ||
							separatedInput.length > 4) {
						System.out.println("Illegal move, try again");

						if(Board.is_white) {
							System.out.print("White's move: ");
						}else {
							System.out.print("Black's move: ");
						}
						str = scan.nextLine().toLowerCase();

						// draw
						if(str.equals("draw") && drawAvailable) {
							System.out.print("draw");
							break;
						}
						else
						{
							drawAvailable = false;
						}

						separatedInput = str.split(" ");
					}

					currentPosition = separatedInput[0];
					nextPosition = separatedInput[1];
					thirdEntry = "";

					if(separatedInput.length == 3)
					{
						thirdEntry = separatedInput[2];

						if (thirdEntry.equals("draw?"))
						{
							drawAvailable = true;
						}
						else if (thirdEntry.equalsIgnoreCase("R") || thirdEntry.equalsIgnoreCase("N") || 
								thirdEntry.equalsIgnoreCase("B") || thirdEntry.equalsIgnoreCase("Q"))
						{
							promoPiece = thirdEntry;
						}
					}
            	}

				// If the check is successful, perform the castling movement and proceed to the next turn
				if (Board.is_white)
				{
					if (Castling.whiteKingCastleAttempt(boardObject, currentPosition, nextPosition))
					{
						boardObject.castleMovement(currentPosition, nextPosition);
						boardObject.changeMove();
						System.out.println("");
						continue;
					}
				}
				else
				{
					if (Castling.blackKingCastleAttempt(boardObject, currentPosition, nextPosition))
					{
						boardObject.castleMovement(currentPosition, nextPosition);
						boardObject.changeMove();
						System.out.println("");
						continue;
					}
				}
            	
            	boardObject.move(currentPosition, nextPosition, promoPiece);
        	}
        	if (Board.is_white)
			{
				Board.whiteKingInCheck = false;
			}
        	else
			{
				Board.blackKingInCheck = false;
			}
        	
    		boardObject.changeMove();

    		if (separatedInput.length < 3)
			{
				drawAvailable = false;
			}
			else if (separatedInput.length == 3 && !separatedInput[2].equals("draw?"))
			{
				drawAvailable = false;
			}

			boardObject.copyToActual();

			System.out.println("");
        }
        
        scan.close();
    }
}
        
