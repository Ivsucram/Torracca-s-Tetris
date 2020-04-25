package br.com.tetris.shapes;

import java.awt.Color;

public class OBlock extends Shape {
    public OBlock() {
        super();
        int[][] coordinate = {{0,  0}, {0, 1}, {1, 1}, {1, 0}};
        this.coordinate = coordinate;
        shapeColor = new Color(255, 172, 48);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.OBlock;
    }
}
