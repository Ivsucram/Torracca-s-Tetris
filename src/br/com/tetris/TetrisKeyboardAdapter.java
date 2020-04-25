package br.com.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import br.com.tetris.shapes.NoBlock;

public class TetrisKeyboardAdapter extends KeyAdapter {
    private final Board board;
    private final int MOVE_LEFT = KeyEvent.VK_LEFT;
    private final int MOVE_RIGHT = KeyEvent.VK_RIGHT;
    private final int DROPDOWN = KeyEvent.VK_UP;
    private final int FALL_FASTER = KeyEvent.VK_DOWN;
    private final int ROTATE_COUNTER_CLOCKWISE = KeyEvent.VK_Q;
    private final int ROTATE_CLOCKWISE = KeyEvent.VK_E;
    private final int PAUSE = KeyEvent.VK_P;

    public TetrisKeyboardAdapter(Board board) {
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (NoBlock.class.isInstance(board.getCurrentPiece())) {
            return;
        }

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case MOVE_LEFT -> board.tryMove(board.getCurrentPiece(),
                                                      board.getCurrentX() - 1,
                                                      board.getCurrentY());

            case MOVE_RIGHT -> board.tryMove(board.getCurrentPiece(),
                                                       board.getCurrentX() + 1,
                                                       board.getCurrentY());

            case DROPDOWN -> board.dropDown();

            case FALL_FASTER -> board.speedUpFallingTime();

            case ROTATE_COUNTER_CLOCKWISE -> {
                boolean didRotate = board.tryMove(board.getCurrentPiece().rotateCounterClockWise(),
                        board.getCurrentX(), board.getCurrentY());
                if (!didRotate) {
                    didRotate = board.tryMove(board.getCurrentPiece().rotateCounterClockWise(),
                            board.getCurrentX() - 1, board.getCurrentY());

                    if (!didRotate) {
                        board.tryMove(board.getCurrentPiece().rotateCounterClockWise(),
                                board.getCurrentX() + 1, board.getCurrentY());
                    }
                }
            }

            case ROTATE_CLOCKWISE -> {
                boolean didRotate = board.tryMove(board.getCurrentPiece().rotateClockWise(),
                        board.getCurrentX(), board.getCurrentY());
                if (!didRotate) {
                    didRotate = board.tryMove(board.getCurrentPiece().rotateClockWise(),
                            board.getCurrentX() - 1, board.getCurrentY());

                    if (!didRotate) {
                        board.tryMove(board.getCurrentPiece().rotateClockWise(),
                                board.getCurrentX() + 1, board.getCurrentY());
                    }
                }
            }

            case PAUSE-> board.pause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (NoBlock.class.isInstance(board.getCurrentPiece())) {
            return;
        }

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case FALL_FASTER -> board.normalFallingTime();
        }
    }
}
