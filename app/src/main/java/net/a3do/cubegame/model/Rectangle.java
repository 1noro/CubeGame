package net.a3do.cubegame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.Nullable;

public class Rectangle {

    int squareSize;

    private final Point topLeft;
    private final Point topRight;
    private final Point bottomLeft;
    private final Point bottomRight;

    private final String name;

    private final Paint paint;

    // Crear un nuevo Rectangle enm base a sus dos esquinas opuestas
    public Rectangle(int aX, int aY, int bX, int bY, @Nullable String name) {
        this.squareSize = bX - aX;

        this.topLeft = new Point(aX, aY);
        this.topRight = new Point(bX, aY);
        this.bottomLeft = new Point(aX, bY);
        this.bottomRight = new Point(bX, bY);
        this.name = name;

        this.paint = new Paint();
        this.paint.setColor(Color.BLUE);
    }

    // Crear un nuevo Rectangle enm base a otro Rectangle
    public Rectangle(Rectangle rectangle) {
        int aX = rectangle.getTopLeft().getX();
        int aY = rectangle.getTopLeft().getY();
        int bX = rectangle.getBottomRight().getX();
        int bY = rectangle.getBottomRight().getY();

        this.squareSize = bX - aX;

        this.topLeft = new Point(aX, aY);
        this.topRight = new Point(bX, aY);
        this.bottomLeft = new Point(aX, bY);
        this.bottomRight = new Point(bX, bY);
        this.name = rectangle.getName();

        this.paint = new Paint();
        this.paint.setColor(Color.BLUE);
    }

    // Crear una copia desplazada de un Rectangle
    public Rectangle(Rectangle rectangle, int dX, int dY) {
        int aX = rectangle.getTopLeft().getX();
        int aY = rectangle.getTopLeft().getY();
        int bX = rectangle.getBottomRight().getX();
        int bY = rectangle.getBottomRight().getY();

        this.squareSize = bX - aX;

        this.topLeft = new Point(aX + dX, aY + dY);
        this.topRight = new Point(bX + dX, aY + dY);
        this.bottomLeft = new Point(aX + dX, bY + dY);
        this.bottomRight = new Point(bX + dX, bY + dY);
        this.name = "Displaced copy of " + rectangle.getName();

        this.paint = new Paint();
        this.paint.setColor(Color.BLUE);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getTopRight() {
        return topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public String getName() {
        return name;
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }

    public void move(int dX, int dY) {
        this.topLeft.move(dX, dY);
        this.topRight.move(dX, dY);
        this.bottomLeft.move(dX, dY);
        this.bottomRight.move(dX, dY);
    }

    public void moveDirectly(int x, int y) {
        this.topLeft.moveDirectly(x, y);
        this.topRight.moveDirectly(x + squareSize, y);
        this.bottomLeft.moveDirectly(x, y + squareSize);
        this.bottomRight.moveDirectly(x + squareSize, y + squareSize);
    }

    public void moveCenterDirectly(int x, int y) {
        moveDirectly(x - squareSize / 2, y - squareSize / 2);
    }

    public void draw(Canvas canvas) {
        Rect rect = new Rect(this.topLeft.getX(), this.topLeft.getY(), this.bottomRight.getX(), this.bottomRight.getY());
        canvas.drawRect(rect, this.paint);
    }

    @Override
    public String toString() {
        return "[" + name + " " + topLeft.toString() + " " + topRight.toString() + " " + bottomLeft.toString() + " " + bottomRight.toString() + "]";
    }
}
