import cs5044.tetris.Board;
import cs5044.tetris.Brain;
import cs5044.tetris.Move;
import cs5044.tetris.Piece;

public class LameBrain implements Brain {

   public void bestMove(Board board, Piece piece, int heightLimit, Move move) {
      move.setPiece(piece);
      move.setX((board.getWidth() - piece.getWidth()) / 2);
      move.setY(0);
      move.setScore(100000.0);
   }
}
