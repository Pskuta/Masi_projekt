package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class SekZroMerge {
    private UnitermZrownoleglenie zrownolegleniaUniterm;
    private UnitermSekwencja sekwencjaUniterm;
    private int mergeOption;

    public SekZroMerge(UnitermZrownoleglenie zrownolegleniaUniterm,
                       UnitermSekwencja sekwencjaUniterm,
                       int mergeOption) {
        // Tworzymy kopie obiektów
        this.zrownolegleniaUniterm = createCopyOfZrownoleglenie(zrownolegleniaUniterm);
        this.sekwencjaUniterm = createCopyOfSekwencja(sekwencjaUniterm);
        this.mergeOption = mergeOption;
    }

    private UnitermZrownoleglenie createCopyOfZrownoleglenie(UnitermZrownoleglenie original) {
        List<String> copiedLinie = new ArrayList<>(original.getLinie());
        return new UnitermZrownoleglenie(
                copiedLinie,
                original.getX(),
                original.getY(),
                original.getCzcionka(),
                original.getOdstep(),
                original.getOffsetX(),
                original.getOffsetY()
        );
    }

    private UnitermSekwencja createCopyOfSekwencja(UnitermSekwencja original) {
        return new UnitermSekwencja(
                original.getX(),
                original.getY(),
                original.getCzcionka(),
                original.getTekst(),
                original.getWysokoscNawiasu()
        );
    }


    public void draw(GraphicsContext gc, String tag) {
        UnitermZrownoleglenie mergedUniterm = zrownolegleniaUniterm;
        mergedUniterm.setOdstep(mergedUniterm.getOdstep() + 10);
        mergedUniterm.setOffsetY(mergedUniterm.getOffsetY() + 10);

        List<String> linie = mergedUniterm.getLinie();

        // Ustawiamy odpowiednie pozycje i kolory strzałek w zależności od opcji merge
        switch (mergeOption) {
            case 1: // Opcja A
                linie.set(0, "");
                mergedUniterm.setLinie(linie);
                mergedUniterm.draw(gc, 200, 50, tag);
                sekwencjaUniterm.draw(gc, 210, 65, tag);

                // Zielona strzałka
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(5);
                drawArrow(gc, 100, 80, 220, 140);
                break;

            case 2: // Opcja B
                linie.set(2, "");
                mergedUniterm.setLinie(linie);
                mergedUniterm.draw(gc, 200, 50, tag);
                sekwencjaUniterm.draw(gc, 245, 65, tag);

                // Pomarańczowa strzałka
                gc.setStroke(Color.ORANGE);
                gc.setLineWidth(5);
                drawArrow(gc, 100, 80, 265, 155);
                break;

            case 3: // Opcja C
                linie.set(4, "");
                mergedUniterm.setLinie(linie);
                mergedUniterm.draw(gc, 200, 50, tag);
                sekwencjaUniterm.draw(gc, 285, 65, tag);

                // Fioletowa strzałka
                gc.setStroke(Color.MEDIUMVIOLETRED);
                gc.setLineWidth(5);
                drawArrow(gc, 100, 80, 290, 155);
                break;
        }
    }



    private void drawArrow(GraphicsContext gc, double startX, double startY, double endX, double endY) {
        // Rysuj linię
        gc.strokeLine(startX, startY, endX, endY);

        // Oblicz kąt dla grotu strzałki
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowLength = 10;
        double arrowAngle = Math.PI / 6; // 30 stopni

        // Oblicz punkty dla grotu strzałki
        double x1 = endX - arrowLength * Math.cos(angle - arrowAngle);
        double y1 = endY - arrowLength * Math.sin(angle - arrowAngle);
        double x2 = endX - arrowLength * Math.cos(angle + arrowAngle);
        double y2 = endY - arrowLength * Math.sin(angle + arrowAngle);

        // Rysuj grot strzałki
        gc.strokeLine(endX, endY, x1, y1);
        gc.strokeLine(endX, endY, x2, y2);
    }

    // Gettery
    public UnitermZrownoleglenie getZrownolegleniaUniterm() { return zrownolegleniaUniterm; }
    public UnitermSekwencja getSekwencjaUniterm() { return sekwencjaUniterm; }
    public int getMergeOption() { return mergeOption; }
}