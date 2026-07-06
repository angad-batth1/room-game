package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import javax.swing.JPanel;
import controller.GameStateManager;
import utils.Constants;

/**
 * This is the renderer class that draws the active game state onto the screen.
 * @author Gurangad Batth
 */
public class Renderer extends JPanel{
    private static final long serialVersionUID = 1L;

    // temp instance of theif just to test the screen
    private transient GameStateManager gsm;

    /**
     * This is the constructor for the renderer panel.
     * It enables double buffering for smoother drawing.
     */
    @SuppressWarnings("this-escape")
    public Renderer(){
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
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
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.scale(Constants.WINDOW_SCALE, Constants.WINDOW_SCALE);

        if(gsm != null){
            gsm.render(g2d);
        }

        g2d.dispose();
        Toolkit.getDefaultToolkit().sync();
    }
}
