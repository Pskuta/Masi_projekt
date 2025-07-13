package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import java.util.Map;

public abstract class Uniterm {
    protected double x;
    protected double y;
    protected Font czcionka;

    public Uniterm(double x, double y, Font czcionka) {
        this.x = x;
        this.y = y;
        this.czcionka = czcionka;
    }

    public abstract void draw(GraphicsContext gc, double dx, double dy, String tag);

    public abstract Map<String, Object> toMap();

    // Gettery i settery
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public Font getCzcionka() { return czcionka; }
    public void setCzcionka(Font czcionka) { this.czcionka = czcionka; }
}