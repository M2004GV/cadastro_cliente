package br.com.mateusgarcia;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TexturedRoundButton extends JButton implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
    private BufferedImage texture;

    public TexturedRoundButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        try {
            texture = ImageIO.read(new File("caminho/para/sua/textura.png")); // Coloque o caminho da sua imagem
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getWidth(), getHeight());
        
        // Desenhar textura
        if (texture != null) {
            g.drawImage(texture, 0, 0, getWidth(), getHeight(), this);
        }
        
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    @Override
    public boolean contains(int x, int y) {
        return new Ellipse2D.Double(0, 0, getWidth(), getHeight()).contains(x, y);
    }
}

