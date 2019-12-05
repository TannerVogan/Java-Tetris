import cs5044.tetris.Board;
import cs5044.tetris.Brain;
import cs5044.tetris.Move;
import cs5044.tetris.Piece;
import junit.framework.TestCase;

public class Test extends TestCase{
	
	
	
	public void testLonRow0()
    {
        // First, set up the objects to use in this test
        Board board = new Board(10, 24,
            "#### #####"
            );
        Piece piece   = Piece.getPiece(Piece.RIGHT_L, 0);
        Move  move    = new Move();
        Brain myBrain = new CleverBrain();

        // Now call the brain
        myBrain.bestMove(board, piece, 20, move);

        // Now apply the move and test the result
        board.apply(move);

        // This should produce a board like this:
        // |          |
        // |    ###   |
        // |##########|
        // ------------
        // But after clearing the resulting row, we end up with
        // just three blocks that fall to the bottom row.
        assertEquals(board,
            new Board(10, 24,
            "    ###   "));
    }
	
	/*
	 * Putting I in board 1 should not result in a hole.
	 */
	public void test2() {
		 Board board = new Board(10, 24,
		            "#### #####"
		            );
		 Piece piece   = Piece.getPiece(Piece.STICK, 0);
	        Move  move    = new Move();
	        Brain myBrain = new CleverBrain();
	        

	        // Now call the brain
	        myBrain.bestMove(board, piece, 20, move);

	        // Now apply the move and test the result
	        board.apply(move);

	        assertEquals(0,
	        		((CleverBrain)myBrain).countHoles(board));
		 
	}
	
	/*
	 * Putting the S or Z in board 2 should result in only two blocks remaining.
	 */
	public void test3() {
		Board board = new Board(9, 24,
	            "#### ####"
	            );
	    Piece piece1  = Piece.getPiece(Piece.RIGHT_DOG, 0);
        Move  move    = new Move();
        Brain myBrain = new CleverBrain();
        

        // Now call the brain
        myBrain.bestMove(board, piece1, 20, move);

        // Now apply the move and test the result
        board.apply(move);

        assertEquals(2,
            board.getBlocksInRow(0));
	}
	
	/*
	 * Putting L, L-rev in board 2 should result in board with height 2 and no holes.
	 */
	public void test4() {
		Board board = new Board(9, 24,
	            "#### ####"
	            );
	    Piece piece1  = Piece.getPiece(Piece.LEFT_L, 0);
	    Piece piece2  = Piece.getPiece(Piece.RIGHT_L, 0);
        Move  move    = new Move();
        Brain myBrain = new CleverBrain();
        

        // Now call the brain
        myBrain.bestMove(board, piece1, 20, move);

        // Now apply the move and test the result
        board.apply(move);
        
        myBrain.bestMove(board, piece2, 20, move);
        board.apply(move);

        assertEquals(0,
            ((CleverBrain)myBrain).countHoles(board));
        assertEquals(2, board.getLargestHeight());
	}
	
