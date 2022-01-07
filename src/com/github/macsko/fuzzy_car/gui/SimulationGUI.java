package com.github.macsko.fuzzy_car.gui;

import com.github.macsko.fuzzy_car.elements.Element;

import javax.swing.*;
import java.awt.*;

public class SimulationGUI extends JFrame {
    private final RoadPanel roadPanel;

    public SimulationGUI(IntObserver simObserver, IntObserver carObserver, Element car, Element[] holes) {
        super("Car simulation");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Road graphics
        roadPanel = new RoadPanel(car, holes);
        add(roadPanel);

        // Options steering
        JPanel optionsPanel = new OptionsPanel(simObserver, carObserver);
        add(optionsPanel, BorderLayout.PAGE_END);

        pack();
        setVisible(true);

        requestFocusInWindow();
    }

    // Repainting road on parameters change
    public void repaint() {
        roadPanel.repaint();
    }
}
