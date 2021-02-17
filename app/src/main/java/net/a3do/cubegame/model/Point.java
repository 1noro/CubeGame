package net.a3do.cubegame.model;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void move(int dX, int dY) {
        this.x += dX;
        this.y += dY;
    }

    public void moveDirectly(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
    public boolean isLeftOf(Point otherPoint) {
        return this.x <= otherPoint.getX();
    }
    public boolean isRightOf(Point otherPoint) {
        return this.x > otherPoint.getX();
    }
    public boolean isAboveOf(Point otherPoint) {
        return this.y <= otherPoint.getY();
    }
    public boolean isBelowOf(Point otherPoint) {
        return this.y > otherPoint.getY();
    }
    */

    public boolean isBetweenX(Point pointLeft, Point pointRight) {
        return this.x >= pointLeft.getX() && this.x < pointRight.getX();
    }

    public boolean isBetweenY(Point pointTop, Point pointBottom) {
        return this.y >= pointTop.getY() && this.y < pointBottom.getY();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
