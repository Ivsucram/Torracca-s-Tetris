package br.com.tetris.shapes;

import java.awt.Color;

public class JBlock extends Shape {
    public JBlock() {
        super();
        int[][] coordinate = {{-1, 0}, {0, 0}, {0, 1}, {0, 2}};
        this.coordinate = coordinate;
        shapeColor = new Color(0,127,255);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.JBlock;
    }
}
