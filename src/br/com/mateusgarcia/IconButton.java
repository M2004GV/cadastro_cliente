package br.com.mateusgarcia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.net.URL;

public class IconButton extends JLabel {
    private static final long serialVersionUID = 1L;
    private ImageIcon originalIcon; // Ícone original
    private ImageIcon scaledIcon;   // Ícone redimensionado
    private Color defaultBackgroundColor; // Cor padrão de fundo do botão

    private int iconWidth = 64;  // Largura desejada do ícone
    private int iconHeight = 64; // Altura desejada do ícone

    public IconButton(String iconPath) {
        // Carregando a imagem do ícone
        URL iconURL = getClass().getClassLoader().getResource(iconPath);
        System.out.println("Icon URL: " + iconURL); // Debugging
        if (iconURL == null) {
            throw new IllegalArgumentException("Caminho de ícone inválido: " + iconPath);
        }
        originalIcon = new ImageIcon(iconURL);
        scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));

        this.setIcon(scaledIcon);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);

        // Definindo cor de fundo padrão
        defaultBackgroundColor = getBackground();
        setOpaque(true); // Para garantir que o fundo seja pintado

        // Definindo tamanho preferido
        setPreferredSize(new Dimension(iconWidth + 20, iconHeight + 20)); // Adiciona padding

        // Adicionando os listeners de mouse para efeitos visuais
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY); // Efeito de hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBackgroundColor); // Volta à cor original
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(Color.GRAY); // Efeito de click
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY); // Volta ao hover ao soltar o click
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Desenhar borda oval
        Graphics2D g2 = (Graphics2D) g.create();
        // Ativar antialiasing para bordas suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Definir a área de recorte para o formato oval
        g2.setClip(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
        // Chamar o método de pintura da superclasse com a área de recorte aplicada
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(iconWidth + 20, iconHeight + 20); // Garantir o tamanho adequado
    }

    @Override
    public boolean contains(int x, int y) {
        // Verifica se o ponto está dentro do oval
        return new Ellipse2D.Double(0, 0, getWidth(), getHeight()).contains(x, y);
    }

    // Método para redimensionar o ícone caso o botão seja redimensionado
    public void resizeIcon(int newWidth, int newHeight) {
        this.iconWidth = newWidth;
        this.iconHeight = newHeight;
        scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH));
        this.setIcon(scaledIcon);
        this.setPreferredSize(new Dimension(iconWidth + 20, iconHeight + 20));
        revalidate();
        repaint();
    }
}
