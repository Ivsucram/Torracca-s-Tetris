package br.com.tetris;

import javax.swing.JLabel;
import java.awt.*;

public class StatusBar extends JLabel{
    private final String PAUSE_MESSAGE = "Paused game. Press \"P\" to unpause";
    private final Color backgroundColor = Color.lightGray;
    private final int SCORE_MULTIPLIER = 100;
    private int score;
    private String text;
    private boolean isPaused = false;
    private final Tetris game;

    public StatusBar(Tetris game) {
        this(game, 0);
    }

    public StatusBar(Tetris game, int score) {
        super();
        this.game = game;
        this.score = score;
    }

    private void initStatusBar() {
        this.setOpaque(true);
        this.setBackground(backgroundColor);
        game.add(this, BorderLayout.SOUTH);
    }

    private void updateScoreText() {
        text = String.format("Score: %d", score * SCORE_MULTIPLIER);
        setText(text);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        super.setText(text);
    }

    public void start() {
        initStatusBar();
        this.score = 0;
        updateScoreText();
    }

    public void pause(boolean isPaused) {
        this.isPaused = !isPaused;
        pause();
    }

    public void pause() {
        isPaused = !isPaused;

        if (isPaused) {
            setText(PAUSE_MESSAGE);
        } else {
            updateScoreText();
        }
    }

    public void setScore(int score) {
        this.score = score;
        updateScoreText();
    }

    public void gameOver() {
        setText(String.format("Game over. Score: %d", score * SCORE_MULTIPLIER));
    }
}
