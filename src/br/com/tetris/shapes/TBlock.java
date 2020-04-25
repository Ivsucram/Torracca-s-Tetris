package br.com.tetris.shapes;

import java.awt.Color;

public class TBlock extends Shape {
    public TBlock() {
        super();
        int[][] coordinate = {{-1, 1}, {0, 1}, {1, 1}, {0, 0}};
        this.coordinate = coordinate;
        shapeColor = new Color(243, 116, 243);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.TBlock;
    }
}
