package com.github.macsko.fuzzy_car.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class OptionsPanel extends JPanel {

    // Creates and adds integer slider from 0-100, which notifies stateObserver on change
    private void addSlider(String text, int defaultValue, IntObserver stateObserver) {
        JLabel sliderLabel = new JLabel(text, JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, defaultValue);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int n = source.getValue();
                    stateObserver.update(n);
                }
            }
        });
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(1);
        slider.setPaintLabels(true);
        Font font = new Font("Serif", Font.ITALIC, 15);
        slider.setFont(font);

        add(sliderLabel);
        add(slider);
    }

    public OptionsPanel(IntObserver simObserver, IntObserver carObserver) {
        addSlider("Car velocity", 50, carObserver);
        addSlider("Simulation speed", 60, simObserver);
    }
}
