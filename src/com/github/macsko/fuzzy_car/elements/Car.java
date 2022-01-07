package com.github.macsko.fuzzy_car.elements;

import com.github.macsko.fuzzy_car.gui.IntObserver;
import com.github.macsko.fuzzy_car.Simulation;

public class Car extends Element implements IntObserver {
    public static final double WIDTH = 1.5;
    public static final double HEIGHT = 3.315;
    private double velocity; // yPos change in one move

    public Car() {
        super(WIDTH, HEIGHT, (Simulation.ROAD_WIDTH - WIDTH)/2, HEIGHT);
        velocity = 0.1;
    }

    public Car(double velocity) {
        super(WIDTH, HEIGHT, (Simulation.ROAD_WIDTH - WIDTH)/2, HEIGHT);
        this.velocity = velocity/500.0;
    }

    // Moves car by scaled velocity in y and xChange in x
    public void move(double xChange) {
        double xChangeAbs = Math.abs(xChange);
        if(xChangeAbs >= 0.3/Simulation.MOVE_STEPS) {
            // Keep car in road boundaries
            xPos = Math.min(Math.max(xPos + xChange, 0.0), Simulation.ROAD_WIDTH - WIDTH);
        }
        if(xChangeAbs < 0.5/Simulation.MOVE_STEPS) {
            yPos += velocity;
        }else if(xChangeAbs < 1.0/Simulation.MOVE_STEPS) { // xChange is too high, have to slow down for a while
            yPos += 0.6*velocity;
        }else {
            yPos += 0.3*velocity;
        }
    }

    public double getYPos() {
        return yPos;
    }

    public void update(int velocity) {
        this.velocity = velocity/500.0; // Input is in range 0-100, output in 0-0.2
    }

    // Computing distances from elements on road or curbs
    public double computeFrontDistance(Element elem) {
        if(elem.right() >= this.left() && elem.left() < this.right() && elem.bottom() >= this.top()) {
            return Math.min(elem.bottom() - this.top(), 25);
        }
        return 25; // It's like infinity
    }

    public double computeLeftDistance(Element elem) {
        if(elem.top() > this.bottom() && elem.right() < this.left()) {
            return this.left() - elem.right();
        }
        return this.left();
    }

    public double computeRightDistance(Element elem) {
        if(elem.top() > this.bottom() && elem.left() >= this.right()) {
            return elem.left() - this.right();
        }
        return Simulation.ROAD_WIDTH - this.right();
    }
}
