package br.com.tetris;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

import br.com.tetris.shapes.Shape;
import br.com.tetris.shapes.NoBlock;

public class Board extends JPanel {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 24;
    private final int BOARD_SIZE = BOARD_HEIGHT * BOARD_WIDTH;
    private int ORIGINAL_PERIOD_INTERVAL;
    private int ACCELERATED_PERIOD_INTERVAL;
    private Shape currentPiece;
    private int currentX = 0;
    private int currentY = 0;
    private Shape.Tetrominos[] board;
    private int score = 0;
    private boolean isPaused = false;
    private boolean isFalling = true;
    private final Tetris game;

    public Board(Tetris game) {
        this.game = game;
        initBoard();
    }

    private void initBoard() {
        game.add(this);


        setFocusable(true);
        addKeyListener(new TetrisKeyboardAdapter(this));
    }

    public void start() {
        isPaused = false;
        isFalling = true;

        currentPiece = new Shape();
        board = new Shape.Tetrominos[BOARD_WIDTH * BOARD_HEIGHT];

        clearBoard();
        requestNewPiece();

        ORIGINAL_PERIOD_INTERVAL = game.getTimer().getDelay();
        ACCELERATED_PERIOD_INTERVAL = ORIGINAL_PERIOD_INTERVAL / 4;
    }

    private void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = Shape.Tetrominos.NoBlock;
        }
    }

    private void requestNewPiece() {
        currentPiece = Shape.setRandomShape();
        currentX = BOARD_WIDTH / 2; // Start in the middle
        currentY = BOARD_HEIGHT - 1 + currentPiece.getMinimumY();

        if (isPlayerDead()) {
            finishGame();
        }
    }

    private boolean isPlayerDead() {
        return !tryMove(currentPiece, currentX, currentY);
    }

    public boolean tryMove(Shape piece, int x, int y) {
        if (isPossibleToMove(piece, x, y)) {
            currentPiece = piece;
            currentX = x;
            currentY = y;

            repaint();

            return true;
        } else {
            return false;
        }
    }

    public boolean isPossibleToMove(Shape piece, int newX, int newY) {
        for (int i = 0; i < Shape.N_SMALL_PIECES_PER_SHAPE; i++) {
            int x = newX + piece.getX(i);
            int y = newY - piece.getY(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                return false;
            }

            if (getTetrominoAt(x, y) != Shape.Tetrominos.NoBlock) {
                return false;
            }
        }

        return true;
    }

    private Shape.Tetrominos getTetrominoAt(int x, int y) {
        return board[y * BOARD_WIDTH + x];
    }

    private void finishGame() {
        currentPiece = new NoBlock();
        game.getTimer().stop();

        game.getStatusBar().gameOver();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        int boardTop = (int) getSize().getHeight() - BOARD_HEIGHT * smallPieceHeight();

        //Draw board pieces
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                Shape.Tetrominos tetromino = getTetrominoAt(j, BOARD_HEIGHT - i - 1);

                if (tetromino != Shape.Tetrominos.NoBlock) {
                    Shape.getShapeFromTetromino(tetromino)
                            .drawTetromino(g,
                                    j * smallPieceWidth(),
                                    boardTop + i * smallPieceHeight(),
                                    smallPieceWidth(),
                                    smallPieceHeight());
                }
            }
        }

        //Draw preview
        drawPreview(g);

        //Draw current piece
        if (!NoBlock.class.isInstance(currentPiece)) {
            for (int i = 0; i < Shape.N_SMALL_PIECES_PER_SHAPE; i++) {
                int x = getCurrentPieceX(i);
                int y = getCurrentPieceY(i);

                currentPiece.drawTetromino(g,
                        x * smallPieceWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * smallPieceHeight(),
                        smallPieceWidth(),
                        smallPieceHeight());

            }
        }
    }

    private void drawPreview(Graphics g) {
        if (NoBlock.class.isInstance(currentPiece)) {
            return;
        }

        int boardTop = (int) getSize().getHeight() - BOARD_HEIGHT * smallPieceHeight();

        for (int i = 0; i < Shape.N_SMALL_PIECES_PER_SHAPE; i++) {
            int x = getCurrentPieceX(i);
            int y = getPreviewY() - currentPiece.getY(i);

            currentPiece.drawTetrominoPreview(g,
                    x * smallPieceWidth(),
                    boardTop + (BOARD_HEIGHT - y - 1) * smallPieceHeight(),
                    smallPieceWidth(),
                    smallPieceHeight());

        }
    }

    private int getPreviewY() {
        int y = getCurrentY();
        Shape tempPiece = currentPiece;

        while (y > 0) {
            if (!isPossibleToMove(tempPiece, currentX, y - 1)) {
                break;
            }
            y--;
        }

        return y;
    }

    private int smallPieceWidth() {
        return (int) getSize().getWidth() / BOARD_WIDTH;
    }

    private int smallPieceHeight() {
        return (int) getSize().getHeight() / BOARD_HEIGHT;
    }

    public void pause() {
        isPaused = !isPaused;
        game.getStatusBar().pause(isPaused);

        repaint();
    }

    public void update() {
        if (isPaused) {
            return;
        }

        if (isFalling) {
             dropPieceOnBoard();
        } else {
            isFalling = true;
            requestNewPiece();
        }
    }

    public void dropDown() {
        tryMove(currentPiece, currentX, getPreviewY());
        dropPieceOnBoard();
    }

    private void dropPieceOnBoard(){
        if (!tryMove(currentPiece, currentX, currentY - 1)) {
            droppingPieceOnBoard();
        }
    }

    private void droppingPieceOnBoard() {
        for (int i = 0 ; i < Shape.N_SMALL_PIECES_PER_SHAPE; i++) {
            int x = getCurrentPieceX(i);
            int y = getCurrentPieceY(i);
            board[y * BOARD_WIDTH + x] = currentPiece.getTetromino();
        }

        removeCompleteLine();

        if (isFalling) {
            requestNewPiece();
        }
    }

    private void removeCompleteLine() {
        int numberCompleteLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isLineComplete = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (getTetrominoAt(j, i) == Shape.Tetrominos.NoBlock) {
                    isLineComplete = false;
                    break;
                }
            }

            if (isLineComplete) {
                numberCompleteLines++;

                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[k * BOARD_WIDTH + j] = getTetrominoAt(j, k + 1);
                    }
                }
            }
        }

        if (numberCompleteLines > 0) {
            score += numberCompleteLines;

            game.getStatusBar().setScore(score);
            isFalling = false;
            currentPiece = new NoBlock();;
        }
    }

    public void speedUpFallingTime() {
        updateTimerDelay(ACCELERATED_PERIOD_INTERVAL);
    }

    public void normalFallingTime() {
        updateTimerDelay(ORIGINAL_PERIOD_INTERVAL);
    }

    private void updateTimerDelay(int delay) {
        game.getTimer().setDelay(delay);
    }

    public Shape getCurrentPiece() {
        return currentPiece;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    private int getCurrentPieceX(int idx) {
        return getCurrentX() + currentPiece.getX(idx);
    }

    private int getCurrentPieceY(int idx) {
        return getCurrentY() - currentPiece.getY(idx);
    }


}
