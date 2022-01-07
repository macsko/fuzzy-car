package com.github.macsko.fuzzy_car.gui;

import com.github.macsko.fuzzy_car.elements.Car;
import com.github.macsko.fuzzy_car.elements.Element;

import javax.swing.*;
import java.awt.*;

public class RoadPanel extends JPanel {
    private final static int WIDTH = 600;
    private final static int HEIGHT = 800;
    private final static int GRASS_WIDTH = 100;
    private final static int LINE_WIDTH = 20;
    private final static int LINE_HEIGHT = 50;
    private final static int LINE_GAP = 30;
    private final static int CAR_WIDTH = 100;
    private final double SCALE_RATIO;
    private int CAR_HEIGHT;
    private Image carImage;
    private final Element car;
    private final Element[] holes;

    private void loadCarImage() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("images/car.png");
        i = i.getScaledInstance(CAR_WIDTH, -1, Image.SCALE_DEFAULT);
        carImage = new ImageIcon(i).getImage();
        CAR_HEIGHT = carImage.getHeight(this);
    }

    public RoadPanel(Element car, Element[] holes) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.car = car;
        this.holes = holes;
        loadCarImage();
        SCALE_RATIO = CAR_WIDTH/ Car.WIDTH;
        car.setScale(SCALE_RATIO);
    }

    void drawCar(Graphics2D g2d) {
        g2d.drawImage(carImage,
                WIDTH - car.rightScaled() - GRASS_WIDTH,
                HEIGHT - CAR_HEIGHT, // draw on the bottom
                this);
    }

    void drawHoles(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        for(Element hole : holes) {
            if(hole == null) {
                break;
            }
            hole.setScale(SCALE_RATIO);
            // Draw hole as black circle
            g2d.fillOval(WIDTH - hole.rightScaled() - GRASS_WIDTH,
                    HEIGHT - hole.topScaled() + car.bottomScaled(),
                    hole.widthScaled(),
                    hole.widthScaled());
        }
    }

    void drawRoad(Graphics2D g2d) {
        // Draw road
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(GRASS_WIDTH, 0, WIDTH - 2*GRASS_WIDTH, HEIGHT);

        // Draw two curbs
        g2d.setColor(new Color(0x20, 0x20, 0x20));
        g2d.fillRect(GRASS_WIDTH - 10, 0, 10, HEIGHT);
        g2d.fillRect(WIDTH - GRASS_WIDTH, 0, 10, HEIGHT);

        // Draw road lines
        g2d.setColor(Color.WHITE);
        int lineFullHeight = LINE_HEIGHT + LINE_GAP;
        int yStart = (-car.bottomScaled())%lineFullHeight - LINE_HEIGHT;
        while(yStart <= HEIGHT) {
            int lineHeight = LINE_HEIGHT;
            if(yStart < 0) {
                lineHeight = LINE_HEIGHT + yStart;
            }
            g2d.fillRect((WIDTH - LINE_WIDTH)/2,
                    HEIGHT - yStart - LINE_HEIGHT,
                    LINE_WIDTH,
                    lineHeight);
            yStart += lineFullHeight;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw grass as background
        setBackground(new Color(0, 0x64, 0));
        Graphics2D g2d = (Graphics2D) g;
        drawRoad(g2d);
        drawHoles(g2d);
        drawCar(g2d);
    }
}
