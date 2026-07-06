package view;

import javax.swing.*;
import java.awt.*;
import controller.GameStateManager;
import model.Player;
import utils.Constants;

/**
 * This is the renderer class that draws the active game state onto the screen.
 * @author Gurangad Batth
 */
public class Renderer extends JPanel{
    // temp instance of theif just to test the screen
    private GameStateManager gsm;

    /**
     * This is the constructor for the renderer panel.
     * It enables double buffering for smoother drawing.
     */
    public Renderer(){
        setDoubleBuffered(true);
    }

    /**
     * Connects the rendering canvas to the state manager.
     * @param gsm the game state manager that supplies the current state to draw
     */
    public void setGSM(GameStateManager gsm){
        this.gsm = gsm;
    }

    /**
     * This method repaints the screen and delegates drawing to the active game state.
     * @param g the graphics object used by Swing during painting
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if(gsm != null){
            gsm.render(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }
}
