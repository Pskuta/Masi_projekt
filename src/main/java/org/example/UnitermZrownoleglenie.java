package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitermZrownoleglenie extends Uniterm {
    private List<String> linie;
    private double odstep;
    private double offsetX;
    private double offsetY;
    private double wysokoscTekstu;

    public UnitermZrownoleglenie(List<String> linie, double x, double y, Font czcionka,
                                 double odstep, double offsetX, double offsetY) {
        super(x, y, czcionka);
        this.linie = new ArrayList<>(linie);
        this.odstep = odstep;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        calculateTextHeight();
    }

    private void calculateTextHeight() {
        Text textNode = new Text("Sample");
        textNode.setFont(czcionka);
        this.wysokoscTekstu = textNode.getBoundsInLocal().getHeight();
    }

    @Override
    public void draw(GraphicsContext gc, double dx, double dy, String tag) {
        double drawX = x + dx;
        double drawY = y + dy;

        // Rysowanie tekstu poziomo z odstępami
        gc.setFont(czcionka);
        gc.setFill(Color.BLACK);

        double currentX = drawX + offsetX;
        double maxWidth = 0;

        // Obliczenie szerokości każdego elementu i całkowitej szerokości
        List<Double> widths = new ArrayList<>();
        for (String linia : linie) {
            Text textNode = new Text(linia);
            textNode.setFont(czcionka);
            double width = textNode.getBoundsInLocal().getWidth();
            widths.add(width);
            maxWidth += width;
        }

        // Dodanie odstępów między elementami
        maxWidth += (linie.size() - 1) * odstep;

        // Rysowanie elementów poziomo
        for (int i = 0; i < linie.size(); i++) {
            String linia = linie.get(i);
            gc.fillText(linia, currentX, drawY + offsetY + wysokoscTekstu);
            currentX += widths.get(i) + odstep;
        }

        // Rysowanie nawiasu kwadratowego [ ]
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);

        double startX = drawX + offsetX - 5;
        double endX = drawX + offsetX + maxWidth + 5;
        double topY = drawY + offsetY - 5;
        double bottomY = drawY + offsetY + wysokoscTekstu + 5;

        // Lewy nawias kwadratowy [
        gc.strokeLine(startX, topY, startX, bottomY);        // Pionowa linia
        gc.strokeLine(startX, topY, startX + 5, topY);       // Górna pozioma linia
        gc.strokeLine(startX, bottomY, startX + 5, bottomY); // Dolna pozioma linia

        // Prawy nawias kwadratowy ]
        gc.strokeLine(endX, topY, endX, bottomY);            // Pionowa linia
        gc.strokeLine(endX - 5, topY, endX, topY);           // Górna pozioma linia
        gc.strokeLine(endX - 5, bottomY, endX, bottomY);     // Dolna pozioma linia
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("linie", new ArrayList<>(linie));
        data.put("x", x);
        data.put("y", y);
        data.put("odstep", odstep);
        data.put("offsetX", offsetX);
        data.put("offsetY", offsetY);
        data.put("fontFamily", czcionka.getFamily());
        data.put("fontSize", czcionka.getSize());
        return data;
    }


    public static UnitermZrownoleglenie fromMap(Map<String, Object> data) {
        List<String> linie = (List<String>) data.get("linie");
        double x = ((Number) data.get("x")).doubleValue();
        double y = ((Number) data.get("y")).doubleValue();
        double odstep = ((Number) data.get("odstep")).doubleValue();
        double offsetX = ((Number) data.get("offsetX")).doubleValue();
        double offsetY = ((Number) data.get("offsetY")).doubleValue();
        String fontFamily = (String) data.get("fontFamily");
        double fontSize = ((Number) data.get("fontSize")).doubleValue();

        Font font = Font.font(fontFamily, fontSize);

        return new UnitermZrownoleglenie(linie, x, y, font, odstep, offsetX, offsetY);
    }

    // Gettery i settery
    public List<String> getLinie() { return new ArrayList<>(linie); }
    public void setLinie(List<String> linie) {
        this.linie = new ArrayList<>(linie);
    }

    public double getOdstep() { return odstep; }
    public void setOdstep(double odstep) { this.odstep = odstep; }

    public double getOffsetX() { return offsetX; }
    public void setOffsetX(double offsetX) { this.offsetX = offsetX; }

    public double getOffsetY() { return offsetY; }
    public void setOffsetY(double offsetY) { this.offsetY = offsetY; }

    public double getWysokoscTekstu() { return wysokoscTekstu; }
}