package controller;

import java.awt.Graphics2D;

/**
 * This is the abstract parent class for every game state in the room game.
 * Each state must know how to update itself and render itself.
 * @author Gurangad Batth
 */
public abstract class GameState {
    private GameStateManager gsm;

    /**
     * This is the constructor for a game state.
     * @param gsm the game state manager that owns this state
     */
    public GameState(GameStateManager gsm){
        this.gsm = gsm;
    }

    /**
     * This method updates the logic of the current state.
     */
    public abstract void update();

    /**
     * This method renders the current state to the screen.
     * @param g2d the graphics object used to draw the state
     */
    public abstract void render(Graphics2D g2d);
}
