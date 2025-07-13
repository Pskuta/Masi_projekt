package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;

public class UnitermSekwencja extends Uniterm {
    private String tekst;
    private double wysokoscNawiasu;
    private double szerokoscTekstu;

    public UnitermSekwencja(double x, double y, Font czcionka, String tekst, double wysokoscNawiasu) {
        super(x, y, czcionka);
        this.tekst = tekst;
        this.wysokoscNawiasu = wysokoscNawiasu ;
        calculateTextWidth();
    }

    private void calculateTextWidth() {
        Text textNode = new Text(tekst);
        textNode.setFont(czcionka);
        this.szerokoscTekstu = textNode.getBoundsInLocal().getWidth() + 5;
    }

    @Override
    public void draw(GraphicsContext gc, double dx, double dy, String tag) {
        double drawX = x + dx;
        double drawY = y + dy;

        // Parsowanie tekstu
        String[] parts = tekst.split(" ");
        String elementA = parts.length > 0 ? parts[0] : "";
        String separator = parts.length > 1 ? parts[1] : "";
        String elementB = parts.length > 2 ? parts[2] : "";

        // Obliczenie wymiarów tekstu
        Text textNodeA = new Text(elementA);
        textNodeA.setFont(czcionka);
        double heightA = textNodeA.getBoundsInLocal().getHeight();
        double widthA = textNodeA.getBoundsInLocal().getWidth();

        Text textNodeSep = new Text(separator);
        textNodeSep.setFont(czcionka);
        double heightSep = textNodeSep.getBoundsInLocal().getHeight();
        double widthSep = textNodeSep.getBoundsInLocal().getWidth();

        Text textNodeB = new Text(elementB);
        textNodeB.setFont(czcionka);
        double heightB = textNodeB.getBoundsInLocal().getHeight();
        double widthB = textNodeB.getBoundsInLocal().getWidth();

        // Obliczenie maksymalnej szerokości dla centrowania
        double maxWidth = Math.max(Math.max(widthA, widthSep), widthB);

        // Rysowanie tekstu pionowo
        gc.setFont(czcionka);
        gc.setFill(Color.BLACK);

        // Element A
        double xA = drawX + (maxWidth - widthA) / 2;
        gc.fillText(elementA, xA, drawY + heightA);

        // Separator
        double xSep = drawX + (maxWidth - widthSep) / 2;
        gc.fillText(separator, xSep, drawY + heightA + heightSep + 5);

        // Element B
        double xB = drawX + (maxWidth - widthB) / 2;
        gc.fillText(elementB, xB, drawY + heightA + heightSep + heightB + 10);

        // Rysowanie pionowego łuku (nawiasu)
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);

        double totalHeight = heightA + heightSep + heightB + 15;
        double arcX = drawX + maxWidth + 5;
        double arcY = drawY;

        // Pionowy łuk -  strokeArc z rotacją o 90 stopni
        gc.strokeArc(arcX, arcY, wysokoscNawiasu, totalHeight, 270, 180, javafx.scene.shape.ArcType.OPEN);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("x", x);
        data.put("y", y);
        data.put("tekst", tekst);
        data.put("wysokoscNawiasu", wysokoscNawiasu);
        data.put("fontFamily", czcionka.getFamily());
        data.put("fontSize", czcionka.getSize());
        return data;
    }

    public static UnitermSekwencja fromMap(Map<String, Object> data) {
        double x = ((Number) data.get("x")).doubleValue();
        double y = ((Number) data.get("y")).doubleValue();
        String tekst = (String) data.get("tekst");
        double wysokoscNawiasu = ((Number) data.get("wysokoscNawiasu")).doubleValue();
        String fontFamily = (String) data.get("fontFamily");
        double fontSize = ((Number) data.get("fontSize")).doubleValue();

        Font font = Font.font(fontFamily, fontSize);

        return new UnitermSekwencja(x, y, font, tekst, wysokoscNawiasu);
    }

    // Gettery i settery
    public String getTekst() { return tekst; }
    public void setTekst(String tekst) {
        this.tekst = tekst;
        calculateTextWidth();
    }

    public double getWysokoscNawiasu() { return wysokoscNawiasu; }
    public void setWysokoscNawiasu(double wysokoscNawiasu) {
        this.wysokoscNawiasu = wysokoscNawiasu;
    }

    public double getSzerokoscTekstu() { return szerokoscTekstu; }
}