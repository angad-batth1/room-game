package view;

import javax.swing.*;
import java.awt.*;
import controller.GameStateManager;
import model.Player;
import utils.Constants;

public class Renderer extends JPanel{
    // temp instance of theif just to test the screen
    private GameStateManager gsm;

    public Renderer(){
        setDoubleBuffered(true);
    }

    /**
     * Connects the rendering canvas to the state manager
     * @param gsm
     */
    public void setGSM(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g); // first wipe the previouys frame clean
        Graphics2D g2d = (Graphics2D) g;
        if(gsm !=null){ // if the manager is plugged, let it handle drawing.
            gsm.render(g2d);
        }
        /*
        Instead of using the standard Graphics object, for this project,
        it is more beneficial to use Graphics 2D because it extends the original
        Graphics class and gives access to more rendering rools I will need for my assets.
        We can implement Graphics2D by casting it onto graphics.
        - TO DO: Remove unnecessary imports from awt & swing packages post graphics production
        - TO DO: Re-implement Graphics strategy
        */

        /* 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(
            (int) placeholderTheif.getX(),
            (int) placeholderTheif.getY(),
            placeholderTheif.getWidth(),
            placeholderTheif.getHeight()
        );
        */
    }
}