package br.com.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCycle implements ActionListener {
    private final Board board;

    public GameCycle(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        performGameCycle();
    }

    private void performGameCycle() {
        board.update();
        board.repaint();
    }

}
