package net.a3do.cubegame.model;

import android.util.Log;

import net.a3do.cubegame.view.MySurfaceView;

public class AnimatedRectangle extends Rectangle {

    private Direction direction = null;
    private final MySurfaceView mySurfaceView;

    public AnimatedRectangle(Rectangle rectangle, MySurfaceView mySurfaceView) {
        super(rectangle);
        this.mySurfaceView = mySurfaceView;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        int pixelInc = mySurfaceView.getPixelInc();
        switch (direction) {
            case TOP_LEFT:
                super.move(-pixelInc, -pixelInc);
                break;
            case TOP_RIGHT:
                super.move(pixelInc, -pixelInc);
                break;
            case BOTTOM_LEFT:
                super.move(-pixelInc, pixelInc);
                break;
            case BOTTOM_RIGHT:
                super.move(pixelInc, pixelInc);
                break;
        }
    }

    public Rectangle getDisplacedCopy() {
        int pixelInc = mySurfaceView.getPixelInc();
        switch (direction) {
            case TOP_LEFT:
                return new Rectangle(this, -pixelInc, -pixelInc);
            case TOP_RIGHT:
                return new Rectangle(this, pixelInc, -pixelInc);
            case BOTTOM_LEFT:
                return new Rectangle(this, -pixelInc, pixelInc);
            case BOTTOM_RIGHT:
                return new Rectangle(this, pixelInc, pixelInc);
            default:
                return new Rectangle(this);
        }
    }

    private boolean isAnyPointInside(Rectangle dR, Rectangle oR) {
        // dR = displacedRectangle
        // oR = otherRectangle
        return (dR.getTopLeft().isBetweenX(oR.getTopLeft(), oR.getTopRight()) && dR.getTopLeft().isBetweenY(oR.getTopLeft(), oR.getBottomLeft())) ||
                (dR.getTopRight().isBetweenX(oR.getTopLeft(), oR.getTopRight()) && dR.getTopRight().isBetweenY(oR.getTopLeft(), oR.getBottomLeft())) ||
                (dR.getBottomLeft().isBetweenX(oR.getTopLeft(), oR.getTopRight()) && dR.getBottomLeft().isBetweenY(oR.getTopLeft(), oR.getBottomLeft())) ||
                (dR.getBottomRight().isBetweenX(oR.getTopLeft(), oR.getTopRight()) && dR.getBottomRight().isBetweenY(oR.getTopLeft(), oR.getBottomLeft()));
    }

    public Direction ifIsGoingToCollideGetNewDirection(Rectangle otherRectangle) {
        // creamos un rectangle en base a este y lo desplazamos lo indicado por pixelInc
        Rectangle displacedRectangle = getDisplacedCopy();

        // obtenemos las esquinas de este nuevo Rectangle desplazado
        Point dTopLeft = displacedRectangle.getTopLeft();
        Point dTopRight = displacedRectangle.getTopRight();
        Point dBottomLeft = displacedRectangle.getBottomLeft();
        Point dBottomRight = displacedRectangle.getBottomRight();

        // obtenemos las esquinas del otherRectangle
        Point oTopLeft = otherRectangle.getTopLeft();
        Point oTopRight = otherRectangle.getTopRight();
        Point oBottomLeft = otherRectangle.getBottomLeft();
        Point oBottomRight = otherRectangle.getBottomRight();

        Direction newDirection = null;

//        Log.d("¿COLISION?", displacedRectangle.toString() + " X " + otherRectangle.toString() + " = " + isAnyPointInside(displacedRectangle, otherRectangle));

        if (isAnyPointInside(displacedRectangle, otherRectangle)) {
            int diffX;
            int diffY;
            switch (direction) {
                case TOP_LEFT:
                    diffX = oBottomRight.getX() - dTopLeft.getX();
                    diffY = oBottomRight.getY() - dTopLeft.getY();
                    if (diffX >= diffY) {
                        newDirection = Direction.BOTTOM_LEFT;
                    } else {
                        newDirection = Direction.TOP_RIGHT;
                    }
                    break;
                case TOP_RIGHT:
                    diffX = dTopRight.getX() - oBottomLeft.getX();
                    diffY = oBottomLeft.getY() - dTopRight.getY();
                    if (diffX >= diffY) {
                        newDirection = Direction.BOTTOM_RIGHT;
                    } else {
                        newDirection = Direction.TOP_LEFT;
                    }
                    break;
                case BOTTOM_LEFT:
                    diffX = oTopRight.getX() - dBottomLeft.getX();
                    diffY = dBottomLeft.getY() - oTopRight.getY();
                    if (diffX >= diffY) {
                        newDirection = Direction.TOP_LEFT;
                    } else {
                        newDirection = Direction.BOTTOM_RIGHT;
                    }
                    break;
                case BOTTOM_RIGHT:
                    diffX = dBottomRight.getX() - oTopLeft.getX();
                    diffY = dBottomRight.getY() - oTopLeft.getY();
                    if (diffX >= diffY) {
                        newDirection = Direction.TOP_RIGHT;
                    } else {
                        newDirection = Direction.BOTTOM_LEFT;
                    }
                    break;
            }
        }

        return newDirection;
    }

}
