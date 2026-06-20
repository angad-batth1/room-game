package controller;

import java.awt.Graphics2D;

/**
 * abstract base class representing a game state (screen/view).
 * all the game screens must implement update logic and the render graphics.
 */
public abstract class GameState {
    private GameStateManager gsm;

    /**
     * constructor for GameState
     * @param gsm the game state manager used to transition between states
     */
    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }

    /**
     * updates the logic for the current state
     */
    public abstract void update();
    
    /**
     * render the graphics for the current state
     * @param g2d the Graphics2D object used for drawing
     */
    public abstract void render(Graphics2D g2d);
    
}
