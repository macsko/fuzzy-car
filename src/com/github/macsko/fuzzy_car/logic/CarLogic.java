package com.github.macsko.fuzzy_car.logic;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

// Controller of car fuzzy logic
public class CarLogic {
    private final FIS fis;
    public CarLogic() {
        fis = FIS.load("car.fcl", false);
    }

    // Evaluates car direction using fuzzy logic.
    // To do this, fuzzy controller need values of three sensors: left, right and front.
    // Right and left sensors have to be in range [0.0, 6.0]. Front sensor have to be in range [0.0, 25.0].
    // Returns direction change from range [-2.0, 2.0].
    public double evaluate(double leftSensor, double rightSensor, double frontSensor) {
        // Input variables
        fis.setVariable("left_sensor", leftSensor);
        fis.setVariable("right_sensor", rightSensor);
        fis.setVariable("front_sensor", frontSensor);
        // Fuzzy interface evaluation
        fis.evaluate();
        // Output variable
        Variable v = fis.getVariable("direction_change");
        return v.getLatestDefuzzifiedValue();
    }

    public void plot(double leftSensor, double rightSensor, double frontSensor) {
        evaluate(leftSensor, rightSensor, frontSensor);
        JFuzzyChart.get().chart(fis);
        Variable v = fis.getVariable("direction_change");
        JFuzzyChart.get().chart(v, v.getDefuzzifier(), true);
    }
}
