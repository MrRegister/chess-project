package chess;

import chess.engine.board.Board;
import chess.gui.PromotionWindow;
import chess.gui.Table;

public class JChess {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table table = new Table();

        PromotionWindow pwindow = new PromotionWindow();

    }

}
