package com.github.macsko.fuzzy_car;

import com.github.macsko.fuzzy_car.elements.Car;
import com.github.macsko.fuzzy_car.elements.Hole;
import com.github.macsko.fuzzy_car.gui.IntObserver;
import com.github.macsko.fuzzy_car.gui.SimulationGUI;
import com.github.macsko.fuzzy_car.logic.CarLogic;

public class Simulation implements IntObserver {
    public static final double ROAD_WIDTH = 6.0;
    public static final double MOVE_STEPS = 5; // Number of car moves on one fuzzy evaluating
    private int speed = 8; // Simulation speed
    private final CarLogic carLogic;
    private final Car car;
    private final Hole[] holes; // Current holes on the road

    public Simulation() {
        carLogic = new CarLogic();
        car = new Car(50);

        holes = new Hole[2];
        holes[0] = new Hole(0);
        holes[1] = new Hole(-25); // For consistency
    }

    public Simulation(CarLogic carLogic) {
        this.carLogic = carLogic;
        car = new Car(50);

        holes = new Hole[2];
        holes[0] = new Hole(0);
        holes[1] = new Hole(-25); // For consistency
    }
    
    public void run() {
        SimulationGUI gui = new SimulationGUI(this, car, car, holes);

        double xChange = 0;
        double moveStep = 0;

        double holeDistance;
        double leftDistance;
        double rightDistance;

        while(true) {
            // Simulating car's front sensor
            holeDistance = car.computeFrontDistance(holes[0]);

            // Simulating car's side sensors
            leftDistance = car.computeLeftDistance(holes[1]);
            rightDistance = car.computeRightDistance(holes[1]);

            // Evaluating car's xPos change with fuzzy controller
            if(moveStep == 0) {
                xChange = carLogic.evaluate(leftDistance, rightDistance, holeDistance);
                // Bounding xChange by side sensors
                if(xChange >= 0) {
                    xChange = Math.min(xChange, rightDistance);
                }else {
                    xChange = -Math.min(-xChange, leftDistance);
                }
                moveStep = MOVE_STEPS;
            }

            // Moving a car by part of xChange
            car.move(xChange/ MOVE_STEPS);
            moveStep--;

            // Generating a new hole if needed
            if(holes[0].bottom() < car.top()) {
                holes[1] = holes[0];
                holes[0] = new Hole(car.getYPos());
            }

            gui.repaint();

            try {
                Thread.sleep(20 - speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(int speed) {
        this.speed = speed/5;
    }
}
