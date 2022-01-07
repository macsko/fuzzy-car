package com.github.macsko.fuzzy_car.elements;

import com.github.macsko.fuzzy_car.Simulation;

import java.util.concurrent.ThreadLocalRandom;

public class Hole extends Element {
    public static final double WIDTH = 0.5;

    public Hole(double currentY) {
        super(WIDTH,
              WIDTH,
              ThreadLocalRandom.current().nextDouble(0, Simulation.ROAD_WIDTH - WIDTH),
              ThreadLocalRandom.current().nextDouble(currentY + 15, currentY + 20));
    }
}
