package net.a3do.cubegame.model;

import android.graphics.Canvas;

// para el futuro
public class WeakWall extends Rectangle {

    private boolean visible = true;

    public WeakWall(Rectangle rectangle) {
        super(rectangle);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void draw(Canvas canvas) {
        if (visible) {
            super.draw(canvas);
        }
    }
}
