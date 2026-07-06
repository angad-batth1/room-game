package controller;

import java.awt.Graphics2D;
import java.util.Stack;

/**
 * This is the game state manager class that controls the active game state stack.
 * @author Gurangad Batth
 */
public class GameStateManager{

    private Stack<GameState> states;

    /**
     * This is the constructor for the game state manager.
     * It initializes the internal state stack.
     */
    public GameStateManager(){
        states = new Stack<>();
    }

    /**
     * This method pushes a new state onto the stack.
     * @param state the state being added to the top of the stack
     */
    public void pushState(GameState state){
        states.push(state);
    }

    /**
     * This method removes the current state from the top of the stack.
     */
    public void popState(){
        if(!states.isEmpty()){
            states.pop();
        }
    }

    /**
     * This method replaces the current state with a new one.
     * @param state the new state that should become active
     */
    public void setState(GameState state){
        popState();
        pushState(state);
    }

    /**
     * This method updates the state at the top of the stack.
     */
    public void update(){
        if(!states.isEmpty()){
            states.peek().update();
        }
    }

    /**
     * This method renders the state at the top of the stack.
     * @param g2d the graphics object used to draw the active state
     */
    public void render (Graphics2D g2d){
        if(!states.isEmpty()){
            states.peek().render(g2d);
        }
    }
}