	/*
	 * Putting I in board 2 should not result in holes.
	 */
	public void test5() {
		Board board = new Board(9, 24,
	            "#### ####"
	            );
	    Piece piece1  = Piece.getPiece(Piece.STICK, 0);
        Move  move    = new Move();
        Brain myBrain = new CleverBrain();
        

        // Now call the brain
        myBrain.bestMove(board, piece1, 20, move);

        // Now apply the move and test the result
        board.apply(move);
 
        assertEquals(0,
            ((CleverBrain)myBrain).countHoles(board));
	}

//	/*
//	 * Putting L, L-rev, or T in board 3 should result in one block remaining.
//	 */
//	public void test6() {
//		Board board = new Board(8, 24,
//	            "### ####"
//	            );
//	    Piece piece1  = Piece.getPiece(Piece.LEFT_L, 0);
//	    Piece piece2  = Piece.getPiece(Piece.RIGHT_L, 0);
//	    Piece piece3  = Piece.getPiece(Piece.T, 0);
//        Move  move    = new Move();
//        Brain myBrain = new CleverBrain();
//        myBrain.bestMove(board, piece1, 20, move);
//        board.apply(move);        
//        myBrain.bestMove(board, piece2, 20, move);
//        board.apply(move);
//        assertEquals(1,
//        		board.getBlocksInRow(0));
//        
//        
//        board = new Board(8, 24,
//	            "### ####"
//	            );
////        myBrain.bestMove(board, piece1, 20, move);
////        board.apply(move);
//        myBrain.bestMove(board, piece3, 20, move);
//        board.apply(move);
//        assertEquals(1,
//        		board.getBlocksInRow(0));
//	}
//	
//	/*
//	 * Putting S or Z in board 3 should result in only one space on botton row and no holes.
//	 */
//	public void test7() {
//		Board board = new Board(8, 24,
//	            "### ####"
//	            );
//	    Piece piece1  = Piece.getPiece(Piece.LEFT_DOG, 0);
//	    Piece piece2  = Piece.getPiece(Piece.RIGHT_DOG, 0);
//        Move  move    = new Move();
//        Brain myBrain = new CleverBrain();
//        
//        myBrain.bestMove(board, piece1, 20, move);
//        board.apply(move);  System.out.println(board.getBlocksInRow(0));    
//        assertEquals(7, board.getBlocksInRow(0));
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//        
//        board = new Board(10, 24,
//	            "### ####  "
//	            );
//        myBrain.bestMove(board, piece2, 20, move);
//        board.apply(move);System.out.println(board);
//        assertEquals(9, board.getBlocksInRow(0));
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//	}
//	
//	/*
//	 * Putting anything except stick in board 4 should result in board with height 2 and no holes.
//	 */
//	public void test8() {
//		Board board = new Board(7, 24,
//	            "### ###"
//	            );
//	    Piece piece1  = Piece.getPiece(Piece.LEFT_DOG, 0);
//	    Piece piece2  = Piece.getPiece(Piece.RIGHT_DOG, 0);
//	    Piece piece3  = Piece.getPiece(Piece.LEFT_L, 0);
//	    Piece piece4  = Piece.getPiece(Piece.RIGHT_L, 0);
//	    Piece piece5  = Piece.getPiece(Piece.T, 0);
//	    Piece piece6  = Piece.getPiece(Piece.SQUARE, 0);
//        Move  move    = new Move();
//        Brain myBrain = new CleverBrain();
//        
//        myBrain.bestMove(board, piece1, 20, move);
//        board.apply(move);      
//        assertEquals(2, board.getLargestHeight());
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//        
//        board = new Board(7, 24,
//	            "### ###"
//	            );
//        myBrain.bestMove(board, piece2, 20, move);
//        board.apply(move);
//        assertEquals(2, board.getLargestHeight());
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//        
//        
//        board = new Board(7, 24,
//	            "### ###"
//	            );
//        myBrain.bestMove(board, piece3, 20, move);
//        board.apply(move);
//        assertEquals(2, board.getLargestHeight());
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//        
//        
//        board = new Board(7, 24,
//	            "### ###"
//	            );
//        myBrain.bestMove(board, piece4, 20, move);
//        board.apply(move);
//        assertEquals(2, board.getLargestHeight());
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//        
//        
//        board = new Board(7, 24,
//	            "### ###"
//	            );
//        myBrain.bestMove(board, piece5, 20, move);
//        board.apply(move);
//        assertEquals(2, board.getLargestHeight());
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//        
//        
//        board = new Board(7, 24,
//	            "### ###"
//	            );
//        myBrain.bestMove(board, piece6, 20, move);
//        board.apply(move);
//        assertEquals(2, board.getLargestHeight());
//        assertEquals(0,
//                ((CleverBrain)myBrain).countHoles(board));
//	}
//	
//	/*
//	 * Putting stick in board 4 should result in no bl.
//	 */
//	public void test9() {
//		Board board = new Board(7, 24,
//	            "### ###"
//	            );
//	    Piece piece1  = Piece.getPiece(Piece.STICK, 0);
//        Move  move    = new Move();
//        Brain myBrain = new CleverBrain();
//        
//        myBrain.bestMove(board, piece1, 20, move);
//        
//        board.apply(move);
//        assertEquals(0, board.getBlocksInRow(0));       
//      
//	}


}
