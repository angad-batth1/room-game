package controller;

import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager{
    // This is our stack instance that will manage the state of the game
    private Stack<GameState> states;

    /**
     * Constructor for gamestatemanager
     */
    public GameStateManager(){
        this.states = new Stack<>();
    }

    /**
     * Adds a new screen to the top of the stack
     * an example is pushing a pause menu over the active game
     * @param state the state to be pushed
     */
    public void pushState(GameState state){
        states.push(state);
    }

    /**
     * Removes the current screen, returning to whatever was beneath it
     */
    public void popState(){
        if(!states.isEmpty()){
            states.pop();
        }
    }

    /**
     * Replaces the current screen entirely.
     * an example is switching from the main menu to level 1
     * @param state
     */
    public void setState(GameState state){
        if(!states.isEmpty()){
            states.pop();
        }
        states.push(state);
    }

    /**
     * Updates only the math of the screen at the top of the stack
     */
    public void update(){
        // .peek() looks at the top item without removing it
        if(!states.isEmpty()){
            states.peek().update();
        }
    }

    /**
     * Draws only the graphics of the screen at the top of the stack
     * @param g2d the graphics to be added to stack
     */
    public void render (Graphics2D g2d){
        if(!states.isEmpty()){
            states.peek().render(g2d);
        }
    }
}