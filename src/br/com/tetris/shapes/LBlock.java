package br.com.tetris.shapes;

import java.awt.Color;

public class LBlock extends Shape {
    public LBlock() {
        super();
        int[][] coordinate = {{1,  0}, {0, 0}, {0, 1}, {0, 2}};
        this.coordinate = coordinate;
        shapeColor = new Color(116, 38, 159);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.LBlock;
    }
}
