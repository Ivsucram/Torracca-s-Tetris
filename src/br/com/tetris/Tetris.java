package br.com.tetris;

import javax.swing.*;

import java.awt.EventQueue;

public class Tetris extends JFrame {
    private Board board;
    private StatusBar statusBar;
    private TetrisMenu tetrisMenu;
    private Timer timer;

    private final int ORIGINAL_PERIOD_INTERVAL = 300;

    public Tetris() {
        setTitle("Cool Ugly Tetris");
        setSize(400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        initStatusBoard();
        initTetrisMenu();
        initBoard();

        start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Tetris::new);
    }

    private void initStatusBoard() {
        statusBar = new StatusBar(this);
    }

    private void initBoard() {
        board = new Board(this);
    }

    private void initTetrisMenu() {
        tetrisMenu = new TetrisMenu(this);
    }

    private void start() {
        timer = new Timer(ORIGINAL_PERIOD_INTERVAL, new GameCycle(board));

        board.start();
        statusBar.start();
        timer.start();
    }

    public Timer getTimer() {
        return timer;
    }
    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void restart() {
        timer.stop();
        start();
    }

    public void exit() {
        System.exit(0);
    }
}