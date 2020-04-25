package br.com.tetris.shapes;

import java.awt.Color;

public class ZBlock extends Shape {
    public ZBlock() {
        super();
        int[][] coordinate = {{-1, 1}, {0, 1}, {0, 0}, {1, 0}};
        this.coordinate = coordinate;
        shapeColor = new Color(243, 50, 50);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.ZBlock;
    }
}
