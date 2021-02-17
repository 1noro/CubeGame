package net.a3do.cubegame.model;

import androidx.annotation.Nullable;

public class Square extends Rectangle {
    public Square(int x, int y, int squareSize, @Nullable String name) {
        super(x, y, x + squareSize, y + squareSize, name);
    }
}
