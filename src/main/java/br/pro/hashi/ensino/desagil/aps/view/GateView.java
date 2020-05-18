package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {


    private final Gate gate;
    private final JCheckBox A_receiverBox;
    private final JCheckBox B_receiverBox;
    private final Switch signal_A;
    private final Switch signal_B;
    private final Image image;
    private final Light light;
    private Color colorOn;
    private Color colorOff;
    private Color color;

    public GateView(Gate gate) {

        super(240, 140);

        this.gate = gate;
        this.signal_A = new Switch();
        this.signal_B = new Switch();
        A_receiverBox = new JCheckBox();
        B_receiverBox = new JCheckBox();

        add(A_receiverBox, 28, 65, 15, 15);
        if (gate.getInputSize() > 1) {
            add(B_receiverBox, 28, 80, 15, 15);
            add(A_receiverBox, 28, 30 + 15, 15, 15);
        }

        this.light = new Light(255, 0, 0, 0, 0, 0);

        String name = gate.toString() + ".png";
        System.out.println(name);
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        A_receiverBox.addActionListener(this);
        B_receiverBox.addActionListener(this);


        addMouseListener(this);
        update();
    }

    private void update() {
        signal_A.turnOff();
        signal_B.turnOff();
        if (A_receiverBox.isSelected()) {
            signal_A.turnOn();
        }

        if (B_receiverBox.isSelected()) {
            signal_B.turnOn();
        }

        gate.connect(0, signal_A);

        if (gate.getInputSize() > 1) {
            gate.connect(1, signal_B);
        }
        light.connect(0, gate);


        repaint();


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        if (gate.read()) {
            if ((x - 212.5) * (x - 212.5) + (y - (70)) * (y - 70) < 12.5 * 12.5) {
                colorOn = JColorChooser.showDialog(this, null, colorOn);
                light.setColorOn(colorOn);
                repaint();
            }
        }
        else {
            if ((x - 212.5) * (x - 212.5) + (y - (70)) * (y - 70) < 12.5 * 12.5) {
                colorOff = JColorChooser.showDialog(this, null, colorOff);
                light.setColorOff(colorOff);
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 0, 0, 221, 150, this);
        // Desenha um quadrado cheio.
        g.setColor(light.getColor());

        g.fillOval(200, 60, 25, 25);
        //g.fillRect(190, 180, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

