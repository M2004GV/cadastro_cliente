package br.com.mateusgarcia;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton implements java.io.Serializable {
	
    private static final long serialVersionUID = 1L;
    public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false); // Evita a área padrão do botão
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100); // Tamanho do botão
    }

    @Override
    public boolean contains(int x, int y) {
        return new Ellipse2D.Double(0, 0, getWidth(), getHeight()).contains(x, y);
    }
}
