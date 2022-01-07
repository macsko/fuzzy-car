package com.github.macsko.fuzzy_car.elements;

// Abstract element on road. Can be a Car or a Hole.
// Produces getters for positions of sides of element on a road.
// Additionally it provides scaled positions and width using general positionScale variable.
public abstract class Element {
    protected double xPos, yPos, positionScale;
    private final double WIDTH, HEIGHT;

    protected Element(double width, double height, double xPos, double yPos) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.xPos = xPos;
        this.yPos = yPos;
        this.positionScale = 1.0;
    }

    // Position of left side of element on a road
    public double left() {
        return xPos;
    }

    public double right() {
        return xPos + WIDTH;
    }

    public double top() {
        return yPos + HEIGHT;
    }

    public double bottom() {
        return yPos;
    }

    // Position of left side scaled by positionScale
    public int leftScaled() {
        return (int) (positionScale*xPos);
    }

    public int rightScaled() {
        return (int) (positionScale*(xPos + WIDTH));
    }

    public int topScaled() {
        return (int) (positionScale*(yPos + HEIGHT));
    }

    public int bottomScaled() {
        return (int) (positionScale*yPos);
    }

    // Scaled width of element
    public int widthScaled() {
        return (int) (positionScale*WIDTH);
    }

    public void setScale(double scale) {
        positionScale = scale;
    }
}
