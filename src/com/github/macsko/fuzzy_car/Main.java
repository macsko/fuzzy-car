package com.github.macsko.fuzzy_car;

import com.github.macsko.fuzzy_car.logic.CarLogic;

public class Main {
    // Program can be called with arguments. When 1st argument is "show", then carLogic is plotted.
    // Otherwise, simulation is starting.
    public static void main(String[] args) {
        CarLogic carLogic = new CarLogic();
        if(args.length > 0 && args[0].equals("show")) {
            if(args.length < 4) {
                System.err.println("To plot carLogic, values of three sensors should be passed.");
                return;
            }
            double leftSensor = Double.parseDouble(args[1]);
            double rightSensor = Double.parseDouble(args[2]);
            double frontSensor = Double.parseDouble(args[3]);
            carLogic.plot(leftSensor, rightSensor, frontSensor);
        }else {
            Simulation sim = new Simulation(carLogic);
            sim.run();
        }
    }
}
