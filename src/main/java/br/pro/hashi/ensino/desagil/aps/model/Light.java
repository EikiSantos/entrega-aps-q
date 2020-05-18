package br.pro.hashi.ensino.desagil.aps.model;

import java.awt.*;

public class Light implements Receiver {
    private Color colorOn;
    private Color colorOff;
    private Emitter emitter;

    public Light(int r, int g, int b, int r2, int g2, int b2) {
        colorOn = new Color(r, g, b);
        colorOff = new Color(r2, g2, b2);
        emitter = null;
    }

    public Color getColor() {
        if (emitter != null && emitter.read()) {
            return colorOn;
        } else {
            return colorOff;
        }
    }

    public void setColorOn(Color color) {
        this.colorOn = color;
    }
    public void setColorOff(Color color) {
        this.colorOff = color;
    }


    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex != 0) {
            throw new IndexOutOfBoundsException(inputIndex);
        }
        this.emitter = emitter;
    }
}
