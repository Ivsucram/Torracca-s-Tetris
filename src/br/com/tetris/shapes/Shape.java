package br.com.tetris.shapes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;

public class Shape {

    public static final int N_SMALL_PIECES_PER_SHAPE = 4;
    private final int X_COORDINATE = 0;
    private final int Y_COORDINATE = 1;

    protected int[][] coordinate;

    protected Color shapeColor;

    public static Shape setRandomShape() {
        Random random = new Random();
        int x = Math.abs(random.nextInt()) % (Tetrominos.values().length - 1);

        Shape shape = switch (x) {
            case 0 -> new IBlock();
            case 1 -> new OBlock();
            case 2 -> new TBlock();
            case 3 -> new SBlock();
            case 4 -> new ZBlock();
            case 5 -> new LBlock();
            case 6 -> new JBlock();
            default -> new NoBlock();
        };
        return shape;
    }

    public int getMinimumY() {
        int minimumY = coordinate[0][Y_COORDINATE];

        for (int i = 1; i < N_SMALL_PIECES_PER_SHAPE; i++) {
            minimumY = Math.min(minimumY, coordinate[i][Y_COORDINATE]);
        }

        return minimumY;
    }

    private void setX(int index, int x) {
        coordinate[index][X_COORDINATE] = x;
    }

    private void setY(int index, int y) {
        coordinate[index][Y_COORDINATE] = y;
    }

    public int getX(int index) {
        return coordinate[index][X_COORDINATE];
    }

    public int getY(int index) {
        return coordinate[index][Y_COORDINATE];
    }

    public Shape rotateClockWise() {
        if (NoBlock.class.isInstance(this) || OBlock.class.isInstance(this)) {
            return this;
        }

        try {
            Constructor<? extends Shape> constructor = this.getClass().getDeclaredConstructor();
            Shape shape = constructor.newInstance();
            for (int i = 0; i < N_SMALL_PIECES_PER_SHAPE; i++) {
                shape.setX(i, -getY(i));
                shape.setY(i, getX(i));
            }
            return shape;
        } catch (Exception e) {
          e.printStackTrace();
        }

        return this;
    }

    public Shape rotateCounterClockWise() {
        if (NoBlock.class.isInstance(this) || OBlock.class.isInstance(this)) {
            return this;
        }

        try {
            Constructor<? extends Shape> constructor = this.getClass().getDeclaredConstructor();
            Shape shape = constructor.newInstance();
            for (int i = 0; i < N_SMALL_PIECES_PER_SHAPE; i++) {
                shape.setX(i, getY(i));
                shape.setY(i, -getX(i));
            }
            return shape;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public Tetrominos getTetromino() {
        return Tetrominos.NoBlock;
    }

    public static Shape getShapeFromTetromino(Tetrominos tetromino) {
        Shape shape = switch(tetromino) {
            case IBlock -> new IBlock();
            case OBlock -> new OBlock();
            case TBlock -> new TBlock();
            case SBlock -> new SBlock();
            case ZBlock -> new ZBlock();
            case LBlock -> new LBlock();
            case JBlock -> new JBlock();
            default -> new NoBlock();
        };

        return shape;
    }

    public enum Tetrominos {
        NoBlock,
        IBlock,
        OBlock,
        TBlock,
        SBlock,
        ZBlock,
        LBlock,
        JBlock
    }

    public void drawTetromino(Graphics g, int x, int y, int width, int height) {
        drawInnerTetromino(shapeColor, g, x, y, width, height);
        drawBrighterBorder(shapeColor, g, x, y, width, height);
        drawDarkerBorder(shapeColor, g, x, y, width, height);
    }

    public void drawTetrominoPreview(Graphics g, int x, int y, int width, int height) {
        Color color = shapeColor.brighter().brighter();
        drawInnerTetromino(color, g, x, y, width, height);
        drawBrighterBorder(color, g, x, y, width, height);
        drawDarkerBorder(color, g, x, y, width, height);
    }

    private void drawInnerTetromino(Color color, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.fillRect(x + 1, y + 1, width - 2, height - 2);
    }

    private void drawBrighterBorder(Color color, Graphics g, int x, int y, int width, int height) {
        g.setColor(color.brighter());
        g.drawLine(x, y + height - 1, x, y);
        g.drawLine(x, y, x + width - 1, y);
    }

    private void drawDarkerBorder(Color color, Graphics g, int x, int y, int width, int height) {
        g.setColor(color.darker());
        g.drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
        g.drawLine(x + width - 1, y + height - 1, x + width - 1, y + 1);
    }


}
