package com.ocr.card.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CardPanel extends JPanel {
    private BufferedImage bufferedImage;

    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(bufferedImage,0,0,this.getWidth(),this.getHeight(),null);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
