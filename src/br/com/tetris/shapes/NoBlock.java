package br.com.tetris.shapes;

import java.awt.Color;

public class NoBlock extends Shape {
    public NoBlock() {
        super();
        int[][] coordinate = {{0,  0}, {0, 0}, {0, 0}, {0, 0}};
        this.coordinate = coordinate;
        shapeColor = new Color(0, 0, 0);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.NoBlock;
    }
}
