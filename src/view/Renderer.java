package view;

import javax.swing.*;
import java.awt.*;

import model.Player;
import utils.Constants;

public class Renderer extends JPanel{
    // temp instance of theif just to test the screen
    private Player placeholderTheif;

    public Renderer(){
        // set background color of the panel to black
        setBackground(Color.BLACK);
        placeholderTheif = new Player(Constants.SCREEN_WIDTH/2.0, 100);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(
            (int) placeholderTheif.getX(),
            (int) placeholderTheif.getY(),
            placeholderTheif.getWidth(),
            placeholderTheif.getHeight()
        );
    }
}