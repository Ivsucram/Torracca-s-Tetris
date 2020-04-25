package br.com.tetris.shapes;

import java.awt.Color;

public class SBlock extends Shape {
    public SBlock() {
        super();
        int[][] coordinate = {{-1, 0}, {0, 0}, {0, 1}, {1, 1}};
        this.coordinate = coordinate;
        shapeColor = new Color(40, 159, 40);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.SBlock;
    }
}
