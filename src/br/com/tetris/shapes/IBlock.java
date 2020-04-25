package br.com.tetris.shapes;

import java.awt.Color;

public class IBlock extends Shape {
    public IBlock() {
        super();
        int[][] coordinate = {{0, -1}, {0, 0}, {0, 1}, {0, 2}};
        this.coordinate = coordinate;
        shapeColor = new Color(185, 185, 30);
    }

    public Tetrominos getTetromino() {
        return Tetrominos.IBlock;
    }
}
