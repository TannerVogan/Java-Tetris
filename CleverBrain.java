import cs5044.tetris.Board;
import cs5044.tetris.Brain;
import cs5044.tetris.Move;
import cs5044.tetris.Piece;

/**
 * 
 * This class reflects AI for this program using bestMove method (because it 
 * implements {@link Brain} interface. Before the selecting of optimal variant
 * the current piece performs all rotations and sorts out all columns, and after 
 * that drops down into the selected place. Each time the board score is generated
 * and compared with the best score. By the such way the best move is selected. 
 *
 * @author
 */
public class CleverBrain implements Brain {
	
    private double score; // Stores current score.


    /**
     * Default constructor (that is, a constructor that 
     * takes absolutely no parameters).
     */
    public CleverBrain() {
        score = 0.0;
    }


    /**
     * Performs a best move for the current piece.
     *
     * @param board 
     *           the current board
     * @param piece 
                 the current piece
     * @param heightLimit 
                 the maximum limit of board height
     * @param move 
                 the move of the current piece
     */
    public void bestMove(Board board, Piece piece, int heightLimit, Move move)
    {
    	// First, pick a really enormous default score that at least
        // one move will be better than (1e20 is scientific notation
        // for 1.0 x 10^20).
        move.setScore(1e20);

        int rotationCount = 0; // Stores number of rotations.
        
        // For this rotation of the piece, try to drop it from every
        // possible column and see which result scores the best.
        while (rotationCount < piece.numRotations()) { // Loop through the all possible rotations for this piece.
            tryAllColumns(board, piece, heightLimit, move);
            piece = piece.nextRotation();
            ++rotationCount;

        }
    }


    /**
     * Sorts out the all columns to generate the best score for each columns.
     * 
     * @param board
     *           the current board
     * @param piece
     *           the given piece
     * @param heightLimit
     *           the maximum limit of board height
     * @param move
     *           the move of the current piece
     */
    private void tryAllColumns(Board board, Piece piece, int heightLimit, Move move) {
        int column = 0; // Stores column count.
        
        while (column < board.getWidth() - piece.getWidth() + 1)
        {
            // Drop piece in the current column (you can use a combination
            // of board.rowAfterDrop() followed by board.place()).
        	int y = board.rowAfterDrop(piece, column);
        	board.place(piece, column, y);

            // Clear any rows that were filled in by dropping the piece.
        	clearRows(board);

            // Compute the score of the resulting board state.
            score = rateBoard(board);
            
            if (score < move.score())// This is the best move so far, so remember it.
            {               
                move.setPiece(piece);
                move.setX(column);
                move.setY(y);
                move.setScore(score);
            }

            // Revert back to original situation for next try.
            board.undo();
            
            ++column; // Increment column count.
        }

    }

    /**
     * This method is for scoring a given board configuration so that
     * you can compare alternative board arrangements to decide which is better.
     * 
     * @param board
     *           the current board
     * @return
     *           rating
     */
    public double rateBoard(Board board) {
    	int column = 0;
        double rating = 0.0;

        while (column < board.getWidth())
        {
            // You can call a support method to assess this column
            // on the board.
            double colRating = countBlocks(board, column);

            // You can use as many support methods as you need, and
            // combine their ratings together in a way that makes sense
            // (you can add them, average them, multiply them by
            // weighting factors of your own divising, etc.).
            colRating = colRating  + penaltyForHoles(board, column) * 4.5;

            // Combine individual column ratings into a composite
            // score as we go along (here simple addition is used,
            // but you can combine them any way you wish).
            rating = rating + colRating;

            // Move to next column.
            ++column;
        }    	
    	return rating;
    }

    


    /**
     * Checks whether any of rows should be cleared
     * and clears them.
     *
     * @param board
     *          the current board
     * @return 
     *          number of cleared rows
     */
    private int clearRows(Board board) {
        int count = 0;

        for (int row = 0; row < board.getLargestHeight(); row++) {
            if (board.getBlocksInRow(row) == board.getWidth()) {
                count++;
            }
        }

        if (count != 0) {
            board.clearRows();
        }
        return count;
    }
    
    /**
     * Calculates number of blocks in given column.
     * 
     * @param board
     *           the current board
     * @param column
     *           the given column
     * @return
     *           number of blocks in the given column
     */
    public double countBlocks(Board board, int column) {

    	int numBlocks = 0; // Stores number of blocks.
    	
    	 for (int x = 0; x < board.getWidth(); x ++) { // Loop through the columns.
             for (int y = 0; y < board.getLargestHeight(); y ++) { // Loop through the rows.
                 if (x == column && board.hasBlockAt(x, y)) { // If there is a block.
                	 numBlocks ++; // Increment number of blocks.
                 }
             }
         }
    	return numBlocks;   	
    }

    /**
     * Calculates penalty for existing holes in the given column.
     * It's used to calculate a final score.
     * 
     * @param board
     *          the current board
     * @param column
     *          the given column
     * @return
     *          penalties
     */
    public double penaltyForHoles(Board board, int column) {
        // this method is not yet used above--it is just for ideas
        return countHoles(board, column) * board.getColumnHeight(column);
    }

    /**
     * Calculates the number of holes in a given column.
     * 
     * @param board
     *            the current board
     * @param column
     *            the given columns
     * @return 
     *            number of holes
     */
    public int countHoles(Board board, int column) {
        int holes = 0; // Stores number of holes.

        for (int x = 0; x < board.getWidth(); x ++) { // Loop through the all columns.
            for (int y = 0; y < board.getLargestHeight(); y ++) { // Loop through the all rows.
                if (x == column && !board.hasBlockAt(x, y)) { // If there is a hole.
                	holes ++; // Increment holes.
                }
            }
        }
        return holes;
    }
    
    /**
     * Calculates all holes in the board,
     * 
     * @param board
     *            the current board
     * @return
     *            number of holes
     */
    public int countHoles(Board board) {
        int holes = 0; // Stores number of holes.

        for (int x = 0; x < board.getWidth(); x ++) { // Loop through the all columns.
            for (int y = 0; y < board.getLargestHeight(); y ++) { // Loop through the all rows.
                if (!board.hasBlockAt(x, y)
                    && board.hasBlockAt(x, y + 1)) { // If there is a hole.
                	holes ++; // Increment holes.
                }
            }
        }
        return holes;
    }
   
}