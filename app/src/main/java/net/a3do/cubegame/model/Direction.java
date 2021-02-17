package net.a3do.cubegame.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Direction {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    private static final Random RANDOM = new Random();
    private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    public static Direction getRandomDirection()  {
        return VALUES.get(RANDOM.nextInt(VALUES.size()));
    }

}
